package homework.servlets;

import homework.service.SupportServiceImpl;
import homework.util.PhraseContainer;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class SupportServlet extends HttpServlet {

    private final SupportServiceImpl supportService;

    public SupportServlet(){
        this.supportService = new SupportServiceImpl(new Random(), new PhraseContainer());
    }

    public SupportServlet(SupportServiceImpl supportService){
        this.supportService = supportService;
    }

    @Override
    public void init(ServletConfig servletConfig) {
        try {
            super.init(servletConfig);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        String phrase = supportService.getRandomPhrase();
        try (PrintWriter writer = resp.getWriter()){
            writer.printf("<html><head><title>Ваша фраза сегодня: </title>" +
                    "</head><body><h1>Фраза: %s</h1></body></html>", phrase);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String phrase = req.getParameter("phrase");
        supportService.addNewPhrase(phrase);
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        try (PrintWriter writer = resp.getWriter()) {
            writer.print("<html><head><title>Спасибо за добавление новой фразы!</title></head></html>");
        }
    }
}