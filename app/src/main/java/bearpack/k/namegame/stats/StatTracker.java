package bearpack.k.namegame.stats;

import android.content.Context;

import java.util.Timer;

import bearpack.k.namegame.db.SQLiteHelper;

/**
 * Created by Shiva on 4/10/2018.
 */

public class StatTracker
{
    private static StatTracker instance;

    private SQLiteHelper dbHelper;

    long currentRoundStartTime = -1L;



    private StatTracker(Context context)
    {
        dbHelper = new SQLiteHelper(context);
    }

    public static synchronized StatTracker getInstance(Context context)
    {
        if(instance == null){
            instance = new StatTracker(context);
        }
        return instance;
    }

    public void startRound()
    {
        currentRoundStartTime = System.currentTimeMillis();
    }

    public void endRound(boolean success)
    {
        if(currentRoundStartTime == -1L)
        {
            return;
        }
        long endTime = System.currentTimeMillis();
        float roundDuration = (float) ((endTime - currentRoundStartTime)/1000.0);
        currentRoundStartTime = -1L;
        dbHelper.insertData(success,roundDuration);
    }

}
