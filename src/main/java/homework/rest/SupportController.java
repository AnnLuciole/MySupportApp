package homework.rest;

import homework.entity.Phrase;

public interface SupportController {

    Phrase getSupportPhrase();

    void addSupportPhrase(Phrase phrase);
}
