package com.example.AIDLLearning;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static com.example.AIDLLearning.SecondActivity.BTN_RECEIVER_ACTION;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        MainActivity.activities.add(this);
    }

    public void onClick(View view){
        senRececive();
    }

    private void senRececive(){

        Intent intent=new Intent();
        intent.setAction(BTN_RECEIVER_ACTION);
        intent.putExtra("out","输出:"+System.currentTimeMillis());
        sendBroadcast(intent);
    }
}
