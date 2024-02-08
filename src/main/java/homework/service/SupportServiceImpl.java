package homework.service;

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
    public void addNewPhrase(String phrase) {
        container.addNewPhrase(phrase);
    }

    @Override
    public String getRandomPhrase() {
        int randomIndex = random.nextInt(container.getSize());
        return container.getPhrase(randomIndex);
    }
}
