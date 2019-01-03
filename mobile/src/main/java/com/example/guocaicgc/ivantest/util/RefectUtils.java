package com.example.guocaicgc.ivantest.util;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by guocai.cgc on 2017/3/8.
 *
 * @author guocai.cgc
 * @date 2017/03/08
 */

public class RefectUtils {
    private static final String TAG = RefectUtils.class.getSimpleName();

    public static void reflectWrapper() {

    }

    public static void reflectSingleInstance(Context context) {
        try {
            Class actionClass = Class.forName("com.example.guocaicgc.ivantest.util.ToReflectAction");
            Method getInstanceM = actionClass.getMethod("getInstance", new Class[]{Context.class});
            Object actionInstance = getInstanceM.invoke(actionClass, new Object[]{context});

            ToReflectAction.getInstance(context).setName("hehe");

            Method getNameM = actionClass.getMethod("getName", new Class[]{});
            Object nameResult = getNameM.invoke(actionInstance, new Object[]{});

            if (null != nameResult) {
                Log.d(TAG, "reflectSingleInstance(). nameResult= " + nameResult.toString());
            } else {
                Log.d(TAG, "reflectSingleInstance(). nameResult= null");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
