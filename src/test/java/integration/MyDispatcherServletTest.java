package integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import homework.Phrase;
import homework.context.ApplicationContext;
import homework.rest.MyDispatcherServlet;
import homework.service.SupportService;
import homework.service.SupportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class MyDispatcherServletTest {
    final String PHRASE = "Test phrase";
    final String EXISTS_PHRASE = "У тебя все получится!";
    final String GET_PATH = "/v1/support/";
    final String POST_PATH = "/v1/support/";
    private ApplicationContext context;
    private ObjectMapper mapper;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private SupportService service;
    private PrintWriter writer;
    private BufferedReader reader;
    private MyDispatcherServlet servlet;

    private Phrase phrase = new Phrase(PHRASE);

    @BeforeEach
    public void init() throws ServletException {
        context = new ApplicationContext();
        servlet = (MyDispatcherServlet) context.getBeanForClass(HttpServlet.class);
        mapper = (ObjectMapper) context.getBeanForClass(ObjectMapper.class);
        response = mock(HttpServletResponse.class);
        request = mock(HttpServletRequest.class);
        writer = mock(PrintWriter.class);
        reader = mock(BufferedReader.class);
        service = mock(SupportServiceImpl.class);
        servlet.init();
    }

    @Test
    public void doGetWhenOk() throws IOException {

        when(request.getContextPath()).thenReturn(GET_PATH);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        verify(writer).println(anyString());
        verify(writer).flush();
    }

    @Test
    public void doPostWhenOk() throws IOException {

        mapper = mock(ObjectMapper.class);
        servlet.setMapper(mapper);

        when(request.getContextPath()).thenReturn(POST_PATH);
        when(request.getReader()).thenReturn(reader);
        when(mapper.readValue(reader, Phrase.class)).thenReturn(phrase);
        when(response.getWriter()).thenReturn(writer);

        servlet.doPost(request, response);

        verify(writer).println("Успешно");
        verify(writer).flush();
    }

    @Test
    public void doPostWhenPhraseAlreadyExists() throws IOException {

        mapper = mock(ObjectMapper.class);
        servlet.setMapper(mapper);

        when(request.getContextPath()).thenReturn(POST_PATH);
        when(request.getReader()).thenReturn(reader);
        when(mapper.readValue(reader, Phrase.class)).thenReturn(new Phrase(EXISTS_PHRASE));
        when(response.getWriter()).thenReturn(writer);

        servlet.doPost(request, response);

        verify(writer).println("Что-то пошло не так");
        verify(writer).flush();
    }

}
