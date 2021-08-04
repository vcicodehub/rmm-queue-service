package com.signet.om;

import com.amazonaws.services.s3.AmazonS3;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OmApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmApplication.class, args);
	}

	@Bean
	ApplicationRunner appRunner(AmazonS3 amazonS3) {
		return args -> {
			amazonS3.listBuckets().forEach(bucket -> bucket.getName());
		};
	}

}
