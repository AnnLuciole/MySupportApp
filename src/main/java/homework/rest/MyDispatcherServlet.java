package homework.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import homework.Phrase;
import homework.annotation.Autowired;
import homework.enums.HttpMethod;
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
        container.initContainer();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Phrase responseObject = (Phrase) getResponseObject(request, HttpMethod.GET, null);
        String responseStr = Objects.nonNull(responseObject) ? mapper.writeValueAsString(responseObject) : "Что-то пошло не так";
        response = updateResponse(Objects.nonNull(responseObject), response);
        PrintWriter out = response.getWriter();
        out.println(responseStr);
        out.flush();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)  throws IOException {
        Phrase phrase = mapper.readValue(request.getReader(), Phrase.class);
        Boolean responseObject = (Boolean) getResponseObject(request, HttpMethod.POST, phrase);
        boolean result = Objects.nonNull(responseObject) && responseObject == Boolean.TRUE;
        String responseStr = result ? "Успешно" : "Что-то пошло не так";
        response = updateResponse(result, response);
        PrintWriter out = response.getWriter();
        out.println(responseStr);
        out.flush();
    }

    private Object getResponseObject(HttpServletRequest request, HttpMethod httpMethod, Phrase phrase) {
        String path = request.getContextPath();
        SupportController controller = container.getController(path);
        Method method = container.getMethod(path).get(httpMethod);
        Object answer;
        try {
            if (Objects.isNull(phrase)) {
                answer = method.invoke(controller);
            } else {
                answer = method.invoke(controller, phrase);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            answer = null;
        }
        return answer;
    }

    private HttpServletResponse updateResponse(boolean type, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if (type) {
            response.setStatus(200);
        } else {
            response.setStatus(500);
        }
        return response;
    }
}
