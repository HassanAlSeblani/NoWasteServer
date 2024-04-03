package com.NoWaste.NoWaste.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.NoWaste.NoWaste.DAO.UtenteDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.NoWaste.NoWaste.DAO.CommentoDAO;
import com.NoWaste.NoWaste.entities.Commento;
import com.NoWaste.NoWaste.entities.Entity;
import com.NoWaste.NoWaste.entities.Utente;

@Service
public class CommentoService {

    @Autowired
    private CommentoDAO commentoDAO;
    @Autowired
    private UtenteDAO utenteDAO;

    public ResponseEntity<Boolean> createCommento(Commento commento) {
        Boolean inserimento = commentoDAO.create(commento);
        ResponseEntity<Boolean> response = null;
        if(inserimento)
        {
            response = new ResponseEntity<>(true, HttpStatus.OK);
        }
        else {
            response = new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    public List<Commento> getAllCommenti() {
        List <Commento> ris = new ArrayList<Commento>();
        for(Entity e : commentoDAO.read().values()){
            ris.add((Commento) e);
        }
        return ris;
    }

    public boolean updateCommento(Commento commento) {
        return commentoDAO.update(commento);
    }

    public boolean deleteCommento(int id) {
        return commentoDAO.delete(id);
    }

    public Commento getCommentoById(int id) {
        return (Commento) commentoDAO.readById(id);
    }

    public List<Commento> getCommentiByUser(int userId) {
        return commentoDAO.getCommentoByUser(userId);
    }

    public List<Commento> getCommentiByRecipe(int recipeId) {
        return commentoDAO.getCommentoByRecipe(recipeId);
    }

}
