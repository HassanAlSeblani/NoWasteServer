package com.NoWaste.NoWaste.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NoWaste.NoWaste.DTO.LoginStatus;
import com.NoWaste.NoWaste.entities.Utente;
import com.NoWaste.NoWaste.services.LoginService;

@CrossOrigin(origins ={"http://localhost:4200"})
@RestController
@RequestMapping("/api/login")
public class LoginRestController {
    
    @Autowired
    private LoginService loginService;


     @PostMapping("/signin")
    public LoginStatus singin(@RequestBody Map<String, String> body) {
        Utente u = (Utente) loginService.findUser(body.get("username"), body.get("password"));
        LoginStatus ls = new LoginStatus();
        ls.setToken ("NONE", 0);

        if (u.getRuolo().equalsIgnoreCase("USER")) {
            ls.setToken ("USER",  u.getId());     
            return ls;
        } else if (u.getRuolo().equalsIgnoreCase("ADMIN")) {
            ls.setToken ("ADMIN", u.getId());
            return ls;
        } else {
            return ls;
        }
    }

// Da riguardare per migliorare le chiamate nei controllers
       @PostMapping("/registerUser")
    public ResponseEntity<Boolean> registerUser(@RequestBody Utente utente) {
        boolean registrato = loginService.registerUser(utente);
        return ResponseEntity.ok(registrato);
    }

    @PostMapping("/checkLogin")
    public boolean checkLogin(@RequestBody Map<String, String> body) {
        return loginService.checkLogin(body.get("token"));
    }

    @PostMapping("/checkLoginAdmin")
    public boolean checkLoginAdmin(@RequestBody Map<String, String> body)
    {
        return loginService.checkLoginAdmin(body.get("token"));
    }
    


}
