package servlet;

import homework.service.SupportServiceImpl;
import homework.servlets.SupportServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;

public class SupportServletTest {

    private SupportServiceImpl service;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private SupportServlet servlet;
    private PrintWriter writer;

    final String PHRASE = "Test phrase";
    final String CONTENT_TYPE = "text/html";
    final String ENCODING = "UTF-8";

    @BeforeEach
    void setup() throws ServletException {
        servlet = new SupportServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        service = mock(SupportServiceImpl.class);
        writer = mock(PrintWriter.class);
        servlet.setSupportService(service);
    }

    @Test
    void doGet() throws ServletException, IOException {

        when(service.getRandomPhrase()).thenReturn(PHRASE);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        verify(response).setContentType(CONTENT_TYPE);
        verify(response).setCharacterEncoding(ENCODING);
        verify(writer).printf(anyString(), eq(PHRASE));
    }

    @Test
    void doPostWhenSubmit() throws ServletException, IOException {

        when(request.getParameter("phrase")).thenReturn(PHRASE);
        when(response.getWriter()).thenReturn(writer);

        servlet.doPost(request, response);

        verify(service).addNewPhrase(PHRASE);
        verify(response).setContentType(CONTENT_TYPE);
        verify(response).setCharacterEncoding(ENCODING);
    }
}
