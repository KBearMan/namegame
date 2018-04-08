package bearpack.k.namegame.viewmodels;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import bearpack.k.namegame.core.ListRandomizer;
import bearpack.k.namegame.network.api.ApiService;
import bearpack.k.namegame.network.api.ApiUtil;
import bearpack.k.namegame.model.Profile;
import bearpack.k.namegame.network.api.RetrofitClient;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by A on 4/3/2018.
 */

public class GameViewModel extends ViewModel
{
    RetrofitClient client;
    ListRandomizer randomizer = new ListRandomizer(new Random());
    ArrayList<Profile> mDataList = new ArrayList<>();
    public GameViewModel()
    {
        ApiService mService = ApiUtil.getApiService();
        mService.getProfiles()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<List<Profile>>()
                                {
                                    @Override
                                    public void onSubscribe(Disposable d)
                                    {

                                    }

                                    @Override
                                    public void onNext(List<Profile> profiles)
                                    {

                                    }

                                    @Override
                                    public void onError(Throwable e)
                                    {

                                    }

                                    @Override
                                    public void onComplete()
                                    {

                                    }
                                });
    }

    public GameData getGameSetup()
    {
        GameData gameSetup = new GameData();
        gameSetup.dataList = randomizer.pickN(mDataList,6);
        gameSetup.selectedProfile = randomizer.pickOne(gameSetup.dataList);
        return gameSetup;
    }
}
