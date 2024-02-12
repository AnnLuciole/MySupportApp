package homework.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import homework.Phrase;
import homework.annotation.Autowired;
import homework.enums.MappingType;
import homework.util.ControllersContainer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class MyDispatcherServlet extends HttpServlet {

    private ControllersContainer container;
    private ObjectMapper mapper;

    @Autowired
    public void setContainer(ControllersContainer container) {
        this.container = container;
    }

    @Autowired
    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getContextPath();
        SupportController controller = container.getController(path);
        Method method = container.getMethod(path).get(MappingType.GET);
        Phrase phrase;
        try {
            phrase = (Phrase) method.invoke(controller);
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            phrase = null;
        }
        String responseStr;
        if (Objects.nonNull(phrase)) {
            responseStr = mapper.writeValueAsString(phrase);
            response.setStatus(200);
        } else {
            responseStr = "Что-то пошло не так";
            response.setStatus(500);
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(responseStr);
        out.flush();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)  throws IOException {
        String path = request.getContextPath();
        SupportController controller = container.getController(path);
        Method method = container.getMethod(path).get(MappingType.POST);
        Phrase phrase = mapper.readValue(request.getReader(), Phrase.class);
        boolean answer;
        try {
            answer = (boolean) method.invoke(controller, phrase);
        } catch (IllegalAccessException | InvocationTargetException e) {
            answer = false;
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String responseStr;
        if (answer) {
            response.setStatus(200);
            responseStr = "Успешно";
        } else {
            response.setStatus(500);
            responseStr = "Что-то пошло не так";
        }
        PrintWriter out = response.getWriter();
        out.println(responseStr);
        out.flush();
    }
}
