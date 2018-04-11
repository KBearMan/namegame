package bearpack.k.namegame.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import bearpack.k.namegame.R;
import bearpack.k.namegame.model.GameData;
import bearpack.k.namegame.model.Profile;
import bearpack.k.namegame.stats.StatTracker;
import bearpack.k.namegame.viewmodels.GameViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class NameGameReverseActivity extends AppCompatActivity {

    @BindView(R.id.activity_game_screen_reverse_list)RecyclerView mRecyclerView;
    @BindView(R.id.activity_game_screen_reverse_image)ImageView mPortraitView;
    GameViewModel gameVM;
    NameRecyclerViewAdapter mRecyclerAdapter;
    GameData currentGameData;
    Observer<GameData> gameDataObserver = new Observer<GameData>()
    {
        @Override
        public void onSubscribe(Disposable d)
        {

        }

        @Override
        public void onNext(GameData gameData)
        {
            currentGameData = gameData;
            applyGameData();
        }

        @Override
        public void onError(Throwable e)
        {

        }

        @Override
        public void onComplete()
        {

        }
    };

    GameViewModel.GameResultsListener listener = new GameViewModel.GameResultsListener()
    {
        @Override
        public void answerResult(boolean correctAnswerSelected)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(NameGameReverseActivity.this);
            builder.setTitle(R.string.name_game_dialog_answer_title);

            if(correctAnswerSelected)
            {
                builder.setMessage(R.string.correct_answer_dialog_message);
            }
            else
            {
                builder.setMessage(R.string.incorrect_answer_dialog_message);
            }
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener()
            {
                @Override
                public void onDismiss(DialogInterface dialog)
                {
                    gameVM.getNewGameData();
                }
            });
        }

        @Override
        public void personRemoved(int position)
        {
            //Should never get here but just in case.
            runOnUiThread(() -> mRecyclerAdapter.removeItem(position));
        }
    };

    NameRecyclerViewAdapter.OnNameClickedListener mNameListener = new NameRecyclerViewAdapter.OnNameClickedListener()
    {
        @Override
        public void onItemClicked(Profile clickedProfile)
        {
            gameVM.profileSelected(clickedProfile);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen_reverse);
        ButterKnife.bind(this);

        gameVM = ViewModelProviders.of(this).get(GameViewModel.class);
        gameVM.setGameMode(getIntent().getBundleExtra("gameModeBundle").getSerializable("GameMode"));
        gameVM.setListener(listener);
        gameVM.setStatTracker(StatTracker.getInstance(this));
        mRecyclerAdapter = new NameRecyclerViewAdapter(this, mNameListener);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        else
        {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        mRecyclerView.setAdapter(mRecyclerAdapter);

        gameVM.getGameDataStream().subscribe(gameDataObserver);
        currentGameData = gameVM.getCurrentGameData();
        applyGameData();
    }
    private void applyGameData()
    {
        if(currentGameData != null)
        {
            Picasso.get()
                    .load("http:".concat(currentGameData.selectedProfile.getHeadshot().getUrl()))
                    .fit()
                    .placeholder(R.drawable.rotation)
                    .error(R.drawable.error)
                    .into(mPortraitView);
            mRecyclerAdapter.setListItems(currentGameData.dataList);
        }
    }

}
