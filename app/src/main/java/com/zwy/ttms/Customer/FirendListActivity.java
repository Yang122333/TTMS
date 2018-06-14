package com.zwy.ttms.Customer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zwy.ttms.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 2018/6/6.
 */

public class FirendListActivity extends Activity {

    public static void actionStart(Context context){
        Intent intent =new Intent(context ,FirendListActivity.class);
        context.startActivity(intent);
    }


    private ListView lv_view;
    private MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView title = new TextView(this);
        setContentView(R.layout.custom_listview);
        title.setText("员工管理");
        lv_view=(ListView) findViewById(R.id.lv_view);
        myAdapter=new MyAdapter();
        myAdapter.setContext(this);
        myAdapter.setmData(getList());
        lv_view.setAdapter(myAdapter);
        lv_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                // TODO Auto-generated method stub
                People_information people=(People_information) myAdapter.getItem(position);
                Toast.makeText(FirendListActivity.this, "昵称："+people.getNickname()+"\n个性签名："+people.getDescription(), 0).show();
            }
        });
    }
    private List<People_information> getList() {
        List <People_information>list=new ArrayList<People_information>();
        for(int i=0;i<9;i++){
            People_information people=new People_information();
            people.setDraw_Id(R.drawable.pic1);
            people.setDescription("哈哈哈哈啊哈哈哈");
            people.setNickname("赵四");
            list.add(people);
        }
        return list;
    }

}
