package com.NoWaste.NoWaste.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

        if(u != null)
        {
            if (u.getRuolo().equalsIgnoreCase("USER")) {
                ls.setToken("USER", u.getId());
            } else if (u.getRuolo().equalsIgnoreCase("ADMIN")) {
                ls.setToken("ADMIN", u.getId());
            }
        }
        return ls;
    }

// Da riguardare per migliorare le chiamate nei controllers
       @GetMapping("/registerUser")
    public ResponseEntity<Boolean> registerUser(@RequestBody Utente utente) {
        boolean registrato = loginService.registerUser(utente);
        return ResponseEntity.ok(registrato);
    }

    @GetMapping("/checkLogin")
    public ResponseEntity<Boolean> checkLogin(@RequestHeader String token) {
        boolean match = loginService.checkLogin(token);
        return ResponseEntity.ok(match);
    }

    @GetMapping("/checkLoginAdmin")
    public ResponseEntity<Boolean> checkLoginAdmin(@RequestHeader String token)
    {
        boolean match = loginService.checkLoginAdmin(token);
        return ResponseEntity.ok(match);
    }
    
    @GetMapping("/checkLoginUser")
    public ResponseEntity<Boolean> checkLoginUser(@RequestHeader String token)
    {
        boolean match = loginService.checkLoginUtente(token);
        return ResponseEntity.ok(match);
    }
    
    @GetMapping("/matchUser")
    public ResponseEntity<Boolean> matchUtente(@RequestHeader String token, @RequestParam("idUtente") int idUtente)
    {
        boolean match = loginService.matchUser(token, idUtente);
        return ResponseEntity.ok(match);
    }
    

}
