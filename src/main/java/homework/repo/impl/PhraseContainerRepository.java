package homework.repo.impl;

import homework.entity.Phrase;
import homework.repo.PhraseRepository;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class PhraseContainerRepository extends PhraseRepository {

    @Override
    public void addRhrase(Phrase phrase) {
        container.addNewPhrase(phrase);
    }
}
