package com.kajal.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/videos/**")
            .addResourceLocations("file:upload/videos/");

    registry.addResourceHandler("/thumbnails/**")
            .addResourceLocations("file:upload/thumbnails/");
}
} 