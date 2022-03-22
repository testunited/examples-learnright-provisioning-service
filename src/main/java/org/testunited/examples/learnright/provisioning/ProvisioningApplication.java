package org.testunited.examples.learnright.provisioning;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProvisioningApplication {
	private static final Logger log = LoggerFactory.getLogger(ProvisioningApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ProvisioningApplication.class, args);
	}
}
