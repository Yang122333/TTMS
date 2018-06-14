package com.zwy.ttms.Administrator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zwy.ttms.R;
import com.zwy.ttms.model.studio.Studio;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${Yang} on 2018/4/12.
 */

public class ManageStudioActivity extends Activity implements View.OnClickListener{

    private RelativeLayout layout;
    private  ListView listView;
    private List<Studio> studioList = new ArrayList<>();
    private List<String> studioNames =new ArrayList<>();

    ArrayAdapter adapter;

    public static void actionStart(Context context){
        Intent intent = new Intent(context,ManageStudioActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studio);
        TextView textView = (TextView)findViewById(R.id.title_message);
        textView.setText("演出厅管理");

        listView =(ListView)findViewById(R.id.studio_list);
        final Button  btn = (Button)findViewById(R.id.add_studio);


        init();

        adapter = new ArrayAdapter
                (this,android.R.layout.simple_list_item_1,studioNames);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent( ManageStudioActivity.this,ManageSeatActivity.class);
//                intent.putExtra(StudioData.STUDIO_ID,String.valueOf(studioList.get(position).getId()));
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int i, long l) {

                AlertDialog.Builder builder =new AlertDialog.Builder(ManageStudioActivity.this);
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int q) {
                        studioNames.remove(i);

                        studioList.remove(i);
                        adapter.notifyDataSetChanged();
                        listView.setSelection(studioNames.size());
                    }
                })
                        .setNegativeButton("修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int q) {
                                modify(i);
                            }
                        });
                AlertDialog alertDialog1 = builder.create();
                alertDialog1.show();
                return true;
            }
        });
        btn.setOnClickListener(this);
    }

    private void init() {

        if(studioList == null){
            studioList = new ArrayList<>();
            Studio studio =new Studio();
            studio.setName("1号演出厅");
            studio.setSeatInfo(1+" "+3+" "+4);


            studioList.add(studio);
            studioNames.add(studio.getName());

            studio.setName("2号演出厅");
            studio.setSeatInfo(2+" "+3+" "+5);



            studioList.add(studio);
            studioNames.add(studio.getName());
        }
        else
        for(Studio s: studioList){
            studioNames.add(s.getName());
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_studio:
                addInfo();
                break;

            default:
                break;
        }
    }
    private void addInfo(){
        View view1 = View.inflate(this,R.layout.altert_dialog,null);

         AlertDialog.Builder builder =new AlertDialog.Builder(this);

         builder.setTitle("演出厅信息")
                .setView(view1)
                .setCancelable(false);

        final AlertDialog alertDialog =builder.create();
        final EditText name = (EditText)view1.findViewById(R.id.studio_name);
        final EditText row = (EditText)view1.findViewById(R.id.studio_row);
        final EditText column = (EditText)view1.findViewById(R.id.studio_column);
        Button cancel = (Button)view1.findViewById(R.id.studio_cancel);
        Button confirm = (Button)view1.findViewById(R.id.studio_confirm);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Studio studio =new Studio();
                    if(!"".equals(name.getText().toString() ) &&
                            name.getText().toString().indexOf(" ") == -1 &&
                            !"".equals(row.getText().toString()) &&
                            !"".equals(column.getText().toString())  )
                    {
                        studio.setName(name.getText().toString());
                        studio.setRow(Integer.valueOf(row.getText().toString()));
                        studio.setColumn(Integer.valueOf(column.getText().toString()));

                        studioList.add(studio);
                        studioNames.add(studio.getName());

                        adapter.notifyDataSetChanged();
                        listView.setSelection(studioNames.size());
                        alertDialog.dismiss();

                    }
                    else{
                        Toast.makeText(ManageStudioActivity.this,"输入不合法，请重新输入！",Toast.LENGTH_SHORT).show();

                    }

            }
        });
        alertDialog.show();
    }
    private  void modify(final int flag){
        View view1 = View.inflate(this,R.layout.altert_dialog,null);

        AlertDialog.Builder builder =new AlertDialog.Builder(this);

        builder.setTitle("演出厅信息")
                .setView(view1);

        final AlertDialog alertDialog = builder.create();
        final EditText name = (EditText)view1.findViewById(R.id.studio_name);
        final EditText row = (EditText)view1.findViewById(R.id.studio_row);
        final EditText column = (EditText)view1.findViewById(R.id.studio_column);

        Studio s = studioList.get(flag);

        name.setText(s.getName());
        row.setText(String.valueOf(s.getRow()));
        column.setText(String.valueOf(s.getColumn()));

        Button cancel = (Button)view1.findViewById(R.id.studio_cancel);
        Button confirm = (Button)view1.findViewById(R.id.studio_confirm);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(!"".equals(name.getText().toString() ) &&!"".equals(row.getText().toString()) && !"".equals(column.getText().toString() ) ) {


                        studioList.get(flag).setName(name.getText().toString());
                        studioList.get(flag).setRow(Integer.valueOf(row.getText().toString()));
                        studioList.get(flag).setColumn(Integer.valueOf(column.getText().toString()));

                        studioNames.set(flag,name.getText().toString());

                    }

                    adapter.notifyDataSetChanged();

                    listView.setSelection(studioNames.size());

                    alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }
}
