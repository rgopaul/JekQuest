package com.jarz.game.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jarz.game.Database.Scores;

import java.util.ArrayList;

/**
 * Created by Margarita on 12/9/2017.
 */

/*
* Our Database Class which Manages an SQLite database
* Stores All Scores Throughout Multiple Playthroughs
* */
public class DataBaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "platformerDB";
    private static final int DATABASE_VERSION = 12;
    private static final String TABLE_SCORES = "scores";
    private static final String NAME = "name";
    private static final String SCORE = "score";

    public DataBaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        // build sql create statement
        String sqlCreate = "create table " + TABLE_SCORES + "( " + NAME;
        sqlCreate += " text, " + SCORE + " real )";

        db.execSQL(sqlCreate);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old table if it exists
        db.execSQL("drop table if exists " + TABLE_SCORES);
        // Re-create tables
        onCreate(db);
    }

    //insert method
    public void submitScore( String name, int score) {
        //Implement this-- first get writeable database and then create a string that
        //inserts into an sqLite database and then actually execute that command
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlinsert = "insert into " + TABLE_SCORES;
        sqlinsert += " values( '" + name;
        sqlinsert += "', '" + score+ "' )";
        db.execSQL(sqlinsert);
        db.close();

    }

    //Selects the Top 10 Scores from the Database
    public ArrayList<Scores> selectAll() {
        SQLiteDatabase db = this.getWritableDatabase();

        String sqlselect = "select * from " + TABLE_SCORES + " ORDER BY SCORE DESC LIMIT 10 " ;

        Cursor cursor = db.rawQuery(sqlselect, null);
        ArrayList<Scores> books = new ArrayList<>();
        while (cursor.moveToNext()) {
            Scores current = new Scores(
                    cursor.getString(0), cursor.getInt(1));
            books.add(current);
        }
        return books;

    }
}
