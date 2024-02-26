package homework.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import homework.repo.PhraseMessageRepository;
import homework.util.PhraseContainer;
import inMemoryBrokerLib.MessageQueue;
import inMemoryBrokerLib.impl.MessagePublisherImpl;
import inMemoryBrokerLib.impl.MessageSubscriberImpl;
import inMemoryBrokerLib.repo.CustomRepository;
import inMemoryBrokerLib.service.MessagePublisher;
import inMemoryBrokerLib.service.MessageSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
@ComponentScan(basePackages = "homework")
public class AppConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public Random random() {
        return new Random();
    }

    @Bean
    public MessageQueue messageQueue() {
        return new MessageQueue();
    }

    @Bean
    public PhraseContainer phraseContainer() {
        return new PhraseContainer();
    }

    @Bean
    public CustomRepository customRepository(PhraseContainer container, ObjectMapper mapper) {
        return new PhraseMessageRepository(container, mapper);
    }

    @Bean
    public MessagePublisher messagePublisher(MessageQueue queue) {
        return new MessagePublisherImpl(queue);
    }

    @Bean
    public MessageSubscriber messageSubscriber(MessageQueue queue, CustomRepository repository) {
        return new MessageSubscriberImpl(queue, repository);
    }
}
