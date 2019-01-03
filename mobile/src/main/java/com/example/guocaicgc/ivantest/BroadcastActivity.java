package com.example.guocaicgc.ivantest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * Created by guocai.cgc on 2018/7/3.
 */

public class BroadcastActivity extends Activity {
    private static final String ACTION_REC = "AUTONAVI_STANDARD_BROADCAST_RECV";

    private EditText mRecEt;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_broadcast);

        mRecEt = (EditText) findViewById(R.id.et_broadcast_rec);

//        registerReceiver(new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                String action = intent.getAction();
//                Log.e("ivan", "action= " + action);
//                if (ACTION_REC.equals(action)) {
//                    double lat = intent.getDoubleExtra("LAT", 0);
//                    double lon = intent.getDoubleExtra("LON", 0);
//
//                    String toShow = "lat=" + lat + ", lon = " + lon;
//                    mRecEt.setText(toShow);
//                }
//            }
//        }, null);
    }

    public void sendBroadcast(View view) {
        Intent intent = new Intent(ACTION_REC);
        intent.putExtra("KEY_TYPE", 10038);
        intent.putExtra("POINAME", "IVAN");
        intent.putExtra("LAT", 24.444593);
        intent.putExtra("LON", 118.101011);
        intent.putExtra("ENTRY_LAT", 24.444593);
        intent.putExtra("ENTRY_LON", 118.101011);
        intent.putExtra("DEV", 1);
        intent.putExtra("STYLE", -1);
        intent.putExtra("SOURCE_APP", "IVAN");

        sendBroadcast(intent);
    }
}
