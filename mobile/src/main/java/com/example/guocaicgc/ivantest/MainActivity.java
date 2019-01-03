package com.example.guocaicgc.ivantest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.AudioFormat;
import android.media.AudioTrack;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.guocaicgc.ivantest.component.factory.IvanFactory;
import com.autonavi.amapauto.sdk.widget.AutoWidgetManager;
import com.autonavi.amapauto.widget.aidl.IAutoWidgetStateControl;
import com.example.guocaicgc.ivantest.media.MediaPlayActivity;
import com.example.guocaicgc.ivantest.surface.SurfaceActivity;
import com.example.guocaicgc.ivantest.util.RefectUtils;
import com.example.ipc.RemoteActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    private Handler mDelayHanlder = new Handler();
    private TextView timeTv;
    private AudioTrack mAudio = null;

    private Dialog mDialog;


    private Runnable updateTimeRun = new Runnable() {
        @Override
        public void run() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            String formateTime = dateFormat.format(new Date());
            timeTv.setText(formateTime);

            mDelayHanlder.postDelayed(updateTimeRun, 100);
        }
    };

    private boolean  mShoundInterruptWidget = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("ivan", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testBroadcast();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("title");
        builder.setMessage("message");
        mDialog = builder.create();

        AutoWidgetManager.getInstance().initWidgetService(this);


//        AudioTest.testRelease();
//        SyncTest.run();

//        Throwable e = new Throwable("ivan throw");
//        e.printStackTrace();

//        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
//        View wView = getLayoutInflater().inflate(R.layout.layout_window, null);
//        timeTv = (TextView) wView.findViewById(R.id.tv_system_time);
//        wView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "hehe", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
//        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
//        params.x = 0;
//        params.y = 0;
//        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        wm.addView(wView, params);
//        mDelayHanlder.postDelayed(updateTimeRun, 100);

        if ("1".equals(1)) {
            Log.e("ivan", "amazing! string equals int");
        }
        final int ssssss = 1;

//        IvanFactory factory = new IvanFactory();
//        factory.getComponents();
        // ===  componentstop
//        ComponentManagerImpl componentManager = new ComponentManagerImpl();
//        componentManager.toastComponentList(this);
        // ===  component end
        final ListView testLv = (ListView) findViewById(R.id.lv_test);
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        adapter.add("1");
        adapter.add("2");
        adapter.add("3");
        adapter.add("4");
        adapter.add("5");
        adapter.add("6");
        adapter.add("7");
        adapter.add("7");
        adapter.add("17");
        adapter.add("27");
        adapter.add("37");
        adapter.add("47");
        adapter.add("57");
        adapter.add("67");
        adapter.add("267");
        adapter.add("367");
        adapter.add("467");
        adapter.add("5467");
        adapter.add("6467");
        adapter.add("7467");
        testLv.setAdapter(adapter);
        testLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int visible = testLv.getFirstVisiblePosition();
                View childView = testLv.getChildAt(adapter.getCount() - 1 - visible);
                String childText = "default";
                if (childView instanceof TextView) {
                    childText = (String) ((TextView)childView).getText();
                }
                Toast.makeText(MainActivity.this, "visible= " + visible + "\nchildAt: " + childText, Toast.LENGTH_SHORT).show();

                Intent intent = null;
                if (position % 2 == 0) {
                    intent = new Intent(MainActivity.this, LifeCycleNoResumeActivity.class);
                } else {
                    intent = new Intent(MainActivity.this, NormalLifeActivity.class);
                }
                startActivity(intent);
            }
        });

//        findViewById(R.id.tv_media_player).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(MainActivity.this, MediaPlayActivity.class);
////                startActivity(intent);
//
//            }
//        });
    }

    public void startBitCacheActivity(View view) {
        startActivity(new Intent(MainActivity.this, BitmapCacheActivity.class));
    }

    public void startSurfaceActivity(View view) {
        startActivity(new Intent(MainActivity.this, SurfaceActivity.class));
    }

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("ivan", "onReceive(). action: " + intent.getAction());
        }
    };

    private void testBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("ivan_first");
        registerReceiver(mReceiver, filter);
        filter = new IntentFilter("ivan_second");
        filter.addDataScheme("package");
        registerReceiver(mReceiver, filter);
    }



    public void startRemoteActivity(View view) {
        AutoWidgetManager.getInstance().setWidgetStateControl(new IAutoWidgetStateControl() {
            @Override
            public boolean shouldInteruptWidgetDisplay() throws RemoteException {

                return mShoundInterruptWidget;
            }

            @Override
            public IBinder asBinder() {
                return null;
            }
        });
        mShoundInterruptWidget = !mShoundInterruptWidget;
        Log.d("ivan", "startRemoteActivity. mShoundInterruptWidget = " + mShoundInterruptWidget);
        startActivity(new Intent(this, RemoteActivity.class));
        sendBroadcast(new Intent("ivan_first"));
    }

    public void onMultiLayerClick(View view) {
//        playPcm();
        mDialog.show();
        sendBroadcast(new Intent("ivan_second"));

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.MICROSECONDS.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mDialog.dismiss();
            }
        }).start();
