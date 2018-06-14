package com.zwy.ttms.Administrator;

/**
 * Created by ${Yang} on 2018/4/16.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.zwy.ttms.R;
import com.zwy.ttms.model.PlayInfo;

import java.util.ArrayList;

/**
 * Created by Administration on 2018/4/12.
 */

public class PlayManagement extends Activity {

    public static void actionStart(Context context){
        Intent intent =new Intent(context ,PlayManagement.class);
        context.startActivity(intent);
    }

    private TextView playinfo1;
    private TextView playinfo2;
    private TextView playinfo3;
    private TextView playinfo4;

    private Button playbutton1;
    private Button playbutton2;
    private Button playbutton3;
    private Button playbutton4;
    private Button playbutton5;
    private Button playbutton6;

    private ArrayList<Integer> playImageId = new ArrayList<Integer>(){
        {
            add(R.drawable.play1);
            add(R.drawable.play2);
            add(R.drawable.play3);
            add(R.drawable.play4);
            add(R.drawable.play5);
        }
    };

    private ArrayList<String> playNameInfo = new ArrayList<String>() {
        {
            add("魔兽");add("杀破狼Ⅱ");add("爱有来生");add("王的盛宴");add("冰封");
        }
    };
    private ArrayList<String> playDuringInfo = new ArrayList<String>() {
        {
            add("123分钟");add("120分钟");add("96分钟");add("109分钟");add("91分钟");
        }
    };
    private ArrayList<String> playTypeInfo = new ArrayList<String>() {
        {
            add("动作/战争/奇幻");add("剧情/动作/犯罪");add("剧情/爱情");add("古装");add("动作/奇幻/喜剧");
        }
    };
    private ArrayList<String> playPublishInfo = new ArrayList<String>() {
        {
            add("2016年上映");add("2015年上映");add("2009年上映");add("2012年上映");add("2014年上映");
        }
    };
    private ArrayList<PlayInfo> playInfoArrayList = new ArrayList<PlayInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        TextView textView = (TextView)findViewById(R.id.title_message);
        textView.setText("剧目管理");

        playinfo1 = (TextView) findViewById(R.id.Play_info1);
        playinfo2 = (TextView) findViewById(R.id.Play_info2);
        playinfo3 = (TextView) findViewById(R.id.Play_info3);
        playinfo4 = (TextView) findViewById(R.id.Play_info4);
        playbutton1 = (Button) findViewById(R.id.Play_button1);
        playbutton2 = (Button) findViewById(R.id.Play_button2);
        playbutton3 = (Button) findViewById(R.id.Play_button3);
        playbutton4 = (Button) findViewById(R.id.Play_button4);
        playbutton5 = (Button) findViewById(R.id.PlayManagement_button1);
        playbutton6 = (Button) findViewById(R.id.PlayManagement_button2);


        final Gallery myGallery = (Gallery)findViewById(R.id.PlayManagement_gallery1);

