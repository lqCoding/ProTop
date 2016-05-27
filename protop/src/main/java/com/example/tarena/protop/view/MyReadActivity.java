package com.example.tarena.protop.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.tarena.protop.Bean.News;
import com.example.tarena.protop.R;
import com.example.tarena.protop.adapter.MyBaseAdapterDecoration;
import com.example.tarena.protop.adapter.MyBaseRecycleAdapter;
import com.example.tarena.protop.biz.NewsBiz;

import java.util.List;

public class MyReadActivity extends AppCompatActivity implements BaseQuickAdapter.OnRecyclerViewItemClickListener, View.OnClickListener {

    List<News> list;
    NewsBiz biz ;
    RecyclerView recyclerView;
    MyBaseRecycleAdapter adapter;
    ImageView iv_back;
    TextView tv_total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_read);
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        try{
            biz=new NewsBiz(this);
            //获取数据
            getNewsList();
            iv_back= (ImageView) findViewById(R.id.iv_newinfo_back);
            iv_back.setOnClickListener(this);
            tv_total= (TextView) findViewById(R.id.tv_read_total);
            tv_total.setText(list.size()+"篇");
            //初始化recycleview
            recyclerView= (RecyclerView) findViewById(R.id.wv_news_info);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.addItemDecoration(new MyBaseAdapterDecoration(this,MyBaseAdapterDecoration.VERTICAL_LIST));
            //初始化adapter
            adapter = new MyBaseRecycleAdapter(this,list);
            adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
            adapter.setOnRecyclerViewItemClickListener(this);
            recyclerView.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"获取文章失败",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 从数据库中获取文章列表
     */
    private void getNewsList() {
        list=biz.getNewsFromDB("tb_read");
    }

    /**
     * 点击页面跳转
     * @param view
     * @param i
     */
    @Override
    public void onItemClick(View view, int i) {
        Intent intent = new Intent(this,NewsInfoActivity.class);
        intent.putExtra("news",list.get(i));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
