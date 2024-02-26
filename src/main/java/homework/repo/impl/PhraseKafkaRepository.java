package homework.repo.impl;

import homework.broker.PhraseProducer;
import homework.entity.Phrase;
import homework.repo.PhraseRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class PhraseKafkaRepository extends PhraseRepository {

    @Autowired
    private PhraseProducer phraseProducer;

    @Override
    public void addRhrase(Phrase phrase) {
        phraseProducer.publishMessage(phrase);
    }
}
