package com.NoWaste.NoWaste.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity singin(@RequestBody Map<String, String> body) {
         return loginService.findUser(body.get("username"), body.get("password"));
    }

// Da riguardare per migliorare le chiamate nei controllers
       @PostMapping("/registerUser")
    public ResponseEntity<Boolean> registerUser(@RequestBody Utente utente) {
        return loginService.registerUser(utente);
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
