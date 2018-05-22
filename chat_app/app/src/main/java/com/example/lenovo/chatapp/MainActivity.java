package com.example.lenovo.chatapp;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    private List<Msg> msgList = new ArrayList<>();
    private List<Msg> showList = new ArrayList<>();
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;
    private DrawerLayout mDrawerLayout;
    private int count = 1;
    private int showImgNum = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 得到Toolbar的实例
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // 调用setSupportActionBar()方法并将Toolbar的实例传入
        setSupportActionBar(toolbar);

        // 得到DrawerLayout的实例
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        // 得到了ActionBar的实例
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_reorder_white_24);
        }

        // 默认选中第一个选项
        navView.setCheckedItem(R.id.nav_account);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        //初始化消息数据
        initMsgs();

        msgRecyclerView = (RecyclerView) findViewById(R.id.msg_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);

        adapter = new MsgAdapter(showList);
        msgRecyclerView.setAdapter(adapter);

        Button b = (Button)findViewById(R.id.option1);
        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getApplicationContext(), "option1 clicked", Toast.LENGTH_SHORT).show();
                count = count + 1;
                resetMsgs(count);
            }
        });

        TimeThread t = new TimeThread(this, msgList.size());
        Thread t1 = new Thread(t);
        t1.start();

        Log.d("MainActivity", "after handler");
    }


    public void addOneMsg(){
        Msg msg = msgList.get(showImgNum);
        showImgNum = showImgNum + 1;
        showList.add(msg);
        adapter.notifyItemInserted(showList.size() - 1);
        msgRecyclerView.scrollToPosition(showList.size() - 1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;

            default:
        }
        return true;
    }


    private void initMsgs(){
        //得到本地json文本内容
        String fileName = "test.json";
        String msgJson = LocalJsonResolutionUtils.getJson(this, fileName);
        //转换为对象
        Gson gson =new Gson();
        msgList = gson.fromJson(msgJson, new TypeToken<List<Msg>>(){}.getType());
    }

    private void resetMsgs(int n){
        //得到本地json文本内容
        String fileName = "test"+String.valueOf(n)+".json";
        String msgJson = LocalJsonResolutionUtils.getJson(this, fileName);
        //转换为对象
        Gson gson =new Gson();
        msgList = gson.fromJson(msgJson, new TypeToken<List<Msg>>(){}.getType());
//        showList.clear();
        showImgNum = 0;
//        adapter = new MsgAdapter(showList);
//        msgRecyclerView.setAdapter(adapter);

        TimeThread t = new TimeThread(this, msgList.size());
        Thread t1 = new Thread(t);
        t1.start();
    }

}
