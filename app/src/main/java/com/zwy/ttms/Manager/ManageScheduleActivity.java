package com.zwy.ttms.Manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.zwy.ttms.LoginActivity;
import com.zwy.ttms.R;
import com.zwy.ttms.model.Service.HttpCallbackListener;
import com.zwy.ttms.model.Service.HttpUtil;
import com.zwy.ttms.model.schedule.InitSpinners;
import com.zwy.ttms.model.schedule.Plays;
import com.zwy.ttms.model.schedule.ScheduleAndParseJson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ManageScheduleActivity extends Activity {

    private ScheduleAdapter adapter;
    private ListView tableListView;
    private ArrayAdapter adp_play;
    private ArrayAdapter adp_stu;
    private String[] playSp;
    private String[] studioSp;
    public static final int SHOW_RESPONSE = 0,SHOW_RESPONSE2=1,SHOW_RESPONSE3=3;
    private Handler handler;
    private String play,studio,price;
    private int year, month, day, hour, minute;
    private List<Plays> list1 = new ArrayList<Plays>();
    //在TextView上显示的字符
    private StringBuffer date, time;

    public static  void actionStart(Context context){
        Intent intent = new Intent(context,ManageScheduleActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_schedule);
        TextView textView = (TextView)findViewById(R.id.title_message);

        textView.setText("管理演出计划");
        InitListView();
        InitPlaySp();
        InitStudioSp();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case SHOW_RESPONSE3:
                     adapter = new ScheduleAdapter(ManageScheduleActivity.this, list1);

                        tableListView.setAdapter(adapter);
                        break;
                    case  SHOW_RESPONSE2:
                        adp_play= new ArrayAdapter<String>
                                (ManageScheduleActivity.this,android.R.layout.simple_spinner_item,playSp);
                        adp_play.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                        break;
                    case  SHOW_RESPONSE:
                        adp_stu= new ArrayAdapter<String>
                                (ManageScheduleActivity.this,android.R.layout.simple_spinner_item,studioSp);
                        adp_stu.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                        break;
                }
            }
        };
        Button add_schdule = (Button) findViewById(R.id.addschedule);
        //设置表格标题的背景颜色
        ViewGroup tableTitle = (ViewGroup) findViewById(R.id.table_title);
        tableTitle.setBackgroundColor(Color.rgb(177, 173, 172));
        TextView title = new TextView(this);
        title.setText("管理演出计划");

        Log.i("12345", list1.size()+"");
       tableListView = (ListView) findViewById(R.id.list);


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
                        date = new StringBuffer();
                        time = new StringBuffer();




                        dialog1.setView(dialogView);
                        dialog1.show();
                        final Spinner sp_play = (Spinner) dialogView.findViewById(R.id.list_play);
                        final Spinner sp_stu = (Spinner) dialogView.findViewById(R.id.list_studio);
                        final EditText play_price = (EditText) dialogView.findViewById(R.id.play_price);
                        final LinearLayout llDate = (LinearLayout) dialogView.findViewById(R.id.ll_date);
                        final TextView tvDate = (TextView) dialogView.findViewById(R.id.tv_date);
                        final LinearLayout llTime = (LinearLayout) dialogView.findViewById(R.id.ll_time);
                        final TextView plTime = (TextView) dialogView.findViewById(R.id.play_time);
                        final TextView tvTime = (TextView) dialogView.findViewById(R.id.tv_time);
                        Button finishiChange = (Button)dialogView.findViewById(R.id.finish_Change);
                        initDateTime();
                        sp_play.setAdapter(adp_play);
                        sp_stu.setAdapter(adp_stu);

                        for(int i=0;i<playSp.length;i++){
                            if(list1.get(position).getPlayName().equals(playSp[i])){
                                sp_play.setSelection(i,true);
                                break;
                            }
                        }
                        for(int i=0;i<studioSp.length;i++){
                            if(list1.get(position).getPlayStudio().equals(studioSp[i])){
                                sp_stu.setSelection(i,true);
                                break;
                            }
                        }
                        play_price.setText(list1.get(position).getPlayPrice());
                        plTime.setText(list1.get(position).getPlayTime());

                        finishiChange.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Plays plays = new Plays();
                                plays.setUnionID(list1.get(position).getUnionID());
                                Log.i("UUUUU", plays.getUnionID());
                                plays.setPlayName(list1.get(position).getPlayName());
                                plays.setPlayStudio(list1.get(position).getPlayStudio());
                                plays.setPlayTime(plTime.getText().toString());
                                plays.setPlayPrice(play_price.getText().toString());
                                plays.setStatus("SHOW");

                                UpdateData(plays,position);
                                InitListView();

                                adapter.notifyDataSetChanged();
                                tableListView.setSelection(list1.size());
                                dialog1.dismiss();
                            }
                        });
                        llDate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ManageScheduleActivity.this);
                                builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (date.length() > 0) { //清除上次记录的日期
                                            date.delete(0, date.length());
                                        }
                                        tvDate.setText(date.append(String.valueOf(year)).append("年").append(String.valueOf(month)).append("月").append(day).append("日"));
                                        dialog.dismiss();
                                    }
                                });
                                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                final AlertDialog dialog = builder.create();
                                View dialogView = View.inflate(ManageScheduleActivity.this, R.layout.dialog_date, null);
                                final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);

                                dialog.setTitle("设置日期");
                                dialog.setView(dialogView);
                                dialog.show();
                                //初始化日期监听事件
