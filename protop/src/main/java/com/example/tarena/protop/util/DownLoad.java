package com.example.tarena.protop.util;

import android.os.AsyncTask;
import android.util.Log;

import com.example.tarena.protop.Bean.News;
import com.example.tarena.protop.listener.OnLoadFinishListener;
import com.example.tarena.protop.listener.RefreshListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：Linqiang
 * 时间：2016/5/18:18:39
 * 邮箱: linqiang2010@outlook.com
 * 说明：网络获取类
 */
public class DownLoad {
    static List<News> list= new ArrayList<News>();

    /**
     * 程序首次运行执行此方法 更新数据到数据库
     * @param listener
     */
    public static void downLoad(final String type, final int count , final int page,final OnLoadFinishListener listener) {
        new AsyncTask<Void, Void, List<News>>() {
            @Override
            protected List<News> doInBackground(Void... params) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://gank.io/api/data/"+type+"/"+count+"/"+page).build();
                String uu = "http://gank.io/api/data/"+type+"/"+count+"/"+page;
                Log.i("TAG=========",uu);
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String re = response.body().string();
                        return jsonToNews(re);
                    } else {
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;

            }

            @Override
            protected void onPostExecute(List<News> news) {
                listener.onLoadFinish(news);
            }
        }.execute();

    }

    /**
     * 刷新数据时执行此方法
     * 获取数据库当前最新文章的日期,截止当前日期,根据天数查询
     * @param year
     * @param mouth
     * @param day
     * @return list
     */
    public static void downLoad2(final String type,final String year, final String mouth, final String day, final RefreshListener listener) {

        new AsyncTask<Void, Void, List<News>>() {
            @Override
            protected List<News> doInBackground(Void... params) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://gank.io/api/day/"+year+"/"+mouth+"/"+day).build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String re = response.body().string();
                        return jsonToNewsCatagory(type, re);
                    } else {
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;

            }

            @Override
            protected void onPostExecute(List<News> news) {

                listener.refreshFinish(news);
            }
        }.execute();

    }

    /**
     * JSON解析
     * @param result
     * @return
     */
    private static List<News> jsonToNews(String result){
        List<News> news = new ArrayList<News>();
        try {
            JSONObject obj = new JSONObject(result);
            JSONArray array = obj.getJSONArray("results");
            for (int i=0;i<array.length();i++){
                JSONObject o = array.getJSONObject(i);
                News n = new News();
                n.set_id(o.getString("_id"));
               String CreatAt = o.getString("createdAt").replace("T", "  ").replace("Z", "  ");
                n.setCreatedAt(String.valueOf(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(CreatAt).getTime()));
                n.setDesc(o.getString("desc"));
                String PublishAt =o.getString("publishedAt").replace("T", "  ").replace("Z", "  ");
                n.setPublishedAt(String.valueOf(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(PublishAt).getTime()));
                try{n.setSource(o.getString("source"));}catch (Exception e){ n.setSource("null");}
                n.setType(o.getString("type"));
                n.setUrl(o.getString("url"));
                try{n.setUsed(o.getBoolean("used"));}catch (Exception e){ n.setUsed(false);}
                try{n.setWho(o.getString("who"));}catch (Exception e){ n.setWho("null");}
                //全部默认的read 都是false 都是未读的
                n.setRead(false);
                //全部默认的fav都是false 都是未收藏的
                n.setFav(false);
                //是否是刷新的数据
                n.setRefresh(false);
                news.add(n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("TAG--------------",result);
        Log.i("TAG-----------",""+news);
        return news;
    }
    /**
     * JSON解析(刷新专用解析器)
     * @param result
     * @return
     */
    private static List<News> jsonToNewsCatagory(String type,String result){
        List<News> news = new ArrayList<News>();
        try {

            JSONObject obj = new JSONObject(result);
            JSONObject obj2 = obj.getJSONObject("results");
            JSONArray array = obj2.getJSONArray(type);
            for (int i=0;i<array.length();i++){

                    JSONObject o = array.getJSONObject(i);
                    News n = new News();
                    n.set_id(o.getString("_id"));
                    String CreatAt = o.getString("createdAt").replace("T", "  ").replace("Z", "  ");
                    n.setCreatedAt(String.valueOf(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(CreatAt).getTime()));
                    n.setDesc(o.getString("desc"));
                    String PublishAt =o.getString("publishedAt").replace("T", "  ").replace("Z", "  ");
                    n.setPublishedAt(String.valueOf(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(PublishAt).getTime()));
                      try{n.setSource(o.getString("source"));}catch (Exception e){ n.setSource("null");}
                n.setType(o.getString("type"));
                n.setUrl(o.getString("url"));
                try{n.setUsed(o.getBoolean("used"));}catch (Exception e){ n.setUsed(false);}
                try{n.setWho(o.getString("who"));}catch (Exception e){ n.setWho("null");}
                //全部默认的read 都是false 都是未读的
                n.setRead(false);
                //全部默认的fav都是false 都是未收藏的
                n.setFav(false);
                //刷新的数据是true的
                n.setRefresh(true);
                news.add(n);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("TAG", "jsonToNewsCatagory: " +news);
        return news;
    }

}
