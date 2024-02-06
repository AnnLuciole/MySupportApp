package homework.service;

import homework.util.PhraseContainer;

import java.util.Random;

public class SupportServiceImpl implements SupportService {

    private Random random;
    private PhraseContainer container;

    public SupportServiceImpl(Random random, PhraseContainer container) {
        this.random = random;
        this.container = container;
    }

    @Override
    public void addNewPhrase(String phrase) {
        container.addNewPhrase(phrase);
    }

    @Override
    public String getRandomPhrase() {
        int randomIndex = random.nextInt(container.getSize());
        return container.getPhrase(randomIndex);
    }
}
