package com.example.guocaicgc.ivantest.component;

import android.content.Context;
import android.widget.Toast;

import com.example.anno_processor_module.BaseComponent;
import com.example.guocaicgc.ivantest.algorithm.IComponent;

import java.util.Map;

/**
 * Created by guocai.cgc on 2017/10/25.
 */

public class ComponentManagerImpl {
    private Map<String, BaseComponent> mComponents;
    private AutoComponentFactory mFactory;

    public ComponentManagerImpl() {
        mFactory = new AutoComponentFactory();
        mComponents = mFactory.getComponents();
    }

    public void toastComponentList(Context context) {
        Toast.makeText(context, "" + mComponents.size(), Toast.LENGTH_SHORT).show();
    }
}
