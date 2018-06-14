package com.zwy.ttms.Administrator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zwy.ttms.R;
import com.zwy.ttms.model.user.User;

import java.util.List;

/**
 * Created by jack on 2018/4/15.
 */


public class UserAdapter extends BaseAdapter {

    private List<User> list;
    private LayoutInflater inflater;

    public UserAdapter(Context context, List<User> list){
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        int ret = 0;
        if(list!=null){
            ret = list.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        User users = (User) this.getItem(position);

        ViewHolder viewHolder;

        if(convertView == null){

            viewHolder = new ViewHolder();

            convertView = inflater.inflate(R.layout.user_item, null);
            viewHolder.ID= (TextView) convertView.findViewById(R.id.user_id);
            viewHolder.UserName = (TextView) convertView.findViewById(R.id.user_name);
            viewHolder.pwd = (TextView) convertView.findViewById(R.id.user_pwd);
            viewHolder.Identity = (TextView) convertView.findViewById(R.id.user_itentity);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.ID.setText(String.valueOf(users.getUserId()));
        viewHolder.ID.setTextSize(13);
        viewHolder.UserName.setText(users.getLogin_name());
        viewHolder.UserName.setTextSize(13);
        viewHolder.pwd.setText("********");
        viewHolder.pwd.setTextSize(13);
        viewHolder.Identity.setText(users.getRole_name()+"");
        viewHolder.Identity.setTextSize(13);

        return convertView;
    }

    public static class ViewHolder{

        private TextView ID;
        private TextView UserName;
        private TextView pwd;
        private TextView Identity;
    }

}