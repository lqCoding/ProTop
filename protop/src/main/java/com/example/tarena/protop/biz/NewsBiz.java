package com.example.tarena.protop.biz;

import android.content.Context;

import com.example.tarena.protop.Bean.News;
import com.example.tarena.protop.listener.OnLoadFinishListener;
import com.example.tarena.protop.listener.RefreshListener;
import com.example.tarena.protop.util.DBUtil;
import com.example.tarena.protop.util.DownLoad;

import java.util.List;

/**
 * 作者：Linqiang
 * 时间：2016/5/19:12:46
 * 邮箱: linqiang2010@outlook.com
 * 说明：获取文章数据业务类
 */
public class NewsBiz {
    Context context;
    DBUtil util;
    public NewsBiz(Context context) {
        this.context=context;
        util=new DBUtil(context);
    }

    /**
     * 删除列表缓存数据库
     * @param table
     * @return
     */
    public  int clearDB(String table){
       return util.clearTable(table);
    }

    /**
     * 更新状态 是否已阅读过
     * @param table
     * @param url
     * @return
     */
    public int updateRead(String table, String url) {
        return util.updateUsed(table, url);
    }
    /**
     * 从数据库中获取所有数据
     * @param table
     * @return
     */
    public List<News> getNewsFromDB(String table){
        return util.getNewsFromDB(table);
    }
    /**
     * 从数据库中获取所有的数据并更新到数据库
     */
    public void getNewsFromNet(String type,int count,int page,OnLoadFinishListener listener){
         DownLoad.downLoad(type,count,page,listener);
    }
    /**
     * 从网络获取最新更新数据
     */
    public void getNewsFromNetNew(String type,String year,String mouth,String day,RefreshListener listener ){
      DownLoad.downLoad2(type, year, mouth, day, listener);
    }

    /**
     * 将刷新的数据存储到数据库当中
     * @param news
     */
    public void addRefreshToDB(String table,News news) {
            util.addNews(table, news);
    }

    /**
     * 将已点击的文章更新到已阅读的文章列表中
     * @param tb_read
     * @param news
     * @return
     */
    public long  updateReadTable(String tb_read, News news) {
        return util.addReadTable(tb_read, news);
    }

    /**
     * 将已收藏的文章添加到数据库
     * @param tb_favo
     * @param news
     */
    public void addFavTable(String tb_favo, News news) {
        util.addNews(tb_favo,news);
    }

    /**
     * 更新数据库中的收藏属性
     * @param tb_android
     * @param url
     */
    public void updateFavo(String tb_android, String url) {
        util.updateFavo(tb_android,url);
    }
}
