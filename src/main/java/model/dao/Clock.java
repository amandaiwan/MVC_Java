package model.dao;

public class Clock {

    private int id;
    private String picText;
    private String picUrl;
    private int likes;
    private int dislikes;

    public Clock() {

    }

    public Clock(int id, String picText, String picUrl) {
        this.id = id;
        this.picText = picText;
        this.picUrl = picUrl;
        this.likes = 0;
        this.dislikes = 0;
    }

    public Clock(int id, String picText, String picUrl, int likes, int dislikes) {
        this.id = id;
        this.picText = picText;
        this.picUrl = picUrl;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicText() {
        return picText;
    }

    public void setPicText(String picText) {
        this.picText = picText;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }
}