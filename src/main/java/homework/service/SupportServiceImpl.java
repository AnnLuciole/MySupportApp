package homework.service;

import homework.entity.Phrase;
import homework.repo.PhraseRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class SupportServiceImpl implements SupportService {

    @Autowired
    private PhraseRepository repo;

    @Override
    public void addNewPhrase(Phrase phrase) {
        repo.addRhrase(phrase);
    }

    @Override
    public Phrase getRandomPhrase() {
        return repo.getRandomPhrase();
    }
}
