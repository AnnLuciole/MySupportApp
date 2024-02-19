package unit.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import homework.entity.Phrase;
import homework.service.SupportServiceImpl;
import homework.util.PhraseContainer;
import inMemoryBrokerLib.service.MessagePublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SupportServiceImplTest {

    private final String PHRASE = "Test phrase";
    private final Phrase phrase = new Phrase(PHRASE);
    private Random random;
    private PhraseContainer container;
    private MessagePublisher publisher;
    private ObjectMapper mapper;

    private SupportServiceImpl service;

    @BeforeEach
    void setup() {
        random = mock(Random.class);
        container = mock(PhraseContainer.class);
        publisher = mock(MessagePublisher.class);
        mapper = mock(ObjectMapper.class);
        service = new SupportServiceImpl(random, container, publisher, mapper);
    }

    @Test
    void addNewPhraseTest() throws JsonProcessingException {

        service.addNewPhrase(phrase);
        String message = mapper.writeValueAsString(phrase);

        verify(publisher).publishMessage(message);
    }

    @Test
    void getRandomPhrase() {

        when(random.nextInt(anyInt())).thenReturn(5);
        when(container.getPhrase(5)).thenReturn(phrase);

        Phrase result = service.getRandomPhrase();

        assertEquals(phrase, result);
    }
}
