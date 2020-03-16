package com.coocaa.union.manager.config;

import com.coocaa.union.manager.utils.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RevokeTokenEndpoint {

  @Autowired
  @Qualifier("consumerTokenServices")
  ConsumerTokenServices consumerTokenServices;

  @RequestMapping(method = RequestMethod.GET, value = "/oauth/delete/token")
  @ResponseBody
  public ResponseObject<Boolean> revokeToken(@RequestParam(name = "access_token") String accessToken) {
    consumerTokenServices.revokeToken(accessToken);
    return ResponseObject.success();
  }
}