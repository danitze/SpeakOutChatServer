package com.coursework.speakoutchat.pairingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class PairingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PairingServiceApplication.class, args);
	}

}
