package bearpack.k.namegame.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import bearpack.k.namegame.R;
import bearpack.k.namegame.db.SQLiteHelper;
import bearpack.k.namegame.viewmodels.GameModeViewModel;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectGameModeActivity extends AppCompatActivity {

    @BindView(R.id.activity_select_game_mode_easy_button) Button easyBtn;
    @BindView(R.id.activity_select_game_mode_matt_button) Button mattBtn;
    @BindView(R.id.activity_select_game_mode_normal_button) Button normalBtn;
    @BindView(R.id.activity_select_game_mode_reverse_button) Button reverseBtn;
    @BindView(R.id.activity_select_game_mode_text) TextView gameModeText;
    @BindView(R.id.activity_select_game_mode_statistics_button) ImageButton statsBtn;

    GameModeViewModel gameModeVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_game_mode);
        ButterKnife.bind(this);
        gameModeVM = ViewModelProviders.of(this).get(GameModeViewModel.class);
        gameModeVM.dbHelper = new SQLiteHelper(this);
        //Setup ViewModel Interactions, mainly the callback to start the game and each button click
        gameModeVM.setClickListener((activity,bundle) -> {
            Intent intent = new Intent(this,activity);
            intent.putExtra("gameMode",bundle);
            startActivity(intent);

        });

        easyBtn.setOnClickListener(v -> gameModeVM.easyModeClicked());

        normalBtn.setOnClickListener( v-> gameModeVM.normalModeClicked());

        reverseBtn.setOnClickListener(v -> gameModeVM.reverseModeClicked());

        mattBtn.setOnClickListener(v -> gameModeVM.mattModeClicked());

        statsBtn.setOnClickListener(v -> {
            //Get stats from data source
            Map<String,String> statMap = gameModeVM.getPlayStats();

            //convert to alert dialog friendly list
            StringBuilder stringBuilder = new StringBuilder();
            String[] items = new String[statMap.size()];
            int i=0;
            for(String key : statMap.keySet())
            {
                stringBuilder.delete(0,stringBuilder.capacity());
                stringBuilder.append(key);
                stringBuilder.append(": ");
                stringBuilder.append(statMap.get(key));
                items[i] = stringBuilder.toString();
                i++;
            }

            //create dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(SelectGameModeActivity.this);
            builder.setTitle("Statistics")
                    .setPositiveButton("Ok", (dialog, which) -> dialog.dismiss())
                    .setItems(items,null);

            AlertDialog dialog = builder.create();
            dialog.show();
        });

    }
}
