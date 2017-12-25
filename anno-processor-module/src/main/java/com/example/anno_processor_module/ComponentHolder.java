package com.example.anno_processor_module;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guocai.cgc on 2017/11/9.
 */

public class ComponentHolder {
    private static Map<String, BaseComponentEntry> sComponentEntries = new HashMap<>();

//    public static void addComponent(BaseComponentEntry entry) {
//        sComponentEntries.put(entry.componentId, entry);
//    }

    public Map<String, BaseComponentEntry> getComponentEntries() {
        return sComponentEntries;
    }
}
