package bearpack.k.namegame.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import bearpack.k.namegame.R;
import bearpack.k.namegame.viewmodels.GameData;
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
            Picasso.get()
                    .load("http:".concat(gameData.selectedProfile.getHeadshot().getUrl()))
                    .fit()
                    .placeholder(R.drawable.rotation)
                    .error(R.drawable.error)
                    .into(mPortraitView);
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
        setContentView(R.layout.activity_game_screen_reverse);
        ButterKnife.bind(this);
        gameVM = ViewModelProviders.of(this).get(GameViewModel.class);
        mRecyclerAdapter = new NameRecyclerViewAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mRecyclerAdapter);
        gameVM.getGameDataStream().subscribe(gameDataObserver);
    }
}
