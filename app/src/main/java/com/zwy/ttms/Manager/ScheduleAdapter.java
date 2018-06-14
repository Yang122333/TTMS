package com.zwy.ttms.Manager;


 /**
 * Created by jack on 2018/4/15.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zwy.ttms.R;
import com.zwy.ttms.model.Plays;

import java.util.List;


public class ScheduleAdapter extends BaseAdapter {

    private List<Plays> list;
    private LayoutInflater inflater;

    public ScheduleAdapter(Context context, List<Plays> list){
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

        Plays plays = (Plays) this.getItem(position);

        ViewHolder viewHolder;

        if(convertView == null){

            viewHolder = new ViewHolder();

            convertView = inflater.inflate(R.layout.schdule_item, null);
            viewHolder.PlayName= (TextView) convertView.findViewById(R.id.play_name);
            viewHolder.PlayStudio = (TextView) convertView.findViewById(R.id.play_studio);
            viewHolder.PlayTime = (TextView) convertView.findViewById(R.id.play_time);
            viewHolder.PlayPrice = (TextView) convertView.findViewById(R.id.play_price);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.PlayName.setText(plays.getPlayName());
        viewHolder.PlayName.setTextSize(13);
        viewHolder.PlayStudio.setText(plays.getPlayStudio());
        viewHolder.PlayStudio.setTextSize(13);
        viewHolder.PlayTime.setText(plays.getPlayTime());
        viewHolder.PlayTime.setTextSize(13);
        viewHolder.PlayPrice.setText(plays.getPlayPrice()+"");
        viewHolder.PlayPrice.setTextSize(13);

        return convertView;
    }

    public static class ViewHolder{

        private TextView PlayStudio;
        private TextView PlayName;
        private TextView PlayTime;
        private TextView PlayPrice;
    }

}