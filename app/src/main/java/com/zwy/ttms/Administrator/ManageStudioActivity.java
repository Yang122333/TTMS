package com.zwy.ttms.Administrator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zwy.ttms.LoginActivity;
import com.zwy.ttms.R;
import com.zwy.ttms.model.Service.HttpCallbackListener;
import com.zwy.ttms.model.Service.HttpUtil;
import com.zwy.ttms.model.Studios.StudioParseJSON;
import com.zwy.ttms.model.Studios.Studios;
import com.zwy.ttms.model.users.UserAndLogParseJSON;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${Yang} on 2018/4/12.
 */

public class ManageStudioActivity extends Activity implements View.OnClickListener{
    private Handler handler;
    private Handler handler1;
    private static final int ADD_SUCCEED = 1;
    private static final int QUERY_SUCCEED = 0;
    private  ListView listView;
    private List<Studios> studioList = new ArrayList<>();
    private StudioAdapter adapter;
    private String add_status;

    public static void actionStart(Context context){
        Intent intent = new Intent(context,ManageStudioActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studio);
        TextView textView = (TextView)findViewById(R.id.title_message);
        textView.setText("演出厅管理");

        listView =(ListView)findViewById(R.id.studio_list);
        final Button  btn = (Button)findViewById(R.id.add_studio);

        init();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case ADD_SUCCEED:
                        Log.i("server123", "handleMessage: 213            ");
                        break;
                    case QUERY_SUCCEED:
                        Log.i("server123", "handleMessage: 21435245435");
                        adapter = new StudioAdapter(ManageStudioActivity.this,R.layout.studio_item,studioList);
                        listView.setAdapter(adapter);
                }
                super.handleMessage(msg);

            }
        };


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position , long id) {

                Intent intent = new Intent( ManageStudioActivity.this,ManageSeatActivity.class);
                intent.putExtra("studio_name",studioList.get(position).getName());
                intent.putExtra("studio_row",studioList.get(position).getRow());
                intent.putExtra("studio_col",studioList.get(position).getCol());
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int i, long l) {

                AlertDialog.Builder builder =new AlertDialog.Builder(ManageStudioActivity.this);
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int q){


                        String name =studioList.get(i).getName();
                        Log.i("intnet", name);
                        String ip = LoginActivity.ip;
                        String address = ip+"administrator?method=deleterStudioByName&name=" + name;
                        Log.i("server", address);

                        HttpUtil.sendHttpRequest(address , "POST",new HttpCallbackListener() {
                            @Override
                            public void onFinish(String response) {
                                Log.i("intnet", response);
                                add_status = UserAndLogParseJSON.login(response);
                                Log.i("intnet", add_status);
//                                Message message = new Message();
//                                message.what = ADD_SUCCEED;
//// 将服务器返回的结果存放到Message中
//                                handler2.sendMessage(message);
                            };
                            @Override
                            public void onError(Exception e) {

                            }
                        });



                        studioList.remove(i);
                        adapter.notifyDataSetChanged();
                        listView.setSelection(studioList.size());
                    }
                })
                        .setNegativeButton("修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int q) {
                                modify(i);
                            }
                        });
                AlertDialog alertDialog1 = builder.create();
                alertDialog1.show();
                return true;
            }
        });
        btn.setOnClickListener(this);
    }

    private void init() {
        String ip = LoginActivity.ip;
        String address = ip+"administrator?method=getStudioList";

        HttpUtil.sendHttpRequest(address, "GET", new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {


                studioList = StudioParseJSON.toStudiolist(response);
                Log.i("studiolist", studioList.get(0).getName()+" "+studioList.get(0).getIntroduce());
                Message message = new Message();
                message.what = QUERY_SUCCEED;
                handler.sendMessage(message);

            }

            @Override
            public void onError(Exception e) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_studio:
                addInfo();
                break;
            default:
                break;
        }
    }
    private void addInfo(){
        View view1 = View.inflate(this,R.layout.altert_dialog,null);

         final AlertDialog.Builder builder =new AlertDialog.Builder(this);

         builder.setTitle("演出厅信息")
                .setView(view1)
                .setCancelable(false);
        final AlertDialog alertDialog =builder.create();
        final EditText name = (EditText)view1.findViewById(R.id.studio_name);
        final EditText row = (EditText)view1.findViewById(R.id.studio_row);
        final EditText column = (EditText)view1.findViewById(R.id.studio_column);
        final EditText introduce = (EditText)view1.findViewById(R.id.studio_introduce);
        Button cancel = (Button)view1.findViewById(R.id.studio_cancel);
        Button confirm = (Button)view1.findViewById(R.id.studio_confirm);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                handler1 = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what){
                            case ADD_SUCCEED:
                                Log.i("handler1", "handleMessage: ");
                                if(add_status.equals("succeed")){
                                    Log.i("succeed", add_status);

                                    Studios studio =new Studios();
                                    studio.setName(name.getText().toString());
                                    studio.setRow(row.getText().toString());
                                    studio.setCol(column.getText().toString());

                                    studioList.add(studio);

                                    adapter.notifyDataSetChanged();
                                    listView.setSelection(studioList.size());
                                    alertDialog.dismiss();                                            //error

                                }
                                else{
                                    Toast.makeText(ManageStudioActivity.this, "已存在该演出厅", Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }

                        super.handleMessage(msg);
                    }
                };


                    if(!"".equals(name.getText().toString() ) &&
                            name.getText().toString().indexOf(" ") == -1 &&
                            !"".equals(row.getText().toString()) &&
                            !"".equals(column.getText().toString())  )
                    {


                        String ip = LoginActivity.ip;
                        String address = ip+"administrator?method=addStudio&name="
                                +name.getText().toString()
                                +"&row="+row.getText().toString()
                                +"&col="+column.getText().toString()
                                +"&introduce="+introduce.getText().toString();
                        Log.i("server", address);

                        HttpUtil.sendHttpRequest(address , "POST",new HttpCallbackListener() {
                            @Override
                            public void onFinish(String response) {
                                Log.i("intnet", response);
                                 add_status = UserAndLogParseJSON.login(response);
                                Log.i("intnet", add_status);
                                Message message = new Message();
                                message.what = ADD_SUCCEED;
// 将服务器返回的结果存放到Message中
                                handler1.sendMessage(message);
                            };
                            @Override
                            public void onError(Exception e) {

                            }
                        });

                    }
                    else{
                        Toast.makeText(ManageStudioActivity.this,"输入不合法，请重新输入！",Toast.LENGTH_SHORT).show();

                    }

            }
        });
        alertDialog.show();
    }
    private  void modify(final int flag){
        View view1 = View.inflate(this,R.layout.altert_dialog,null);

        AlertDialog.Builder builder =new AlertDialog.Builder(this);

        builder.setTitle("演出厅信息")
                .setView(view1);

        final AlertDialog alertDialog = builder.create();
        final EditText name = (EditText)view1.findViewById(R.id.studio_name);
        final EditText row = (EditText)view1.findViewById(R.id.studio_row);
        final EditText column = (EditText)view1.findViewById(R.id.studio_column);
        final EditText introduce = (EditText)view1.findViewById(R.id.list_introduce);

        Studios s = studioList.get(flag);

        name.setText(s.getName());
        row.setText(String.valueOf(s.getRow()));
        column.setText(String.valueOf(s.getCol()));

        Button cancel = (Button)view1.findViewById(R.id.studio_cancel);
        Button confirm = (Button)view1.findViewById(R.id.studio_confirm);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(!"".equals(name.getText().toString() ) &&!"".equals(row.getText().toString()) && !"".equals(column.getText().toString() ) ) {


                        studioList.get(flag).setName(name.getText().toString());
                        studioList.get(flag).setRow(row.getText().toString());
                        studioList.get(flag).setCol(column.getText().toString());
                        studioList.get(flag).setIntroduce(introduce.getText().toString());

                    }

                    adapter.notifyDataSetChanged();

                    listView.setSelection(studioList.size());

                    alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }


}
