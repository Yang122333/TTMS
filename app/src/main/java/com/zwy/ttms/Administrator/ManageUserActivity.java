package com.zwy.ttms.Administrator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zwy.ttms.LoadingDialog;
import com.zwy.ttms.LoginActivity;
import com.zwy.ttms.R;
import com.zwy.ttms.model.Service.HttpCallbackListener;
import com.zwy.ttms.model.Service.HttpUtil;
import com.zwy.ttms.model.users.UserAndLogParseJSON;
import com.zwy.ttms.model.users.Users;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${Yang} on 2018/4/16.
 */

public class ManageUserActivity extends Activity {

    public static  void actionStart(Context context){
        Intent intent = new Intent(context,ManageUserActivity.class);
        context.startActivity(intent);
    }
    private static final int ADD_USER = 1;
    private static final int DELETE_USER = 2;
    private static final int UPDATE_USER = 3;
    private static final int QUERY_USER = 4;
    private Button add_user;
    private ViewGroup tableTitle;
    private ListView tableListView;
    private LoadingDialog loadingDialog = new LoadingDialog(this);
    private UserAdapter adapter;
    private List<Users> list = new ArrayList<>();
    private int currentPosition;
    private Handler handler;
    private String identity = null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_user);
        InitUserList();
        createView();
        setListener();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
