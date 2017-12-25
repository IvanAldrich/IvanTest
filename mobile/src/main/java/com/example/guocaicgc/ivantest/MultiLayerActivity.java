package com.example.guocaicgc.ivantest;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Window;

/**
 * Created by guocai.cgc on 2017/8/15.
 */

public class MultiLayerActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_multi_layer);
    }
}
