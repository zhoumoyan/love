package cn.LoveUO.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class Diary extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 3314210770756844864L;
    private Integer did;
    private Integer uid;
    private String username;
    private String avatar;
    private String title;
    private String words;
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date completedTime;

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public Date getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(Date completedTime) {
        this.completedTime = completedTime;
    }

    @Override
    public String toString() {
        return "Diary{" +
                "did=" + did +
                ", uid=" + uid +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", title='" + title + '\'' +
                ", words='" + words + '\'' +
                ", completedTime=" + completedTime +
                '}';
    }
}
