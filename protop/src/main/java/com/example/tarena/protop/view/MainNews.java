package com.example.tarena.protop.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.tarena.protop.R;
import com.example.tarena.protop.adapter.MyViewPagerAdapter;
import com.example.tarena.protop.biz.NewsBiz;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.TabItemBuilder;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;

public class MainNews extends AppCompatActivity implements OnTabItemSelectListener {
    private static final long RIPPLE_DURATION = 250;
    ViewPager vp ;
    PagerBottomTabLayout bottomTabLayout;
    Controller c;
    RadioGroup rg;
    //两个二级菜单布局
    LinearLayout  clear_cache;
    //两个布局状态显示器
    ImageView iv_skin_orientation,iv_clear_orientation;
    //两个删除缓存的按钮
    Button btn_clear_db,btn_clear_web;
    //业务类
    NewsBiz biz;
    ImageView iv_back;
    //是否可点击
    boolean isClick=false;

 //   @InjectView
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_news);
     //   LinearLayout ll = (LinearLayout) findViewById(R.id.ll_linearlayout);
        initView();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(isClick) {
            iv_back.performClick();
            isClick=false;
        }
    }
/**
 * create：2016/5/14
 * description：初始化视图 添加旋转菜单,Tabs
 */
    private void initView() {
        //业务类
        biz = new NewsBiz(this);
      //  biz.clearDB("tb_android");
        //radioGroup
        rg= (RadioGroup) findViewById(R.id.rg_color);
        //旋转菜单
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FrameLayout root = (FrameLayout) findViewById(R.id.root);
        View contentHamburger = findViewById(R.id.content_hamburger);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);
     //   skin = (LinearLayout) guillotineMenu.findViewById(R.id.ll_skin);
        iv_back= (ImageView)guillotineMenu.findViewById(R.id.guillotine_hamburger);
        clear_cache= (LinearLayout) guillotineMenu.findViewById(R.id.ll_clear_cache);
       // iv_skin_orientation= (ImageView) guillotineMenu.findViewById(R.id.skin_orientation);
        iv_clear_orientation= (ImageView) guillotineMenu.findViewById(R.id.clear_orientation);
        btn_clear_db= (Button) clear_cache.findViewById(R.id.btn_clear_db);
        btn_clear_web= (Button) clear_cache.findViewById(R.id.btn_clear_web);
        root.addView(guillotineMenu);

        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();
        /**
         * 初始化动态菜单
         */
        bottomTabLayout = (PagerBottomTabLayout) findViewById(R.id.tab);

