package com.coocaa.union.manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Oauth2Controller extends BaseController{

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.PUT)
    public String loginInfo(String username, String password){

        return "login";
    }
}
