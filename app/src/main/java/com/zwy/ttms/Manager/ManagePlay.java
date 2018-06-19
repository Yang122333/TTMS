package com.zwy.ttms.Manager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.zwy.ttms.LoginActivity;
import com.zwy.ttms.R;
import com.zwy.ttms.model.Service.HttpCallbackListener;
import com.zwy.ttms.model.Service.HttpUtil;
import com.zwy.ttms.model.play.Play;
import com.zwy.ttms.model.play.PlayAndLogParseJSON;


import java.util.ArrayList;
import java.util.List;

public class ManagePlay extends Activity{

    public static  void actionStart(Context context){
        Intent intent = new Intent(context,ManagePlay.class);
        context.startActivity(intent);
    }
    private Handler handler;
    private Button add_play;
    private ViewGroup tableTitle;

    List<Play> list =new ArrayList<>();
    ImageButton imb;
    String path = "";
    Play play;
    public static final int SHOW_RESPONSE = 0;
    private PlayAda adapter;
     ListView tableListView;

    String ip = LoginActivity.ip;
     String address1 = ip+"director?method=getPlayList"; //查询所有剧目
     String address2 = ip+"director?method=addPlay";
     String address3 = ip+"director?method=updatePlay";
     String  address4 = ip+"director?method=deleterPlay";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_ui);
       Button add_play = (Button) findViewById(R.id.add_play);
        tableListView = (ListView) findViewById(R.id.list);
        TextView tv =(TextView) findViewById(R.id.title_message);
        tv.setText("剧目管理");


