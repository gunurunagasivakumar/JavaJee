package com.microservices.msfour;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RefreshScope
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@RestController
public class MsFourApplication {

	@Autowired
	private static DiscoveryClient discoveryClient;
	@Autowired
	private MSFiveServiceProxy msFiveServiceProxy;
	private static final Logger logger = LoggerFactory.getLogger(MsFourApplication.class);

	public static void main(String[] args) {
		logger.info(" IN MSFour application");
		SpringApplication.run(MsFourApplication.class, args);
		System.out.println("Application instances:: " + discoveryClient.getInstances("MSFive"));
	}

	@RequestMapping("/greetingFromMSFour")
	public String greeting() {
		return "Greetings from MSFour applications!";
	}

	@RequestMapping("/greetingFromMSFive")
	public String greetingFromMSFive() {
		return msFiveServiceProxy.greetingFromMSFive();
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
