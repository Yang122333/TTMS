package com.zwy.ttms;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by ${Yang} on 2018/4/12.
 */

public class TitleLayout extends LinearLayout{
    public TitleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


        LayoutInflater.from(context).inflate(R.layout.title,this);
        ImageView back = (ImageView)this.findViewById(R.id.title_back);
        ImageView cancel = (ImageView)this.findViewById(R.id.title_cancel);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity)getContext()).finish();
            }
        });
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
//                        .setTitle("注销")
                        .setMessage("是否退出到登录界面")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getContext(),LoginActivity.class);
                                getContext().startActivity(intent);
                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();

            }
        });
    }
}
