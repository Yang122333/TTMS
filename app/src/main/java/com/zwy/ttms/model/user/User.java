package com.zwy.ttms.model.user;

/**
 * Created by ${Yang} on 2018/4/16.
 */

public class User {
    public static final int ADMINISTRATOR = 1;
    public static final int MANAGER =2;
    public static final int CONDUCTOR =3;
    private int userId ;
    private String loginName;
    private String password;
    private int role;
    private String role_name;
    public User(){
        password = "123456";
        role = CONDUCTOR;
        setRole_name(role);
    }
    public User(String loginName ,String password , int role){
        this.loginName = loginName;
        this.password = password;
        this.role = role;
        setRole_name(role);
    }
    public void setRole_name(int role){
        if(role ==User.ADMINISTRATOR)
            role_name = "管理员";
        else if(role == User.CONDUCTOR)
            role_name = "售票员";
        else if(role == User.MANAGER)
            role_name ="经理";
    }
    public String getLogin_name() {
        return loginName;
    }

    public void setLogin_name(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        setRole_name(role);
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRole_name() {
        return role_name;
    }
    public static String toRole(int roleId){
        String role ="售票员";
        if(roleId == User.ADMINISTRATOR)
        {
            role = "管理员";
        }
        else if (roleId ==User.MANAGER){
            role ="经理";
        }
        else if (roleId == User.CONDUCTOR){
            role = "售票员";
        }
        return role;
    }
    public static int toIdentity(String role){
        int roleId = 3;
        if(role.equals("管理员")){
            roleId = User.ADMINISTRATOR;
        }
        else if(role.equals("经理")){
            roleId = User.MANAGER;
        }
        else if(role.equals("售票员")){
            roleId = User.CONDUCTOR;
        }
        return roleId;
    }
}
