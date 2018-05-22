package com.example.lenovo.chatapp;

/**
 * Created by lenovo on 2018/5/21.
 */
import android.os.Message;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;



/**
 * 线程, 计时线程
 */
public class TimeThread implements Runnable{
    private TimeHandler mHandler;
    private int num;

    public TimeThread(MainActivity a, int n){
        mHandler = new TimeHandler(a);
        num = n;
    }


    @Override
    public void run() {
        while(num > 0){   // 这句话 表明一直执行
            try {
                Thread.sleep(1000);    // 1000 ms
                Message msg = new Message();
                msg.what = 1;
                mHandler.sendMessage(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            num = num - 1;
        }
    }

}