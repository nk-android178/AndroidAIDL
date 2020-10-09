package com.nk.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class AIDLService extends Service {

    private List<Books> booksList;

    public static final String TAG = "AIDLService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() executed");
        booksList = new ArrayList<>();
        initData();
        Log.d(TAG,"启动服务端服务");
    }

    private void initData() {
        Books book1 = new Books("nk");
        Books book2 = new Books("github");
        Books book3 = new Books("https://github.com/");
        Books book4 = new Books("https://github.com/nk-android178/AndroidAIDL");
        booksList.add(book1);
        booksList.add(book2);
        booksList.add(book3);
        booksList.add(book4);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() executed");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() executed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    private final BookController.Stub stub = new BookController.Stub() {
        @Override
        public List<Books> getBookList(String data) throws RemoteException {
            if (data.equals("1")) {
                return booksList;
            } else {
                booksList.clear();
                Books book1 = new Books("类型错误");
                booksList.add(book1);
                return booksList;
            }
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    };
}