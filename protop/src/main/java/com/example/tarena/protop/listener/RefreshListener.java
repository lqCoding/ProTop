package com.example.tarena.protop.listener;

import com.example.tarena.protop.Bean.News;

import java.util.List;

/**
 * 作者：Linqiang
 * 时间：2016/5/21:17:02
 * 邮箱: linqiang2010@outlook.com
 * 说明：刷新完成监听器
 */
public interface RefreshListener {
    void refreshFinish(List<News> list);
}
