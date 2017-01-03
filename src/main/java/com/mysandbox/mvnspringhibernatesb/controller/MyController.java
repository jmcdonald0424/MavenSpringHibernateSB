package com.mysandbox.mvnspringhibernatesb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysandbox.mvnspringhibernatesb.entity.model.SandboxUser;
import com.mysandbox.mvnspringhibernatesb.service.SandboxUserService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MyController{
    
    @Autowired
    private SandboxUserService sandboxUserService;
    private static final Logger LOGGER = LoggerFactory.getLogger(MyController.class);
    
    // Use for Tomcat access
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView view = new ModelAndView();
        view.setViewName("index");
        return view;
    }
    

    // Remote access methods
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/result", method = RequestMethod.GET, produces = "application/json", headers = "content-type=application/json")
    public String getSandboxUsers(){
        LOGGER.debug("Getting Sandbox Users...");
        List<SandboxUser> list = sandboxUserService.getSandboxUsers();
        LOGGER.debug("Sandbox User retrieved: " + list.size());
        return buildJsonString(list);
    }
    
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/result", method = RequestMethod.POST, produces = "application/json", headers = "content-type=application/json")
    public String addSandboxUser(@RequestBody String username){	
    	SandboxUser user = new SandboxUser();
        Long userId = 0L;
    	user.setUsername(username);   
        LOGGER.debug("Inserting user: " + username);
    	userId = sandboxUserService.insert(user);
        LOGGER.debug("User: " + username + " : " + userId + " insert complete.");
        return buildJsonString(user);
    }
    
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/result/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteSandboxUser(@PathVariable("id") Long id){        
        LOGGER.debug("Deleting sandbox user ID: " + id);
        try{
            sandboxUserService.deleteById(id);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"status\":\"FAILED\"}");
        }
        return ResponseEntity.ok("{\"status\":\"OK\"}");
    }
    
    private String buildJsonString(List list){
        ObjectMapper mapper = new ObjectMapper();
        LOGGER.debug("called mapToJsonString...");
        try{
            return mapper.writeValueAsString(list);
        }catch(JsonProcessingException jpe){
            LOGGER.debug("mapToJsonString failed: " + jpe);
            return null;
        }
    }
    
    private String buildJsonString(Object object){
        List<Object> list = new ArrayList<>();
        list.add(object);
        return buildJsonString(list);
    }
}
