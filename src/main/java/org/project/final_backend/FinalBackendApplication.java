package org.project.final_backend;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication
@EnableCaching
public class FinalBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinalBackendApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
//	@PostConstruct
//	public void init() {
//		try {
//			FirebaseOptions options = new FirebaseOptions.Builder()
//					.setCredentials(GoogleCredentials.fromStream(new ClassPathResource("config/sh-app-338813-7b0205b127e6.json").getInputStream()))
//					.build();
//
//			if (FirebaseApp.getApps().isEmpty()) {
//				FirebaseApp.initializeApp(options);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

}