//        Log.e("ivan", "post handler");
//        System.out.print("ivan handler post");
//        mDelayHanlder.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                System.out.print("ivan handler run");
//                Log.e("ivan", "hanlder run");
//            }
//        }, 1000 * 60 * 5);
//        Intent intent = new Intent(MainActivity.this, MultiLayerActivity.class);
//        startActivity(intent);
    }

    public void onMediaPlayerClick(View view) {
//        Intent intent = new Intent(MainActivity.this, MediaPlayActivity.class);
//        startActivity(intent);
        testNull();
    }

    public void startWidget(View view) {
        AutoWidgetManager.getInstance().startAutoWidget();
    }

    public void stopWidget(View view) {
        AutoWidgetManager.getInstance().stopAutoWidget();
    }

    private void testNull() {
        NullTest test = new NullTest();
        test.test();
    }

    @Override
    protected void onStart() {
        Log.d("ivan", "onStart");
        super.onStart();

        writeFile();
    }

    private void writeFile() {
        String txt = "开始导航，前方右转";
        try {
            byte[] buf = txt.getBytes("utf-16");

            File file = new File("/sdcard/ivan");
            if (!file.exists()) {
                file.mkdir();
            }
            File pcm = new File(file.getAbsolutePath() + File.separator + "ivan.pcm");
            OutputStream outputStream = new FileOutputStream(pcm, false);
            outputStream.write(buf);
            outputStream.flush();
            outputStream.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onRestart() {
        Log.d("ivan", "onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.d("ivan", "onResume");
        super.onResume();

        testEnum();
        RefectUtils.reflectSingleInstance(this);
    }

    @Override
    protected void onPause() {
        Log.d("ivan", "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("ivan", "onStop。 isFinishing= " + isFinishing());
        super.onStop();

        startActivity(new Intent(this, MainActivity.class));

        try {
            TimeUnit.MILLISECONDS.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        unregisterReceiver(mReceiver);
    }

    @Override
    protected void onDestroy() {
        Log.d("ivan", "onDestroy");
        super.onDestroy();

    }

    InputStream iss;

    private void playPcm() {
        mAudio = new AudioTrack(9, 16000,
                AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT, 10240,
                AudioTrack.MODE_STREAM);


        File componentFile = null;
        String projectRootPath = null;
        try {
            projectRootPath = new File(".").getCanonicalPath();
            StringBuilder configFilePath = new StringBuilder(projectRootPath);
            configFilePath.append(File.separatorChar)
                    .append("mobile")
                    .append(File.separatorChar)
                    .append("assets")
                    .append(File.separatorChar)
                    .append("pcm.pcm");
            componentFile = new File(configFilePath.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                    try {
//                        iss = getAssets().open
//                                ("pcm.pcm");
//                        byte[] pcm = new byte[(int)10240];
//                        iss.read(pcm);
//                        BufferedReader is = new BufferedReader(iss);
//                        StringBuilder sb = new StringBuilder();
//                        String tmpStr = null;
//                        while ((tmpStr = is.readLine()) != null) {
//                            sb.append(tmpStr);
//                            byte[] pcm = tmpStr.getBytes();
//                            Log.e("pcm", tmpStr);
                        File file = new File("/sdcard/1.pcm");
                        FileInputStream in = null;
                        try {
                            in = new FileInputStream(file);
                            long fileSize = file.length();
                            byte[] buffer = new byte[(int)fileSize];
                            in.read(buffer);


                            mAudio.play();
                            mAudio.write(buffer, 0, buffer.length);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        }
//                        is.close();
                    } catch (Exception e) {}
            }
        }).run();
    }

    private void testEnum() {
        DEMO_STATE e1 = DEMO_STATE.END;
        Log.e("ivan", "testEnum(). e1 == END ? " + (e1 == DEMO_STATE.END));
    }

    private class NullTest {
        public AudioTrack track;

        public void test() {
            track.release();
        }
    }

    public static enum DEMO_STATE {
        START,
        END;

        private DEMO_STATE() {
        }
    }
}
