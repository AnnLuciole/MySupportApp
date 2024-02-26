package unit.servlet;

import homework.entity.Phrase;
import homework.rest.SupportControllerImpl;
import homework.service.SupportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SupportControllerImplTest {

    private final String PHRASE = "Test phrase";
    private final Phrase phrase = new Phrase(PHRASE);
    private SupportServiceImpl service;
    private SupportControllerImpl controller;

    @BeforeEach
    void setup() {
        service = mock(SupportServiceImpl.class);
        controller = new SupportControllerImpl(service);
    }

    @Test
    void getSupportPhraseShouldReturnPhrase() {

        when(service.getRandomPhrase()).thenReturn(phrase);

        Phrase result = controller.getSupportPhrase();

        assertEquals(phrase, result);

    }

    @Test
    void addSupportPhraseWhenTrue() {

        when(service.addNewPhrase(phrase)).thenReturn(true);

        boolean result = controller.addSupportPhrase(phrase);

        assertTrue(result);
    }
    @Test
    void addSupportPhraseWhenFalse() {

        when(service.addNewPhrase(phrase)).thenReturn(false);

        boolean result = controller.addSupportPhrase(phrase);

        assertFalse(result);
    }
}
