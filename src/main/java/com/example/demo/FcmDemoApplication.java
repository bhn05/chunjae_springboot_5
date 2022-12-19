package com.example.demo;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.controller.fcmandroid;


@SpringBootApplication
public class FcmDemoApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(FcmDemoApplication.class, args);
		System.out.println("1111");
		System.out.println("android call");
		fcmandroid.androidSet();
		System.out.println("android end");
		//fcmIos.iosSet();

	}

}
