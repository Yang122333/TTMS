package com.zwy.ttms.model.schedule;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 2018/6/14.
 */

public class ScheduleAndParseJson {
    public static List<Plays> toPlaysList(String jsonData) {              //  查询所有用这个
        List<Plays> playsList = new ArrayList<>();
        try {

            JSONObject jsonObject = new JSONObject(jsonData);
            String data = jsonObject.getString("scheduleList");
            JSONArray jsonArray = new JSONArray(data);


            Log.i("length = ", String.valueOf(jsonArray.length()));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject plays_Object = jsonArray.getJSONObject(i);
                String unionID = plays_Object.getString("unionId");
                String studioName = plays_Object.getString("studioId");

                String playName = plays_Object.getString("playId");
                String time = plays_Object.getString("time");
                String price = plays_Object.getString("price");
                String status = plays_Object.getString("status");


                Plays plays = new Plays();
                plays.setUnionID(unionID);
                plays.setPlayStudio(studioName);
                plays.setPlayName(playName);
                plays.setPlayPrice(price);
                plays.setPlayTime(time);
                plays.setStatus(status);

                playsList.add(plays);
                Log.i("qwer", playsList.size()+"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("12345134", playsList.size()+"");
        return playsList;
    }

    public static Plays toPlays(String jsonData) {                   //单条目查询用这个
        Plays plays = new Plays();
        try {

            JSONObject jsonObject = new JSONObject(jsonData);
            String schedule_info = jsonObject.getString("scheduleList");

            JSONObject plays_Object = new JSONObject(schedule_info);
            String studioName = plays_Object.getString("studioId");
            String playName = plays_Object.getString("playId");
            String time = plays_Object.getString("time");
            String price = plays_Object.getString("price");
            String status = plays_Object.getString("status");

            plays.setPlayStudio(studioName);
            plays.setPlayName(playName);
            plays.setPlayTime(time);
            plays.setPlayPrice(price);
            plays.setStatus(status);

        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return plays;
    }

    public static String getRespond(String jsonData) {                   //登录   返回失败或者用户身份 1 2 3
        String res = null;
        try {

            JSONObject jsonObject = new JSONObject(jsonData);
            res = jsonObject.getString("result");

        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return res;
    }
}