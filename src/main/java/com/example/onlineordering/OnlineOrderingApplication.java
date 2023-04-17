package com.example.onlineordering;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"com.example.onlineordering.repository"})
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@ComponentScan({"com.example.onlineordering.repository", "com.example.onlineordering.controller",
"com.example.onlineordering.service"})
public class OnlineOrderingApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineOrderingApplication.class, args);
    }

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**").allowedOrigins("*");
//            }
//        };
//    }

}
