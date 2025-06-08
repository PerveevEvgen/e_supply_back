package ua.kpi.diploma.e_supply.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "aws")
public class AwsProperties {

    private String accessKey;
    private String secretKey;

}
