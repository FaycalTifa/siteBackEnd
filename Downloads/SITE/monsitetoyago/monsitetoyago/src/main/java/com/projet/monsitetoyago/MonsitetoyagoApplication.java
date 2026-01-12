package com.projet.monsitetoyago;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MonsitetoyagoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonsitetoyagoApplication.class, args);
        System.out.println("🚀 Application Spring Boot démarrée sur http://localhost:9999");
    }

    @Bean
    public WebMvcConfigurer webConfigurer() {
        return new WebMvcConfigurer() {

            /* ============================
             *  CORS CONFIGURATION
             * ============================ */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }

            /* ============================
             *  STATIC IMAGES CONFIGURATION
             * ============================ */
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {

                registry.addResourceHandler("/uploads/**")
                        .addResourceLocations("file:uploads/")
                        .setCachePeriod(3600);
            }
        };
    }
}
