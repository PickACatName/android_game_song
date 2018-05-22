package com.example.lenovo.chatapp;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lenovo on 2018/5/21.
 */

public class TimeHandler extends Handler{

    WeakReference<MainActivity> w_a;

    public TimeHandler(MainActivity a){
        w_a = new WeakReference<MainActivity>(a);
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                MainActivity w = w_a.get();
                w.addOneMsg();
                break;
        }
        super.handleMessage(msg);
    }
}
