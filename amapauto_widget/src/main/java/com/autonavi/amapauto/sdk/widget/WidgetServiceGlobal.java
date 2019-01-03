package com.autonavi.amapauto.sdk.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.autonavi.amapauto.widget.aidl.IAmapAutoWidgetManager;
import com.autonavi.amapauto.widget.aidl.IAutoWidgetStateControl;

/**
 *
 * Created by guocai.cgc on 2018/12/5.
 */

public class WidgetServiceGlobal implements IWidgetServiceInteraction {
    private static final String TAG = WidgetServiceGlobal.class.getSimpleName();

    private static final String SERVICE_PACKAGE_NAME = "com.autonavi.amapauto";
    private static final String SERVICE_CLASS_NAME = "com.autonavi.amapauto.widget.service.AmapAutoWidgetControlService";

    private Context mContext;
    private IAmapAutoWidgetManager mWidgetManager;

    public WidgetServiceGlobal(Context context) {
        mContext = context;
    }

    private ServiceConnection mServiceCon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected().");
            mWidgetManager = IAmapAutoWidgetManager.Stub.asInterface(service);

            try {
                service.linkToDeath(mDeathRecipient, 0);
            } catch (RemoteException e) {
                Log.e(TAG, "linkToDeath fail.", e);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected().");
            mWidgetManager = null;
        }
    };

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.w(TAG, "binderDied().");
            if (null != mWidgetManager) {
                mWidgetManager.asBinder().unlinkToDeath(mDeathRecipient, 0);
                mWidgetManager = null;
            }

            connectWidgetService();
        }
    };

    public boolean initWidgetService() {
        return isServiceConnected();
    }


    /**
     * aidl服务是否正常连接
     *
     * @return
     */
    private boolean isServiceConnected() {
        boolean isConnected = false;
        if (null != mWidgetManager) {
            isConnected = true;
        } else {
            isConnected = connectWidgetService();
        }

        if (null == mWidgetManager) {
            isConnected = false;
        }

        Log.d(TAG, "isServiceConnected. isConnected = " + isConnected);

        return isConnected;
    }

    private boolean connectWidgetService() {
        Log.d(TAG, "connectWidgetService");
        if (mContext == null) {
            Log.w(TAG, "connectWidgetService(). mContext null");
            return false;
        }

        Intent intent = new Intent();
        intent.setComponent(new ComponentName(SERVICE_PACKAGE_NAME, SERVICE_CLASS_NAME));
        return mContext.getApplicationContext().bindService(intent, mServiceCon,
                Context.BIND_AUTO_CREATE);
    }

    @Override
    public boolean startAutoWidget() {
        Log.d(TAG, "startAutoWidget()");
        if (isServiceConnected()) {
            try {
                Log.d(TAG, "startAutoWidget(). start");
                mWidgetManager.startAutoWidget();
                return true;
            } catch (RemoteException e) {
                Log.e(TAG, "startAutoWidget(). fail. ", e);
            }
        }
        return false;
    }

    @Override
    public boolean stopAutoWidget() {
        Log.d(TAG, "stopAutoWidget()");
        if (isServiceConnected()) {
            try {
                Log.d(TAG, "stopAutoWidget(). stop");
                mWidgetManager.stopAutoWidget();
                return true;
            } catch (RemoteException e) {
                Log.e(TAG, "stopAutoWidget(). fail. ", e);
            }
        }
        return false;
    }

    @Override
    public boolean setWidgetStateControl(IAutoWidgetStateControl stateControl) {
        Log.d(TAG, "setWidgetStateControl()");
        if (isServiceConnected()) {
            try {
                Log.d(TAG, "setWidgetStateControl(). set");
                mWidgetManager.setWidgetStateControl(stateControl);
                return true;
            } catch (RemoteException e) {
                Log.e(TAG, "setWidgetStateControl(). fail. ", e);
            }
        }
        return false;
    }
}
