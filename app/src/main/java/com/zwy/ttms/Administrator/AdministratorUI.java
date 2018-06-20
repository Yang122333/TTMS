package com.zwy.ttms.Administrator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zwy.ttms.R;
import com.zwy.ttms.model.DataHelper;


/**
 * Created by ${Yang} on 2018/4/15.
 */
public class AdministratorUI extends Activity implements View.OnClickListener{

    public static void actionStart(Context context){
        Intent intent =new Intent(context ,AdministratorUI.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administrator);
        Button btn1 = (Button)findViewById(R.id.user_manage);
        Button btn2 = (Button)findViewById(R.id.studio_manage);
//        Button btn3 = (Button)findViewById(R.id.play_manage);
        TextView textView = (TextView)findViewById(R.id.title_message);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataHelper dataHelper =new DataHelper(AdministratorUI.this);
                dataHelper.deleteDatabase(AdministratorUI.this);
            }
        });
        textView.setText("系统管理员");
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
//        btn3.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.user_manage:
                ManageUserActivity.actionStart(this);
                break;
            case R.id.studio_manage:
                ManageStudioActivity.actionStart(this);
                break;
            default:
                break;
        }
    }
}