//                        datePicker.init(year, month - 1, day, (DatePicker.OnDateChangedListener) ManageScheduleActivity.this);
                            }
                        });
                        llTime.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder builder2 = new AlertDialog.Builder(ManageScheduleActivity.this);
                                builder2.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (time.length() > 0) { //清除上次记录的日期
                                            time.delete(0, time.length());
                                        }
                                        StringBuffer Temp=new StringBuffer();
                                        tvTime.setText(time.append(String.valueOf(hour)).append("时").append(String.valueOf(minute)).append("分"));
                                        plTime.setText(Temp.append(date).append(time));
                                        dialog.dismiss();
                                    }
                                });
                                builder2.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog dialog2 = builder2.create();
                                View dialogView2 = View.inflate(ManageScheduleActivity.this, R.layout.dialog_time, null);
                                TimePicker timePicker = (TimePicker) dialogView2.findViewById(R.id.timePicker);
                                timePicker.setCurrentHour(hour);
                                timePicker.setCurrentMinute(minute);
                                timePicker.setIs24HourView(true); //设置24小时制
                                timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                                    @Override
                                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                                        hour = hourOfDay;
                                        minute = minute;
                                    }
                                });
                                dialog2.setTitle("设置时间");
                                dialog2.setView(dialogView2);
                                dialog2.show();
                            }
                        });
                    }
                }).setNegativeButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteSchedule(currentPosition);
                        list1.remove(currentPosition);
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

                date = new StringBuffer();
                time = new StringBuffer();
                dialog1.setView(dialogView);
                dialog1.show();
                final Spinner sp_play = (Spinner) dialogView.findViewById(R.id.list_play);
                final Spinner sp_stu = (Spinner) dialogView.findViewById(R.id.list_studio);
                final EditText play_price = (EditText) dialogView.findViewById(R.id.play_price);
                final LinearLayout llDate = (LinearLayout) dialogView.findViewById(R.id.ll_date);
                final TextView tvDate = (TextView) dialogView.findViewById(R.id.tv_date);
                final LinearLayout llTime = (LinearLayout) dialogView.findViewById(R.id.ll_time);
                final TextView tvTime = (TextView) dialogView.findViewById(R.id.tv_time);
                final TextView plTime = (TextView) dialogView.findViewById(R.id.play_time);
                Button finishiChange = (Button)dialogView.findViewById(R.id.finish_Change);
                initDateTime();
                sp_play.setAdapter(adp_play);
                sp_stu.setAdapter(adp_stu);
                sp_play.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        play = sp_play.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        play = playSp[0];
                    }
                });
                sp_stu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        studio = sp_stu.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        studio = studioSp[0];
                    }
                });
                llDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ManageScheduleActivity.this);
                        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (date.length() > 0) { //清除上次记录的日期
                                    date.delete(0, date.length());
                                }
                                tvDate.setText(date.append(String.valueOf(year)).append("年").append(String.valueOf(month)).append("月").append(day).append("日"));
                                dialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        final AlertDialog dialog = builder.create();
                        View dialogView = View.inflate(ManageScheduleActivity.this, R.layout.dialog_date, null);
                        final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);

                        dialog.setTitle("设置日期");
                        dialog.setView(dialogView);
                        dialog.show();
                        //初始化日期监听事件
//                        datePicker.init(year, month - 1, day, (DatePicker.OnDateChangedListener) ManageScheduleActivity.this);
                    }
                });
                llTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(ManageScheduleActivity.this);
                        builder2.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (time.length() > 0) { //清除上次记录的日期
                                    time.delete(0, time.length());
                                }
                                StringBuffer Temp=new StringBuffer();
                                tvTime.setText(time.append(String.valueOf(hour)).append("时").append(String.valueOf(minute)).append("分"));
                                plTime.setText(Temp.append(date).append(time));
                                dialog.dismiss();
                            }
                        });
                        builder2.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog2 = builder2.create();
                        View dialogView2 = View.inflate(ManageScheduleActivity.this, R.layout.dialog_time, null);
                        TimePicker timePicker = (TimePicker) dialogView2.findViewById(R.id.timePicker);
                        timePicker.setCurrentHour(hour);
                        timePicker.setCurrentMinute(minute);
                        timePicker.setIs24HourView(true); //设置24小时制
                        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                            @Override
                            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                                hour = hourOfDay;
                                minute = minute;
                            }
                        });
                        dialog2.setTitle("设置时间");
                        dialog2.setView(dialogView2);
                        dialog2.show();
                    }
                });
