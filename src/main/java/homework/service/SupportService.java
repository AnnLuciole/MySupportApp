package homework.service;

import homework.Phrase;
import homework.annotation.Logging;

public interface SupportService {

    @Logging
    Boolean addNewPhrase(Phrase phrase);
    @Logging
    Phrase getRandomPhrase();
}
