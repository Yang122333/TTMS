package com.zwy.ttms;

import android.app.ProgressDialog;
import android.content.Context;

public class LoadingDialog {
    private Context mcontext;
    private ProgressDialog progressDialog;

    public LoadingDialog(Context context){
        mcontext = context ;
    }
    public void showDialog(){
        progressDialog = ProgressDialog.show(mcontext, "", "请稍后");
    }
    public void dismissDialog(){
        if (progressDialog != null)
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
    }
}
