package service;

import homework.service.SupportServiceImpl;
import homework.util.PhraseContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SupportServiceImplTest {

    Random random;
    PhraseContainer container;
    SupportServiceImpl service;
    final String PHRASE = "Test phrase";

    @BeforeEach
    void setup() {
        random = mock(Random.class);
        container = mock(PhraseContainer.class);
        service = new SupportServiceImpl(random, container);
    }

    @Test
    void addNewPhraseTest() {

        service.addNewPhrase(PHRASE);

        verify(container).addNewPhrase(PHRASE);
    }

    @Test
    void getRandomPhrase() {

        when(random.nextInt(anyInt())).thenReturn(5);
        when(container.getPhrase(5)).thenReturn(PHRASE);

        String result = service.getRandomPhrase();

        assertEquals(PHRASE, result);
    }
}
