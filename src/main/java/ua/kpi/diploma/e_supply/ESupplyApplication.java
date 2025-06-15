package ua.kpi.diploma.e_supply;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import ua.kpi.diploma.e_supply.config.AwsProperties;

@SpringBootApplication
@EnableConfigurationProperties(AwsProperties.class)
public class ESupplyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ESupplyApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
