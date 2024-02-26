package homework.broker;

import homework.entity.Phrase;
import homework.util.PhraseContainer;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;

@Data
public class PhraseConsumer {

    @Autowired
    private PhraseContainer container;

    @KafkaListener(topics = "${spring.kafka.topic-name}", containerFactory="kafkaListenerContainerFactory")
    public void listen(@Payload Phrase phrase) {
        container.addNewPhrase(phrase);
    }
}
