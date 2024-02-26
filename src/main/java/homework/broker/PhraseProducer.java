package homework.broker;

import homework.entity.Phrase;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Data
@EnableTransactionManagement
public class PhraseProducer {

    @Autowired
    private KafkaTemplate<String, Phrase> template;

    @Value("${spring.kafka.topic-name}")
    private String topicName;

    @Transactional
    public void publishMessage(Phrase phrase) {
        template.executeInTransaction(x -> x.send(topicName, phrase));
    }
}
