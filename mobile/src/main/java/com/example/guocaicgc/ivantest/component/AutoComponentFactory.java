package com.example.guocaicgc.ivantest.component;


import com.example.ComponentFactory;
import com.example.anno_processor_module.BaseComponent;
import com.example.anno_processor_module.BaseComponentEntry;
import com.example.anno_processor_module.ComponentHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guocai.cgc on 2017/10/25.
 */
@ComponentFactory("com.example.guocaicgc.ivantest.algorithm")
public class AutoComponentFactory {

    public Map<String, BaseComponent> getComponents() {
        return null;
//        ComponentHolder holder = new ComponentHolder();
//        Map<String, BaseComponentEntry> entryMap = holder.getComponentEntries();
//        Map<String, BaseComponent> componentMap = new HashMap<>();
//        for (Map.Entry<String, BaseComponentEntry> entry : entryMap.entrySet()) {
//            componentMap.put(entry.getKey(), entry.getValue().createComponent());
//        }
//        return componentMap;
    }

    public BaseComponent createComponent(String componentId) {
//        ComponentHolder holder = new ComponentHolder();
//        Map<String, BaseComponentEntry> entryMap = holder.getComponentEntries();
//        BaseComponentEntry entry = entryMap.get(componentId);
//        return entry.createComponent();
        return null;
    }
}
