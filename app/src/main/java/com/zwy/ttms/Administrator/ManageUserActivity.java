package com.zwy.ttms.Administrator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import com.zwy.ttms.LoginActivity;
import com.zwy.ttms.R;
import com.zwy.ttms.model.Service.HttpCallbackListener;
import com.zwy.ttms.model.Service.HttpUtil;
import com.zwy.ttms.model.user.UserDao;
import com.zwy.ttms.model.users.UserAndLogParseJSON;
import com.zwy.ttms.model.users.Users;

import java.util.List;

/**
 * Created by ${Yang} on 2018/4/16.
 */

public class ManageUserActivity extends Activity {

    public static  void actionStart(Context context){
        Intent intent = new Intent(context,ManageUserActivity.class);
        context.startActivity(intent);
    }

    private Button add_user;
    private ViewGroup tableTitle;
    private ListView tableListView;


    private UserDao data;
    private UserAdapter adapter;
    private List<Users> list;
    private int currentPosition;

    private String identity = null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_user);
        InitUserList();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        createView();
        data = new UserDao(this);
        setListener();
//        data.close();
    }
    private void createView(){
        adapter = new UserAdapter(ManageUserActivity.this,list);
        add_user = (Button) findViewById(R.id.add_user);
        tableTitle = (ViewGroup) findViewById(R.id.table_title);
        tableListView = (ListView) findViewById(R.id.list);
        tableListView.setAdapter(adapter);

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
                            adapter.notifyDataSetChanged();
                            tableListView.setSelection(list.size());
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

                   DeleteUser(list.get(currentPosition).getUnionId());

                adapter.notifyDataSetChanged();
                tableListView.setSelection(list.size());
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
                            adapter.notifyDataSetChanged();
                            tableListView.setSelection(list.size());
                            dialog1.dismiss();

                        }//#else
                    }//#if
                    }//#onclick
                });
            }


        };
    }
    public void DeleteUser(String UnionId){
        String ip = LoginActivity.ip;
        String address = ip+"administrator?method=deleterUser&unionId="+UnionId;
        Log.i("durl", address);
        HttpUtil.sendHttpRequest(address, "POST", new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("delete", response);
                String res = UserAndLogParseJSON.login(response);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
    public void InsertData(Users users){
        String ip = LoginActivity.ip;
        final String address = ip+"administrator?method=addUser&name="+users.getName()+"&pass="+users.getPass()
                +"&identity="+users.getIdentity()+"&teL="+users.getTeL()+"&addr="+users.getAddr()+"&email="+users.getEmail();
        HttpUtil.sendHttpRequest(address, "POST", new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.i("urlurlu", address);
                String res = UserAndLogParseJSON.login(response);
                Log.i("DELETE", response);

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
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}
