package com.example.telikosredislibrary.annotation;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class CustomConfigImporter implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata metadata) {
        return new String[]{
                "com.example.telikosredislibrary.config.CustomConfiguration"
        };
    }
}
