package com.example.guocaicgc.ivantest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by guocai.cgc on 2017/1/18.
 */

public class LifeCycleNoResumeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("ivan", "onCreate");
        super.onCreate(savedInstanceState);
        finish();
    }

    @Override
    protected void onResume() {
        Log.e("ivan", "onResume");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        Log.e("ivan", "onRestart");
        super.onRestart();
    }

    @Override
    protected void onPause() {
        Log.e("ivan", "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.e("ivan", "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.e("ivan", "onDestroy");
        super.onDestroy();
    }
}
