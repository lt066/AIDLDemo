package com.example.AIDLLearning2;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.AIDLLearning.aidl.Book;
import com.example.AIDLLearning.aidl.IBookManager;

public class MainActivity extends AppCompatActivity {
    private IBookManager iBookManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent =new Intent();
        intent.setComponent(new ComponentName("com.example.AIDLLearning","com.example.AIDLLearning.service.IpcService"));
        bindService(intent,connection,BIND_AUTO_CREATE);

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
