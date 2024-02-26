package unit.service;

import homework.entity.Phrase;
import homework.repo.PhraseRepository;
import homework.service.SupportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


class SupportServiceImplTest {

    private final String PHRASE = "Test phrase";
    private final Phrase phrase = new Phrase(PHRASE);
    private PhraseRepository repo;

    private SupportServiceImpl service;

    @BeforeEach
    void setup() {
        repo = mock(PhraseRepository.class);
        service = new SupportServiceImpl();
        service.setRepo(repo);
    }

    @Test
    void addNewPhraseTest() {

        service.addNewPhrase(phrase);

        verify(repo).addRhrase(phrase);

    }

    @Test
    void getRandomPhrase() {

        service.getRandomPhrase();

        verify(repo).getRandomPhrase();
    }
}
