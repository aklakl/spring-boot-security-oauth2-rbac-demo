package com.leewaiho.Controller;

import com.leewaiho.Bean.User;
import com.leewaiho.ExceptionHandler.ServiceException;
import com.leewaiho.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static com.leewaiho.Controller.ApiController.USER;

/**
 * Created by leewaiho on 2017/7/20.
 */
@RestController
@RequestMapping(USER)
public class UserController extends ApiController {
    
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/me")
    public User me(Principal principal) throws ServiceException {
        if (principal != null) {
            String name = principal.getName();
            User user = userRepository.findByUsername(name);
            if (user == null) {
                throw new ServiceException(HttpStatus.NOT_FOUND, "User Not Exist!");
            }
            return user;
        }
        throw new ServiceException(HttpStatus.NOT_ACCEPTABLE, "你要先登录啊!");
    }
}
