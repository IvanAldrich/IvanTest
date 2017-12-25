package com.ivan.example.annatation.processor;

import com.example.ComponentFactory;
import com.example.utils.AutoComponentsMap;
import com.google.auto.service.AutoService;
import com.ivan.example.annatation.utils.TypeUtils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by guocai.cgc on 2017/10/25.
 */
@AutoService(Processor.class)
public class ComponentFactoryProcessor extends AbstractProcessor {
    private Elements mElementUtil;
    private int mComponentSize = 0;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
//        return Collections.singleton(ComponentFactory.class.getCanonicalName());
        Set<String> types = new HashSet<>();
        types.add(ComponentFactory.class.getCanonicalName());
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

//        String rootProjectPath = null;
//        try {
//            rootProjectPath = new File(".").getCanonicalPath();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String rootAgain = this.getClass().getClassLoader().getResource("").getPath();
//        System.out.println("rootProjectPath=" + rootProjectPath
//                + "\nrootAgain=" + rootAgain);
//        TypeName methodReturnType = ClassName.get(HashMap.class);
//
//        TypeName mapType = ClassName.get(Map.class);
//        ClassName componentMapClass = TypeUtils.sComponentMap;
//        MethodSpec.Builder methBuilder = MethodSpec.methodBuilder("creatComponnets")
//                .addModifiers(Modifier.FINAL)
//                .addModifiers(Modifier.PUBLIC);
//        methBuilder.returns(methodReturnType);
//        methBuilder.addStatement("HashMap<String, $T> componentMap = new HashMap<>();", TypeUtils.sComponentClassName);
////        methBuilder.addStatement("Set<$T.Entry<String, String>> entrySet = $T.sComponentMap.entrySet" +
////                "()", mapType, componentMapClass);
//        methBuilder.addStatement("$T.out.println($S)", System.class, "factoryTime: " + System.currentTimeMillis());
//        methBuilder.addStatement("return componentMap");
//        Set<Map.Entry<String, String>> entrySet = AutoComponentsMap.sComponentMap.entrySet();
//        String componentId = null;
//        String componentName = null;
//        for (Map.Entry<String, String> entry : entrySet) {
//            methBuilder.addStatement("BaseComponent component = new $N()", entry.getValue());
//            methBuilder.addStatement("componentMap.put($S, component)", entry.getKey());
//        }
//
////        methBuilder.addStatement("for (Map.Entry<String, String> entry : entrySet) {componentMap.put(entry.getkey(), " +
////                "new $N());}",);
////        CodeBlock.Builder builder = CodeBlock.builder();
////        builder.beginControlFlow("for (Map.Entry<String, String> entry : entrySet)")
////                .addStatement("String componentClass = entry.getValue()")
////                .addStatement("BaseComponent component = new $N();", )
//
//        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(ComponentFactory.class);
//        int ii = 0;
//        for (Element element : elements) {
//            TypeElement typeElement = (TypeElement) element;
//
//            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("initComponents")
//                    .addModifiers(Modifier.PUBLIC);
//
//            methodBuilder.addStatement("$T.out.println($S)", System.class, "factorySzie :" + elements.size());
//            methodBuilder.addStatement("$T.out.println($S)", System.class, "processor :" + ComponentFactoryProcessor
//                    .this);
//
//            methodBuilder.addStatement("HashMap<String, $T> componentMap = new HashMap<>();", String.class);
//
////            String projectPath = System.getProperty("user.dir");
//            methodBuilder.addStatement("$T.out.println($S)", System.class, "ivan initComponents size " +
//                    mComponentSize);
//
////            ClassName methodReturnType = ClassName.get(Map.class);
////            TypeName methodReturnType = ParameterizedTypeName.get(ClassName.get("java.util", "Map"),TypeName.get
////                    (String.class), iComponentClassName);
//            methodBuilder.returns(methodReturnType);
////            methodBuilder.addStatement("$T.out.println($S)", System.class, "ivan initComponents" + projectPath);
//
//            methodBuilder.addStatement("return componentMap");
//
//            MethodSpec methodSpec = methodBuilder.build();
//
//            TypeSpec.Builder typeBuilder = TypeSpec.classBuilder("AutoComponentFactoryImpl" + ii++)
//                    .addModifiers(Modifier.FINAL)
//                    .addModifiers(Modifier.PUBLIC)
//                    .addMethod(methodSpec)
//                    .addMethod(methBuilder.build());
//            TypeSpec typeSpec = typeBuilder.build();
//
//            String packageName = getPackageName(typeElement);
//            JavaFile javaFile = JavaFile.builder(packageName, typeSpec).build();
//            try {
//                javaFile.writeTo(processingEnv.getFiler());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        return true;
    }

    private class ComponentEntry {
        public String group;
        public String artifact;
        public String className;

        public String getComponentIdentifier() {
            return group + "." + artifact;
        }
    }

    private List<ComponentEntry> getComponentEntries() {
        List<ComponentEntry> entries = new ArrayList<>();

        ComponentEntry parkEntry = new ComponentEntry();
        parkEntry.group = "com.autonavi.amapauto";
        parkEntry.artifact = "park";
        parkEntry.className = "com.example.guocaicgc.ivantest.component.ParkComponent";
        entries.add(parkEntry);

        ComponentEntry agroupEntry = new ComponentEntry();
        agroupEntry.group = "com.autonavi.amapauto";
        agroupEntry.artifact = "agroup";
        agroupEntry.className = "com.example.guocaicgc.ivantest.component.AGroupComponent";
        entries.add(agroupEntry);

        entries = transferToEntries(getComponentConfig());

        return entries;
    }

    private List<ComponentEntry> transferToEntries(JSONArray jsonArray) {
        List<ComponentEntry> entries = new ArrayList<>(jsonArray.length());
        JSONObject object = null;
        for (int i = 0, size = jsonArray.length(); i < size; i++) {
            try {
                object = jsonArray.getJSONObject(i);
                ComponentEntry entry = new ComponentEntry();
                entry.group = object.optString("groupId");
                entry.artifact = object.optString("artifactId");
                entry.className = object.optString("class");

                entries.add(entry);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return entries;
    }

    private JSONArray getComponentConfig() {
        // TODO: guocai.cgc 2017/10/26
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
                    .append("components.json");
            componentFile = new File(configFilePath.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (null != componentFile) {
            try {
                BufferedReader is = new BufferedReader(new FileReader(componentFile));
                StringBuilder sb = new StringBuilder();
                String tmpStr = null;
                while ((tmpStr = is.readLine()) != null) {
                    sb.append(tmpStr);
                }
                is.close();
                return new JSONArray(sb.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            JSONArray jsonArray = new JSONArray();
            JSONObject parkJson = new JSONObject();
            parkJson.put("groupId", "com.autonavi.amapauto");
            parkJson.put("artifactId", "park");
            parkJson.put("class", "com.example.guocaicgc.ivantest.component.ParkComponent");
            jsonArray.put(parkJson);

//            JSONObject agroupJson = new JSONObject();
//            agroupJson.put("groupId", "com.autonavi.amapauto");
//            agroupJson.put("artifactId", "agroup");
//            agroupJson.put("class", "com.example.guocaicgc.ivantest.component.AGroupComponent");
//            jsonArray.put(agroupJson);

            return jsonArray;
        } catch (Exception e) {
            return null;
        }

    }

    private String getPackageName(TypeElement typeElement) {
        return mElementUtil.getPackageOf(typeElement).getQualifiedName().toString();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mElementUtil = processingEnvironment.getElementUtils();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