//                    case ADD_USER:{
//                        list.add((Users) msg.obj);
//                        adapter.notifyDataSetChanged();
//                        tableListView.setSelection(list.size());
//                        break;
//
//                    }
//                    case DELETE_USER:{
//                        list.remove(msg.arg1);
//                        adapter.notifyDataSetChanged();
//                        tableListView.setSelection(list.size());
//                        break;
//                    }
//                    case UPDATE_USER:{
//                        break;
//                    }
                    case QUERY_USER:{

                        adapter = new UserAdapter(ManageUserActivity.this,list);
                        tableListView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        tableListView.setSelection(list.size());
                        break;
                    }
                }
                super.handleMessage(msg);
            }
        };
    }



    private void createView(){

        add_user = (Button) findViewById(R.id.add_user);
        tableTitle = (ViewGroup) findViewById(R.id.table_title);
        tableListView = (ListView) findViewById(R.id.list);


    }
    private void setListener(){
        tableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                  currentPosition = position;

                AlertDialog.Builder builder= new AlertDialog.Builder(ManageUserActivity.this);
                builder.setPositiveButton("修改",update())
                       .setNegativeButton("删除",delete());
                builder.create().show();
            }
        });
        add_user.setOnClickListener(add());

    }

    private DialogInterface.OnClickListener update(){

        return new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ManageUserActivity.this);
                final AlertDialog dialog1 = builder.create();
                View dialogView = View.inflate(ManageUserActivity.this, R.layout.change_user, null);
                //设置对话框布局
                dialog1.setView(dialogView);
                dialog1.show();
                final TextView user_name = (TextView) dialogView.findViewById(R.id.user_name);
                final EditText user_pwd = (EditText) dialogView.findViewById(R.id.user_pwd);
                final TextView user_idt = (TextView)dialogView.findViewById(R.id.user_identity);
                Button finishiChange = (Button)dialogView.findViewById(R.id.finish_Change);
                user_name.setText(list.get(currentPosition).getName());
                user_pwd.setText(list.get(currentPosition).getPass());
                user_idt.setText(list.get(currentPosition).getRole_name());


                finishiChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (user_name.getText().toString().equals("") ||
                                user_pwd.getText().toString().indexOf(" ") != -1 ) {
                            Toast.makeText(ManageUserActivity.this, "输入不合法值为空或含有空格！", Toast.LENGTH_SHORT).show();
                        } else {
                            Users users = new Users();
                            users.setUnionId(list.get(currentPosition).getUnionId());
                            users.setName(user_name.getText().toString());
                            users.setPass(user_pwd.getText().toString());
                            users.setIdentity(list.get(currentPosition).getIdentity());
                            users.setAddr("null");
                            users.setTeL("null");
                            users.setEmail("null");
                            UpdateData(users);
                            dialog1.dismiss();
                        }
                    }
                });
            }
        };
    }
    private DialogInterface.OnClickListener delete(){
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                   DeleteUser(list.get(currentPosition).getUnionId(),currentPosition);

                dialog.dismiss();
            }
        };
    }
    private View.OnClickListener add(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ManageUserActivity.this);
                final AlertDialog dialog1 = builder.create();
                View dialogView = View.inflate(ManageUserActivity.this, R.layout.add_user, null);
                //设置对话框布局
                dialog1.setView(dialogView);
                dialog1.show();


                final EditText user_Name = (EditText) dialogView.findViewById(R.id.user_name);
                final EditText user_pwd = (EditText) dialogView.findViewById(R.id.user_pwd);

                RadioGroup role =(RadioGroup)dialog1.findViewById(R.id.role_choise);
                role.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        RadioButton r = (RadioButton)group.findViewById(checkedId);
                        if(r != null)
                        identity =  r.getText().toString();
                    }
                });


                Button finishiChange = (Button)dialogView.findViewById(R.id.finish_Change);


                finishiChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (identity == null) {
                            Toast.makeText(ManageUserActivity.this, "请选择身份!", Toast.LENGTH_SHORT).show();

                        } else{
                            if (user_Name.getText().toString().isEmpty() ||
                                user_pwd.getText().toString().equals("") ||
                                user_Name.getText().toString().indexOf(" ") != -1 ||
                                user_pwd.getText().toString().indexOf(" ") != -1
                                ) {
                            Toast.makeText(ManageUserActivity.this, "输入不合法值为空或含有空格！", Toast.LENGTH_SHORT).show();
                        } else {
                            Users users = new Users();
                            users.setName(user_Name.getText().toString());
                            users.setPass(user_pwd.getText().toString());
                            users.setIdentity(users.toIdentity(identity));
                            users.setAddr("null");
                            users.setTeL("null");
                            users.setEmail("null");
                            InsertData(users);
                            dialog1.dismiss();

                        }//#else
                    }//#if
                    }//#onclick
                });
            }


        };
    }
    public void DeleteUser(String UnionId, final int position){
        String ip = LoginActivity.ip;
        String address = ip+"administrator?method=deleterUser&unionId="+UnionId;
        Log.i("durl", address);
        HttpUtil.sendHttpRequest(address, "POST", new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("delete", response);
                String res = UserAndLogParseJSON.login(response);
                InitUserList();
//                Message message = new Message();
//                message.arg1 = position;
//                message.what = DELETE_USER;
//                handler.sendMessage(message);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
    public void InsertData(final Users users){
        String ip = LoginActivity.ip;
        final String address = ip+"administrator?method=addUser&name="+users.getName()+"&pass="+users.getPass()
                +"&identity="+users.getIdentity()+"&teL="+users.getTeL()+"&addr="+users.getAddr()+"&email="+users.getEmail();
        HttpUtil.sendHttpRequest(address, "POST", new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.i("urlurlu", address);
                String res = UserAndLogParseJSON.login(response);
                Log.i("DELETE", response);
                InitUserList();
//                Message message = new Message();
//                message.what = ADD_USER;
//                message.obj = users;
//                handler.sendMessage(message);

            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
    public void UpdateData(Users users){
        String ip = LoginActivity.ip;
        final String address = ip+"administrator?method=updateUser&unionId="+users.getUnionId()+"&name="+users.getName()+"&pass="+users.getPass()
                +"&identity="+users.getIdentity()+"&teL="+users.getTeL()+"&addr="+users.getAddr()+"&email="+users.getEmail();
        Log.i("urlurl", address);
        HttpUtil.sendHttpRequest(address, "POST", new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                String res = UserAndLogParseJSON.login(response);
                Log.i("OKOKOKOK", response);
                InitUserList();

            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
    public void InitUserList(){

        String ip =LoginActivity.ip;
        String address = ip+"administrator?method=getUserList";
        HttpUtil.sendHttpRequest(address, "GET", new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.i("INSERT", response);
                list = UserAndLogParseJSON.toUserlist(response);
                Log.i("OKKK", list.size()+"");
                Message message = new Message();
                message.what = QUERY_USER;
                handler.sendMessage(message);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}
