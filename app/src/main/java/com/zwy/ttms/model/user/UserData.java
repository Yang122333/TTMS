package com.zwy.ttms.model.user;

/**
 * Created by ${Yang} on 2018/4/26.
 */

public class UserData {
    /**
     * 数据库信息
     */
    public static final String SQLITE_NAME ="ttms.db";
    public static final int SQLITE_VERSION  = 1;
    /**
     * 信息表，及其字段
     */
    public static final String USER_TABLE ="user_table";
    public static final String USERID     ="userid";
    public static final String LOGINNAME  ="loginname";
    public static final String PASSWORD   ="password";
    public static final String ROLE       = "role";


    public static final int INSERT_REPEAT  = -1;
    public static final int INSERT_ILLEGAL = -2;
    public static final int INSERT_EMPTY   = -3;
    public static final int INSERT_SUCCESS = 1;



    /**
     * 时间字段的格式
     */
//    public static final String DATE_FORMAT="YYYY-MM-DD";
    /**
     * 时间字段的降序，采用date函数比较
     */
//    public static final String ORDER_BY="date("+COLUMN_DATE+") desc";
}
