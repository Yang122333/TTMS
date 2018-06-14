package com.zwy.ttms.model;

/**
 * Created by ${Yang} on 2018/4/16.
 */


public class Plays {
    private String PlayStudio;
    private String PlayName;
    private String PlayTime;
    private String PlayPrice;

    public Plays(){
        super();
    }
    public Plays(String PlayName,String PlayStudio,String PlayTime,String PlayPrice){
        super();
        this.PlayName = PlayName;
        this.PlayStudio = PlayStudio;
        this.PlayTime = PlayTime;
        this.PlayPrice = PlayPrice;
    }

    public String getPlayName() {
        return PlayName;
    }

    public String getPlayStudio() {
        return PlayStudio;
    }

    public String getPlayTime() {
        return PlayTime;
    }

    public String getPlayPrice() {
        return PlayPrice;
    }

    public void setPlayName(String playName) {
        PlayName = playName;
    }

    public void setPlayStudio(String playStudio) {
        PlayStudio = playStudio;
    }

    public void setPlayTime(String playTime) {
        PlayTime = playTime;
    }

    public void setPlayPrice(String playPrice) {
        PlayPrice = playPrice;
    }
}
