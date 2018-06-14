package com.zwy.ttms.Customer;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by jack on 2018/6/6.
 */

public class MyTextView extends android.support.v7.widget.AppCompatTextView{
    public MyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }
    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }
    public MyTextView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
    //一直return true
    @Override
    public boolean isFocused() {
        // TODO Auto-generated method stub
        return true;
    }
}
