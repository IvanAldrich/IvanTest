package com.example.guocaicgc.ivantest;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.util.Log;

/**
 * Created by guocai.cgc on 2017/10/23.
 */

public class IvanApplication extends Application {

    @Override
    public void onCreate() {

        super.onCreate();

        Log.e("ivan", "application onCreate. processName = " + getProcessName(Process.myPid()));
    }

    private String getProcessName(int processId) {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo runningProcess : activityManager
                .getRunningAppProcesses()) {
            if (runningProcess.pid == processId) {
                return runningProcess.processName;
            }
        }
        return null;
    }
}
