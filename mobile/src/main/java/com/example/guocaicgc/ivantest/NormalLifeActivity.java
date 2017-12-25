package com.example.guocaicgc.ivantest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.example.guocaicgc.ivantest.fragment.YeahFragment;

/**
 * Created by guocai.cgc on 2017/1/18.
 */

public class NormalLifeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("ivan", "onCreate normal");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_life);
        findViewById(R.id.tv_normal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        addFragment();
    }

    private void addFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.ll_fragment_container, YeahFragment.getInstance(R.id.ll_fragment_container));
        ft.commit();
    }

    @Override
    protected void onResume() {
        Log.e("ivan", "onResume normal");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        Log.e("ivan", "onRestart normal");
        super.onRestart();
    }

    @Override
    protected void onPause() {
        Log.e("ivan", "onPause normal");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.e("ivan", "onStop normal");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.e("ivan", "onDestroy normal");
        super.onDestroy();
    }
}
