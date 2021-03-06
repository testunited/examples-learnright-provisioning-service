package org.testunited.examples.learnright.provisioning;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAutoConfiguration
@ComponentScan("org.testunited.examples.learnright.provisioning")
public class SpringConfig {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}