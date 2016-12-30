package com.mysandbox.mvnspringhibernatesb.controller;

import com.mysandbox.mvnspringhibernatesb.entity.model.SandboxUser;
import com.mysandbox.mvnspringhibernatesb.service.SandboxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MyController{
    
    @Autowired
    private SandboxUserService sandboxUserService;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView view = new ModelAndView();
        view.setViewName("index");
        return view;
    }
        
    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public void runQueries(ModelMap model){	
    	SandboxUser user = new SandboxUser();
    	user.setUsername("johndoe");    	
    	sandboxUserService.insert(user);
        model.addAttribute("username", user.getUsername());
    }
}
