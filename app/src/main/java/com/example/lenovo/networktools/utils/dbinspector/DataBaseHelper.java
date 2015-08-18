package com.example.lenovo.networktools.utils.dbinspector;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.example.lenovo.networktools.utils.DeviceUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by 10129302 on 15-2-15.
 */
public class DataBaseHelper {

    public static final String COLUMN_NAME = "name";
    public static final String TABLE_LIST_QUERY = "SELECT name FROM sqlite_master WHERE type='table'";

    public static final String PRAGMA_FORMAT = "PRAGMA table_info(%s)";

    /**
     * 获取该context相关的所有数据库文件
     * @param dir 数据库所在的路径
     * @return
     */
    public static List<File> getDatabaseList(String dir) {
        List<File> databaseList = new ArrayList<>();

        File sqliteDir = new File(dir);
        FilenameFilter filenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".sql") ||
                        filename.endsWith(".sqlite") ||
                        filename.endsWith(".db") ||
                        filename.endsWith(".cblite");
            }
        };

        File[] sqliteFiles = sqliteDir.listFiles(filenameFilter);
        if (sqliteFiles != null) {
            databaseList.addAll(Arrays.asList(sqliteFiles));
        } else {
            Log.d("", "### no database file");
        }

        return databaseList;
    }

    /**
     * 获取当前目录下所有的数据表
     * @param database
     * @return
     */
    public static List<String> getAllTables(File database) {
        CursorOperation<List<String>> operation = new CursorOperation<List<String>>(database) {

            @Override
            public Cursor provideCursor(SQLiteDatabase database) {
                return database.rawQuery(TABLE_LIST_QUERY, null);
            }

            @Override
            public List<String> provideResult(SQLiteDatabase database, Cursor cursor) {
                List<String> tableList = new ArrayList<String>();
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        tableList.add(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                        cursor.moveToNext();
                    }
                }
                return tableList;
            }
        };

        return operation.execute();
    }

    public static List<File>  getListFile(File dir) {
        List<File> fileList = new ArrayList<>();

        FilenameFilter filenameFilter = getFileFilter();
        File[] sqliteFiles = dir.listFiles(filenameFilter);
        if (sqliteFiles != null) {
            fileList.addAll(Arrays.asList(sqliteFiles));
        } else {
            Log.d("", "### no database file");
        }

        Collections.sort(fileList); //按照第一个字母升序排列, 大写在前面

        return fileList;
    }

    private static FilenameFilter getFileFilter() {
        return new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                if(dir.getAbsolutePath().equals("/")) {
                    if(DeviceUtils.hasExternalStorage()) {
                        return !filename.startsWith(".") &&
                                (Environment.getExternalStorageDirectory().getAbsolutePath().contains("/" + filename) ||
                                        filename.startsWith("data"));
                    } else {
                        return !filename.startsWith(".") &&
                                (filename.startsWith("data"));
                    }
                } else if(dir.getAbsolutePath().equals("/data")) {
                    return !filename.startsWith(".") &&
                            (filename.startsWith("data"));
                } else {
                    return !filename.startsWith(".");
                }
            }
        };
    }

}
