package com.example.tarena.protop.util;

import android.content.Context;

import java.io.File;

/**
 * 作者：Linqiang
 * 时间：2016/5/19:19:00
 * 邮箱: linqiang2010@outlook.com
 * 说明：删除缓存目录
 */
public class DeleteCache {
   static Context context;
    public DeleteCache(Context context) {
        this.context=context;
    }

    /**
     * 清除WebView缓存
     */
    public static void clearWebViewCache(){

        //清理Webview缓存数据库
        try {
         //   deleteDatabase("webview.db");
         //   deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //WebView 缓存文件
       // File appCacheDir = new File(context.getCacheDir().getAbsolutePath()+APP_CACAHE_DIRNAME);
      //  Log.e(TAG, "appCacheDir path="+appCacheDir.getAbsolutePath());

      //  File webviewCacheDir = new File(getCacheDir().getAbsolutePath()+"/webviewCache");
     //   Log.e(TAG, "webviewCacheDir path="+webviewCacheDir.getAbsolutePath());

        //删除webview 缓存目录
      //  if(webviewCacheDir.exists()){
     //       deleteFile(webviewCacheDir);
    //    }
        //删除webview 缓存 缓存目录
     //   if(appCacheDir.exists()){
      //      deleteFile(appCacheDir);
     //   }
    }

    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    public void deleteFile(File file) {

     //   Log.i(TAG, "delete file path=" + file.getAbsolutePath());

        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
       //     Log.e(TAG, "delete file no exists " + file.getAbsolutePath());
        }
    }


}
