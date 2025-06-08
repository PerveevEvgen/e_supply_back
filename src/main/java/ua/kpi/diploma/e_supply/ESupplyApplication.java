package ua.kpi.diploma.e_supply;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ua.kpi.diploma.e_supply.config.AwsProperties;

@SpringBootApplication
@EnableConfigurationProperties(AwsProperties.class)
public class ESupplyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ESupplyApplication.class, args);
	}

}
