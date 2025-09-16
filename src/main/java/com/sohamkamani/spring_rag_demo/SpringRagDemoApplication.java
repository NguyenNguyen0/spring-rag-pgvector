package com.sohamkamani.spring_rag_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringRagDemoApplication {

	public static void main(String[] args) {
        System.setProperty("user.timezone", "Asia/Ho_Chi_Minh");
		SpringApplication.run(SpringRagDemoApplication.class, args);
	}

}
