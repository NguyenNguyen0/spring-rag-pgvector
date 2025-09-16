package com.sohamkamani.spring_rag_demo;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringRagDemoApplication {

    public static void main(String[] args) {
        Dotenv.load();
        System.setProperty("OPEN_ROUTER_API", Dotenv.load().get("OPEN_ROUTER_API"));
        System.setProperty("user.timezone", "Asia/Ho_Chi_Minh");
        SpringApplication.run(SpringRagDemoApplication.class, args);
    }

}
