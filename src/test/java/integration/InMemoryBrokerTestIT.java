package integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import homework.entity.Phrase;
import inMemoryBrokerLib.MessageQueue;
import inMemoryBrokerLib.impl.MessagePublisherImpl;
import inMemoryBrokerLib.impl.MessageSubscriberImpl;
import inMemoryBrokerLib.repo.CustomRepository;
import inMemoryBrokerLib.service.MessagePublisher;
import inMemoryBrokerLib.service.MessageSubscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class InMemoryBrokerTestIT {

    private final String PHRASE = "Test phrase";
    private final Phrase phrase = new Phrase(PHRASE);
    private MessagePublisher publisher;
    private MessageQueue queue;
    private ObjectMapper mapper;
    private CustomRepository repository;
    private MessageSubscriber subscriber;

    @BeforeEach
    void init() {
        queue = new MessageQueue();
        mapper = new ObjectMapper();
        publisher = new MessagePublisherImpl(queue);
        repository = mock(CustomRepository.class);
        subscriber = new MessageSubscriberImpl(queue, repository);
        subscriber.subscribe();
        Thread thread = new Thread(queue);
        thread.start();
    }

    @Test
    void testPublishMessage() throws JsonProcessingException {
        String message = mapper.writeValueAsString(phrase);

        publisher.publishMessage(message);

        verify(repository).save(message);
    }
}
