package com.dm.micropifs;


import com.dm.micropifs.fileio.DataStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.io.File;


@Configuration
public class MicroConfiguration implements WebMvcConfigurer {

    @Value("${local.storage.path}")
    private String localStoragePath;

    @PostConstruct
    void setStoragePath(){

        if (localStoragePath.equals("default")) {
            ApplicationHome home = new ApplicationHome(MicrocamPifs.class);
            localStoragePath = home.getDir().getAbsolutePath();
        }

        localStoragePath = String.join(File.separator, DataStore.fixPath(localStoragePath));

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classlocalStoragePath:/static/");
    }

    public String getLocalStoragePath() {
        return localStoragePath;
    }
}

