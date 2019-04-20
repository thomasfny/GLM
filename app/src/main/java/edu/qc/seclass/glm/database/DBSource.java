package edu.qc.seclass.glm.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBSource {
    private Context mContext;
    private SQLiteDatabase membDatabase;
    SQLiteOpenHelper mDBHelper;

    public DBSource(Context context) {
        this.mContext = context;
        mDBHelper = new DBHelper(mContext);
        membDatabase = mDBHelper.getWritableDatabase();
    }

    public void open(){
        membDatabase = mDBHelper.getWritableDatabase();
    }

    public void close(){
        mDBHelper.close();
    }
}
