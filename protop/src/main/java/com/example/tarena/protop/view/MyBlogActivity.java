package com.example.tarena.protop.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.tarena.protop.R;

import java.io.File;

public class MyBlogActivity extends AppCompatActivity {

    WebView wv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_blog);

        initView();
    }

    private void initView() {
        wv= (WebView) findViewById(R.id.wv_my_blog);
        Intent intent = getIntent();
        //设置点击页面里面的连接不再重新开启新的浏览器打开链接
        wv.setWebViewClient(new MyWebViewClient());
        File dir = new File("/data/data/" + getPackageName() + "/cache");
        //  File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        //设置 缓存模式 这个模式只要是缓存中有这个网页的缓存无论有没有网络都不再从网络中重新获取页面
        wv.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 开启 DOM storage API 功能
        wv.getSettings().setDomStorageEnabled(true);
        //开启 database storage API 功能
        wv.getSettings().setDatabaseEnabled(true);
        // String cacheDirPath = dir.getAbsolutePath();
        //设置数据库缓存路径
        // wb.getSettings().setDatabasePath(getExternalCacheDir().getAbsolutePath());
        wv.getSettings().setDatabasePath(dir.getAbsolutePath());
        //设置  Application Caches 缓存目录
        wv.getSettings().setAppCachePath(dir.getAbsolutePath());
        //开启 Application Caches 功能
        wv.getSettings().setAppCacheEnabled(true);
        wv.loadUrl(intent.getStringExtra("url"));


    }
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i("TAG", "shouldOverrideUrlLoading: " + wv.getSettings().getDatabaseEnabled());
            if (Uri.parse(url).getHost().equals("www.example.com")) {
                // This is my web site, so do not override; let my WebView load the page
                return false;
            }
            return false;
        }

        //网页加载完成执行此方法
        @Override
        public void onPageFinished(WebView view, String url) {

            Toast.makeText(MyBlogActivity.this, "网页加载完成+" + wv.getSettings().getDatabasePath(), Toast.LENGTH_LONG).show();
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
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()) {
            wv.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        finish();

        return true;

    }
}
