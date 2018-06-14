package com.zwy.ttms.model.users;

public class Users {

    public static final String ADMINISTRATOR = "1";
    public static final String MANAGER ="2";
    public static final String CONDUCTOR ="3";

    private String updateTime;
    private String unionId;
    private String name;
    private String pass;
    private String identity;
    private String teL;
    private String addr;
    private String email;
    private String role_name;

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        setRole_name(identity);
        this.identity = identity;
    }

    public String getTeL() {
        return teL;
    }

    public void setTeL(String teL) {
        this.teL = teL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public void setRole_name(String role){
        if(role .equals(Users.ADMINISTRATOR))
            role_name = "管理员";
        else if(role.equals(Users.CONDUCTOR))
            role_name = "售票员";
        else if(role.equals(Users.MANAGER))
            role_name ="经理";
    }
    public static String toIdentity(String role){
        String roleId = "3";
        if(role.equals("管理员")){
            roleId = Users.ADMINISTRATOR;
        }
        else if(role.equals("经理")){
            roleId = Users.MANAGER;
        }
        else if(role.equals("售票员")){
            roleId = Users.CONDUCTOR;
        }
        return roleId;
    }

    public String getRole_name() {
        return role_name;
    }
}
