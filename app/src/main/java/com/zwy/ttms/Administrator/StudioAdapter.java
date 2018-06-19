package com.zwy.ttms.Administrator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zwy.ttms.R;
import com.zwy.ttms.model.Studios.Studios;

import java.util.List;

public class StudioAdapter extends ArrayAdapter<Studios> {
    private List<Studios> list;
    private Context context;
    private int resource ;

    public StudioAdapter(@NonNull Context context, int resource, @NonNull List<Studios> objects) {
        super(context, resource, objects);
        list = objects;
        this.context = context;
        this.resource = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Studios studio = (Studios) this.getItem(position);

        ViewHolder viewHolder;

        if(convertView == null){

            viewHolder = new StudioAdapter.ViewHolder();

            convertView = View.inflate(context,resource,null);
            viewHolder.name = (TextView) convertView.findViewById(R.id.list_name);
            viewHolder.row = (TextView) convertView.findViewById(R.id.list_row);
            viewHolder.col = (TextView) convertView.findViewById(R.id.list_col);
            viewHolder.introduce = (TextView) convertView.findViewById(R.id.list_introduce);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(studio.getName());

        viewHolder.row.setText(studio.getRow());
        viewHolder.col.setText(studio.getCol());
        viewHolder.introduce.setText(studio.getIntroduce());

        return convertView;
    }

    public static class ViewHolder{

        private TextView name;
        private TextView row;
        private TextView col;
        private TextView introduce;
    }
}
