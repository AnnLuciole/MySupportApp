package homework.util;

import homework.Phrase;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PhraseContainer {

    private static final Map<String, Phrase> allPhrases;

    static {
        allPhrases = new ConcurrentHashMap<>();
        allPhrases.put("У тебя все получится!", new Phrase("У тебя все получится!"));
        allPhrases.put("Ты не один - всегда найдутся люди, готовые помочь.",
                new Phrase("Ты не один - всегда найдутся люди, готовые помочь."));
    }

    public Boolean addNewPhrase(Phrase phrase) {
        if (allPhrases.containsKey(phrase.getPhraseString())) return Boolean.FALSE;
        allPhrases.put(phrase.getPhraseString(), phrase);
        return Boolean.TRUE;
    }

    public Phrase getPhrase(int index) {
        return new ArrayList<>(allPhrases.entrySet()).get(index).getValue();
    }

    public int getSize() {
        return allPhrases.size();
    }
}
