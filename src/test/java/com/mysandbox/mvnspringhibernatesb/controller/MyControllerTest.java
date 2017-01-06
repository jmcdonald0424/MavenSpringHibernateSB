package com.mysandbox.mvnspringhibernatesb.controller;

import com.mysandbox.mvnspringhibernatesb.config.MyConfig;
import com.mysandbox.mvnspringhibernatesb.entity.model.SandboxUser;
import com.mysandbox.mvnspringhibernatesb.exception.SandboxUserNotFoundException;
import com.mysandbox.mvnspringhibernatesb.exception.handler.RestExceptionHandler;
import com.mysandbox.mvnspringhibernatesb.service.SandboxUserService;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MyConfig.class})
@WebAppConfiguration
public class MyControllerTest {
    
    @Mock
    private SandboxUserService sandboxUserServiceMock;
    
    @InjectMocks
    private MyController myController;
    
    private MockMvc mockMvc;
    
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(myController)
                .setHandlerExceptionResolvers(createExceptionResolver())
                .build();
    }
    
    @Test
    public void getSandboxUsers_SandboxUsersFound_ShouldReturnFoundSandboxUserEntries() throws Exception{
        SandboxUser first = new SandboxUser();
        first.setUserId(1L);
        first.setUsername("first");
        SandboxUser second = new SandboxUser();
        second.setUserId(2L);
        second.setUsername("second");
        
        when(sandboxUserServiceMock.getSandboxUsers()).thenReturn(Arrays.asList(first, second));
        
        mockMvc.perform(get("/result").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].userId", is(1)))
                .andExpect(jsonPath("$[0].username", is("first")))
                .andExpect(jsonPath("$[1].userId", is(2)))
                .andExpect(jsonPath("$[1].username", is("second")));
        
        verify(sandboxUserServiceMock, times(1)).getSandboxUsers();
        verifyNoMoreInteractions(sandboxUserServiceMock);
    }
    
    @Test
    public void getSandboxUser_SandboxUserEntryFound_ShouldReturnFoundSandboxUserEntry() throws Exception{
        SandboxUser sandboxUser = new SandboxUser();
        sandboxUser.setUserId(1L);
        sandboxUser.setUsername("username");
        
        when(sandboxUserServiceMock.find(1L)).thenReturn(sandboxUser);
        
        mockMvc.perform(get("/result/{id}", 1L).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].userId", is(1)))
                .andExpect(jsonPath("$[0].username", is("username")));
        
        verify(sandboxUserServiceMock, times(1)).find(1L);
        verifyNoMoreInteractions(sandboxUserServiceMock);
    }
    
    @Test
    public void getSandboxUser_SandboxUserEntryNotFound_ShouldReturnHttpStatusCode404() throws Exception{
        when(sandboxUserServiceMock.find(1L)).thenThrow(new SandboxUserNotFoundException(""));
        
        mockMvc.perform(get("/result/{id}", 1L).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
        
        verify(sandboxUserServiceMock, times(1)).find(1L);
        verifyNoMoreInteractions(sandboxUserServiceMock);
    }
    
    /*@Test
    public void deleteSandboxUser_SandboxUserEntryNotFound_ShouldReturnHttpStatusCode404() throws Exception{
        when(sandboxUserServiceMock.deleteById(1L)).thenThrow(new SandboxUserNotFoundException(""));
        
        mockMvc.perform(delete("/result/{id}", 1L))
                .andExpect(status().isNotFound());
        
        verify(sandboxUserServiceMock, times(1)).deleteById(1L);
        verifyNoMoreInteractions(sandboxUserServiceMock);
    }*/
    
    private ExceptionHandlerExceptionResolver createExceptionResolver(){
        ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
            @Override
            protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod, Exception exception) {
                Method method = new ExceptionHandlerMethodResolver(RestExceptionHandler.class).resolveMethod(exception);
                return new ServletInvocableHandlerMethod(new RestExceptionHandler(), method);
            }
        };
        exceptionResolver.afterPropertiesSet();
        return exceptionResolver;
    }
}