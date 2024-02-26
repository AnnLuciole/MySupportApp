package homework.repo;

import homework.entity.Phrase;
import homework.util.PhraseContainer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

public abstract class PhraseRepository {

    @Autowired
    protected PhraseContainer container;

    @Autowired
    private Random random;

    public Phrase getRandomPhrase() {
        int randomIndex = random.nextInt(container.getSize());
        return container.getPhrase(randomIndex);
    }
    public abstract void addRhrase(Phrase phrase);
}
