package com.ivan.example.annatation.processor;

import com.example.Component;
import com.example.ServiceComponent;
import com.google.auto.service.AutoService;
import com.ivan.example.annatation.utils.TypeUtils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
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
import java.util.Collections;
import java.util.HashSet;
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
 * Created by guocai.cgc on 2017/11/22.
 */
@AutoService(Processor.class)
public class AnnoEntryProcessor extends AbstractProcessor {
    private Elements mElementUtils;

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
                    .addStatement("$T.out.println($S)", System.class, getConfigFilePath(componentId));

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
////            createLocalClass(getReleaseClassPath());
//            typeBuilder.addJavadoc("start = $L, end = $L", mStartTime, mEndTime);

//            String packageName = getPackageName(componentEle);
//            JavaFile javaFile = JavaFile.builder(packageName, typeBuilder.build()).build();
//            try {
//                javaFile.writeTo(processingEnv.getFiler());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        }
        return true;
    }

    private void updateConfiguredCompoennts(String componentPath, String componentKey) {
        JSONObject componentJson = new JSONObject();
        try {
            componentJson.put("componentIdentifier", componentKey);
            componentJson.put("component", componentPath);
            writeLocalComponents(componentKey, componentJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void writeLocalComponents(String componentKey, JSONObject jsonObject) {
        BufferedWriter bufferedWriter = null;
        try {
            File localFile = new File(getConfigFilePath(componentKey));
            if (localFile.exists()) {
                localFile.delete();
            }
            localFile.createNewFile();
            bufferedWriter = new BufferedWriter(new FileWriter(localFile, false));

            System.out.print(jsonObject.toString(4));

            bufferedWriter.write(jsonObject.toString());
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

    private JSONArray getLocalComponents(String componentKey) {
        String configFilePath = getConfigFilePath(componentKey);
        System.out.println("getLocalComponents(). configFilePath=" + configFilePath);
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

    private String getConfigFilePath(String key) {
        try {
            String projectRootPath = new File(".").getCanonicalPath();
            StringBuilder sb = new StringBuilder(projectRootPath);
            sb.append(File.separator).append("componentTmp");
            String dirPath = sb.toString();
            File dirFile = new File(dirPath);
            System.out.println("dirFile: " + dirPath);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            sb.append(File.separator).append(String.format("components_%s.json", key));
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
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annos = new HashSet<>();
        annos.add(Component.class.getCanonicalName());
        annos.add(ServiceComponent.class.getCanonicalName());
        return annos;
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
