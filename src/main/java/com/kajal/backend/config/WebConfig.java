package com.kajal.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/videos/**")
                .addResourceLocations("file:/home/kaju/projects/youtube-clone/upload/videos/");

        registry.addResourceHandler("/thumbnails/**")
                .addResourceLocations("file:/home/kaju/projects/youtube-clone/upload/thumbnails/");
    }
}