//       playdao = new PlayDao(this);
//       try {
//           Iterator it = (playdao.queryAll()).iterator();
//           while (it.hasNext()) {
//               list.add((Play) it.next());
//           }     //查询所有剧目信息
//       }catch (Exception e){
//           System.out.print("000000000");
//       }

            HttpUtil.sendHttpRequest(address1, "GET",new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    Log.i("1234", response);
                    list = PlayAndLogParseJSON.toPlaylist(response);
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);

                }
                @Override
                public void onError(Exception e) {
                }
            });
       handler = new Handler(){
           @Override
           public void handleMessage(Message msg) {
               super.handleMessage(msg);
               switch (msg.what){
                   case 1:{
                       adapter = new PlayAda(ManagePlay.this,R.layout.play_item, list);
                       tableListView.setAdapter(adapter);  //注册单击ListView中的Item响应的事件

                       break;
                   }
               }
           }
       };

        tableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                final int currentPosition = position;
                AlertDialog.Builder builder= new AlertDialog.Builder(ManagePlay.this);
                builder.setPositiveButton("修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ManagePlay.this);
                        final AlertDialog dialog1 = builder.create();
                        View dialogView = View.inflate(ManagePlay.this, R.layout.change_play, null); /////////////change
                        //设置对话框布局
                        dialog1.setView(dialogView);
                        dialog1.show();
                        final EditText play_name = (EditText) dialogView.findViewById(R.id.play_name);
                        final EditText play_type = (EditText) dialogView.findViewById(R.id.play_type);
                        final EditText play_length= (EditText) dialogView.findViewById(R.id.length);
                        final EditText play_language = (EditText) dialogView.findViewById(R.id.play_language);
                        final EditText play_intro = (EditText) dialogView.findViewById(R.id.play_intro);
                        Button finishiChange = (Button)dialogView.findViewById(R.id.finish_Change);

                        play_name.setText(list.get(currentPosition).getPlay_name());
                        play_type.setText(list.get(currentPosition).getPlay_type());
                        play_length.setText(list.get(currentPosition).getPlay_length());
                        play_language.setText(list.get(currentPosition).getPlay_language());
                        play_intro.setText(list.get(currentPosition).getPlay_intro());

                        finishiChange.setOnClickListener(new View.OnClickListener() {   //修改剧目
                            @Override
                            public void onClick(View v) {
                                list.set(currentPosition,new Play(play_name.getText().toString(), play_type.getText().toString(),
                                        play_language.getText().toString(),Integer.parseInt(play_length.getText().toString()),play_intro.getText().toString()));

                                String[] enum1 = new String[]{"COMEDY","ADVENTURE","MYSTERY","DOCUMENTARY","CHINESE","ENGLISH"};
                                int k =0,q=0;
                                for(int i=1;i<=enum1.length;i++){
                                    if ((play_type.getText().toString()).equals(enum1[i-1])) {
                                        k = i - 1;
                                        for (int j = 1; j <= enum1.length; j++) {
                                            if ((play_language.getText().toString()).equals(enum1[j - 1])) {
                                                q = j - 1;
                                                address3 += "&unionId"+list.get(position).getPlay_id()+"&type=" + enum1[k]+ "&language="+enum1[q]+"&name="+play_name.getText().toString() +
                                                         "&introduction=" + play_intro.getText().toString()+"&image=" + "00"+"&length=" + play_length.getText().toString();
//
                                            }
                                        }
                                    }
                                }
                                HttpUtil.sendHttpRequest(address3, "POST",new HttpCallbackListener() {
                                    @Override
                                    public void onFinish(String response) {
                                        list = PlayAndLogParseJSON.toPlaylist(response);
                                    }
                                    @Override
                                    public void onError(Exception e) {
                                    }
                                });
                                try{ Thread.sleep(2000);
                                }catch (Exception e){
                                }
                                adapter.notifyDataSetChanged();
                                dialog1.dismiss();
                            }
                        });


                    }
                }).setNegativeButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {     //删除剧目
                         address4 += "&unionId="+list.get(position).getPlay_id();
                        HttpUtil.sendHttpRequest(address4, "POST",new HttpCallbackListener() {
                            @Override
                            public void onFinish(String response) {
                                list = PlayAndLogParseJSON.toPlaylist(response);
                            }
                            @Override
                            public void onError(Exception e) {
                            }
                        });
                        try{ Thread.sleep(2000);
                        }catch (Exception e){
                        }
                        list.remove(currentPosition);
                        adapter.notifyDataSetChanged();
                    }

                });
                builder.create().show();
            }
        });

        add_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ManagePlay.this);
                final AlertDialog dialog1 = builder.create();
                View dialogView = View.inflate(ManagePlay.this, R.layout.add_play, null);
                //设置对话框布局
                dialog1.setView(dialogView);
                dialog1.show();
                final ImageButton imageButton = (ImageButton) dialogView.findViewById(R.id.play_picture);
                final EditText play_name = (EditText) dialogView.findViewById(R.id.play_name);
                final EditText play_type = (EditText) dialogView.findViewById(R.id.play_type);
                final EditText play_length = (EditText) dialogView.findViewById(R.id.play_length);
                final EditText play_language = (EditText) dialogView.findViewById(R.id.play_language);
                final EditText play_intro = (EditText) dialogView.findViewById(R.id.play_intro);
                Button finishiChange = (Button)dialogView.findViewById(R.id.finish_Change);

                imb = imageButton;
                imageButton.setOnClickListener(new View.OnClickListener() {   //上传图片
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        startActivityForResult(intent, 1);
                    }

                });
                finishiChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        list.add(new Play(play_name.getText().toString(),Integer.parseInt(play_length.getText().toString()),play_type.getText().toString(),
                                play_language.getText().toString(), play_intro.getText().toString(),"00"));
                        adapter.notifyDataSetChanged();
                        tableListView.setSelection(list.size());
                        //插入剧目
                        String[] enum1 = new String[]{"COMEDY","ADVENTURE","MYSTERY","DOCUMENTARY","CHINESE","ENGLISH"};
                        int k =0,q=0;
                        for(int i=1;i<=enum1.length;i++){
                            if ((play_type.getText().toString()).equals(enum1[i-1])) {
                                k = i - 1;
                                for (int j = 1; j <= enum1.length; j++) {
                                    if ((play_language.getText().toString()).equals(enum1[j - 1])) {
                                        q = j - 1;
                                        address2 += "&name=" + play_name.getText().toString() + "&length=" + play_length.getText().toString() +
                                                "&type=" + enum1[k] + "&language=" + enum1[q] + "&introduction=" + play_intro.getText().toString() +
                                                "&image=" + "00";
                                    }
                                }
                            }
                        }
                        HttpUtil.sendHttpRequest(address2, "POST",new HttpCallbackListener() {
                            @Override
                            public void onFinish(String response) {
                                list = PlayAndLogParseJSON.toPlaylist(response);
                            }
                            @Override
                            public void onError(Exception e) {
                            }
                        });
                        try{ Thread.sleep(2000);
                        }catch (Exception e){
                        }
                        dialog1.dismiss();
                        //插入剧目信息
//                        playdao.insert(new Play(play_name.getText().toString(),Integer.parseInt( play_length.getText().toString()),play_type.getText().toString(),
//                                play_language.getText().toString(),play_intro.getText().toString(),"00"));
                    }
                });

            }
        });
    }
    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(null!=cursor&&cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }
        return res;
    }
    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if (uri != null) {
                imb.setImageURI(uri);
                if ("file".equalsIgnoreCase(uri.getScheme())){//使用第三方应用打开
                    path = uri.getPath();
                }
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                    path = getPath(this, uri);
                } else {
                    path = getRealPathFromURI(uri);
                }
            }
        }
    }


}

