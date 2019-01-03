package com.example.guocaicgc.ivantest.util;

import android.content.Context;

/**
 * Created by guocai.cgc on 2018/9/3.
 */

public class ToReflectAction {
    private String mName;

    private static final class Holder {
        private static final ToReflectAction INSTANCE = new ToReflectAction();
    }

    private ToReflectAction() {}

    public static ToReflectAction getInstance(Context context) {
        return Holder.INSTANCE;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }
}
