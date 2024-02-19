package homework.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import homework.entity.Phrase;
import homework.util.PhraseContainer;
import inMemoryBrokerLib.service.MessagePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class SupportServiceImpl implements SupportService {

    private final Random random;
    private final PhraseContainer container;
    private final MessagePublisher publisher;
    private final ObjectMapper mapper;

    @Override
    public Boolean addNewPhrase(Phrase phrase) {
        try {
            return publisher.publishMessage(mapper.writeValueAsString(phrase));
        } catch (JsonProcessingException e) {
            return false;
        }
    }

    @Override
    public Phrase getRandomPhrase() {
        int randomIndex = random.nextInt(container.getSize());
        return container.getPhrase(randomIndex);
    }
}
