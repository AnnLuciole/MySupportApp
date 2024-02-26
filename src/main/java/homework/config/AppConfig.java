package homework.config;

import homework.properties.BrokerConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
@ComponentScan(basePackages = "homework")
@EnableConfigurationProperties(BrokerConfigurationProperties.class)
public class AppConfig {
    @Bean
    public Random random() {
        return new Random();
    }
}
