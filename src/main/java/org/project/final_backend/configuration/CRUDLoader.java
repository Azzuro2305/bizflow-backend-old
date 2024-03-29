package org.project.final_backend.configuration;
import java.io.InputStream;

public class CRUDLoader {
    private ClassLoader classLoader;

    public CRUDLoader() {
        this.classLoader = getClass().getClassLoader();
    }

    public InputStream getResourceAsStream(String resourcePath) {
        return classLoader.getResourceAsStream(resourcePath);
    }
}