package com.zwy.ttms.model.play;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PlayAndLogParseJSON {
    public static List<Play> toPlaylist(String jsonData) {              //  查询所有用这个
        List<Play> playList = new ArrayList<>();
        try {

            JSONObject jsonObject = new JSONObject(jsonData);
            String data = jsonObject.getString("playList");
            JSONArray jsonArray = new JSONArray(data);


            Log.i("length =", String.valueOf(jsonArray.length()));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject play_Object = jsonArray.getJSONObject(i);

                String unionId = play_Object.getString("unionId");
                String type = play_Object.getString("type");
                String language= play_Object.getString("language");
                String name = play_Object.getString("name");
                String introduction = play_Object.getString("introduction");
                String image = play_Object.getString("image");
                int    length = play_Object.getInt("length");

                Play play = new Play();
                play.setPlay_id(unionId);
                play.setPlay_type(type);
                play.setPlay_language(language);
                play.setPlay_name(name);
                play.setPlay_intro(introduction);
                play.setPicture_path(image);
                play.setPlay_length(length);
                playList.add(play);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return playList;
    }

    public static Play toPlay(String jsonData) {                   //单条目查询用这个
        Play play = new Play();
        try {

            JSONObject jsonObject = new JSONObject(jsonData);
            String play_info = jsonObject.getString("play");

            JSONObject play_Object = new JSONObject(play_info);
            String unionId = play_Object.getString("unionId");
            String type = play_Object.getString("type");
            String language= play_Object.getString("language");
            String name = play_Object.getString("name");
            String introduction = play_Object.getString("introduction");
            String image = play_Object.getString("image");
            int    length = play_Object.getInt("length");

            play.setPlay_id(unionId);
            play.setPlay_type(type);
            play.setPlay_language(language);
            play.setPlay_name(name);
            play.setPlay_intro(introduction);
            play.setPicture_path(image);
            play.setPlay_length(length);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return play;
    }

    public static String login(String jsonData) {                   //登录   返回失败或者用户身份 1 2 3
        String log_status = null;
        try {

            JSONObject jsonObject = new JSONObject(jsonData);
            log_status = jsonObject.getString("result");

        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return log_status;
    }
}
