package com.example.guocaicgc.ivantest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by guocai.cgc on 2018/12/21.
 */

public class BitmapCacheActivity extends Activity {
    private ImageView mBitIv;
    private Bitmap mSourceBit;
    private Bitmap mCachedBit;
    private Bitmap mToDrawBit;
    private Canvas canvas = new Canvas();
    private Paint mPaint = new Paint();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_cache);

        mBitIv = (ImageView) findViewById(R.id.iv_cache_);
    }

    public void doRound(View view) {
        mSourceBit = BitmapFactory.decodeResource(getResources(), R.drawable.ivan_map_cache);

        mCachedBit = Bitmap.createBitmap(mSourceBit.getWidth(), mSourceBit.getHeight(), Bitmap
                .Config.ARGB_4444);
        mToDrawBit = Bitmap.createBitmap(mSourceBit.getWidth(), mSourceBit.getHeight(), Bitmap
                .Config.ARGB_4444);
        mPaint.setAntiAlias(true);
        getRoundBit();

        updateImage();
    }

    private void updateImage() {
        mToDrawBit.eraseColor(Color.TRANSPARENT);
        canvas.drawARGB(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT);
        canvas.setBitmap(mToDrawBit);
        canvas.save();
        canvas.drawBitmap(mCachedBit, 0, 0, null);
        canvas.restore();

        mBitIv.setImageBitmap(mToDrawBit);
    }

    private void getRoundBit() {
        canvas.setBitmap(mCachedBit);
        canvas.save();
        canvas.drawRoundRect(new RectF(0, 0, mCachedBit.getWidth(), mCachedBit.getHeight()),
                200, 200, mPaint);
        canvas.drawRect(new RectF(0, 200, mCachedBit.getWidth(), mCachedBit.getHeight()), mPaint);
        canvas.drawRect(new RectF(0, 0, mCachedBit.getWidth() - 200,
                200), mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(mSourceBit, 0, 0, mPaint);
        mPaint.setXfermode(null);
        canvas.restore();
    }

    private void getRoundBitOld() {
        mCachedBit.eraseColor(Color.TRANSPARENT);
        canvas = new Canvas(mCachedBit);
        canvas.drawARGB(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT);
        mPaint.setShader(new BitmapShader(mSourceBit, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect(new RectF(0, 0, mSourceBit.getWidth(), mSourceBit.getHeight()), 220,
                220, mPaint);

    }

    private Bitmap transformTopCorners(Bitmap source) {
        int width = source.getWidth();
        int height = source.getHeight();
        Bitmap newSource = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newSource);
        canvas.save();
        canvas.drawText("hehehelsijfls/nsdkls", 0, 0, mPaint);
        mPaint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        //绘制一个全部圆角的矩形区域长宽分别为 right 和 bottom
        canvas.drawRoundRect(new RectF(0, 0, width, height), 100, 100, mPaint);
        //绘制一个矩形长宽分别为 right 和  bottom-radius，相当于底部和上面对齐而高度差个 radius, 和上面所绘制的并集，即为图片的可见区域。并集即为上部为圆角而底部是直角的一个区域
        canvas.drawRect(new RectF(0, 20, width, height), mPaint);
//        source.recycle();
        canvas.restore();
        return newSource;
    }

}