//                addview(play_name1);
                finishiChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if( play_price.getText().toString().indexOf(" ") != -1 ||
                                plTime.getText().toString().indexOf(" ") != -1 ||
                                tvDate.getText().toString().indexOf(" ") != -1 ||
                                tvTime.getText().toString().indexOf(" ") != -1
                                ){
                            Toast.makeText(ManageScheduleActivity.this,"输入不能为空！",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Plays plays = new Plays();
                            plays.setPlayName(play);
                            plays.setPlayStudio(studio);
                            plays.setPlayTime(date.append(time).toString());
                            plays.setPlayPrice(play_price.getText().toString());
                            plays.setStatus("SHOW");
                            InsertData(plays);
                            InitListView();

                            adapter.notifyDataSetChanged();
                            tableListView.setSelection(list1.size());
                            dialog1.dismiss();
                        }
                    }
                });
            }


        });

    }
    /**
     * 获取当前的日期和时间
     */
    private void initDateTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);

    }

    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear;
        this.day = dayOfMonth;
    }

    /**
     * 时间改变的监听事件
     *
     * @param view
     * @param hourOfDay
     * @param minute
     */

    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        this.hour = hourOfDay;
        this.minute = minute;
    }
    public void InitPlaySp(){
String ip = LoginActivity.ip;
        String address = ip+"director?method=getPlayList";
        HttpUtil.sendHttpRequest(address, "GET", new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                playSp=InitSpinners.toPlaylist(response);
                Message message = new Message();
                message.what = SHOW_RESPONSE2;
                handler.sendMessage(message);

            }

            @Override
            public void onError(Exception e) {

            }
        });

    }
    public void InitStudioSp(){

        String ip = LoginActivity.ip;
        String address = ip+"administrator?method=getStudioList";
        HttpUtil.sendHttpRequest(address, "GET", new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                studioSp=InitSpinners.toStudiolist(response);
                Log.i("chengle", studioSp[0]);
                Message message= new Message();
                message.what = SHOW_RESPONSE;
                handler.sendMessage(message);

            }

            @Override
            public void onError(Exception e) {

            }
        });

    }
    public boolean deleteSchedule(int currentPosition){
        String ip = LoginActivity.ip;
        String address = ip+"director?method=deleterSchedule&unionId="+list1.get(currentPosition).getUnionID();
        HttpUtil.sendHttpRequest(address, "POST", new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.i("resultsss", response);
            }

            @Override
            public void onError(Exception e) {

            }
        });
        return true;
    }
    public void InitListView(){

        String ip = LoginActivity.ip;
        String address = ip+"director?method=getScheduleList";
        HttpUtil.sendHttpRequest(address, "GET",new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.i("22121", response);

                list1= ScheduleAndParseJson.toPlaysList(response);
                Message message = new Message();
                message.what = SHOW_RESPONSE3;
                handler.sendMessage(message);
                Log.i("sucessss", list1.get(0).getPlayName());
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
    public String InsertData(Plays plays){
        String ip = LoginActivity.ip;
        String address = ip+"director?method=addSchedule&playName="+plays.getPlayName()+"&studioName="+plays.getPlayStudio()
                +"&time="+plays.getPlayTime()+"&price="+plays.getPlayPrice()+"&status="+plays.getStatus();
        Log.i("URLRRR", address);
        HttpUtil.sendHttpRequest(address, "POST", new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                String res = ScheduleAndParseJson.getRespond(response);
                Log.i("pppooo", response);
            }

            @Override
            public void onError(Exception e) {

            }
        });
        return null;
    }
    public void UpdateData(Plays plays,int i){
        String ip = LoginActivity.ip;
        String address = ip+"director?method=updateSchedule&unionId="+plays.getUnionID()+"&playName="+plays.getPlayName()+"&studioName="+plays.getPlayStudio()
                +"&time="+plays.getPlayTime()+"&price="+plays.getPlayPrice()+"&status="+plays.getStatus();
        Log.i("POQ", address);
        HttpUtil.sendHttpRequest(address, "POST", new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                String res = ScheduleAndParseJson.getRespond(response);
                Log.i("pppooo", response);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
//        public  boolean selectAll(String log_status){
//            boolean log = false ;
//
//            if(log_status.equals("fail")){
//
//                Message message = new Message();
//                message.what = SHOW_RESPONSE;
//// 将服务器返回的结果存放到Message中
//                handler.sendMessage(message);
//                Toast.makeText(ManageScheduleActivity.this,log_status,Toast.LENGTH_SHORT).show();
//
//            }else {
//                Toast.makeText(ManageScheduleActivity.this,"查询成功",Toast.LENGTH_SHORT).show();
//            }
//            return true;
//        }
}