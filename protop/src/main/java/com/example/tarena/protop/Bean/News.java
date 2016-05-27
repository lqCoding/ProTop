package com.example.tarena.protop.Bean;

import java.io.Serializable;

/**
 * 作者：Linqiang
 * 时间：2016/5/14:12:29
 * 邮箱: linqiang2010@outlook.com
 * 说明：
 */
public class News implements Serializable {

    /**
     * _id : 57334c9d67765903fb61c418
     * createdAt : 2016-05-11T23:15:41.98Z
     * desc : 还在用ListView？
     * publishedAt : 2016-05-12T12:04:43.857Z
     * source : web
     * type : Android
     * url : http://www.jianshu.com/p/a92955be0a3e
     * used : true
     * who : 陈宇明
     */

    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    private  boolean read;
    public  boolean refresh;

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    private  boolean fav;

    public boolean isRefresh() {return refresh;}

    public void setRefresh(boolean refresh) {
        this.refresh = refresh;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    @Override
    public String toString() {
        return "News{" +
                "_id='" + _id + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", desc='" + desc + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", source='" + source + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", used=" + used +
                ", who='" + who + '\'' +
                ", read=" + read +
                ", refresh=" + refresh +
                ", fav=" + fav +
                '}';
    }
}