        for(int i = 0;i < Gallery_adapter.getCount(); i++){
            PlayInfo playInfo = new PlayInfo();
            playInfo.setPlayName(playNameInfo.get(i).toString());
            playInfo.setPlayDuring(playDuringInfo.get(i).toString());
            playInfo.setPlayType(playTypeInfo.get(i).toString());
            playInfo.setPlayPublish(playPublishInfo.get(i).toString());
            playInfoArrayList.add(playInfo);
        }
        myGallery.setAdapter(Gallery_adapter);
        myGallery.setSelection(0);
        myGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                playinfo1.setText(playInfoArrayList.get(i).getPlayName());
                playinfo2.setText(playInfoArrayList.get(i).getPlayDuring());
                playinfo3.setText(playInfoArrayList.get(i).getPlayType());
                playinfo4.setText(playInfoArrayList.get(i).getPlayPublish());
                playbutton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final EditText editText = new EditText(PlayManagement.this);
                        new AlertDialog.Builder(PlayManagement.this)
                                .setTitle("请修改该信息：")
                                .setView(editText)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int l) {
                                        playinfo1.setText(editText.getText().toString());
                                        playInfoArrayList.get(i).setPlayName(editText.getText().toString());
                                    }
                                })
                                .setNegativeButton("取消",null)
                                .show();
                    }
                });
                playbutton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final EditText editText = new EditText(PlayManagement.this);
                        new AlertDialog.Builder(PlayManagement.this)
                                .setTitle("请修改该信息：")
                                .setView(editText)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int l) {
                                        playinfo2.setText(editText.getText().toString());
                                        playInfoArrayList.get(i).setPlayDuring(editText.getText().toString());
                                    }
                                })
                                .setNegativeButton("取消",null)
                                .show();
                    }
                });
                playbutton3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final EditText editText = new EditText(PlayManagement.this);
                        new AlertDialog.Builder(PlayManagement.this)
                                .setTitle("请修改该信息：")
                                .setView(editText)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int l) {
                                        playinfo3.setText(editText.getText().toString());
                                        playInfoArrayList.get(i).setPlayType(editText.getText().toString());
                                    }
                                })
                                .setNegativeButton("取消",null)
                                .show();
                    }
                });
                playbutton4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final EditText editText = new EditText(PlayManagement.this);
                        new AlertDialog.Builder(PlayManagement.this)
                                .setTitle("请修改该信息：")
                                .setView(editText)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int l) {
                                        playinfo4.setText(editText.getText().toString());
                                        playInfoArrayList.get(i).setPlayPublish(editText.getText().toString());
                                    }
                                })
                                .setNegativeButton("取消",null)
                                .show();
                    }
                });
                playbutton5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new AlertDialog.Builder(PlayManagement.this)
                                .setTitle("确定要删除该剧目吗？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int l) {

                                        if(playImageId.size() != 0) {
                                            if (i != playImageId.size()-1) {
                                                playinfo1.setText(playInfoArrayList.get(i).getPlayName());
                                                playinfo2.setText(playInfoArrayList.get(i).getPlayDuring());
                                                playinfo3.setText(playInfoArrayList.get(i).getPlayType());
                                                playinfo4.setText(playInfoArrayList.get(i).getPlayPublish());
                                            }
                                            else {
                                                playinfo1.setText(playInfoArrayList.get(i - 1).getPlayName());
                                                playinfo2.setText(playInfoArrayList.get(i - 1).getPlayDuring());
                                                playinfo3.setText(playInfoArrayList.get(i - 1).getPlayType());
                                                playinfo4.setText(playInfoArrayList.get(i - 1).getPlayPublish());
                                            }
                                        }else {

                                            playinfo1.setText("");
                                            playinfo2.setText("");
                                            playinfo3.setText("");
                                            playinfo4.setText("");
                                        }
                                        playInfoArrayList.remove(i);
                                        playImageId.remove(i);
                                        Gallery_adapter.notifyDataSetChanged();
                                    }
                                })
                                .setNegativeButton("取消",null)
                                .show();
                    }
                });
                playbutton6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(PlayManagement.this);
                        builder.setTitle("请输入要添加的电影信息:");
                        view = LayoutInflater.from(PlayManagement.this).inflate(R.layout.playinfo_dialog_view, null);
                        builder.setView(view);

                        final EditText dialog_playName = (EditText)view.findViewById(R.id.dialog_edit1);
                        final EditText dialog_playDuring = (EditText)view.findViewById(R.id.dialog_edit2);
                        final EditText dialog_playType = (EditText)view.findViewById(R.id.dialog_edit3);
                        final EditText dialog_playPublish = (EditText)view.findViewById(R.id.dialog_edit4);

                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                PlayInfo playInfo = new PlayInfo();
                                playInfo.setPlayName(dialog_playName.getText().toString());
                                playInfo.setPlayDuring(dialog_playDuring.getText().toString());
                                playInfo.setPlayType(dialog_playType.getText().toString());
                                playInfo.setPlayPublish(dialog_playPublish.getText().toString());
                                playNameInfo.add(dialog_playName.getText().toString());
                                playDuringInfo.add(dialog_playDuring.getText().toString());
                                playTypeInfo.add(dialog_playType.getText().toString());
                                playPublishInfo.add(dialog_playPublish.getText().toString());
                                playInfoArrayList.add(playInfo);
                                playImageId.add(R.drawable.play6);
                                Gallery_adapter.notifyDataSetChanged();
                            }
                        });
                        builder.setNegativeButton("取消", null);
                        builder.show();
                    }
                });
            }
        });

    }
    BaseAdapter Gallery_adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return playImageId.size();
        }

        @Override
        public Object getItem(int position) {
            return playImageId.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if(convertView == null) {
                imageView = new ImageView(PlayManagement.this);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setLayoutParams(new Gallery.LayoutParams(400, 600));
                TypedArray typedArray = obtainStyledAttributes(R.styleable.Gallery);
                imageView.setBackgroundResource(typedArray.getResourceId(
                        R.styleable.Gallery_android_galleryItemBackground, 0));
                imageView.setPadding(5, 0, 5, 0);
            }else{
                imageView = (ImageView)convertView;
            }
            imageView.setImageResource(playImageId.get(position));
            return imageView;
        }
    };
}
