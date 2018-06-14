package com.zwy.ttms.Manager;

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

import com.zwy.ttms.R;
import com.zwy.ttms.model.Plays;

import java.util.ArrayList;
import java.util.List;


public class ManageScheduleActivity extends Activity {


    public static  void actionStart(Context context){
        Intent intent = new Intent(context,ManageScheduleActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_ui);
        Button add_schdule = (Button) findViewById(R.id.addschedule);
        //设置表格标题的背景颜色
        ViewGroup tableTitle = (ViewGroup) findViewById(R.id.table_title);
        tableTitle.setBackgroundColor(Color.rgb(177, 173, 172));

        final List<Plays> list = new ArrayList<Plays>();

        list.add(new Plays("one day", "01", "2018","25"));
        list.add(new Plays("Once", "02", "2018","28"));
        list.add(new Plays("Panda", "03", "2018","30"));
        list.add(new Plays("Guns", "04", "2018","30"));
        list.add(new Plays("Boom", "05", "2018","30"));


        final ListView tableListView = (ListView) findViewById(R.id.list);

        final ScheduleAdapter adapter = new ScheduleAdapter(this, list);

        tableListView.setAdapter(adapter);
        //注册单击ListView中的Item响应的事件
        tableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                final int currentPosition = position;

                AlertDialog.Builder builder= new AlertDialog.Builder(ManageScheduleActivity.this);
                builder.setPositiveButton("修改", new DialogInterface.OnClickListener() {
                    @Override

                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ManageScheduleActivity.this);
                        final AlertDialog dialog1 = builder.create();
                        View dialogView = View.inflate(ManageScheduleActivity.this, R.layout.change_schedule, null);
                        //设置对话框布局
                        dialog1.setView(dialogView);
                        dialog1.show();
                        final EditText play_name = (EditText) dialogView.findViewById(R.id.play_name);
                        final EditText play_studio = (EditText) dialogView.findViewById(R.id.play_studio);
                        final EditText play_time = (EditText) dialogView.findViewById(R.id.play_time);
                        final EditText play_price = (EditText) dialogView.findViewById(R.id.play_price);
                        Button finishiChange = (Button)dialogView.findViewById(R.id.finish_Change);

                        play_name.setText(list.get(currentPosition).getPlayName());
                        play_studio.setText(list.get(currentPosition).getPlayStudio());
                        play_time.setText(list.get(currentPosition).getPlayTime());
                        play_price.setText(list.get(currentPosition).getPlayPrice());

                        finishiChange.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                list.set(currentPosition,new Plays(play_name.getText().toString(),play_studio.getText().toString(),play_time.getText().toString(),play_price.getText().toString()));
                                adapter.notifyDataSetChanged();
                                dialog1.dismiss();
                            }
                        });
                    }
                }).setNegativeButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        list.remove(currentPosition);
                        adapter.notifyDataSetChanged();
                    }

                });
                builder.create().show();
//                list.set(currentPosition,new Plays("chenggong","000","2000","30"));
//                adapter.notifyDataSetChanged();
            }
        });
        add_schdule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ManageScheduleActivity.this);
                final AlertDialog dialog1 = builder.create();
                View dialogView = View.inflate(ManageScheduleActivity.this, R.layout.add_schedule, null);
                //设置对话框布局
                dialog1.setView(dialogView);
                dialog1.show();
                final EditText play_name = (EditText) dialogView.findViewById(R.id.play_name);
                final EditText play_studio = (EditText) dialogView.findViewById(R.id.play_studio);
                final EditText play_time = (EditText) dialogView.findViewById(R.id.play_time);
                final EditText play_price = (EditText) dialogView.findViewById(R.id.play_price);
                Button finishiChange = (Button)dialogView.findViewById(R.id.finish_Change);
                finishiChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        list.add(new Plays(play_name.getText().toString(),play_studio.getText().toString(),play_time.getText().toString(),play_price.getText().toString()));
                        adapter.notifyDataSetChanged();
                        tableListView.setSelection(list.size());
                        dialog1.dismiss();
                    }
                });
            }


        });
    }

}