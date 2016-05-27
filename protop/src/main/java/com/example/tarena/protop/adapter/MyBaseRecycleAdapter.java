package com.example.tarena.protop.adapter;

import android.content.Context;
import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.tarena.protop.Bean.News;
import com.example.tarena.protop.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 作者：Linqiang
 * 时间：2016/5/16:19:26
 * 邮箱: linqiang2010@outlook.com
 * 说明：
 */
public class MyBaseRecycleAdapter extends BaseQuickAdapter<News>{
    public MyBaseRecycleAdapter(Context context, List<News> data) {
        super(context,R.layout.news_layout, data);
    }
    @Override
    protected void convert(BaseViewHolder baseViewHolder, News news) {
        String cache="";
        int color=0;
        int id=0;
        if(news.isRefresh()&&!news.isRead()){//是刚刷新的文章
            cache=" 最新";
            color=Color.parseColor("#515151");

            switch (news.getType()){
                case "Android":
                    id=R.drawable.android01;
                    break;
                case "iOS":
                    id=R.drawable.ios01;
                    break;
                case "前端":
                    id=R.drawable.web01;
                    break;
                case "拓展资源":
                    id=R.drawable.setting01;
                    break;
            }

        }else{
            cache = (news.isRead()?"已阅读":"");
            color = (news.isRead()?Color.parseColor("#00BCD4"):Color.parseColor("#939393"));
          switch (news.getType()){
              case "Android":
                  id = (news.isRead()?R.drawable.android02:R.drawable.android01);
                  break;
              case "iOS":
                  id = (news.isRead()?R.drawable.ios02:R.drawable.ios01);
                  break;
              case "前端":
                  id = (news.isRead()?R.drawable.web02:R.drawable.web01);
                  break;
              case "拓展资源":
                  id = (news.isRead()?R.drawable.setting02:R.drawable.setting01);
                  break;
          }

        }

        String publishAt="";
        try {
            publishAt= String.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(new Date(Long.valueOf(news.getPublishedAt()))));
        } catch (Exception e) {
            e.printStackTrace();
        }

        baseViewHolder.setImageResource(R.id.iv_news_Category, id)
                .setText(R.id.tv_cache, cache).setTextColor(R.id.tv_news_Title, color)
                .setTextColor(R.id.tv_cache, color)
                .setText(R.id.tv_news_Title, news.getDesc())
                .setText(R.id.tv_news_whoAndtime, news.getWho() + "       " + publishAt);
        }

}
