package homework.configuration;

import homework.annotation.Bean;
import homework.annotation.Configuration;
import homework.service.SupportService;
import homework.service.SupportServiceImpl;
import homework.servlets.SupportServlet;
import homework.util.PhraseContainer;

import javax.servlet.http.HttpServlet;

@Configuration
public class SupportAppConfig {

    @Bean
    public SupportService supportService() {
        return new SupportServiceImpl();
    }

    @Bean
    public HttpServlet supportServlet() {
        return new SupportServlet();
    }

    @Bean
    public PhraseContainer phraseContainer() {
        return new PhraseContainer();
    }
}
