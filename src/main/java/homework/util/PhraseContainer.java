package homework.util;

import homework.entity.Phrase;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class PhraseContainer {

    private static final List<Phrase> allPhrases;

    static {
        allPhrases = new CopyOnWriteArrayList<>();
    }

    public void addNewPhrase(Phrase phrase) {
        System.out.println(phrase.getPhraseString());
        allPhrases.add(phrase);
        System.out.println(allPhrases.size());
    }

    public Phrase getPhrase(int index) {
        return allPhrases.get(index);
    }

    public int getSize() {
        return allPhrases.size();
    }
}
