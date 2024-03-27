package com.NoWaste.NoWaste.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.NoWaste.NoWaste.entities.Utente;
import com.NoWaste.NoWaste.services.LoginService;
import com.NoWaste.NoWaste.services.UtenteService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;



@CrossOrigin(origins = {"http://loclhost:4200"})
@RestController
@RequestMapping("/api/user")

public class UserRestController {
    
    @Autowired
    private LoginService loginService;

    @Autowired
    private UtenteService utenteService;
    @GetMapping("/allUsers")
    public List<Utente> getAllUtenti(@RequestHeader("token") String token) {
        if(token != null && loginService.checkLoginAdmin(token)) //da fare il controllo sul token
        {
                return utenteService.getAllUtenti();
        }
        return new ArrayList<Utente>();
    }

    @GetMapping("/userById")
    public Utente getUtenteById(@RequestHeader("token") String token, @RequestParam("id") int idUtente) {
        if(token != null) //da fare il controllo sul token
        {
                if((loginService.checkLoginAdmin(token)) || (loginService.checkLoginUtente(token) && loginService.matchUser(token, idUtente)))
                    return utenteService.getUtenteById(idUtente);
        }
            return null;
    }

    @DeleteMapping("/deleteUser")
    public boolean deleteUtenteById(@RequestHeader("token") String token, @RequestParam("id") int idUtente) {

        if(token != null) //da fare il controllo sul token
        {
            if((loginService.checkLoginAdmin(token)) || (loginService.checkLoginUtente(token) && loginService.matchUser(token, idUtente)))
                return utenteService.deleteUtenteById(idUtente);
        }
            return false;
    }
    
    @PostMapping("/updateUser")
    public Utente updateUtente(@RequestHeader("token") String token, @RequestBody Utente utente) {
        if(token != null) //da fare il controllo sul token
        {
            if((loginService.checkLoginAdmin(token)) || (loginService.checkLoginUtente(token) && loginService.matchUser(token, utente.getId())))
                return utenteService.updateUtente(utente);
        }
            return null;
    }
}
