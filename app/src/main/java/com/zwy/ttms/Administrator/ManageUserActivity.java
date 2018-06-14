package com.zwy.ttms.Administrator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

import com.zwy.ttms.R;
import com.zwy.ttms.model.user.UserDao;
import com.zwy.ttms.model.user.UserData;
import com.zwy.ttms.model.user.User;

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

    private Button add_user;
    private ViewGroup tableTitle;
    private ListView tableListView;


    private UserDao data;
    private UserAdapter adapter;
    private List<User> list;
    private int currentPosition;

    private String identity = null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_user);
        createView();
        data = new UserDao(this);
        initUsers();
        setListener();
//        data.close();
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
    private void initUsers(){
        tableTitle.setBackgroundColor(Color.rgb(177, 173, 172));
        list =  new ArrayList<>();
        list =data.queryAll();
        if(list == null) {
            data.insert(new User("1", "1", User.ADMINISTRATOR));
            data.insert(new User("2", "2", User.CONDUCTOR));
            data.insert(new User("3", "3", User.MANAGER));
            data.insert(new User("4", "4", User.ADMINISTRATOR));
            data.insert(new User("5", "5", User.CONDUCTOR));
            data.insert(new User("6", "6", User.MANAGER));
        }
            adapter = new UserAdapter(this, list);
        tableListView.setAdapter(adapter);

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

                final TextView user_ID = (TextView) dialogView.findViewById(R.id.user_id);
                final EditText user_pwd = (EditText) dialogView.findViewById(R.id.user_pwd);
                final TextView user_Identity = (TextView) dialogView.findViewById(R.id.user_identity);
                Button finishiChange = (Button)dialogView.findViewById(R.id.finish_Change);
                user_ID.setText(String.valueOf(list.get(currentPosition).getUserId()));
                user_pwd.setText(list.get(currentPosition).getPassword());

                user_Identity.setText(User.toRole(list.get(currentPosition).getRole()));

                finishiChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (user_pwd.getText().toString().equals("") ||
                                user_Identity.getText().toString().equals("") ||
                                user_pwd.getText().toString().indexOf(" ") != -1 ||
                                user_Identity.getText().toString().indexOf(" ") != -1) {
                            Toast.makeText(ManageUserActivity.this, "输入不合法值为空或含有空格！", Toast.LENGTH_SHORT).show();
                        } else {
                            User user = new User(
                                    list.get(currentPosition).getLogin_name(),
                                    user_pwd.getText().toString(),
                                    list.get(currentPosition).getRole());
                            data.updateById(list.get(currentPosition).getUserId(),
                                    user);

                            list.set(currentPosition, user);
                            adapter.notifyDataSetChanged();
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
                if(!list.get(currentPosition).getLogin_name().equals("1"))
                {
                    data.deleteById(list.get(currentPosition).getUserId());
                    list.remove(currentPosition);
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(ManageUserActivity.this,"无法删除超级用户！",Toast.LENGTH_LONG).show();
                }

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


                final  EditText user_ID = (EditText) dialogView.findViewById(R.id.user_id);
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
                            User user = new User(
                                    user_Name.getText().toString(),
                                    user_pwd.getText().toString(),
                                    User.toIdentity(identity));

                            switch (data.insert(user)) {

                                case UserData.INSERT_REPEAT: {
                                    Toast.makeText(ManageUserActivity.this, "该账号已存在，请重新输入！", Toast.LENGTH_SHORT).show();

                                    break;
                                }
                                case UserData.INSERT_ILLEGAL: {
                                    Toast.makeText(ManageUserActivity.this, "输入不合法，不能含有空格请重新输入！", Toast.LENGTH_SHORT).show();

                                    break;
                                }
                                case UserData.INSERT_EMPTY: {
                                    Toast.makeText(ManageUserActivity.this, "输入不能为空！", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                                default: {
                                    Toast.makeText(ManageUserActivity.this, "添加成功！", Toast.LENGTH_SHORT);
                                    int maxUserId = data.findLastestId();

                                    user.setUserId(maxUserId);
                                    list.add(user);

                                    adapter.notifyDataSetChanged();
                                    tableListView.setSelection(list.size());
                                    dialog1.dismiss();
                                    break;
                                }

                            }//#switch
                        }//#else
                    }//#if
                    }//#onclick
                });
            }


        };
    }

}
