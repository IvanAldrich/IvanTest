package com.ivan.example.annatation.processor;

import com.example.Component;
import com.google.auto.service.AutoService;
import com.ivan.example.annatation.utils.TypeUtils;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by guocai.cgc on 2017/11/8.
 */
@AutoService(Processor.class)
public class AnnoComponentUtilsProcessor extends AbstractProcessor {
    private long mStartTime;
    private long mEndTime;
    private static int sCount = 0;
    private Elements mElementUtils;
    private static final String MARKER = "// marker";

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        String rootProjectPath = null;
        try {
            rootProjectPath = new File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        String rootAgain = this.getClass().getClassLoader().getResource("").getPath();
//        System.out.println("rootProjectPath=" + rootProjectPath
//                + "\nrootAgain=" + rootAgain);
        System.out.println("componentSet:" + set.toString());
        mStartTime = System.currentTimeMillis();
        sCount++;
        TypeUtils.STypeCount++;
        Set<? extends Element> components = roundEnvironment.getElementsAnnotatedWith(Component.class);
        String componentName = null;
        String componentWholeName = null;
        String componentId = null;
        ClassName componentMap = TypeUtils.sComponentMap;
        ClassName baseEntryClass = TypeUtils.sBaseComponentEntry;
        for (Element component : components) {
            if (component.getKind() != ElementKind.CLASS) {
                continue;
            }
            TypeElement componentEle = (TypeElement) component;
            componentWholeName = componentEle.getQualifiedName().toString();
            componentName = componentEle.getSimpleName().toString();
            Component componentAnno = componentEle.getAnnotation(Component.class);
            componentId = componentAnno.value();

            updateConfiguredCompoennts(componentWholeName, componentId);

            String superClass = componentEle.getSuperclass().toString();

            String generatedClassName = componentName + "Entry";

            MethodSpec.Builder constructMthBuilder = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ClassName.get(String.class), "componentId")
                    .addStatement("super(componentId)")
                    .addStatement("$T.out.println($S)", System.class, getConfigFilePath());

            MethodSpec.Builder createMthBuilder = MethodSpec.methodBuilder("createComponent")
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(ClassName.get(Override.class))
                    .returns(TypeUtils.sComponentClassName);
            createMthBuilder.addStatement("$T.out.println($S)", System.class, "annoTime: " + System.currentTimeMillis
                    ());
            createMthBuilder.addStatement("return new $N()", componentWholeName);

            TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(generatedClassName)
                    .superclass(baseEntryClass)
                    .addModifiers(Modifier.PUBLIC)
                    .addModifiers(Modifier.FINAL);
            typeBuilder.addStaticBlock(CodeBlock.of("$T.addComponent(new $N($S));", TypeUtils.sComponentHolder,
                    generatedClassName, componentId));


            typeBuilder.addMethod(constructMthBuilder.build())
                    .addMethod(createMthBuilder.build());

//            createLocalClass(getClassPath());
//            createLocalClass(getReleaseClassPath());
            typeBuilder.addJavadoc("start = $L, end = $L", mStartTime, mEndTime);

//            String packageName = getPackageName(componentEle);
//            JavaFile javaFile = JavaFile.builder(packageName, typeBuilder.build()).build();
//            try {
//                javaFile.writeTo(processingEnv.getFiler());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        }
        return false;
    }

