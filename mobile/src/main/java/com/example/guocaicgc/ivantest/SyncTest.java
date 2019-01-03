package com.example.guocaicgc.ivantest;

import android.util.Log;

import org.json.JSONObject;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by guocai.cgc on 2018/6/19.
 */

public class SyncTest {

    private static AtomicBoolean sFocus = new AtomicBoolean(false);
    private static Object sJson = new Object();
    private static ExecutorService mSingle = Executors.newSingleThreadExecutor();

    private static void request() {
        Log.e("SyncTest", "request obj = " + sJson + "\nthread= " + Thread.currentThread());
        synchronized (sFocus) {
            try {
                Log.e("SyncTest", "request before； " + sFocus.get());
                TimeUnit.MICROSECONDS.sleep(1000000);
                sFocus.set(true);
                Log.e("SyncTest", "request end； " + sFocus.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static int sInt = 0;
    private static int sIntExe = 0;
    private static void abandon() {
        Log.d("SyncTest", "abadom. obj = " + sJson + "\nthread= " + Thread.currentThread());
//        synchronized (sFocus) {
//            synchronized (sFocus) {
//                try {
//                    Log.d("SyncTest", "abadom before; " + sFocus.get());
//                    TimeUnit.MICROSECONDS.sleep(1000000);
//                    sFocus.set(false);
//                    Log.d("SyncTest", "abadom after; " + sFocus.get());
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        sInt = 0;
        while (true) {
            Log.e("SyncTest", "abadon while. sInt= " + sInt++);
            try {
                TimeUnit.MILLISECONDS.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mSingle.execute(new Runnable() {
                @Override
                public void run() {
                    Log.d("SyncTest", "abadon while execute. sIntExe= " + sIntExe++);
                    try {
                        TimeUnit.MILLISECONDS.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void run() {
//        for (int i = 0; i < 40; i++) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    SyncTest.request();
//                }
//            }, "thread-" + i).start();
//        }
//
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                SyncTest.request();
//            }
//        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                SyncTest.abandon();

                Log.d("SyncTest", "abadon while exit . sInt = " + sInt);
            }
        }).start();
    }
}
