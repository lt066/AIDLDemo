package com.example.AIDLLearning.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.AIDLLearning.aidl.Book;
import com.example.AIDLLearning.aidl.IBookManager;

import java.util.ArrayList;
import java.util.List;

public class IpcService extends Service {
    private ArrayList<Book> list;
    public IpcService() {
        if(list==null)
            list=new ArrayList<>();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBookManager.asBinder();
    }

    private IBookManager iBookManager=new IBookManager.Stub(){

        @Override
        public List<Book> getListBook() throws RemoteException {
            return list;
        }

        @Override
        public void addBook(Book Book) throws RemoteException {
            list.add(Book);
        }
    };
}
