package bearpack.k.namegame.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import bearpack.k.namegame.db.SQLiteHelper;
import bearpack.k.namegame.model.GameMode;
import bearpack.k.namegame.ui.NameGameActivity;
import bearpack.k.namegame.ui.NameGameReverseActivity;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by KBearMan on 4/3/2018.
 */

public class GameModeViewModel extends ViewModel
{
    private static final String TAG = GameModeViewModel.class.getSimpleName();
    public SQLiteHelper dbHelper;
    GameModeClickListener listener;

    public void setClickListener(GameModeClickListener listener)
    {
        this.listener = listener;
    }

    public interface GameModeClickListener
    {
        void startGameActivity(Class activityToStart,Bundle bundle);
    }

    public Map<String,String> getPlayStats()
    {
        HashMap<String,String> statMap = new HashMap<>();
        try
        {
            statMap.put("Total Guesses", String.valueOf(dbHelper.getAllAnswers().size()));
            statMap.put("Correct Guesses", String.valueOf(dbHelper.getCorrectAnswerCount()));
            statMap.put("Incorrect Guesses", String.valueOf(dbHelper.getIncorrectAnswerCount()));
            statMap.put("Shortest Guess Time", String.format("%.2f",dbHelper.getShortestTime()) + " Seconds");
            statMap.put("Average Guess Time", String.format("%.2f",dbHelper.getAverageTime()) + " Seconds");
            statMap.put("Longest Guess Time", String.format("%.2f",dbHelper.getLongestTime()) + " Seconds");
        }
        catch(NullPointerException e)
        {
            Log.e(TAG,e.toString());
            statMap.put("Error"," retrieving stats");

        }

        return statMap;
    }

    public void normalModeClicked()
    {
        Bundle bundle = new Bundle();
        bundle.putSerializable("GameMode", GameMode.Normal);
        listener.startGameActivity(NameGameActivity.class,bundle);
    }
    public void easyModeClicked()
    {
        Bundle bundle = new Bundle();
        bundle.putSerializable("GameMode", GameMode.Easy);
        listener.startGameActivity(NameGameActivity.class,bundle);
    }
    public void reverseModeClicked()
    {
        Bundle bundle = new Bundle();
        bundle.putSerializable("GameMode", GameMode.Normal);
        listener.startGameActivity(NameGameReverseActivity.class,bundle);
    }
    public void mattModeClicked()
    {
        Bundle bundle = new Bundle();
        bundle.putSerializable("GameMode", GameMode.Matt);
        listener.startGameActivity(NameGameActivity.class,bundle);
    }

}
