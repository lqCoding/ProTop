package com.example.tarena.protop.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.tarena.protop.Bean.News;
import com.example.tarena.protop.R;
import com.example.tarena.protop.adapter.MyBaseAdapterDecoration;
import com.example.tarena.protop.adapter.MyBaseRecycleAdapter;
import com.example.tarena.protop.biz.NewsBiz;
import com.example.tarena.protop.listener.RefreshListener;
import com.example.tarena.protop.listener.onLoadFirstAsyncFinished;
import com.example.tarena.protop.util.LoadFirstAsyncTask;
import com.example.tarena.protop.view.NewsInfoActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnRecyclerViewItemClickListener {
    MyBaseRecycleAdapter adapter;
    SwipeRefreshLayout srl;
    RecyclerView recyclerView;
    int page=1;//初始数据
    NewsBiz biz;
    List<News> list;
    View view;
    View header;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =inflater.inflate(R.layout.fragment_web,container,false);
        header = inflater.inflate(R.layout.load_header,null);
        biz=new NewsBiz(getActivity());
        initView(view);
        return view;

    }
    Handler handler = new Handler(Looper.getMainLooper());
    //初始化View
    private void initView(View view) {

        //初始化SwipeRefreshLayout 并设置下拉刷新
        srl= (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        srl.setOnRefreshListener(this);

        //设置下拉刷新的条的颜色
        srl.setColorSchemeColors(Color.parseColor("#00BCD4"));
        //初始化recycleView
        recyclerView= (RecyclerView) view.findViewById(R.id.web_recycler_view);
        // 设置固定大小
        recyclerView.setHasFixedSize(true);
        //初始化LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //初始化BaseRecycleAdapter
        adapter = new MyBaseRecycleAdapter(getActivity(),new ArrayList<News>());
        adapter.setOnRecyclerViewItemClickListener(this);
        //开启动画
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        //设置动画是否出现一次
        //  adapter.isFirstOnly(false);
        //设置加载更多
        // adapter.setOnLoadMoreListener(this);
        //开启触发加载更多
        // adapter.openLoadMore(10,true);
        //设置分割线
        recyclerView.addItemDecoration(new MyBaseAdapterDecoration(getActivity(),MyBaseAdapterDecoration.VERTICAL_LIST));
        //给recyclerView设置adapter
        recyclerView.setAdapter(adapter);

    }

    public MyBaseRecycleAdapter getNewAdapter() {
        MyBaseRecycleAdapter adapter = new MyBaseRecycleAdapter(getActivity(), new ArrayList<News>());
        adapter.setOnRecyclerViewItemClickListener(this);
        //开启动画
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        //设置加载更多
        //  adapter.setOnLoadMoreListener(this);
        //开启触发加载更多
        //  adapter.openLoadMore(10, true);
        //设置分割线
        recyclerView.addItemDecoration(new MyBaseAdapterDecoration(getActivity(), MyBaseAdapterDecoration.VERTICAL_LIST));
        //给recyclerView设置adapter
        recyclerView.setAdapter(adapter);
        return adapter;
    }
RecyclerView.LayoutParams params=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    @Override
    public void onResume() {
        super.onResume();
        //先检查数据中有没有数据
        //如果数据库中有数据 直接加载数据中的数据 不更新数据库  如果没有先更新数据库
        try{
            list=biz.getNewsFromDB("tb_web");
            if(list.size()<=1){

                Log.i("TAG", "onResume: " + "更新数据库" + list.size());
                final ImageView img = (ImageView) header.findViewById(R.id.iv_load);
                final TextView tv_touch= (TextView) header.findViewById(R.id.tv_touch);
                tv_touch.setText("轻触更新数据");
                final RotateAnimation loadAnim = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                loadAnim.setDuration(1000);
                //设置重复模式 无限
                loadAnim.setRepeatCount(Animation.INFINITE);
                //设置加速器,添加一个匀速插值器 让旋转匀速
                loadAnim.setInterpolator(new LinearInterpolator());

                //添加头部这句话必须要设 要不不居中
                header.setLayoutParams(params);
                adapter=getNewAdapter();
                adapter.addHeaderView(header);
                recyclerView.setAdapter(adapter);
                header.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //启动动画
                        tv_touch.setText("正在更新数据中,请稍后.....");
                        img.startAnimation(loadAnim);
                        updateDB();
                    }
                });

            }else{
                Log.i("TAG", "onResume: " + "没有更新数据库" + list.size());
                adapter.setNewData(list);
            }
        }catch (Exception e){
            updateDB();
        }

    }

    /**
     * 更新数据库
     */
    public void updateDB(){

        //启动异步任务更新数据到数据库
        new Thread(){
            @Override
            public void run() {

                LoadFirstAsyncTask task = new LoadFirstAsyncTask(getActivity(),new onLoadFirstAsyncFinished(){
                    @Override
                    public void finish() {
                        adapter=getNewAdapter();
                        recyclerView.setAdapter(adapter);
                        Log.i("TAG", "web------------------");
                        list=biz.getNewsFromDB("tb_web");
                        Log.i("TAG", "finish: 现在数据库大小" + list.size());
                       adapter.setNewData(list);

                    }
                });
                task.execute("前端");

            }
        }.start();

    }
    /**
     * 下拉刷新
     *  刷新完弹出通知
     */
    @Override
    public void onRefresh() {
        final int refreshOldNum=list.size();
        if(refreshOldNum==0){srl.setRefreshing(false); return;}
        //标记最新日期
        String listnew=  String.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(new Date(Long.valueOf(list.get(0).getPublishedAt()))));
        long listTime=Long.valueOf(list.get(0).getPublishedAt());
        long currenTime =new Date().getTime();
        int count = (int) ((currenTime-listTime)/86400000+1);

        for(int i =0;i<=count;i++){
            String[] temp= String.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(new Date(listTime))).split("-");
            final long finalListTime = listTime;
            biz.getNewsFromNetNew("前端",temp[0], temp[1],temp[2], new RefreshListener() {
                @Override
                public void refreshFinish(List<News> nlist) {
                    if(nlist!=null) {
                        int num=0;
                        for (News n : nlist) {
                            if(Long.valueOf(n.getPublishedAt())> finalListTime){
                                list.add(0, n);
                                num++;

                                biz.addRefreshToDB("tb_web", n);
                            }
                        }
                        //  Toast.makeText(getActivity(), "发现了" + nlist.size() + "篇新文章", Toast.LENGTH_SHORT).show();
                        Log.i("TAG", "发现了" + num + "篇新文章");

                        srl.setRefreshing(false);
                    }else{
                        Toast.makeText(getActivity(), "服务器链接失败,请检查网络稍后重试", Toast.LENGTH_SHORT).show();
                        srl.setRefreshing(false);
                    }
                    adapter.setNewData(list);
                }
            });
            listTime+=86400000;
        }

    }


    /**
     * 点击跳转到浏览器页面
     * @param view
     * @param i
     */
    @Override
    public void onItemClick(View view, int i) {
        //更新数据库状态 已阅读过此篇文章
       /* try{
            //更新数据库的阅读状态
            int num= biz.updateRead("tb_web", list.get(i).getUrl());
            //更新已浏览的文章的表(判断是否曾经浏览过,不能重复添加)
            if(!list.get(i).isRead()){
                long num2 = biz.updateReadTable("tb_read", list.get(i));
            }
            if(num>0){
                //Toast.makeText(getActivity(),"数据更新成功",Toast.LENGTH_LONG).show();
            }else{
                //   Toast.makeText(getActivity(),"数据更新失败,没有查到这对应数据!",Toast.LENGTH_LONG).show();
            }
        }catch (Exception e ){
            Toast.makeText(getActivity(),"数据更新遇到异常!!!!",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        //更新list
        list.get(i).setRead(true);
        //更新界面
        adapter.setNewData(list);*/
        //跳转页面
        Intent intent = new Intent(getActivity(), NewsInfoActivity.class);
        intent.putExtra("news",list.get(i));
        startActivity(intent);

    }

    @Override
    public void onLoadMoreRequested() {

    }
}
