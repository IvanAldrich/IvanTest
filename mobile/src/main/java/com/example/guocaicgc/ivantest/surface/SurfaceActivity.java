package com.example.guocaicgc.ivantest.surface;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.guocaicgc.ivantest.R;

/**
 * Created by guocai.cgc on 2019/1/3.
 */

public class SurfaceActivity extends Activity {
    private RelativeLayout mContainerRl;
    private WindowManager mWManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface);

        CustomSurfaceView surfaceView = (CustomSurfaceView) findViewById(R.id.sv_surface);
        mContainerRl = (RelativeLayout) findViewById(R.id.rv_surface_container);

        mContainerRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWindowSurface();
            }
        });
    }

    private void addWindowSurface() {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                500,
                600,
                WindowManager.LayoutParams.TYPE_SGM_NOTIFICATION_CARD_WINDOW, // the window type
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);
        lp.gravity = Gravity.LEFT | Gravity.TOP;
        lp.windowAnimations = -1;

        View windowView = View.inflate(SurfaceActivity.this, R.layout.layout_surface_window,
                null);
        findViewById(R.id.sv_window_surface);

        mWManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mWManager.addView(windowView, lp);
    }
}
