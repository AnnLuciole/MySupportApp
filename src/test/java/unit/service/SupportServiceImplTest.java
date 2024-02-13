package unit.service;

import homework.Phrase;
import homework.service.SupportServiceImpl;
import homework.util.PhraseContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SupportServiceImplTest {

    final String PHRASE = "Test phrase";
    Random random;
    PhraseContainer container;
    SupportServiceImpl service;
    Phrase phrase = new Phrase(PHRASE);

    @BeforeEach
    void setup() {
        service = new SupportServiceImpl();
        random = mock(Random.class);
        container = mock(PhraseContainer.class);
        service.setContainer(container);
        service.setRandom(random);
    }

    @Test
    void addNewPhraseTest() {

        service.addNewPhrase(phrase);

        verify(container).addNewPhrase(phrase);
    }

    @Test
    void getRandomPhrase() {

        when(random.nextInt(anyInt())).thenReturn(5);
        when(container.getPhrase(5)).thenReturn(phrase);

        Phrase result = service.getRandomPhrase();

        assertEquals(phrase, result);
    }
}
