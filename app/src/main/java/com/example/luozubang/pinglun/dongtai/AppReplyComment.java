package com.example.luozubang.pinglun.dongtai;

/**
 * 回复评论
 */
/**
 * @author arryluo
 */
public class AppReplyComment {
    private String content;
    private String createtime;
    private String fromname;//评论者
    private String toname;//被评论者
    private String formuid;
    private String touid;//
    private String frouimg;
    private String touimg;

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

    public String getFromname() {
        return fromname;
    }

    public void setFromname(String fromname) {
        this.fromname = fromname;
    }

    public String getToname() {
        return toname;
    }

    public void setToname(String toname) {
        this.toname = toname;
    }

    public String getFormuid() {
        return formuid;
    }

    public void setFormuid(String formuid) {
        this.formuid = formuid;
    }

    public String getTouid() {
        return touid;
    }

    public void setTouid(String touid) {
        this.touid = touid;
    }

    public String getFrouimg() {
        return frouimg;
    }

    public void setFrouimg(String frouimg) {
        this.frouimg = frouimg;
    }

    public String getTouimg() {
        return touimg;
    }

    public void setTouimg(String touimg) {
        this.touimg = touimg;
    }
}
