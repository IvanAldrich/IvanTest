package com.example.guocaicgc.ivantest.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.guocaicgc.ivantest.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by guocai.cgc on 2017/2/15.
 */

public class YeahFragment extends Fragment {
    private static final String TAG = YeahFragment.class.getSimpleName();
    private int mContainerId;

    public static YeahFragment getInstance(int containerId) {
        YeahFragment yeahFragment = new YeahFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("container_id", containerId);
        yeahFragment.setArguments(bundle);
        return yeahFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("ivan", TAG + "oncreate");
        mContainerId = getArguments().getInt("container_id");
        startNewFragment(null);
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        Log.e("ivan", TAG + "oncreateView");

        return inflater.inflate(R.layout.fragment_yeah, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout target = new LinearLayout(getActivity());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(200, 500);
        target.setLayoutParams(params);
        ViewWrapper wrapper = new ViewWrapper(target);
        runnable.run();
        reflectViewWrapper(wrapper);


        try {
            return;
        } finally {
            Log.e("ivan", "try finally");
        }

    }

    private void reflectViewWrapper(ViewWrapper wrapper) {
        Log.d("ivan", "reflect");
        Method method = null;
        try {
            method = ViewWrapper.class.getMethod("getHeight");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Log.e("ivan", e.getMessage());
        }
        Log.e("ivan", "method = " + (null == method));
        try {
            int result = (int) method.invoke(wrapper);
            Log.e("ivan", "result = " + result);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //预览下拉，淡出
            LinearLayout target = new LinearLayout(getActivity());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(200, 500);
            target.setLayoutParams(params);
            ViewWrapper wrapper = new ViewWrapper(target);
            AnimatorSet set = new AnimatorSet();
            set.playTogether(
                    ObjectAnimator.ofInt(wrapper, "height", 1000),
                    ObjectAnimator.ofFloat(target, "alpha", 1, 0)
            );
            set.setInterpolator(new FastOutSlowInInterpolator());
            set.setDuration(300);
            set.start();
        }
    };

    private static class ViewWrapper {
        private View mTarget;

        public ViewWrapper(View target) {
            mTarget = target;
        }

        public int getWidth() {
            return mTarget.getLayoutParams().width;
        }

        public void setWidth(int width) {
            mTarget.getLayoutParams().width = width;
            mTarget.requestLayout();
        }

        public int getHeight(){
            return mTarget.getLayoutParams().height;
        }

        public void setHeight(int height) {
            mTarget.getLayoutParams().height = height;
            mTarget.requestLayout();
        }
    }

    @Override
    public void onAttach(Context context) {
        Log.e("ivan", TAG + "onAttach");
        super.onAttach(context);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        Log.e("ivan", TAG + "onAttachFRagment");
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onDetach() {
        Log.e("ivan", TAG + "onDetach");
        super.onDetach();
    }

    @Override
    public void onResume() {
        Log.e("ivan", TAG + "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.e("ivan", TAG + "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.e("ivan", TAG + "onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.e("ivan", TAG + "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        Log.e("ivan", TAG +
                "onDestroyddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddView");
        super.onDestroyView();
    }

    public void startNewFragment(View view) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(mContainerId, GoMineFragment.getInstance());
        ft.commit();
    }
}

