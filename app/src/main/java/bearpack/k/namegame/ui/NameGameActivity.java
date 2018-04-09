package bearpack.k.namegame.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import bearpack.k.namegame.R;
import bearpack.k.namegame.viewmodels.GameData;
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
            mNameTextView.setText(currentGameData.selectedProfile.getFirstName() + " " +currentGameData.selectedProfile.getLastName());
            mRecyclerAdapter.setListItems(currentGameData.dataList);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen_normal);
        ButterKnife.bind(this);
        gameVM = ViewModelProviders.of(this).get(GameViewModel.class);
        mRecyclerAdapter = new PortraitRecyclerViewAdapter(this);
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
    }


}
