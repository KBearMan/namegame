package bearpack.k.namegame.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import bearpack.k.namegame.core.ListRandomizer;
import bearpack.k.namegame.model.GameData;
import bearpack.k.namegame.model.GameMode;
import bearpack.k.namegame.network.api.ApiService;
import bearpack.k.namegame.network.api.ApiUtil;
import bearpack.k.namegame.model.Profile;
import bearpack.k.namegame.stats.StatTracker;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by KBearMan on 4/3/2018.
 */

public class GameViewModel extends ViewModel
{
    private static final String TAG = GameViewModel.class.getName();
    private final int easyModeTimeOut = 3000; // in milliseconds

    private GameMode gameMode;
    private ListRandomizer randomizer = new ListRandomizer(new Random());
    private ArrayList<Profile> mDataList = new ArrayList<>();
    private GameData currentGameData;
    private PublishSubject<GameData> gameDataStream;
    private StatTracker statTracker;
    private GameResultsListener listener;

    private Timer easyModeTimer = new Timer();
    private TimerTask easyModeListDeleterTask = new TimerTask()
    {
        @Override
        public void run()
        {
            Random random = new Random();
            int answerLocation = currentGameData.dataList.indexOf(currentGameData.selectedProfile);
            int deletion = answerLocation;

            while(deletion == answerLocation)
            {
                deletion = random.nextInt(currentGameData.dataList.size() - 1);
            }
            currentGameData.dataList.remove(deletion);
            listener.personRemoved(deletion);

        }
    };


    public interface GameResultsListener
    {
        void answerResult(boolean correctAnswerSelected);
        void personRemoved(int position);
    }

    public void setListener(GameResultsListener listener)
    {
        this.listener = listener;
    }


    public void setStatTracker(StatTracker tracker)
    {
        statTracker = tracker;
    }

    public void setGameMode(Serializable gameMode)
    {
        if(this.gameMode == gameMode)
        {
            //Game already in progress, no need to refresh
            return;
        }
        this.gameMode = (GameMode) gameMode;
        mDataList.clear();
        ApiService mService = ApiUtil.getApiService();
        mService.getProfiles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(profiles -> Observable.fromIterable(profiles))
                .filter(profile ->
                {
                    if (gameMode.equals(GameMode.Matt))
                    {
                        return profile.getFirstName().toLowerCase().startsWith("mat");
                    }
                    else
                    {
                        return true;
                    }
                })
                .subscribe(new Observer<Profile>()
                    {
                        @Override
                        public void onSubscribe(Disposable d)
                        {

                        }

                        @Override
                        public void onNext(Profile profile)
                        {
                            mDataList.add(profile);
                        }

                        @Override
                        public void onError(Throwable e)
                        {
                            Log.e(TAG,"getting remote profiles onError:" + e.toString());
                        }

                        @Override
                        public void onComplete()
                    {
                        getNewGameData();
                    }
                    });

    }

    public GameData getCurrentGameData()
    {
        return currentGameData;
    }

    public Observable<GameData> getGameDataStream()
    {
        return gameDataStream;
    }

    public GameViewModel()
    {
        gameDataStream = PublishSubject.create();
        gameDataStream.subscribe(new Observer<GameData>()
        {
            @Override
            public void onSubscribe(Disposable d)
            {

            }

            @Override
            public void onNext(GameData gameData)
            {
                startGame();
            }

            @Override
            public void onError(Throwable e)
            {
                Log.e(TAG,"gameDataStream onError:" + e.toString());
            }

            @Override
            public void onComplete()
            {

            }
        });
    }

    public void getNewGameData()
    {
        GameData gameSetup = new GameData();
        gameSetup.dataList = randomizer.pickN(mDataList,6);
        gameSetup.selectedProfile = randomizer.pickOne(gameSetup.dataList);
        currentGameData = gameSetup;
        gameDataStream.onNext(currentGameData);
    }

    private void startGame()
    {
        statTracker.startRound();
        easyModeTimer.purge();
        easyModeTimer.schedule(easyModeListDeleterTask,0,3000);
    }

    public void profileSelected(Profile profile)
    {
        boolean answer = currentGameData.selectedProfile.getId().equals(profile.getId());
        statTracker.endRound(answer);
        listener.answerResult(answer);
        easyModeTimer.purge();
    }

}
