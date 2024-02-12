package homework.rest;

import homework.Phrase;
import homework.annotation.Autowired;
import homework.annotation.Controller;
import homework.annotation.Mapping;
import homework.service.SupportService;

@Controller
@Mapping(path = "/v1/support")
public class SupportControllerImpl implements SupportController {

    private SupportService supportService;

    @Autowired
    public void setSupportService(SupportService supportService) {
        this.supportService = supportService;
    }

    @Mapping(path = "/")
    public Phrase getSupportPhrase() {
        Phrase phrase = supportService.getRandomPhrase();
        return phrase;
    }

    @Mapping(path = "/")
    public boolean addSupportPhrase(Phrase phrase) {
        return supportService.addNewPhrase(phrase);
    }
}
