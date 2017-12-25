package com.example.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by guocai.cgc on 2017/11/8.
 */

public class AutoComponentsMap {

    public static Map<String, String> sComponentMap = new HashMap<>();

    public static void addComponent(String componentId, String componentClass) {
        sComponentMap.put(componentId, componentClass);
    }
}
