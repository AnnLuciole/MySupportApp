package homework.rest;

import homework.annotation.Logging;
import homework.entity.Phrase;
import homework.service.SupportService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Data
@RestController
@RequestMapping(path = "/v1/support")
public class SupportControllerImpl implements SupportController {

    @Autowired
    private SupportService supportService;


    @Logging
    @GetMapping(path = "/")
    public Phrase getSupportPhrase() {
        return supportService.getRandomPhrase();
    }

    @Logging
    @PostMapping(path = "/")
    public void addSupportPhrase(@RequestBody Phrase phrase) {
        supportService.addNewPhrase(phrase);
    }
}
