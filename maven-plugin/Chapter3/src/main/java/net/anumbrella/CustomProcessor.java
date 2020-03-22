package net.anumbrella;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


@SupportedAnnotationTypes("net.anumbrella.CustomEntity")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class CustomProcessor extends AbstractProcessor {

    // Processor初始化回调
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        System.out.println("CustomProcessor init");
    }

    // processor处理过程的回调
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("process");
        for (Element element : roundEnv.getElementsAnnotatedWith(CustomEntity.class)) {
            if (!(element instanceof TypeElement)) {
                continue;
            }
            File file = new File("/Users/anumbrella/Desktop/Q" + element.getSimpleName() + ".java");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        // 在此处声明该processor支持的注解类型
        // 和注解@SupportedAnnotationTypes功能相同
        Set<String> set = new HashSet<>();
        set.add(CustomEntity.class.getCanonicalName());
        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        // 和注解@SupportedSourceVersion功能相同
        return SourceVersion.latestSupported();
    }
}