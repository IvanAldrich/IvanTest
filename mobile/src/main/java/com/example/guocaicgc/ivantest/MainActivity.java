package com.example.guocaicgc.ivantest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guocaicgc.ivantest.component.factory.IvanFactory;
import com.example.guocaicgc.ivantest.media.MediaPlayActivity;

public class MainActivity extends AppCompatActivity {
    private Handler mDelayHanlder = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("ivan", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if ("1".equals(1)) {
            Log.e("ivan", "amazing! string equals int");
        }

        IvanFactory factory = new IvanFactory();
        factory.getComponents();
        // ===  component
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

        findViewById(R.id.tv_media_player).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MediaPlayActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onMultiLayerClick(View view) {

        Log.e("ivan", "post handler");
        System.out.print("ivan handler post");
        mDelayHanlder.postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.print("ivan handler run");
                Log.e("ivan", "hanlder run");
            }
        }, 1000 * 60 * 5);
//        Intent intent = new Intent(MainActivity.this, MultiLayerActivity.class);
//        startActivity(intent);
    }

    public void onMediaPlayerClick(View view) {
//        Intent intent = new Intent(MainActivity.this, MediaPlayActivity.class);
//        startActivity(intent);
    }

    @Override
    protected void onStart() {
        Log.d("ivan", "onStart");
        super.onStart();
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
    }

    @Override
    protected void onDestroy() {
        Log.d("ivan", "onDestroy");
        super.onDestroy();
    }
}
