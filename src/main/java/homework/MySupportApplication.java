package homework;

import inMemoryBrokerLib.MessageQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MySupportApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MySupportApplication.class, args);
        MessageQueue queue = (MessageQueue) context.getBean("messageQueue");
        Thread thread = new Thread(queue);
        thread.start();
    }
}
