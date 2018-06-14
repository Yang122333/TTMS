package com.zwy.ttms.Administrator;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zwy.ttms.R;
import com.zwy.ttms.model.seat.Seat;
import com.zwy.ttms.model.studio.Studio;
import com.zwy.ttms.model.ticket.Ticket;

import java.util.ArrayList;


/**
 * Created by ${Yang} on 2018/4/11.
 */

public class ManageSeatActivity extends Activity {
    RelativeLayout layout;

    ImageView[] btn;

    Studio studio = new Studio();

    ArrayList<Seat> seatList = new ArrayList<>();

    ArrayList<String> data = new ArrayList<>();

    ArrayList<Ticket> tickets = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedIntanceState) {
        super.onCreate(savedIntanceState);

        studio.setColumn(9);
        studio.setRow(5);
        init();
        setContentView(R.layout.manage_seat);

        TextView title = (TextView)findViewById(R.id.title_message);

        layout = (RelativeLayout)findViewById(R.id.seatlayout);


        Button btn = (Button)findViewById(R.id.submit_btn);

        title.setText("座位管理");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int flag = 0 ;
                for(int i =0;i<studio.getSeatCount();i++){
                    if(tickets.get(i).getStatus() == Ticket.LOCKED){
                        tickets.get(i).setStatus(Ticket.SALED);
                        flag++;
                    }
                }
                if(flag != 0){
                    createLayout();
                    new AlertDialog.Builder(ManageSeatActivity.this)
                            .setMessage("出票成功！")
                            .setNegativeButton("确认",null)
                            .show();
                }
                else{
                    new AlertDialog.Builder(ManageSeatActivity.this)
                            .setMessage("请选择座位！")
                            .setNegativeButton("确认",null)
                            .show();

                }
            }//#onclick
        });

        createLayout();

    }

    @SuppressLint("ResourceType")
    private void createLayout() {
        //获取屏幕大小,以合理设定 按钮 大小及位置
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        btn = new ImageView[studio.getSeatCount()];
        int  j = -1;

        for (int i =0 ;i<studio.getSeatCount() ; i++){

            btn[i] = new ImageView(this);
            if(seatList.get(i).getStatus()== Seat.NORMAL) {//  设置座位图片
                if (tickets.get(i).getStatus() == Ticket.ONSALED) {
                    btn[i].setImageResource(R.drawable.seat_unchecked);
                }
//            else if(tickets.get(i).getState()==Ticket.LOCKED){
//                btn[i].setImageResource(R.drawable.seat_locked);
//            }
                else if (tickets.get(i).getStatus() == Ticket.SALED) {
                    btn[i].setImageResource(R.drawable.seat_checked1);
                }
            }
            else{
                btn[i].setImageResource(R.drawable.seat_damaged1);
            }
            btn[i].setScaleType(ImageView.ScaleType.FIT_XY);
            btn[i].setTag(i);
            //设置图片的宽度和高度
            RelativeLayout.LayoutParams btnParams = new RelativeLayout.LayoutParams(
                    width/studio.getColumn()-50,width/studio.getColumn()-50 );
            if(i%studio.getColumn() == 0){
                j++;
            }
            btnParams.leftMargin = 10+((width-50)/studio.getColumn()+10)*(i%studio.getColumn());//横坐标定位
            btnParams.topMargin = ((width-50)/studio.getColumn()+10)*j;
            layout.addView(btn[i],btnParams);
        }//#for
        //设置监听
        for(int k = 0;k<btn.length;k++){

            btn[k].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i = (Integer) view.getTag();

                    int current_position = i + 1;
                    ImageView v = (ImageView) view;//更换图片
                    Drawable.ConstantState t1 = v.getDrawable().getConstantState();
                    Drawable.ConstantState t2 = getDrawable(R.drawable.seat_locked).getConstantState();
                    Drawable.ConstantState t3 = getDrawable(R.drawable.seat_checked1).getConstantState();
                    Drawable.ConstantState t4 = getDrawable(R.drawable.seat_damaged1).getConstantState();

                    if (!t1.equals(t3) && !t1.equals(t4)) {
                        int seatX;
                        int seatY;
                        if (current_position % studio.getColumn() == 0) {
                            seatX = current_position / studio.getColumn();
                            seatY = studio.getColumn();
                        } else {
                            seatX = current_position / studio.getColumn() + 1;
                            seatY = current_position % studio.getColumn();
                        }
                        String info = "第" + seatX + "行" + seatY + "列";
                        if (t1.equals(t2)) {
                            v.setImageResource(R.drawable.seat_unchecked);
                            for (int q = 0; q < data.size(); q++) {
                                if (info.equals(data.get(q))) {
                                    data.remove(q);
                                    tickets.get(i).setStatus(Ticket.ONSALED);
                                }

                            }
                        } else {
                            v.setImageResource(R.drawable.seat_locked);

                            tickets.get(i).setStatus(Ticket.LOCKED);

                        }

                    }
                }
            });

        }
    }
    private void init(){
        for(int i=0; i<studio.getSeatCount();i++){
            Seat seat =new Seat();
            Ticket ticket = new Ticket();
            ticket.setSeatId(seat.getId());
            if(i%10==0){
                seat.setStatus(Seat.DAMAGED);
            }
            if(i%7== 0){
                ticket.setStatus(Ticket.SALED);
            }
//            if(i%9 ==0){
//                ticket.setState(Ticket.LOCKED);
//            }
            seatList.add(seat);
            tickets.add(ticket);
        }
    }

}

