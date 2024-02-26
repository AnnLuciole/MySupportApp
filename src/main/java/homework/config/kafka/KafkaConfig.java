package homework.config.kafka;

import homework.broker.PhraseConsumer;
import homework.broker.PhraseProducer;
import homework.repo.PhraseRepository;
import homework.repo.impl.PhraseKafkaRepository;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@ConditionalOnProperty(prefix = "kafka-broker", name = "enabled", havingValue = "true")
public class KafkaConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${spring.kafka.topic-name}")
    private String topicName;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic1() {
        return new NewTopic(topicName, 3, (short) 1);
    }

    @Bean
    public PhraseConsumer phraseConsumer() {
        return new PhraseConsumer();
    }

    @Bean
    public PhraseProducer phraseProducer() {
        return new PhraseProducer();
    }

    @Bean
    @Primary
    public PhraseRepository phraseRepository() {
        return new PhraseKafkaRepository();
    }
}
