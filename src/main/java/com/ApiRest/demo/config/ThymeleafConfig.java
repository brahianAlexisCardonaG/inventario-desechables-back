package com.ApiRest.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class ThymeleafConfig implements WebMvcConfigurer {
    // No necesitas añadir más configuración si tus templates están en resources/templates
}