    private void createLocalClass() {
        try {
            File classFile = new File(getClassPath());
            if (!classFile.exists()) {
                classFile.createNewFile();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(classFile, false));

            StringBuilder classSb = new StringBuilder();
            String factoryPackage = processingEnv.getOptions().get("componentFactoryPackage");
//            classSb.append("package ").append("com.example.guocaicgc.ivantest.component").append(";\n")
            classSb.append("package ").append(factoryPackage).append(";\n")
                    .append("import ").append("com.example.anno_processor_module.BaseComponent").append(";\n\n")
                    .append("public final class IvanFactory {\n")
                    .append("public void getComponents() {\n");
            JSONArray components = getLocalComponents();
            for (int i = 0; i < components.length(); i++) {
                JSONObject object = (JSONObject) components.get(i);
                classSb.append(String.format("BaseComponent %s = new %s();\n", object.opt("key"), object.opt
                        ("component")));
            }
            mEndTime = System.currentTimeMillis();
            classSb.append("\n}")
                    .append("\n}")
            .append(String.format("//starttime = %d, endTime = %d", mStartTime, mEndTime));

            bw.write(classSb.toString());
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void createLocalClass(String filePath) {
        try {
            File classFile = new File(filePath);
            classFile.createNewFile();
//            OutputStreamWriter writer = (OutputStreamWriter) processingEnv.getFiler().createSourceFile(filePath).openWriter();
            BufferedWriter bw = new BufferedWriter(new FileWriter(classFile, false));

            StringBuilder classSb = new StringBuilder();
            String factoryPackage = processingEnv.getOptions().get("componentFactoryPackage");
//            classSb.append("package ").append("com.example.guocaicgc.ivantest.component").append(";\n")
            classSb.append("package ").append(factoryPackage).append(";\n")
                    .append("import ").append("com.example.anno_processor_module.BaseComponent").append(";\n\n")
                    .append("public final class IvanFactory {\n")
                    .append("public void getComponents() {\n");
            JSONArray components = getLocalComponents();
            for (int i = 0; i < components.length(); i++) {
                JSONObject object = (JSONObject) components.get(i);
                classSb.append(String.format("BaseComponent %s = new %s();\n", object.opt("key"), object.opt
                        ("component")));
            }
            mEndTime = System.currentTimeMillis();
            classSb.append("\n}")
                    .append("\n}")
                    .append(String.format("//starttime = %d, endTime = %d", mStartTime, mEndTime));

            bw.write(classSb.toString());
            bw.flush();
//            writer.write(classSb.toString());
//            writer.write("ivan");
//            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getClassPath() {
        try {
            String rootProjectPath = new File(".").getCanonicalPath();
//            String rootAgain = this.getClass().getClassLoader().getResource("").getPath();
//            System.out.println("rootProjectPath=" + rootProjectPath
//            + "\nrootAgain=" + rootAgain);
            StringBuilder classPath = new StringBuilder(rootProjectPath);
            classPath.append(File.separator).append("mobile")
                    .append(File.separator).append("src")
                    .append(File.separator).append("main")
                    .append(File.separator).append("java")
                    .append(File.separator).append("com")
                    .append(File.separator).append("example")
                    .append(File.separator).append("guocaicgc")
                    .append(File.separator).append("ivantest")
                    .append(File.separator).append("component")
                    .append(File.separator).append("factory");
//            classPath.append(File.separator).append("mobile")
//                    .append(File.separator).append("build")
//                    .append(File.separator).append("generated")
//                    .append(File.separator).append("source")
//                    .append(File.separator).append("apt")
//                    .append(File.separator).append("debug")
//                    .append(File.separator).append("com")
//                    .append(File.separator).append("example")
//                    .append(File.separator).append("guocaicgc")
//                    .append(File.separator).append("ivantest")
//                    .append(File.separator).append("component")
//                    .append(File.separator).append("factory");
            File classPackage = new File(classPath.toString());
            if (!classPackage.exists()) {
                classPackage.mkdirs();
            }
            classPath.append(File.separator).append("IvanFactory.java");
            return classPath.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getReleaseClassPath() {
        try {
            String rootProjectPath = new File(".").getCanonicalPath();
            StringBuilder classPath = new StringBuilder(rootProjectPath);
//            classPath.append(File.separator).append("mobile")
//                    .append(File.separator).append("src")
//                    .append(File.separator).append("main")
//                    .append(File.separator).append("java")
//                    .append(File.separator).append("com")
//                    .append(File.separator).append("example")
//                    .append(File.separator).append("guocaicgc")
//                    .append(File.separator).append("ivantest")
//                    .append(File.separator).append("component")
//                    .append(File.separator).append("factory");
            classPath.append(File.separator).append("mobile")
                    .append(File.separator).append("build")
                    .append(File.separator).append("generated")
                    .append(File.separator).append("source")
                    .append(File.separator).append("apt")
                    .append(File.separator).append("release")
                    .append(File.separator).append("com")
                    .append(File.separator).append("example")
                    .append(File.separator).append("guocaicgc")
                    .append(File.separator).append("ivantest")
                    .append(File.separator).append("component")
                    .append(File.separator).append("factory");
            File classPackage = new File(classPath.toString());
            if (!classPackage.exists()) {
                classPackage.mkdirs();
            }
            classPath.append(File.separator).append("IvanFactory.java");
            return classPath.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void updateConfiguredCompoennts(String componentPath, String componentKey) {
        JSONArray jsonArray = getLocalComponents();
        if (null == jsonArray) {
            jsonArray = new JSONArray();
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject object = (JSONObject) jsonArray.get(i);
                String key = object.optString("key");
                if (null == componentKey || componentKey.equals(key)) {
                    return;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        JSONObject componentJson = new JSONObject();
        try {
            componentJson.put("key", componentKey);
            componentJson.put("component", componentPath);
            jsonArray.put(componentJson);
            writeLocalComponents(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void writeLocalComponents(JSONArray jsonArray) {
        BufferedWriter bufferedWriter = null;
        try {
            File localFile = new File(getConfigFilePath());
            if (localFile.exists()) {
                localFile.delete();
            }
            localFile.createNewFile();
            bufferedWriter = new BufferedWriter(new FileWriter(localFile, false));

            System.out.print(jsonArray.toString(4));

            bufferedWriter.write(jsonArray.toString());
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (null != bufferedWriter) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private JSONArray getLocalComponents() {
        String configFilePath = getConfigFilePath();
        File componentFile = new File(configFilePath);
        if (componentFile.exists()) {
            BufferedReader reader = null;
            StringBuilder sb;
            try {
                reader = new BufferedReader(new FileReader(componentFile));
                sb = new StringBuilder();
                String tmpStr = null;
                while ((tmpStr = reader.readLine()) != null) {
                    sb.append(tmpStr);
                }
                return new JSONArray(sb.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (null != reader) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    private String getConfigFilePath() {
        try {
            String projectRootPath = new File(".").getCanonicalPath();
            StringBuilder sb = new StringBuilder(projectRootPath);
            sb.append(File.separator).append("components.json");
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getPackageName(TypeElement typeElement) {
        return mElementUtils.getPackageOf(typeElement).getQualifiedName().toString();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Component.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mElementUtils = processingEnvironment.getElementUtils();
    }

    @Override
    public Set<String> getSupportedOptions() {
        return Collections.singleton("componentFactoryPackage");
    }
}
