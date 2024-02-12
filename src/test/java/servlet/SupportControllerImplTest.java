package servlet;

import homework.Phrase;
import homework.service.SupportServiceImpl;
import homework.rest.SupportControllerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SupportControllerImplTest {

    final String PHRASE = "Test phrase";
    private SupportServiceImpl service;
    private SupportControllerImpl servlet;
    private Phrase phrase = new Phrase(PHRASE);

    @BeforeEach
    void setup() {
        servlet = new SupportControllerImpl();
        service = mock(SupportServiceImpl.class);
        servlet.setSupportService(service);
    }

    @Test
    void getSupportPhraseShouldReturnPhrase() {

        when(service.getRandomPhrase()).thenReturn(phrase);

        Phrase result = servlet.getSupportPhrase();

        assertEquals(phrase, result);

    }

    @Test
    void addSupportPhraseWhenTrue() {

        when(service.addNewPhrase(phrase)).thenReturn(true);

        boolean result = servlet.addSupportPhrase(phrase);

        assertTrue(result);
    }
    @Test
    void addSupportPhraseWhenFalse() {

        when(service.addNewPhrase(phrase)).thenReturn(false);

        boolean result = servlet.addSupportPhrase(phrase);

        assertFalse(result);
    }
}
