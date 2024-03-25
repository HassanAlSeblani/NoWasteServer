package com.NoWaste.NoWaste.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NoWaste.NoWaste.DAO.CommentoDAO;
import com.NoWaste.NoWaste.entities.Commento;
import com.NoWaste.NoWaste.entities.Entity;

@Service
public class CommentoService {

    @Autowired
    private CommentoDAO commentoDAO;

    public boolean createCommento(Commento commento) {
        return commentoDAO.create(commento);
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
