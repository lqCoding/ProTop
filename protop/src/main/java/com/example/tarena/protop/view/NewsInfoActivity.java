package com.example.tarena.protop.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tarena.protop.Bean.News;
import com.example.tarena.protop.R;
import com.example.tarena.protop.biz.NewsBiz;

import java.io.File;

public class NewsInfoActivity extends AppCompatActivity implements View.OnClickListener {

    public  WebView wb;
    ImageView iv_fav;
    TextView tv_fav;
    News news;
    NewsBiz biz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_info);
      initView();
    }

    private void initView() {

        wb  =(WebView) findViewById(R.id.wv_news_info);
        ImageView iv = (ImageView) findViewById(R.id.iv_newinfo_back);
        Intent intent=getIntent();
        news = (News) intent.getSerializableExtra("news");
        biz=new NewsBiz(this);
        //更新数据库
        rerfshDB();
        //关闭页面
        iv.setOnClickListener(this);
        iv_fav = (ImageView) findViewById(R.id.iv_newinfo_fav);
        tv_fav= (TextView) findViewById(R.id.tv_newsinfo_fav);
        if(news.isFav()){
            tv_fav.setText("已收藏");
            iv_fav.setImageResource(R.drawable.fav02);
        }else{
            tv_fav.setText("");
            iv_fav.setImageResource(R.drawable.fav);
        }
        iv_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!news.isFav()){
                   biz.addFavTable("tb_favo", news);
                   switch (news.getType()){
                       case "Android":
                           biz.updateFavo("tb_android",news.getUrl());
                           break;
                       case "iOS":
                           biz.updateFavo("tb_ios",news.getUrl());
                           break;
                       case "前端":
                           biz.updateFavo("tb_web",news.getUrl());
                           break;
                       case "拓展资源":
                           biz.updateFavo("tb_tools",news.getUrl());
                           break;

                   }

                   tv_fav.setText("已收藏");
                   iv_fav.setImageResource(R.drawable.fav02);

               }
            }
        });

        //设置点击页面里面的连接不再重新开启新的浏览器打开链接
        wb.setWebViewClient(new MyWebViewClient());
        //初始化view
        TextView tvtitle = (TextView) findViewById(R.id.tv_news_info_title);

      //  String url=  intent.getStringExtra("url");
      //  String title = intent.getStringExtra("title");
        setCache();
        wb.loadUrl(news.getUrl());
        tvtitle.setText(news.getDesc());

    }

    /**
     * 设置webview缓存页面
     */
    private void setCache() {
      // String state = Environment.getExternalStorageState();
      // File dir = (!state.equals(Environment.MEDIA_MOUNTED)||!state.equals(Environment.MEDIA_REMOVED)?getExternalCacheDir():getCacheDir());
        File dir = new File("/data/data/" + getPackageName() + "/cache");
      //  File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
       wb.getSettings().setJavaScriptEnabled(true);
       wb.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        //设置 缓存模式 这个模式只要是缓存中有这个网页的缓存无论有没有网络都不再从网络中重新获取页面
       wb.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 开启 DOM storage API 功能  
       wb.getSettings().setDomStorageEnabled(true);
        //开启 database storage API 功能  
       wb.getSettings().setDatabaseEnabled(true);
      // String cacheDirPath = dir.getAbsolutePath();
        //设置数据库缓存路径  
      // wb.getSettings().setDatabasePath(getExternalCacheDir().getAbsolutePath());
       wb.getSettings().setDatabasePath(dir.getAbsolutePath());
        //设置  Application Caches 缓存目录  
       wb.getSettings().setAppCachePath(dir.getAbsolutePath());
        //开启 Application Caches 功能
       wb.getSettings().setAppCacheEnabled(true);

    }
    /**
     * 回退
     * @param v
     */
    @Override
    public void onClick(View v) {
        finish();
    }

    /**
     * 自定义webView 并重写其方法实现在webview中点击链接不再重开浏览器
     */
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i("TAG", "shouldOverrideUrlLoading: " + wb.getSettings().getDatabaseEnabled());
            if (Uri.parse(url).getHost().equals("www.example.com")) {
                // This is my web site, so do not override; let my WebView load the page
                return false;
            }
            return false;
        }

        //网页加载完成执行此方法
        @Override
        public void onPageFinished(WebView view, String url) {

           // Toast.makeText(NewsInfoActivity.this,"网页加载完成+"+wb.getSettings().getDatabasePath(),Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 将阅读状态更新到数据库
     */
    public void rerfshDB() {
        try {
            //更新数据库的阅读状态
            int num=-1;
            switch (news.getType()){
                case "Android":
                    num = biz.updateRead("tb_android", news.getUrl());
                    break;
                case "iOS":
                    num = biz.updateRead("tb_ios", news.getUrl());
                    break;
                case "前端":
                    num=biz.updateRead("tb_web",news.getUrl());
                    break;
                case "拓展资源":
                    num=biz.updateRead("tb_tools",news.getUrl());
                    break;
            }
            //更新已浏览的文章的表(判断是否曾经浏览过,不能重复添加)
            if (!news.isRead()) {
                long num2 = biz.updateReadTable("tb_read", news);
            }
            if (num > 0) {
                //Toast.makeText(getActivity(),"数据更新成功",Toast.LENGTH_LONG).show();
            } else {
                  Toast.makeText(NewsInfoActivity.this,"数据更新失败,没有查到这对应数据!",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(NewsInfoActivity.this, "数据更新遇到异常!!!!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
    /**
     * 重写activity的onKeyDown方法 实现在webview页面点击回退键是在webview回退 不会直接关闭页面
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            if ((keyCode == KeyEvent.KEYCODE_BACK) && wb.canGoBack()) {
                wb.goBack(); //goBack()表示返回WebView的上一页面
                return true;
            } else {
                finish();
                return true;
            }
        }else {
            return false;
        }
    }
}
