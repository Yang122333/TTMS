package com.zwy.ttms.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.zwy.ttms.model.user.UserData;

/**
 * Created by ${Yang} on 2018/4/26.
 */

public class DataHelper extends SQLiteOpenHelper {
    private final String TAG = "DataHelper";
    public static final String CREATE_USER ="create table "+
            UserData.USER_TABLE+" ("+
            UserData.USERID + " integer primary key autoincrement,"+
            UserData.LOGINNAME + " varchar(50)," +
            UserData.PASSWORD + " varchar(50),"+
            UserData.ROLE +" integer"+
            ")";
    public  static final String CREATE_PLAY="create table play("
            + "play_id integer primary key autoincrement, "
            +"play_type text, "
            +"play_name text, "
            +"play_introduction text, "
            +"play_length integer, "
            +"emp_status text)";

    public  static  final  String CREATE_SALE ="create table sale("
            +"sale_id integer primary key autoincrement, "
            +"emp_id integer , "
            +"ticket_id integer, "
            +"sale_status integer, "
            +"sale_price integer)";
    public  static  final String CREATE_SCHEDULE = "create table schedule( "
            +"sched_id integer primary key autoincrement, "
            +"studio_id integer, "
            +"play_id   integer, "
            +"sched_time text, "
            +"sched_ticket_price integer)";
    public  static  final String CREATE_SEAT ="create table seat( "                                //需要删除行列加入座位号（1到n）
            +"seat_id integer primary key autoincrement, "
            +"studio_id integer, "
            +"seat_number integer, "
            +"seat_status integer)";
    public  static  final  String CREATE_STUDIO ="create table studio( "
            +"studio_id integer primary key autoincrement, "
            +"studio_name text, "
            +"studio_row_count integer, "
            +"studio_col_count integer, "
            +"seat_info text, "
            +"studio_status integer)";
    public  static  final String CREATE_TICKET = "create table ticket("
            +"ticket_id integer primary key autoincrement, "
            +"seat_id   integer, "
            +"sche_id   integer, "
            +"ticket_status integer) ";

    public DataHelper(Context context){
        super(context, UserData.SQLITE_NAME,null, UserData.SQLITE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_PLAY);
        db.execSQL(CREATE_SALE);
        db.execSQL(CREATE_SCHEDULE);
        db.execSQL(CREATE_SEAT);
        db.execSQL(CREATE_STUDIO);
        db.execSQL(CREATE_TICKET);
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
    public boolean deleteDatabase(Context context) {
        Log.i(TAG, "delete Database------------->");
        return context.deleteDatabase(UserData.SQLITE_NAME);
    }
}
