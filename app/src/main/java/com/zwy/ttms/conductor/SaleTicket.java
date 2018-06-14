package com.zwy.ttms.conductor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zwy.ttms.R;

import java.util.ArrayList;

/**
 * Created by ${Yang} on 2018/4/11.
 */

public class SaleTicket extends Activity {
    public SeatTable seatTableView;
    ArrayList<Integer> sales = new ArrayList<>();
    View.OnClickListener Listener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            ArrayList<Integer> selects = seatTableView.getSelects();

//        for(Ticket ticket:tickets){
//            sales.add(seatTableView.getId())
//        }
            for(int i :selects){
                sales.add(i);
            }
            seatTableView.clearSelects();
            seatTableView.setSales(sales);
            seatTableView.invalidate();

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sale_ticket);

        seatTableView = (SeatTable) findViewById(R.id.seatView);
        Button btn = (Button)findViewById(R.id.sale_commit);
        btn.setOnClickListener(Listener);
        seatTableView.setScreenName("8号厅荧幕");//设置屏幕名称
        seatTableView.setMaxSelected(100);//设置最多选中

        seatTableView.setSeatChecker(new SeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {    //是否可用

                return true;
            }

            @Override
            public boolean isSold(int row, int column) {

                return false;
            }

            @Override
            public void checked(int row, int column) {

            }

            @Override
            public void unCheck(int row, int column) {

            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });

        seatTableView.setData(10,15); // 设置座位





    }

}