//  .setMode(TabLayoutMode.HIDE_TEXT | TabLayoutMode.CHANGE_BACKGROUND_COLOR)
        TabItemBuilder tabItemBuilder = new TabItemBuilder(this).create()
                .setDefaultColor(Color.parseColor("#a9b7b7"))
                .setSelectedColor(Color.parseColor("#00BCD4"))
                .setDefaultIcon(R.drawable.android01)
                .setText("Android")
                .build();

        TabItemBuilder tabItemBuilder2 = new TabItemBuilder(this).create()
                .setDefaultColor(Color.parseColor("#a9b7b7"))
                .setSelectedColor(Color.parseColor("#00BCD4"))
                .setDefaultIcon(R.drawable.ios01)
                .setText("IOS")
                .build();
        TabItemBuilder tabItemBuilder3 = new TabItemBuilder(this).create()
                .setDefaultColor(Color.parseColor("#a9b7b7"))
                .setSelectedColor(Color.parseColor("#00BCD4"))
                .setDefaultIcon(R.drawable.web01)
                .setText("Web前端")
                .build();
        TabItemBuilder tabItemBuilder4 = new TabItemBuilder(this).create()
                .setDefaultColor(Color.parseColor("#a9b7b7"))
                .setSelectedColor(Color.parseColor("#00BCD4"))
                .setDefaultIcon(R.drawable.setting01)
                .setText("拓展资源")
                .build();
       /* TabItemBuilder tabItemBuilder5 = new TabItemBuilder(this).create()
                .setDefaultColor(Color.parseColor("#a9b7b7"))
                .setSelectedColor(Color.parseColor("#00BCD4"))
                .setDefaultIcon(R.drawable.question)
                .setText("面试题")
                .build();*/

        c= bottomTabLayout.builder()
                .addTabItem(tabItemBuilder)
                .addTabItem(tabItemBuilder2)
                .addTabItem(tabItemBuilder3)
                .addTabItem(tabItemBuilder4)
               // .addTabItem(tabItemBuilder5)
                .build();
        c.addTabItemClickListener(this);
        //c.setMessageNumber(0,5);
        /**
         * 初始化fragment
         */
        vp= (ViewPager) findViewById(R.id.vp_allnews);
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);
       vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


           }

           @Override
           public void onPageSelected(int position) {
               for(int i=0;i<=3;i++){ rg.getChildAt(i).setBackgroundColor(Color.WHITE);}
               rg.getChildAt(position).setBackgroundColor(Color.parseColor("#00BCD4"));
               c.setSelect(position);
           }

           @Override
           public void onPageScrollStateChanged(int state) {

           }
       });

    }

    /**
     * 弹出或者隐藏更换布局的二级菜单
     * @param v
     */
 /*   public  void onClick_skin(View v){
        if(skin.getVisibility()==View.VISIBLE){
            ScaleAnimation anim = new ScaleAnimation(1,0,1,0, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            anim.setDuration(500);
            skin.setAnimation(anim);
            skin.startAnimation(anim);
            skin.setVisibility(View.GONE);
            iv_skin_orientation.setImageResource(R.drawable.down);
        }else{
            ScaleAnimation anim = new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            anim.setDuration(1000);
            skin.setAnimation(anim);
            skin.startAnimation(anim);
            skin.setVisibility(View.VISIBLE);
            iv_skin_orientation.setImageResource(R.drawable.up);
        }
    }
*/
    /**
     * 已浏览过的文章
     * @param v
     */
    public  void onClick_read(View v){
        Intent intent = new Intent(this,MyReadActivity.class);
        startActivity(intent);
        isClick=true;
    }
    public  void  onClick_fav(View v){
        Intent intent = new Intent(this,FavoActivity.class);
        startActivity(intent);
        isClick=true;
    }

    /**
     * 知识点模块(取消)
     * @param v
     */
    public  void onClick_question(View v){
        // Toast.makeText(this,"面试题",Toast.LENGTH_LONG).show();
    }

    /**
     * 弹出或者隐藏清除缓存的二级菜单
     * @param v
     */
    public  void onClick_cache(View v){
        // Toast.makeText(this,"清除缓存",Toast.LENGTH_LONG).show();
        if(clear_cache.getVisibility()==View.VISIBLE){
            ScaleAnimation anim = new ScaleAnimation(1,0,1,0,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            //  TranslateAnimation anim = new TranslateAnimation();
            anim.setDuration(500);
            clear_cache.setAnimation(anim);
            clear_cache.startAnimation(anim);
            clear_cache.setVisibility(View.GONE);
            iv_clear_orientation.setImageResource(R.drawable.down);
        }else{
            ScaleAnimation anim = new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            anim.setDuration(1000);
            clear_cache.setAnimation(anim);
            clear_cache.startAnimation(anim);
            clear_cache.setVisibility(View.VISIBLE);
            iv_clear_orientation.setImageResource(R.drawable.up);
        }
         String state = Environment.getExternalStorageState();
         File dir = (!state.equals(Environment.MEDIA_MOUNTED)||!state.equals(Environment.MEDIA_REMOVED)?getExternalCacheDir():getCacheDir());
       // Toast.makeText(this,getCacheSize(dir),Toast.LENGTH_SHORT).show();"占用空间: "+FormetFileSize(blockSize)
        getWebCacheSize();
        File dir2 = new File("/data/data/" + getPackageName() + "/databases/news.db");
        Log.i("TAG", "onClick_cache: " + dir2.getAbsolutePath());
        String size=(FormetFileSize(getCacheSize(dir2)).equals("40.00KB")?"0KB":FormetFileSize(getCacheSize(dir2)));
        btn_clear_db.setText("清除列表缓存\n\n" +"占用空间: "+size);
    }

    /**
     * 我的博客
     * @param v
     */
    public void onClick_MyBlog(View v){
        Intent intent = new Intent(this,MyBlogActivity.class);
        intent.putExtra("url","http://lqcoding.github.io");
        startActivity(intent);
        isClick=true;
    }

    /**
     * 关于软件
     * @param v
     */
    public  void onClick_about(View v) {
        Toast.makeText(this, "ProTop 版本1.0\n作者:Linqiang\n邮箱:linqiang2010@outlook.com", Toast.LENGTH_LONG).show();
    }

    /**
     * 清除数据库缓存
     * @param v
     */
    public void onClick_Clear_DB(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("缓存删除");
        builder.setIcon(android.R.drawable.ic_menu_info_details);
        builder.setMessage("确定要删除列表数据库缓存吗?\r\n(删除后需要重新联网更新)");

        builder.setPositiveButton("狠心删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {

                    int num = biz.clearDB("tb_android");
                    Toast.makeText(MainNews.this, "数据库删除!总共删除" + num + "条数据!", Toast.LENGTH_LONG).show();
                    //FindFragmentByTag("Android:switcher:" + R.id.viewpager + ":0");
                    //  Fragment androidFragment  = MainNews.this.getSupportFragmentManager().findFragmentByTag("Android:switcher:" + R.id.vp_allnews + ":0");
                    FragmentPagerAdapter f = (FragmentPagerAdapter) vp.getAdapter();
                    for (int i = 0; i < 4; i++) {
                        Fragment androidFragment = (Fragment) f.instantiateItem(vp, i);
                        if (androidFragment != null) {
                            androidFragment.onResume();
                        } else {
                            // Toast.makeText(MainNews.this,"fragment=null",Toast.LENGTH_SHORT).show();
                        }
                    }
                    File dir2 = new File("/data/data/" + getPackageName() + "/databases/news.db");
                    Log.i("TAG", "onClick_cache: " + dir2.getAbsolutePath());
                    String size=(FormetFileSize(getCacheSize(dir2)).equals("40.00KB")?"0KB":FormetFileSize(getCacheSize(dir2)));
                    btn_clear_db.setText("清除列表缓存\n\n" +"占用空间: "+size);
                } catch (Exception e) {
                    Toast.makeText(MainNews.this, "删除数据库失败!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton("我再想想", null);
        builder.create().show();

    }
    public  void getWebCacheSize(){

      //  long size=getCacheSize(new File("/data/data/" + getPackageName() + "/cache"+"/webviewCacheChromium"));
        File dir = new File("/data/data/" + getPackageName() + "/cache");
        long size=getCacheSize(dir);
        size+=getCacheSize(new File("/data/data/" + getPackageName() + "/databases/"));
      //  size+=getCacheSize(new File("/data/data/" + getPackageName() + "/databases/webview.db-journal"));
      //  size+=getCacheSize(new File("/data/data/" + getPackageName() + "/databases/webviewCookiesChromium.db"));
     //   size+=getCacheSize(new File("/data/data/" + getPackageName() + "/databases/webviewCookiesChromiumPrivate.db"));
        btn_clear_web.setText("清除网页缓存\n\n" + "占用空间: " + FormetFileSize(size));
    }
    /**
     * 清除网页缓存
     * @param v
     */
    public  void onClick_Clear_WEB(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("缓存删除");
        builder.setIcon(android.R.drawable.ic_menu_info_details);
        builder.setMessage("确定要删除列表数据库缓存吗?\r\n(删除后将失去以前缓存的网页)");

        builder.setPositiveButton("狠心删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    FragmentPagerAdapter f = (FragmentPagerAdapter) vp.getAdapter();
                    for (int i = 0; i < 4; i++) {
                        Fragment androidFragment = (Fragment) f.instantiateItem(vp, i);
                        if (androidFragment != null) {
                            androidFragment.onResume();
                        } else {
                            // Toast.makeText(MainNews.this,"fragment=null",Toast.LENGTH_SHORT).show();
                        }
                    }
                    deleteFile(new File("/data/data/" + getPackageName() + "/cache/webviewCacheChromium"));
                    deleteFile(new File("/data/data/" + getPackageName() + "/databases/webview.db"));
                    deleteFile(new File("/data/data/" + getPackageName() + "/databases/webview.db-journal"));
                    deleteFile(new File("/data/data/" + getPackageName() + "/databases/webviewCookiesChromium.db"));
                    deleteFile(new File("/data/data/" + getPackageName() + "/databases/webviewCookiesChromiumPrivate.db"));
                    getWebCacheSize();
                } catch (Exception e) {
                    Toast.makeText(MainNews.this, "删除数据库失败!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton("我再想想", null);
        builder.create().show();

    }
    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    public void deleteFile(File file) {

        Log.i("TAG", "delete file path=" + file.getAbsolutePath());

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
            Log.e("TAG", "delete file no exists " + file.getAbsolutePath());
        }
    }

    /***
     * 计算缓存占用空间大小
     * @param file
     * @return
     */
    private  long getCacheSize(File file){
        long blockSize=0;
        try {
            if(file.isDirectory()){
                blockSize = getFileSizes(file);
            }else{
              //  Toast.makeText(this,"===="+blockSize,Toast.LENGTH_SHORT).show();
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小", "获取失败!");
        }
        return  blockSize;
    }
    private static long getFileSize(File file) throws Exception
    {
        long size = 0;
        if (file.exists()){
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
            Log.i("TAG", "文件名======" +file.getName());
        }
        else{
            file.createNewFile();
            Log.e("获取文件大小","文件不存在!");
        }
        return size;
    }
    private static String FormetFileSize(long fileS)
    {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize="0B";
        if(fileS==0){
            return wrongSize;
        }
        if (fileS < 1024){
            fileSizeString = df.format((double) fileS) + "B";
        }
        else if (fileS < 1048576){
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        }
        else if (fileS < 1073741824){
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        }
        else{
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }
    /**
     * 获取指定文件夹
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFileSizes(File f) throws Exception
    {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++){
            if (flist[i].isDirectory()){
                size = size + getFileSizes(flist[i]);
            }
            else{
                size =size + getFileSize(flist[i]);
            }
        }
        return size;
    }
    /**
     * 以下两个方法监听Tab选中事件 ,tab选中切换viewpager
     * @param index
     * @param tag
     */
    //选中触发此事件
    @Override
    public void onSelected(int index, Object tag) {
       // Toast.makeText(this,""+index,Toast.LENGTH_SHORT).show();
        vp.setCurrentItem(index);
        for(int i=0;i<=3;i++){ rg.getChildAt(i).setBackgroundColor(Color.WHITE);}
        rg.getChildAt(index).setBackgroundColor(Color.parseColor("#00BCD4"));
    }
    //重复选中触发此事件
    @Override
    public void onRepeatClick(int index, Object tag) {

    }

    boolean isExit =false;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0) {
            if (!isExit) {
                isExit = true;
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                new Handler(Looper.getMainLooper()) {
                    public void handleMessage(Message msg) {
                        isExit = false;
                    }
                }.sendEmptyMessageDelayed(1, 3000);
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