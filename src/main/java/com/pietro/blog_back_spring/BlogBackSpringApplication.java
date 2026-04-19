package com.pietro.blog_back_spring;

import javax.crypto.SecretKey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

@SpringBootApplication
public class BlogBackSpringApplication {

	public static void main(String[] args) {


		//Generating a safe HS256 Secret key
		SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
		String secretString = Encoders.BASE64.encode(key.getEncoded());
		System.out.println("Secret key: " + secretString);



		SpringApplication.run(BlogBackSpringApplication.class, args);
	}

}
