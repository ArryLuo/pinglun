package com.example.luozubang.pinglun.dongtai;

import android.net.Uri;

import java.io.Serializable;
import java.net.URI;
import java.util.List;

/**
 * 动态列表
 */
/**
 * @author arryluo
 */
public class NodeTable implements Serializable {
    private String username;//用户名称
    private String create_time;
    private int dianzansize;//点赞个数
    private String content;//动态内容
    private String uimg;
    private String uid;
    private List<Uri>fujian;//附件
    private List<AppComment>AppComment;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getDianzansize() {
        return dianzansize;
    }

    public void setDianzansize(int dianzansize) {
        this.dianzansize = dianzansize;
    }

    public String getUimg() {
        return uimg;
    }

    public void setUimg(String uimg) {
        this.uimg = uimg;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<Uri> getFujian() {
        return fujian;
    }

    public void setFujian(List<Uri> fujian) {
        this.fujian = fujian;
    }

    public List<com.example.luozubang.pinglun.dongtai.AppComment> getAppComment() {
        return AppComment;
    }

    public void setAppComment(List<com.example.luozubang.pinglun.dongtai.AppComment> appComment) {
        AppComment = appComment;
    }
}
