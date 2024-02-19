package homework.repo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import homework.entity.Phrase;
import homework.util.PhraseContainer;
import inMemoryBrokerLib.repo.CustomRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PhraseMessageRepository implements CustomRepository {

    private final PhraseContainer container;
    private final ObjectMapper mapper;

    @Override
    public void save(String message) {
        try {
            container.addNewPhrase(mapper.readValue(message, Phrase.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
