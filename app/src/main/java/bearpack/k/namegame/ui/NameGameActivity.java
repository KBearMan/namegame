package bearpack.k.namegame.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import bearpack.k.namegame.R;
import bearpack.k.namegame.model.GameData;
import bearpack.k.namegame.model.Profile;
import bearpack.k.namegame.viewmodels.GameViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class NameGameActivity extends AppCompatActivity {

    @BindView(R.id.activity_game_screen_normal_list) RecyclerView mRecyclerView;
    @BindView(R.id.activity_game_screen_text) TextView mNameTextView;
    GameViewModel gameVM;
    PortraitRecyclerViewAdapter mRecyclerAdapter;
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
            AlertDialog.Builder builder = new AlertDialog.Builder(NameGameActivity.this);
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
    };

    PortraitRecyclerViewAdapter.OnPortraitClickedListener mPortraitListener = new PortraitRecyclerViewAdapter.OnPortraitClickedListener()
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
        setContentView(R.layout.activity_game_screen_normal);
        ButterKnife.bind(this);

        gameVM = ViewModelProviders.of(this).get(GameViewModel.class);
        gameVM.setListener(listener);

        mRecyclerAdapter = new PortraitRecyclerViewAdapter(this,mPortraitListener );

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }
        else
        {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
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
            mNameTextView.setText(currentGameData.selectedProfile.getFirstName() + " " + currentGameData.selectedProfile.getLastName());
            mRecyclerAdapter.setListItems(currentGameData.dataList);
        }
    }

}
