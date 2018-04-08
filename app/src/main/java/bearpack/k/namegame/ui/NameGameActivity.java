package bearpack.k.namegame.ui;

import android.arch.lifecycle.ViewModelProviders;
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

public class NameGameActivity extends AppCompatActivity {

    @BindView(R.id.activity_game_screen_normal_list) RecyclerView mRecyclerView;
    @BindView(R.id.activity_game_screen_text) TextView mNameTextView;
    GameViewModel gameVM;
    PortraitRecyclerViewAdapter mRecyclerAdapter;
    GameData currentGameData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen_normal);
        ButterKnife.bind(this);
        gameVM = ViewModelProviders.of(this).get(GameViewModel.class);
        mRecyclerAdapter = new PortraitRecyclerViewAdapter();
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setAdapter(mRecyclerAdapter);
        currentGameData = gameVM.getGameSetup();
        mRecyclerAdapter.setListItems(currentGameData.dataList);
        mNameTextView.setText(currentGameData.selectedProfile.getFirstName() + " " +currentGameData.selectedProfile.getLastName());

    }


}
