package com.zwy.ttms.model.users;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserAndLogParseJSON {
    public static List<Users> toUserlist(String jsonData) {              //  查询所有用这个
        List<Users> userList = new ArrayList<>();
        try {

            JSONObject jsonObject = new JSONObject(jsonData);
            String data = jsonObject.getString("userList");
            JSONArray jsonArray = new JSONArray(data);


            Log.i("length = ", String.valueOf(jsonArray.length()));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject user_Object = jsonArray.getJSONObject(i);

                String unionId = user_Object.getString("unionId");
                String name = user_Object.getString("name");
                String pass = user_Object.getString("pass");
                String identity = user_Object.getString("identity");
                String teL = user_Object.getString("teL");
                String addr = user_Object.getString("addr");
                String email = user_Object.getString("email");

                Users user = new Users();
                user.setUnionId(unionId);
                user.setName(name);
                user.setPass(pass);
                user.setIdentity(identity);
                user.setTeL(teL);
                user.setAddr(addr);
                user.setEmail(email);

                userList.add(user);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    public static Users toUser(String jsonData) {                   //单条目查询用这个
        Users user = new Users();
        try {

            JSONObject jsonObject = new JSONObject(jsonData);
            String user_info = jsonObject.getString("user");

            JSONObject user_Object = new JSONObject(user_info);
            String unionId = user_Object.getString("unionId");
            String name = user_Object.getString("name");
            String pass = user_Object.getString("pass");
            String identity = user_Object.getString("identity");
            String teL = user_Object.getString("teL");
            String addr = user_Object.getString("addr");
            String email = user_Object.getString("email");

            user.setUnionId(unionId);
            user.setName(name);
            user.setPass(pass);
            user.setIdentity(identity);
            user.setTeL(teL);
            user.setAddr(addr);
            user.setEmail(email);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return user;
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