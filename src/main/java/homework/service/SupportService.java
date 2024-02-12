package homework.service;

import homework.Phrase;
import homework.annotation.Logging;

public interface SupportService {

    @Logging
    boolean addNewPhrase(Phrase phrase);
    @Logging
    Phrase getRandomPhrase();
}
