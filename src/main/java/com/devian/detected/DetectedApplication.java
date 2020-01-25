package com.devian.detected;

import com.devian.detected.security.EncryptionProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties(EncryptionProperties.class)
public class DetectedApplication {

	public static boolean encryptionEnabled;
	public static String encryptionKey;

	EncryptionProperties encryptionProperties;

	public DetectedApplication(EncryptionProperties encryptionProperties) {
		try {
			this.encryptionProperties = encryptionProperties;
			encryptionEnabled = encryptionProperties.isEnabled();
			if (encryptionEnabled)
				encryptionKey = encryptionProperties.getKey();
		} catch (Exception e) {
			log.error("Unable to set \"encryption.enable\" and \"encryption.key\" properties from application.properties");
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(DetectedApplication.class, args);
	}

}
