package homework.util;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PhraseContainer {

    private final static List<String> allPhrases;

    static {
        allPhrases = new CopyOnWriteArrayList<>();
        allPhrases.add("У тебя все получится!");
        allPhrases.add("Ты не один - всегда найдутся люди, готовые помочь.");
    }

    public void addNewPhrase(String phrase) {
        allPhrases.add(phrase);
    }

    public String getPhrase(int index) {
        return allPhrases.get(index);
    }

    public int getSize() {
        return allPhrases.size();
    }
}
