package com.example.ipc.util;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;

/**
 * Created by guocai.cgc on 2018/4/2.
 */

public class SystemInfoUtils {

    public static String getProcessName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo processInfo : am.getRunningAppProcesses()) {
            if (processInfo.pid == Process.myPid()) {
                return processInfo.processName;
            }
        }
        return null;
    }
}
