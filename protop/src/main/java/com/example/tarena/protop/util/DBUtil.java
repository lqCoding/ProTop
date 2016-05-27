package com.example.tarena.protop.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tarena.protop.Bean.News;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Linqiang
 * 时间：2016/5/18:20:44
 * 邮箱: linqiang2010@outlook.com
 * 说明：数据库操作类 提供文章列表更新到数据库,数据库查询所有文章的功能
 */
public class DBUtil {
    Context context ;
    DBHelper dbHelper ;
    public DBUtil(Context context) {
        this.context = context;
        dbHelper = DBHelper.getInstance(context);
    }

    /**
     * 清空数据库
     * @param table
     * @return
     */
    public  int clearTable(String table) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int num=db.delete("tb_android", null, null);
        int num2= db.delete("tb_ios", null, null);
        int num3= db.delete("tb_web", null, null);
        int num4= db.delete("tb_tools", null, null);
        int num5=db.delete("tb_read",null,null);
      /*  db.execSQL("drop table tb_android");
        db.execSQL("drop table tb_ios");
        db.execSQL("drop table tb_web");
        db.execSQL("drop table tb_tools");*/
        return num+num2+num3+num4+num5;
    }

    /**
     * 更新数据到数据库
     * @param table
     * @param n
     * @return
     */
    public  long addNews(String table,News n){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("wid", n.get_id());
        values.put("createdAt", n.getCreatedAt());
        values.put("desc", n.getDesc());
        values.put("publishedAt", n.getPublishedAt());
        values.put("source",n.getSource());
        values.put("type",n.getType());
        values.put("url",n.getUrl());
        int used=(n.isUsed()?1:2);
        values.put("used",used);
        values.put("who", n.getWho());
        int read=(n.isRead()?1:2);
        values.put("read",read);
        int fav=(n.isRead()?1:2);
        values.put("fav",read);
        long id=  db.insert(table,null,values);
        return  id;
    }

    /**
     * 更新阅读状态
     * @param table
     * @param url
     * @return
     */
    public int updateUsed(String table,String url){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("read",1);
       return db.update(table,values,"url =?",new String[]{url});

    }
    /**
     * 根据类型从数据库中获取文章
     * @param table
     * @return
     */
    public List<News> getNewsFromDB(String table){
        SQLiteDatabase	db=dbHelper.getReadableDatabase();
        Cursor cursor= db.query(table, null, null, null, null, null, "publishedAt DESC");
        List<News> list=new ArrayList<News>();
        while(cursor.moveToNext()){
           News n = new News();
           n.set_id(cursor.getString(1));
            n.setCreatedAt(cursor.getString(2));
            n.setDesc(cursor.getString(3));
            n.setPublishedAt(cursor.getString(4));
            n.setSource(cursor.getString(5));
            n.setType(cursor.getString(6));
            n.setUrl(cursor.getString(7));
            n.setUsed(cursor.getInt(8) == 1 ? true : false);
            n.setWho(cursor.getString(9));
            n.setRead(cursor.getInt(10) == 1 ? true : false);
            n.setFav(cursor.getInt(11)==1?true:false);
            n.setRefresh(false);
            list.add(n);
        }
        cursor.close();
        return list;

    }

    /**
     * 将已经阅读的文章添加到数据库中
     * @param tb_read
     * @param n
     * @return
     */
    public long addReadTable(String tb_read, News n) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("wid", n.get_id());
        values.put("createdAt", n.getCreatedAt());
        values.put("desc", n.getDesc());
        values.put("publishedAt", n.getPublishedAt());
        values.put("source",n.getSource());
        values.put("type",n.getType());
        values.put("url",n.getUrl());
        int used=(n.isUsed()?1:2);
        values.put("used",used);
        values.put("who", n.getWho());
        values.put("read", 1);
        long id=  db.insert(tb_read,null,values);
        return  id;

    }

    /**
     * 修改收藏属性
     * @param tb_android
     */
    public void updateFavo(String tb_android,String url) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("fav",1);
        db.update(tb_android, values, "url =?", new String[]{url});

    }
}
