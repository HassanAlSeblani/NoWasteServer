package com.NoWaste.NoWaste.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NoWaste.NoWaste.DAO.UtenteDAO;
import com.NoWaste.NoWaste.entities.Entity;
import com.NoWaste.NoWaste.entities.Utente;



@Service
public class UtenteService {
    
    @Autowired
    private UtenteDAO utenteDAO;

    public List<Utente> getAllUtenti() {
        Map<Integer,Entity> result =  utenteDAO.read();
        List<Utente> res = new ArrayList<Utente>();

        for(Entity u : result.values()) {
            res.add((Utente)u);
        }
        return res;
    }

    public Utente getUtenteById(int id) {
        return (Utente)utenteDAO.readById(id);
    }

    public boolean deleteUtenteById(int id) {
        return utenteDAO.delete(id);
    }

    public Utente updateUtente(Utente utente)
    {
        utenteDAO.update(utente);
        return (Utente)utenteDAO.readById(utente.getId());
    }
}
