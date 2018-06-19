package com.zwy.ttms;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.zwy.ttms.Administrator.AdministratorUI;
import com.zwy.ttms.Manager.ManagerUI;
import com.zwy.ttms.conductor.ConductorUI;
import com.zwy.ttms.model.Service.HttpCallbackListener;
import com.zwy.ttms.model.Service.HttpUtil;
import com.zwy.ttms.model.users.UserAndLogParseJSON;
import com.zwy.ttms.model.users.Users;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ${Yang} on 2018/4/12.
 */




public class LoginActivity extends Activity{

    public static final String ip = "http://192.168.1.199:8080/";

    private EditText username;
    private EditText password;
    private ImageView loginbutton;

    public static final int SHOW_RESPONSE = 0;
    private Handler handler;




    private View.OnClickListener mylistener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String name = username.getText().toString();
            String pass = password.getText().toString();

            String address = ip+"login?method=login&name="+name+"&pass="+pass;
            HttpUtil.sendHttpRequest(address, "POST",new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    Log.i("Server", response);
                    String log_status = UserAndLogParseJSON.login(response);
                    login(log_status);
                }

                @Override
                public void onError(Exception e) {


                }
            });

        }//#onClick
    };

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox rememberPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        rememberPass = (CheckBox) findViewById(R.id.remember_pass);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R .id.password);
        loginbutton = (ImageView)findViewById(R.id.loginbutton);

        pref = PreferenceManager.getDefaultSharedPreferences(this);

        boolean isRemember = pref.getBoolean("remember_password", false);
        if (isRemember) {
// 将账号和密码都设置到文本框中
            String account = pref.getString("account", "");
            String passwords = pref.getString("password", "");
            username.setText(account);
            password.setText(passwords);
            rememberPass.setChecked(true);
        }

        loginbutton.setOnClickListener(mylistener);
         handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SHOW_RESPONSE:
                        new AlertDialog.Builder(LoginActivity.this)
                                .setMessage("登录失败！")
                                .setPositiveButton("确定", null)
                                .show();
                }
                }
            };
    }
    private boolean login(String log_status){

            boolean log = false ;

            if(log_status.equals("fail")){

                Message message = new Message();
                message.what = SHOW_RESPONSE;
// 将服务器返回的结果存放到Message中
                handler.sendMessage(message);
            }
            else{

                editor = pref.edit();
                if (rememberPass.isChecked()) { // 检查复选框是否被选中
                      editor.putBoolean("remember_password", true);
                      editor.putString("account", username.getText().toString());
                      editor.putString("password", password.getText().toString());
                } else {
                    editor.clear();
             }

             editor.commit();

                if (log_status .equals(Users.ADMINISTRATOR)) {  //管理员
                    log = true;
                    AdministratorUI.actionStart(LoginActivity.this);
                } else if (log_status .equals(Users.MANAGER)) {    //经理
                    log = true;
                    ManagerUI.actionStart(LoginActivity.this);
                } else if (log_status .equals(Users.CONDUCTOR)) {   //售票员
                    log = true;
                    ConductorUI.actionStart(LoginActivity.this);
                }
            }

        return log;
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setMessage("是否退出该系统？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LoginActivity.this.finish();
                    }
                })
                .setNegativeButton("取消",null)
                .show();
    }








    private String parseJSONWithJSONObject(String jsonData){
        String result_message = null;
        try {
            JSONObject resultJson = new JSONObject(jsonData);
             result_message = resultJson.getString("result").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result_message;
    }
}
