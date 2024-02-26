package integration;

import homework.MySupportApplication;
import homework.entity.Phrase;
import lombok.var;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = MySupportApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SupportControllerTestIT {
    private final String PHRASE = "Test phrase";
    private final String PATH = "/v1/support/";
    private final Phrase phrase = new Phrase(PHRASE);

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getSupportPhrase() {
        assertFalse(this.restTemplate
                        .getForObject("http://localhost:" + port + PATH, Phrase.class)
                        .getPhraseString().isEmpty());
    }

    @Test
    void addSupportPhraseWhenOk() {
        ResponseEntity response = this.restTemplate
                .postForEntity("http://localhost:" + port + PATH, phrase, ResponseEntity.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }
}
