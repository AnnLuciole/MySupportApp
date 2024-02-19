package homework.rest;

import homework.entity.Phrase;

public interface SupportController {

    Phrase getSupportPhrase();

    Boolean addSupportPhrase(Phrase phrase);
}
