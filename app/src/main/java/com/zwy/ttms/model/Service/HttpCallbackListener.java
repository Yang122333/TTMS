package com.zwy.ttms.model.Service;

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
