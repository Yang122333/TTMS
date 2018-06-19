package com.zwy.ttms.Manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwy.ttms.R;
import com.zwy.ttms.model.play.Play;

import java.util.List;

public class PlayAda extends ArrayAdapter {

    private List<Play> list;
    private LayoutInflater inflater;
    private final int resourceId;
    public PlayAda(Context context, int textViewResourceId, List<Play> list){
        super(context, textViewResourceId, list);
        this.list = list;
        resourceId = textViewResourceId;
        inflater = LayoutInflater.from(context);  //获得布局文件对象
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
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Play plays = (Play) this.getItem(position);
        ViewHolder viewHolder;

        if(view == null){
            viewHolder = new PlayAda.ViewHolder();
           // view = inflater.inflate(R.layout.play_item, null);
           view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
            viewHolder.play_name= (TextView) view.findViewById(R.id.play_name);
            viewHolder.play_type = (TextView) view.findViewById(R.id.play_type);
            viewHolder.play_length= (TextView) view.findViewById(R.id.play_length);
            viewHolder.play_language= (TextView) view.findViewById(R.id.play_language);
            viewHolder.play_picture= (ImageView) view.findViewById(R.id.play_picture);
            viewHolder.play_intro= (TextView) view.findViewById(R.id.play_intro);
            view.setTag(viewHolder);
        }else{
            viewHolder = (PlayAda.ViewHolder) view.getTag();
        }
        if(plays != null) {
//            if(plays.getPicture_path() != null)
//                viewHolder.play_picture.setImageURI(Uri.fromFile(new File(plays.getPicture_path().toString())));
            //显示本地路径绝对路径
            viewHolder.play_name.setText(""+plays.getPlay_name().toString());
            viewHolder.play_name.setTextSize(13);
            viewHolder.play_type.setText(""+plays.getPlay_type().toString());
            viewHolder.play_type.setTextSize(13);
            viewHolder.play_length.setText(""+plays.getPlay_length());
            viewHolder.play_length.setTextSize(13);
            viewHolder.play_language.setText(""+plays.getPlay_language());
            viewHolder.play_language.setTextSize(13);
            viewHolder.play_intro.setText(""+plays.getPlay_intro());
            viewHolder.play_intro.setTextSize(50);
        }
        return view;
    }
    public  static  class ViewHolder{
        private ImageView play_picture;
        private TextView play_name;
        private TextView play_type;
        private TextView play_length;
        private TextView play_language;
        private  TextView play_intro;

    }

}
