package com.example.AIDLLearning;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.AIDLLearning.aidl.Book;
import com.example.AIDLLearning.aidl.IBookManager;
import com.example.AIDLLearning.service.IpcService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<Activity> activities=new ArrayList<>();
    private IBookManager iBookManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activities.add(this);
        Intent intent =new Intent(this, IpcService.class);
        bindService(intent,connection,BIND_AUTO_CREATE);
    }

    public void onClick(View view){
        startActivity(new Intent(this,SecondActivity.class));
    }

    @Override
    protected void onDestroy() {
        unbindService(connection);
        super.onDestroy();
    }

    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iBookManager=IBookManager.Stub.asInterface(service);
            try {
                String processName=getProcessName();
                int sizeId=0;
                if(iBookManager.getListBook()!=null) {
                    sizeId=iBookManager.getListBook().size();
                    Log.d("测试", processName + ":" + sizeId);
                }
                sizeId+=1;
                iBookManager.addBook(new Book(sizeId,processName+"书包"+sizeId));
                if(iBookManager.getListBook()!=null)
                    for (Book book: iBookManager.getListBook()){
                        Log.d("测试",processName+":"+book.name);
                    }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    /**
     * 获取进程名
     * @return
     */
    private String getProcessName(){
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo process: manager.getRunningAppProcesses()) {
            if(process.pid == pid)
            {
                processName = process.processName;
            }
        }
        return processName;
    }
}
