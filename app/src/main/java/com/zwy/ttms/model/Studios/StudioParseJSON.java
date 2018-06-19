package com.zwy.ttms.model.Studios;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudioParseJSON {

    public static List<Studios> toStudiolist(String jsonData) {              //  查询所有用这个
        List<Studios> studioList = new ArrayList<>();
        try {

            JSONObject jsonObject = new JSONObject(jsonData);
            String data = jsonObject.getString("studioList");
            JSONArray jsonArray = new JSONArray(data);


            Log.i("length = ", String.valueOf(jsonArray.length()));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject user_Object = jsonArray.getJSONObject(i);

                String unionId = user_Object.getString("unionId");
                String name = user_Object.getString("name");
                String row = user_Object.getString("row");
                String col = user_Object.getString("col");
                Studios studio = new Studios();
                studio.setUnionId(unionId);
                studio.setName(name);
                studio.setRow(row);
                studio.setCol(col);

                studioList.add(studio);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studioList;
    }

    public static Studios toStudios(String jsonData) {                   //单条目查询用这个
        Studios studio = new Studios();
        try {

            JSONObject jsonObject = new JSONObject(jsonData);
            String user_info = jsonObject.getString("studio");

            JSONObject user_Object = new JSONObject(user_info);
            String unionId = user_Object.getString("unionId");
            String name = user_Object.getString("name");
            String row = user_Object.getString("row");
            String col = user_Object.getString("col");

            studio.setUnionId(unionId);
            studio.setName(name);
            studio.setRow(row);
            studio.setCol(col);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return studio;
    }


    public static String getStatus(String jsonData) {
        String status = null;
        try {

            JSONObject jsonObject = new JSONObject(jsonData);
            status = jsonObject.getString("result");

        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return status;
    }
}
