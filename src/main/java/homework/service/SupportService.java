package homework.service;

import homework.entity.Phrase;

public interface SupportService {

    void addNewPhrase(Phrase phrase);
    Phrase getRandomPhrase();
}
