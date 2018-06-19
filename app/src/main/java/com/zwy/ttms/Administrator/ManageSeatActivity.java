package com.zwy.ttms.Administrator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zwy.ttms.LoginActivity;
import com.zwy.ttms.R;
import com.zwy.ttms.model.Seats.SeatParseJSON;
import com.zwy.ttms.model.Seats.Seats;
import com.zwy.ttms.model.Service.HttpCallbackListener;
import com.zwy.ttms.model.Service.HttpUtil;

import java.util.ArrayList;
import java.util.List;

public class ManageSeatActivity extends Activity{
    private RelativeLayout layout;
    private ImageView[] btn ;
    private List<Seats> seatList = new ArrayList<>();
    private Handler handler;
    private String studio_name;
    private int studio_row;
    private int studio_col;
    private int seat_count;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent =getIntent();
        studio_name = intent.getStringExtra("studio_name");
        studio_row = Integer.valueOf(intent.getStringExtra("studio_row"));
        studio_col = Integer.valueOf(intent.getStringExtra("studio_col"));
        seat_count = studio_row*studio_col;

        init(studio_name);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        createLayout();
                        break;
                }
            }
        };
        btn = new ImageView[seat_count];
        setContentView(R.layout.manage_seat);
        TextView title = (TextView)findViewById(R.id.title_message);
        TextView dapingmu =(TextView)findViewById(R.id.dapingmu);

        layout = (RelativeLayout)findViewById(R.id.seatlayout);
        Button submit_btn = (Button)findViewById(R.id.submit_btn);

        submit_btn.setText("确认修改");
        title.setText("座位管理");
        dapingmu.setText(studio_name);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable.ConstantState t4 = getDrawable(R.drawable.seat_damaged1).getConstantState();


                new AlertDialog.Builder(ManageSeatActivity.this)
                        .setMessage("修改成功！")
                        .setNegativeButton("确认",null)
                        .show();

            }//#onclick
        });



    }

    private void init(String studio_name) {

        String address = LoginActivity.ip + "administrator?method=getSeatByStudioName&studioName="+studio_name;
        HttpUtil.sendHttpRequest(address, "GET", new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.i("ManageSeatActivity", "response:   "+response);
                seatList = SeatParseJSON.toSeatList(response);
                Log.i("ManageSeatActivity", "status:      "+seatList.get(0).getStatus());
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }

            @Override
            public void onError(Exception e) {

            }
        });

    }


    private void createLayout( ) {
        //获取屏幕大小,以合理设定 按钮 大小及位置
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        int  j = -1;

        for (int i =0 ;i<seat_count; i++){

            btn[i] = new ImageView(this);

            btn[i].setImageResource(R.drawable.seat_unchecked);

            btn[i].setScaleType(ImageView.ScaleType.FIT_XY);
            btn[i].setTag(i);
            //设置图片的宽度和高度
            RelativeLayout.LayoutParams btnParams = new RelativeLayout.LayoutParams(
                    width/studio_col-50,width/studio_col-50 );
            if(i%studio_col == 0){
                j++;
            }
            btnParams.leftMargin = 10+((width-50)/studio_col+10)*(i%studio_col);//横坐标定位
            btnParams.topMargin = ((width-50)/studio_col+10)*j;
            layout.addView(btn[i],btnParams);
            if(seatList.get(i).getStatus().equals("DAMAGE")){
                btn[i].setImageResource(R.drawable.seat_damaged1);

            }
        }//#for
        //设置监听
        for(int k = 0;k<btn.length;k++){

            btn[k].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i = (Integer) view.getTag();

                    ImageView v = (ImageView) view;//更换图片
                    Drawable.ConstantState t1 = v.getDrawable().getConstantState();
                    Drawable.ConstantState t2 = getDrawable(R.drawable.seat_locked).getConstantState();
                    Drawable.ConstantState t3 = getDrawable(R.drawable.seat_checked1).getConstantState();
                    Drawable.ConstantState t4 = getDrawable(R.drawable.seat_damaged1).getConstantState();


                    if (t1.equals(t4)) {
                        v.setImageResource(R.drawable.seat_unchecked);
                        String address = LoginActivity.ip+"administrator?method=updateSeat&unionId="
                                +seatList.get(i).getUnionId()
                                +"&status=USE";
                        HttpUtil.sendHttpRequest(address, "POST", new HttpCallbackListener() {
                            @Override
                            public void onFinish(String response) {
                                Log.i("ManageSeatActivity", "response:   "+response);

                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });

                    } else {
                        v.setImageResource(R.drawable.seat_damaged1);
                        String address = LoginActivity.ip+"administrator?method=updateSeat&unionId="
                                +seatList.get(i).getUnionId()
                                +"&status=DAMAGE";
                        HttpUtil.sendHttpRequest(address, "POST", new HttpCallbackListener() {
                            @Override
                            public void onFinish(String response) {
                                Log.i("ManageSeatActivity", "response:   "+response);


                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });

                    }

                }
            });

        }
    }
}
