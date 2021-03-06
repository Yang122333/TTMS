package com.zwy.ttms.Customer;

/**
 * Created by jack on 2018/6/6.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwy.ttms.R;

import java.util.List;

;

public class MyAdapter extends BaseAdapter{
    private List<People_information> mData;
    private Context context;
    public void setmData(List mData) {
        this.mData = mData;
    }
    public void setContext(Context context) {
        this.context = context;
    }
    //决定了列表item显示的个数
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mData.size();
    }
    //根据position获取对应item的内容
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mData.get(position);
    }
    //获取对应position的item的ID
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    //创建列表item视图
    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        // TODO Auto-generated method stub
        View view=View.inflate(context, R.layout.item_customer,null);
        //获取item对应的数据对象
        People_information people=mData.get(position);
        //初始化view
        ImageView iv_picture=(ImageView) view.findViewById(R.id.iv_picture);
        TextView tv_nickname=(TextView) view.findViewById(R.id.tv_nickname);
        TextView tv_description=(TextView) view.findViewById(R.id.tv_description);
        //绑定数据到view
        iv_picture.setImageResource(people.getDraw_Id());
        tv_nickname.setText(people.getNickname());
        tv_description.setText(people.getDescription());
        return view;
    }
}