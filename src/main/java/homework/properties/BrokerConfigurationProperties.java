package homework.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties("kafka-broker")
public class BrokerConfigurationProperties {
    boolean enabled;
}
