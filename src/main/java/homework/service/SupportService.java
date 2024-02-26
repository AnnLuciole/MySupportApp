package homework.service;

import homework.entity.Phrase;

public interface SupportService {

    Boolean addNewPhrase(Phrase phrase);
    Phrase getRandomPhrase();
}
