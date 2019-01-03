package com.example.guocaicgc.ivantest.surface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

/**
 * Created by guocai.cgc on 2019/1/3.
 */

public class CustomSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private int mWidth;
    private int mHeight;

    public CustomSurfaceView(Context context) {
        super(context);
    }

    public CustomSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = getHolder();
        mHolder.addCallback(this);
//        setZOrderOnTop(true);
        mHolder.setFormat(PixelFormat.TRANSLUCENT);
    }

    public CustomSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setFormat(PixelFormat.TRANSLUCENT);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        new DrawThread().start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public class DrawThread extends Thread {
        private Paint mPaint;
        private int[] mColors = new int[3];
        private Random mRandom = new Random();

        public DrawThread() {
            mPaint = new Paint();
            mColors[0] = Color.RED;
            mColors[1] = Color.WHITE;
            mColors[2] = Color.GRAY;
        }

        @Override
        public void run() {
            super.run();
            while (true) {
                drawCircle();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void drawCircle() {
            Canvas canvas = mHolder.lockCanvas();
            if (null == canvas) {
                return;
            }
            mPaint = new Paint();
            mPaint.setColor(mColors[mRandom.nextInt(3)]);
            canvas.drawRect(0, 0, mWidth, mHeight, mPaint);

            mHolder.unlockCanvasAndPost(canvas);
        }
    }
}
