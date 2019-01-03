package com.example.ipc;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * Created by guocai.cgc on 2018/3/31.
 */

public class RemoteActivity extends Activity {
    private IBookManager mBookManager;
    private List<IBook> mBookList;
    private INewBookCallback mCallback = new INewBookCallback.Stub() {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void onNewBook(IBook book) throws RemoteException {
            Log.d("ivan", "onNewBook in client, thread= " + Thread.currentThread().getName() +
                    "\nbooname = " + book.getBookName());
        }
    };

    private ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("ivan", "service connected. thread= " + Thread.currentThread().getName());
            mBookManager = IBookManager.Stub.asInterface(service);
            try {
                mBookManager.registCallback(mCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBookManager = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("ivan", "RemoteActivity onCreate");
        setContentView(R.layout.activity_remote);
    }

    public void startService(View view) {
        Intent intent = new Intent(this, BookManagerService.class);
        startService(intent);
        bindService(intent, mServiceConn, 0);
    }

    public void addBook(View view) {
        IBook book = new IBook();
        book.setAuthor("ivan");
        int versionCode = 0;
        if (null != mBookList) {
            versionCode = mBookList.size();
        }
        book.setBookName("<< Map The Future >>. " + versionCode);

        try {
            mBookManager.addBook(book);
            Log.e("ivan", "addBook local. " + book.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void getBookList(View view) {
        if (null == mBookManager) {
            return;
        }
        try {
            mBookList = mBookManager.getBookList();
            if (null == mBookList) {
                Log.e("ivan", "getBookList null");
            } else {
                Log.e("ivan", "getBookList: " + mBookList.toString());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        try {
            mBookManager.unregistCallback(mCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        unbindService(mServiceConn);
        super.onDestroy();
    }
}
