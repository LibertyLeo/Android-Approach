package com.libertyleo.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.libertyleo.criminalintent.database.CrimeBaseHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.libertyleo.criminalintent.database.CrimeCursorWrapper;
import com.libertyleo.criminalintent.database.CrimeDbSchema.CrimeTable;

/**
 * Created by Leo_Lei on 8/17/17.
 */

public class CrimeLab {
    private static CrimeLab sCrimeLab;

    private Context mContext;
    private SQLiteDatabase mDataBase;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mContext = context.getApplicationContext();
        mDataBase = new CrimeBaseHelper(mContext).getWritableDatabase();
    }

    public void addCrime(Crime crime) {
        ContentValues values = getContentValues(crime);
        mDataBase.insert(CrimeTable.NAME, null, values);
    }

    public List<Crime> getCrimes() {
        List<Crime> crimes = new ArrayList<>();

        CrimeCursorWrapper cursor = queryCrimes(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return crimes;
    }

    public Crime getCrime(UUID id) {
        CrimeCursorWrapper cursor = queryCrimes(
                CrimeTable.Columns.UUID + " = ?",
                new String[] { id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getCrime();
        } finally {
            cursor.close();
        }
    }

    public File getPhotoFile(Crime crime) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, crime.getPhotoFilename());
    }

    public void updateCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);

        mDataBase.update(CrimeTable.NAME, values,
                CrimeTable.Columns.UUID + " = ?",
                new String[] { uuidString });
    }

    public CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDataBase.query(
                CrimeTable.NAME,
                null, // Columns - null selectes all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderby
        );
        return new CrimeCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Columns.UUID, crime.getId().toString());
        values.put(CrimeTable.Columns.TITLE, crime.getTitle());
        values.put(CrimeTable.Columns.DATE, crime.getDate().toString());
        values.put(CrimeTable.Columns.SOLVED, crime.isSolved() ? 1 : 0);
        values.put(CrimeTable.Columns.SUSPECT, crime.getSuspect());
        return values;
    }
}
