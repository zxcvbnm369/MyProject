package edu.feicui.com.myproject.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.feicui.com.myproject.bean.News;

/**
 * Created by Administrator on 2016/8/31 0031.
 * 数据库的管理类
 */
public class DBTools extends SQLiteOpenHelper {
    private static final String TAG = "DBTools";
    private static final String DB_NAME = "news.db";
    private static final String TABLE_NAME = "new";
    private SQLiteDatabase sd;

    /**
     * context属于一个上下文的参数
     * name，属于数据库的名字
     * CursorFactory 给null
     * version 1
     * 当实例化DBTools的时候。执行完构造器的时候，数据库已经建立出来
     *
     * @param context
     */
    public DBTools(Context context) {
        super(context, DB_NAME, null, 1);
        Log.i(TAG, "DBTools: 数据库创建成功！！！");
    }


    public DBTools(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table " + TABLE_NAME + " (uid INTEGER PRIMARY KEY AUTOINCREMENT,icon TEXT,title TEXT,summary TEXT,stamp TEXT )";
        sqLiteDatabase.execSQL(sql);
        Log.i(TAG, "onCreate: " + sql);
    }


    public void saveData(News news) {
        sd = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("icon", news.getIcon());
        values.put("title", news.getTitle());
        values.put("summary", news.getSummary());
        values.put("stamp", news.getStamp());
        sd.insert(TABLE_NAME, null, values);
    }

    public List<News> findAll() {
        sd = getReadableDatabase();
        List<News> list = null;
        Cursor c = sd.query(TABLE_NAME, null, null, null, null, null, null);
        if (c != null) {
            list = new ArrayList<News>();
            while (c.moveToNext()) {
                News news = new News(c.getString(1), c.getString(2), c.getString(3), c.getString(4));
                list.add(news);
            }
        }
        return list;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
