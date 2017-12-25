package com.ivan.example.annatation.utils;

import com.squareup.javapoet.ClassName;

/**
 * Created by guocai.cgc on 2017/11/8.
 */

public class TypeUtils {
    public static int STypeCount = 0;

    public static ClassName sComponentMap = ClassName.get("com.example.utils", "AutoComponentsMap");
    public static ClassName sComponentClassName = ClassName.get("com.example.anno_processor_module", "BaseComponent");
    public static ClassName sBaseComponentEntry = ClassName.get("com.example.anno_processor_module",
            "BaseComponentEntry");
    public static ClassName sComponentHolder = ClassName.get("com.example.anno_processor_module", "ComponentHolder");

}
