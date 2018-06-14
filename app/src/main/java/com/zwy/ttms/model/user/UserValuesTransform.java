package com.zwy.ttms.model.user;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by ${Yang} on 2018/4/26.
 */

public class UserValuesTransform {

    public static User transformUser(Cursor cursor){

        User user = new User();
        user.setUserId(cursor.getInt(cursor.getColumnIndex(UserData.USERID)));
        user.setLogin_name(cursor.getString(cursor.getColumnIndex(UserData.LOGINNAME)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(UserData.PASSWORD)));
        user.setRole(cursor.getInt(cursor.getColumnIndex(UserData.ROLE)));

        return user;
    }
    public static ContentValues transformContentValues(User user){
        ContentValues contentValues =new ContentValues();
        contentValues.put(UserData.LOGINNAME,user.getLogin_name());
        contentValues.put(UserData.PASSWORD,user.getPassword());
        contentValues.put(UserData.ROLE,user.getRole());
        return contentValues;
    }
}
