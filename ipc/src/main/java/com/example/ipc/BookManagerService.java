package com.example.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.example.ipc.util.SystemInfoUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by guocai.cgc on 2018/4/2.
 */

public class BookManagerService extends Service {
    private CopyOnWriteArrayList mBookList = new CopyOnWriteArrayList();
    private RemoteCallbackList<INewBookCallback> mCallbacks = new RemoteCallbackList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("ivan", "rpc service onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private IBinder mBinder = new IBookManager.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void addBook(IBook book) throws RemoteException {
            Log.e("ivan", "service addBook. processName = "+ SystemInfoUtils.getProcessName
                    (BookManagerService.this));
            book.setAuthor(book.getAuthor() + "  modify");
            mBookList.add(book);

            int size = mCallbacks.beginBroadcast();
            for (int i = 0; i < size; i++) {
                INewBookCallback callback = mCallbacks.getBroadcastItem(i);
                callback.onNewBook(book);
            }
            mCallbacks.finishBroadcast();
        }

        @Override
        public List<IBook> getBookList() throws RemoteException {
            Log.e("ivan", "service getBookList. processName = "+ SystemInfoUtils.getProcessName
            (BookManagerService.this));
            return mBookList;
        }

        @Override
        public void registCallback(com.example.ipc.INewBookCallback callback) throws RemoteException {
            mCallbacks.register(callback);
        }

        @Override
        public void unregistCallback(com.example.ipc.INewBookCallback callback) throws RemoteException {
            boolean result = mCallbacks.unregister(callback);
            Log.e("ivan", "unregist result = " + result);
        }
    };
}
