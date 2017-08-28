package com.libertyleo.criminalintent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.libertyleo.criminalintent.database.CrimeDbSchema.CrimeTable;

/**
 * Created by Leo_Lei on 8/28/17.
 */

public class CrimeBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "crimeBase.db";

    public CrimeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + CrimeTable.NAME + "(" +
                               " _id integer primary key autoincrement, " +
                               CrimeTable.Columns.UUID + ", " +
                               CrimeTable.Columns.TITLE + ", " +
                               CrimeTable.Columns.DATE + ", " +
                               CrimeTable.Columns.SOLVED + ", " +
                               CrimeTable.Columns.SUSPECT + ", " +
                               CrimeTable.Columns.NUMBER +
                               ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
