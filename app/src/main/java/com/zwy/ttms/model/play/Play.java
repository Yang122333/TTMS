package com.zwy.ttms.model.play;
/**
 * Created by Administrator on 2018/5/27 0027.
 */
public class Play {
    private String play_id;
    private String play_type;
    private String play_language;
    private String play_name;
    private String play_intro;
    private String picture_path;
    private int play_length;//剧目时长  毫秒 （七个字段）


    public Play(String play_name,int  play_length,String play_type,String play_language,String play_intro,String picture_path){
        this.play_name = play_name;
        this.play_type = play_type;
        this.play_language = play_language;
        this.play_length = play_length;
        this.play_intro = play_intro;
        this.picture_path = picture_path;
    }
    public Play(String play_name,String play_type,String play_language,int  play_length,String play_intro){
        this.play_name = play_name;
        this.play_type = play_type;
        this.play_language = play_language;
        this.play_length = play_length;
        this.play_intro = play_intro;

    }
    public Play(){
    }
    public String getPicture_path() { return picture_path; }
    public void setPicture_path(String picture_path) { this.picture_path = picture_path; }

    public void setPlay_id(String play_id) { this.play_id = play_id; }
    public void setPlay_type(String play_type) {
        this.play_type = play_type;
    }

    public void setPlay_name(String play_name) {
        this.play_name = play_name;
    }

    public void setPlay_intro(String play_intro) {
        this.play_intro = play_intro;
    }

    public void setPlay_length(int play_length) {
        this.play_length = play_length;
    }

    public String getPlay_id() { return play_id; }

    public String getPlay_type() {
        return play_type;
    }

    public String getPlay_name() {
        return play_name;
    }

    public String getPlay_intro() {
        return play_intro;
    }

    public int getPlay_length() {
        return play_length;
    }

    public void setPlay_language(String play_language) { this.play_language = play_language; }
    public String getPlay_language() { return play_language; }

}
