package com.example.guocaicgc.ivantest.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guocaicgc.ivantest.R;

/**
 * Created by guocai.cgc on 2017/2/15.
 */

public class GoMineFragment extends Fragment {
    private static final String TAG = GoMineFragment.class.getSimpleName();

    public static GoMineFragment getInstance() {
        GoMineFragment fragment = new GoMineFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i("ivan", TAG + "oncreate");
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("ivan", TAG + "oncreateView");

        return inflater.inflate(R.layout.fragment_go_mine, container, false);
    }

    @Override
    public void onResume() {
        Log.i("ivan", TAG + "onResume");
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        Log.i("ivan", TAG + "onAttach");
        super.onAttach(context);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        Log.i("ivan", TAG + "onAttachFragment");
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onDetach() {
        Log.i("ivan", TAG + "onDetach");
        super.onDetach();
    }

    @Override
    public void onPause() {
        Log.i("ivan", TAG + "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i("ivan", TAG + "onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.i("ivan", TAG + "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.i("ivan", TAG + "onDestroy");
        super.onDestroy();
    }
}
