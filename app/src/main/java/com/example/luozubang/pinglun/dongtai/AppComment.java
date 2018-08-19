package com.example.luozubang.pinglun.dongtai;

import java.util.List;

/**
 * 评论
 * /**
 * @author arryluo

 */
public class AppComment {
    private String content;
    private String createtime;
    private String danjuid;
    private String img;
    private String nickname;
    private String uid;
    private List<AppReplyComment>AppReplyComment;

    public List<AppReplyComment> getAppReplyComment() {
        return AppReplyComment;
    }

    public void setAppReplyComment(List<AppReplyComment> appReplyComment) {
        AppReplyComment = appReplyComment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getDanjuid() {
        return danjuid;
    }

    public void setDanjuid(String danjuid) {
        this.danjuid = danjuid;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
