package com.example.lenovo.networktools.utils.dbinspector;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;

/**
 * Created by 10129302 on 15-2-15.
 */
public abstract class CursorOperation<T> {

    private File database;

    public CursorOperation(File database) {
        this.database = database;
    }

    public T execute() {
        try {
            SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(database, null);
            Cursor cursor = provideCursor(sqLiteDatabase);
            T result = provideResult(sqLiteDatabase, cursor);
            cursor.close();
            sqLiteDatabase.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public abstract Cursor provideCursor(SQLiteDatabase database);

    public abstract T provideResult(SQLiteDatabase database, Cursor cursor);

}
