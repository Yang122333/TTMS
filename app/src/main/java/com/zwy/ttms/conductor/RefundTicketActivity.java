package com.zwy.ttms.conductor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zwy.ttms.R;
import com.zwy.ttms.model.sale.Sale;
import com.zwy.ttms.model.sale.SaleDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${Yang} on 2018/4/12.
 */

public class RefundTicketActivity extends Activity {
    private Button bt;
    private EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.refundticket);

        bt = (Button)findViewById(R.id.RefundTicket_button);
        TextView title = (TextView)findViewById(R.id.title_message);
        title.setText("退票");
        input = (EditText)findViewById(R.id.RefundTicket_input);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitRefundInfor();
            }
        });
    }

    private void submitRefundInfor() {
        int flag =0;
        List<Sale> saleList = new ArrayList<>();
        SaleDao saleDao =new SaleDao(this);
        saleList = saleDao.queryAll();
        if (input.getText().toString().equals("")||
                input.getText().toString().indexOf(" ") != -1) {
            Toast.makeText(getApplicationContext(), "输入值为空", Toast.LENGTH_SHORT).show();
        }
        else
        {
            for(Sale sale :saleList){
                if(input.getText().toString().equals(sale.getTicket_id() ) ){

                    Toast.makeText(getApplicationContext(), "退票成功", Toast.LENGTH_SHORT).show();
                    flag ++;
                }
            }
            //执行退票操作
            if(flag == 0){
                Toast.makeText(getApplicationContext(), "不存在该id", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
