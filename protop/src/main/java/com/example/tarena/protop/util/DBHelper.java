package com.example.tarena.protop.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 作者：Linqiang
 * 时间：2016/5/18:20:24
 * 邮箱: linqiang2010@outlook.com
 * 说明：
 */
public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper instance;

    public static DBHelper getInstance(Context context){
       instance= new DBHelper(context);
        return  instance;
    }
    public DBHelper(Context context) {
        super(context, "news.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * _id : 57334c9d67765903fb61c418
         * createdAt : 2016-05-11T23:15:41.98Z
         * desc : 还在用ListView？
         * publishedAt : 2016-05-12T12:04:43.857Z
         * source : web
         * type : Android
         * url : http://www.jianshu.com/p/a92955be0a3e
         * used : true
         * who : 陈宇明
         */
        //Android表
        String sql = "create table tb_android(_id integer primary key autoincrement,wid text ,createdAt integer not null ," +
                "desc text not null, publishedAt integer not null,source text ,type text ,url text,used integer ,who text,read integer,fav integer )";
        db.execSQL(sql);
        //ios表
        String sql2 = "create table tb_ios(_id integer primary key autoincrement,wid text ,createdAt integer not null ," +
                "desc text not null, publishedAt integer not null,source text ,type text ,url text,used integer ,who text ,read integer,fav integer)";
        db.execSQL(sql2);
        //web表
        String sql3 = "create table tb_web(_id integer primary key autoincrement,wid text ,createdAt integer not null ," +
                "desc text not null, publishedAt integer not null,source text ,type text ,url text,used integer ,who text,read integer ,fav integer)";
        db.execSQL(sql3);
        //tools表
        String sql4 = "create table tb_tools(_id integer primary key autoincrement,wid text ,createdAt integer not null ," +
                "desc text not null, publishedAt integer not null,source text ,type text ,url text,used integer ,who text ,read integer,fav integer)";
        db.execSQL(sql4);
        //已阅读过的文章
        String sql5 = "create table tb_read(_id integer primary key autoincrement,wid text ,createdAt integer not null ," +
                "desc text not null, publishedAt integer not null,source text ,type text ,url text,used integer ,who text ,read integer,fav integer)";
        db.execSQL(sql5);
        //我的收藏
        String sql6 = "create table tb_favo(_id integer primary key autoincrement,wid text ,createdAt integer not null ," +
                "desc text not null, publishedAt integer not null,source text ,type text ,url text,used integer ,who text ,read integer,fav integer)";
        db.execSQL(sql6);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
