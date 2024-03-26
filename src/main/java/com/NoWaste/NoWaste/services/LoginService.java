package com.NoWaste.NoWaste.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.NoWaste.NoWaste.DAO.UtenteDAO;

import com.NoWaste.NoWaste.entities.Entity;
import com.NoWaste.NoWaste.entities.Utente;

@Service
public class LoginService {
    
    @Autowired
    private UtenteDAO utenteDAO;

    public Entity findUser(String username, String password)  {
        // Validazione dei dati di input
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Lo username e la password non possono essere vuoti");
        }
        
        // Ricerca dell'utente nel database
        Entity user = utenteDAO.readFromUsernameAndPassword(username, password);
        
        // Gestione dell'utente non trovato
        if (user == null) {
            System.out.println("Utente non trovato");
        }
        
        return user;
        
    }

    public boolean registerUser(Utente utente) {
        // Verifica se l'utente esiste già nel database
        if (utenteDAO.readFromUsernameAndPassword(utente.getUser(), utente.getPassword()) != null) {
            System.out.println("L'utente esiste già");
            return false;
        }
        
        // Effettua la registrazione dell'utente nel database
        return utenteDAO.create(utente);
    }


    public boolean checkLoginUtente(String token) {
        if (token!= null){
            if (!token.equalsIgnoreCase("") && token.split("-")[0].equalsIgnoreCase("USER")){
               return utenteDAO.readById(Integer.parseInt(token.split("-")[1])) != null;
            }
        }
        return false;
    
    }

    public boolean checkLoginAdmin(String token) {
        if (token!= null){
            if (!token.equalsIgnoreCase("") && token.split("-")[0].equalsIgnoreCase("ADMIN")){
                return utenteDAO.readById(Integer.parseInt(token.split("-")[1])) != null;
            }
        }
        return false;   
    }

    public boolean checkLogin (String token) {
            if (token!= null){
            return checkLoginUtente(token) || checkLoginAdmin(token);
        
        }
            return false;
    }



    
}
