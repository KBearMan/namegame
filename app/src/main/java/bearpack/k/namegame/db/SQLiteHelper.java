package bearpack.k.namegame.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Shiva on 4/3/2018.
 */

public class SQLiteHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "NameGame.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "GuessStats";
    public static final String COL1= "ID";
    public static final String COL2 = "CORRECT";
    public static final String COL3 = "TIME";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,"+COL2+" BOOL,"+ COL3+" REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public void insertData(boolean correct, float timeToAnswer){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, correct);
        contentValues.put(COL3, timeToAnswer);
        db.insert(TABLE_NAME, null, contentValues);
    }

    //Get all
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor results = db.rawQuery("select * from "+ TABLE_NAME, null);
        return results;
    }

    public int getIncorrectAnswerCount()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor results = db.rawQuery("select " + COL2 + " from "+ TABLE_NAME + " WHERE " + COL2 + " = false", null);
        return results.getCount();
    }

    public int getCorrectAnswerCount()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor results = db.rawQuery("select " + COL2 + " from "+ TABLE_NAME + " WHERE " + COL2 + " = true", null);
        return results.getCount();
    }

    public List<Boolean> getAllAnswers()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor results = db.rawQuery("select " + COL2 + " from "+ TABLE_NAME, null);
        List<Boolean> tempList = new ArrayList<>();
        while(results.moveToNext())
        {
           tempList.add(Boolean.valueOf(results.getString(0)));
        }
        return tempList;
    }

    public Float getAverageTime()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor results = db.rawQuery("select avg(" + COL3 + ") from "+ TABLE_NAME, null);
        return results.getFloat(0);
    }

    public List<Float> getAllTimes()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor results = db.rawQuery("select " + COL3 + " from "+ TABLE_NAME, null);
        List<Float> tempList = new ArrayList<>();
        while(results.moveToNext())
        {
            tempList.add(results.getFloat(0));
        }
        return tempList;
    }

    public Float getShortestTime()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor results = db.rawQuery("select min(" + COL3 + ") from "+ TABLE_NAME, null);
        return results.getFloat(0);
    }

    public Float getLongestTime()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor results = db.rawQuery("select max(" + COL3 + ") from "+ TABLE_NAME, null);
        return results.getFloat(0);
    }
}
