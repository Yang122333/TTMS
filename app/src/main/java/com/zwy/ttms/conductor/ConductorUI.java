package com.zwy.ttms.conductor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.zwy.ttms.R;

/**
 * Created by ${Yang} on 2018/4/12.
 */
public class ConductorUI extends Activity{
    private Button bt1;
    private Button bt2;

    public static void actionStart(Context context){
        Intent intent = new Intent(context,ConductorUI.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.conductorlayout);

        bt1 = (Button)findViewById(R.id.button1);
        bt2 = (Button)findViewById(R.id.button2);
        TextView title = (TextView)findViewById(R.id.title_message);
        title.setText("售票员");

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
              public void onClick(View view) {
                startActivity1();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity2();
            }
        });
    }

    private void startActivity1(){
        Intent intent = new Intent(this,SaleTicket.class);
        startActivity(intent);
    }
    private void startActivity2(){
        Intent intent = new Intent(this,RefundTicketActivity.class);
        startActivity(intent);
    }

}
