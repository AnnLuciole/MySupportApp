package homework.configuration;

import homework.annotation.Bean;
import homework.annotation.Configuration;
import homework.rest.MyDispatcherServlet;
import homework.rest.SupportController;
import homework.rest.SupportControllerImpl;
import homework.service.SupportService;
import homework.service.SupportServiceImpl;
import homework.util.ControllersContainer;
import homework.util.PhraseContainer;

import javax.servlet.http.HttpServlet;

@Configuration
public class SupportAppConfig {

    @Bean
    public SupportService supportService() {
        return new SupportServiceImpl();
    }

    @Bean
    public SupportController supportServlet() {
        return new SupportControllerImpl();
    }

    @Bean
    public HttpServlet myDispatcherServlet() {
        return new MyDispatcherServlet();
    }

    @Bean
    public PhraseContainer phraseContainer() {
        return new PhraseContainer();
    }

    @Bean
    public ControllersContainer controllersContainer() {
        return new ControllersContainer();
    }
}
