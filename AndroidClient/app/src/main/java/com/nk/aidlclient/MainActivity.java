package com.nk.aidlclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nk.aidlserver.BookController;
import com.nk.aidlserver.Books;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button button;

    private BookController bookController;
    private List<Books> bookList;
    private boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        button = findViewById(R.id.button);
        bindService();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (connected) {
                    try {
                        bookList = bookController.getBookList("1");
                        Log.d("获取服务数据","booklist"+bookList.get(0).getName());
                        for (Books books : bookList){
                            textView.append(books.getName());
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            bookController = BookController.Stub.asInterface(iBinder);
            connected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            connected = false;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connected) {
            unbindService(serviceConnection);
        }
    }

    private void bindService() {
//        Intent intent = new Intent();
//        intent.setPackage("com.nk.aidlserver");
//        intent.setClassName("com.nk.aidlserver","com.nk.aidlserver.AIDLService");
//        intent.setAction("com.nk.aidlserver.MyService");
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.nk.aidlserver", "com.nk.aidlserver.AIDLService"));
        bindService(intent, serviceConnection, getApplicationContext().BIND_AUTO_CREATE);
        Log.d("去启动服务","");
    }
}