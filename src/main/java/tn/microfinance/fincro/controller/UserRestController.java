package tn.microfinance.fincro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import tn.microfinance.fincro.dao.model.Transaction;
import tn.microfinance.fincro.dao.model.User;
import tn.microfinance.fincro.services.interfaces.UserService;

import java.util.List;
import java.util.Map;

@RestController
public class UserRestController {
    @Autowired
    UserService userService;
    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    //URL: http://localhost:8081/SpringMVC/getAllUsers
    @GetMapping("getAllUsers")
    public List<User> getAllUsers() {
        return userService.retrieveAllUsers();
    }

    // URL : http://localhost:8081/SpringMVC/addUser
    @PostMapping("/addtUser")
    public int addUser(@RequestBody User user) {
        return user.getIdUser();
    }

    // URL : http://localhost:8081/SpringMVC/getUserById/1
    @GetMapping("getUserById/{user_id}")
    public User getUserById(@PathVariable("user_id") int id) {
        return userService.getUserById(id);
    }



    // URL: http://localhost:8081/SpringMVC/updateUserPasswordById/15465/1
    @PutMapping("/updateUserPasswordById/{password}/{id}")
    public int updateUserPasswordById(@PathVariable("password") String password, @PathVariable("id") int id) {
        userService.updateUserPasswordById(password,id);
        return id;
    }

    // URL : http://localhost:8081/SpringMVC/deleteUserById
    @DeleteMapping("/deleteUserById/{user_id}")
    public void deleteUserById(@PathVariable("user_id")int id) {
        userService.deleteUserById(id);
    }

    @GetMapping("/")
    public String helloWorld() {
        return "you don't need to be logged in";
    }
    @GetMapping("/not-restricted")
    public String notRestricted() {
        return "you don't need to be logged in";
    }
    @GetMapping("/restricted")
    public String restricted(OAuth2AuthenticationToken auth2AuthenticationToken,Model model) {

        OAuth2AuthorizedClient client=authorizedClientService.loadAuthorizedClient(
                auth2AuthenticationToken.getAuthorizedClientRegistrationId(),auth2AuthenticationToken.getName()
        );
        String tmp="";
        String userInfoEndpointUri=client.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUri();
        if(!StringUtils.isEmpty(userInfoEndpointUri)){
            RestTemplate restTemplate= new RestTemplate();
            HttpHeaders headers= new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer"+client.getAccessToken().getTokenValue() );
            HttpEntity entity=new HttpEntity("",headers);
            ResponseEntity<Map> response=restTemplate .exchange(userInfoEndpointUri,HttpMethod.GET, entity, Map.class);
            Map userAttributes=response.getBody();
             model.addAttribute("name", userAttributes.get("name"));
             tmp=(String) userAttributes.get("name")+(String) userAttributes.get("email")+(String) userAttributes.get("hd")+"+++++"+(String) userAttributes.get("picture");
            userService.addUserGoogle((String) userAttributes.get("email"),(String) userAttributes.get(("name")));
        }
        return "if you see this you are logged in "+ tmp ;
    }


}











