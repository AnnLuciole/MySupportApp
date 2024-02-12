package homework.service;

import homework.Phrase;
import homework.annotation.Autowired;
import homework.util.PhraseContainer;

import java.util.Random;
public class SupportServiceImpl implements SupportService {

    private Random random;
    private PhraseContainer container;

    @Autowired
    public void setRandom(Random random) {
        this.random = random;
    }

    @Autowired
    public void setContainer(PhraseContainer container) {
        this.container = container;
    }

    @Override
    public boolean addNewPhrase(Phrase phrase) {
        return container.addNewPhrase(phrase);
    }

    @Override
    public Phrase getRandomPhrase() {
        int randomIndex = random.nextInt(container.getSize());
        return container.getPhrase(randomIndex);
    }
}
