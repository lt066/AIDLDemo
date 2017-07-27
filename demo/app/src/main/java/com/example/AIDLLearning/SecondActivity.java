package com.example.AIDLLearning;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SecondActivity extends AppCompatActivity {

    public static final String BTN_RECEIVER_ACTION="changeAcceptBtn";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        MainActivity.activities.add(this);
        Log.d("测试","注册广播");
        myBroadCast=new MyBroadCast();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(BTN_RECEIVER_ACTION);
        registerReceiver(myBroadCast,intentFilter);
    }

    public void onClick(View view){
        startActivity(new Intent(this,ThirdActivity.class));
    }

    @Override
    protected void onDestroy() {
        if(myBroadCast!=null){
            Log.d("测试","注销广播");
            unregisterReceiver(myBroadCast);
        }
        super.onDestroy();
    }

    public MyBroadCast myBroadCast;
    public class MyBroadCast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("测试",intent.getStringExtra("out"));
        }
    }
}
