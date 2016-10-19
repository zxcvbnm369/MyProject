package edu.feicui.com.myproject.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import edu.feicui.com.myproject.bean.User;

/**
 * Created by Administrator on 2016/9/1 0001.
 */

public class DBUtils extends SQLiteOpenHelper {
    private static final String TAG = "DBUtils";
    private static final String DB_NAME = "myuser.db";
    private static final String TABLE_NAME = "use";
    private SQLiteDatabase sd;

    public DBUtils(Context context) {
        super(context, DB_NAME, null, 1);
    }

    public DBUtils(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table " + TABLE_NAME + " (uid INTEGER PRIMARY KEY AUTOINCREMENT,uname TEXT,upwd TEXT)";
        sqLiteDatabase.execSQL(sql);
        Log.i(TAG, "onCreate: " + sql);
    }

    /**
     * 检查是否有重名的
     *
     * @param user
     * @return
     */
    public boolean CheckName(User user) {
        sd = getReadableDatabase();
        //select uname from uses where uname = zyl110;
        String sql = "select uname from " + TABLE_NAME + " where uname = ?";
        Cursor c = sd.rawQuery(sql, new String[]{user.getUname()});
        if (c != null && c.getCount() > 0) {
            return true;
        }
        return false;
    }


    /**
     * 插入注册数据
     *
     * @param user
     */
    public void saveData(User user) {
        sd = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("uname", user.getUname());
        values.put("upwd", user.getPwd());
        sd.insert(TABLE_NAME, null, values);
    }

    /**
     * 判断数据库中是否有指定的数据
     *
     * @param user
     * @return
     */
    public boolean login(User user) {
        sd = getReadableDatabase();
        String sql = "select uname,upwd from " + TABLE_NAME + " where uname = ? and upwd = ?";
        Cursor c = sd.rawQuery(sql, new String[]{user.getUname(), user.getPwd()});
        if (c != null && c.getCount() > 0) {
            return true;
        }
        return false;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
