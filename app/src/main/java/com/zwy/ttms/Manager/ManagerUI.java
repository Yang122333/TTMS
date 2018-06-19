package com.zwy.ttms.Manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zwy.ttms.R;

/**
 * Created by jack on 2018/6/6.
 */

public class ManagerUI extends Activity implements View.OnClickListener {
    public static  void actionStart(Context context){
        Intent intent = new Intent(context,ManagerUI.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_ui);
        Button btn1 = (Button)findViewById(R.id.schedule_manage);
        Button btn2 = (Button)findViewById(R.id.customer_manage);
        TextView textView = (TextView)findViewById(R.id.title_message);

        textView.setText("经理");
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.schedule_manage:
                ManageScheduleActivity.actionStart(this);

                break;
            case R.id.customer_manage:

                ManagePlay.actionStart(this);
                break;
            default:
                break;
        }
    }
}


