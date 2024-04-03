package com.NoWaste.NoWaste.services;

import com.NoWaste.NoWaste.DTO.LoginStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.NoWaste.NoWaste.DAO.UtenteDAO;

import com.NoWaste.NoWaste.entities.Entity;
import com.NoWaste.NoWaste.entities.Utente;

@Service
public class LoginService {

    @Autowired
    private UtenteDAO utenteDAO;

    public ResponseEntity findUser(String username, String password) {

        if(username == null || password == null)
        {
            return new ResponseEntity<>("username o password non trovati",HttpStatus.FORBIDDEN);
        }
        Utente u = (Utente)utenteDAO.readFromUsernameAndPassword(username, password);
        LoginStatus ls = new LoginStatus();
        if(u != null) {
            if (u.getRuolo().equalsIgnoreCase("USER")) {
                ls.setToken("USER", u.getId());
            } else if (u.getRuolo().equalsIgnoreCase("ADMIN")) {
                ls.setToken("ADMIN", u.getId());
            }
        }
        else {
            return new ResponseEntity<>("utente non trovato",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<LoginStatus>(ls, HttpStatus.OK);
    }

    public ResponseEntity<Boolean> registerUser(Utente utente) {
        // Verifica se l'utente esiste già nel database
        ResponseEntity<Boolean> response =  null;
        if (utenteDAO.findFromUsername(utente.getUsername()) != null) {
            response = new ResponseEntity<>(false, HttpStatus.METHOD_NOT_ALLOWED);
        }
        else
        {
            utente.setRuolo("user");
            boolean inserimento = utenteDAO.create(utente);
            response = new ResponseEntity<>(inserimento, HttpStatus.OK);
        }
        // Effettua la registrazione dell'utente nel database
        return response;
    }

    public boolean checkLoginUtente(String token) {
        if (token != null) {
            if (!token.equalsIgnoreCase("") && token.split("-")[0].equalsIgnoreCase("USER")) {
                Utente u = (Utente)utenteDAO.readById(Integer.parseInt(token.split("-")[1]));
                return u != null && u.getRuolo().equalsIgnoreCase("USER");
            }
        }
        return false;
    }

    public boolean checkLoginAdmin(String token) {
        if (token != null) {
            if (!token.equalsIgnoreCase("") && token.split("-")[0].equalsIgnoreCase("ADMIN")) {
                Utente u = (Utente)utenteDAO.readById(Integer.parseInt(token.split("-")[1]));
                return u != null && u.getRuolo().equalsIgnoreCase("ADMIN");
            }
        }
        return false;
    }

    public boolean checkLogin(String token) {
        if (token != null) {
            return checkLoginUtente(token) || checkLoginAdmin(token);

        }
        return false;
    }

    public boolean matchUser(String token, int id) {
        if (token != null) {
            Entity u = utenteDAO.readById(Integer.parseInt(token.split("-")[1]));
            if (u != null)
                return u.getId() == id;
        }
        return false;
    }

}
