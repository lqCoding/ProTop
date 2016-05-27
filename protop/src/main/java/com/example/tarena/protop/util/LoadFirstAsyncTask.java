package com.example.tarena.protop.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tarena.protop.Bean.News;
import com.example.tarena.protop.listener.OnLoadFinishListener;
import com.example.tarena.protop.listener.onLoadFirstAsyncFinished;

import java.util.List;

/**
 * 作者：Linqiang
 * 时间：2016/5/20:13:19
 * 邮箱: linqiang2010@outlook.com
 * 说明：第一次初始化程序,更新数据
 */
public class LoadFirstAsyncTask  extends AsyncTask<String,Integer,Void>{
    TextView tv;
    Context context;
    DBUtil dbUtil ;
    onLoadFirstAsyncFinished listener;
    public LoadFirstAsyncTask(Context context,onLoadFirstAsyncFinished listener) {
        this.context=context;
       // this.tv=tv;
        this.listener=listener;
        dbUtil= new DBUtil(context);
    }

    @Override
    protected Void doInBackground(final String... params) {
       // publishProgress(0);
        Log.i("TAG", "web------------------");
       DownLoad.downLoad(params[0], 1000, 1, new OnLoadFinishListener() {
           int i=0;

           @Override
           public void onLoadFinish(List<News> nlist) {
            if(nlist==null){
                Toast.makeText(context,"服务器异常!",Toast.LENGTH_LONG).show();
            }else{
                //插入数据库
                for(News n :nlist){
                    String table="";
                    switch (params[0]){
                        case "Android":
                            table="tb_android";
                            break;
                        case "iOS":
                            table="tb_ios";
                            break;
                        case "前端":
                            table="tb_web";
                            break;
                        case "拓展资源":
                            table="tb_tools";
                            break;
                    }
                    long id=  dbUtil.addNews(table,n);
                    if(id==-1){
                        Log.i("TAG", "onLoadFinish: 插入数据库失败!!!");
                    }
                    i++;
                    publishProgress(i,nlist.size());
                }
            }
           }
       });
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Log.i("TAG", "已插入数据== " +values[0]+"/"+values[1]);
        if(values[0]==values[1]-1){
            listener.finish();
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
       // listener.finish();
    }
}
