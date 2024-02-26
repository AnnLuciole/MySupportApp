package homework.rest;

import homework.annotation.Logging;
import homework.entity.Phrase;
import homework.service.SupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/support")
@RequiredArgsConstructor
public class SupportControllerImpl implements SupportController {

    @Autowired
    private final SupportService supportService;


    @Logging
    @GetMapping(path = "/")
    public Phrase getSupportPhrase() {
        return supportService.getRandomPhrase();
    }

    @Logging
    @PostMapping(path = "/")
    public Boolean addSupportPhrase(Phrase phrase) {
        return supportService.addNewPhrase(phrase);
    }
}
