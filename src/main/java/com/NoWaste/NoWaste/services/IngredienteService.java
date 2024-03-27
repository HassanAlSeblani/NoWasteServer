package com.NoWaste.NoWaste.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NoWaste.NoWaste.DAO.IngredienteDAO;
import com.NoWaste.NoWaste.entities.Entity;
import com.NoWaste.NoWaste.entities.Ingrediente;

@Service
public class IngredienteService {

    @Autowired
    private IngredienteDAO ingredienteDAO;


    public List<Ingrediente> findAllIngredients(){
        List<Ingrediente> ingredienti = new ArrayList<>();

        for (Entity ingrediente : ingredienteDAO.read().values()){
            ingredienti.add((Ingrediente) ingrediente);
        }

        return ingredienti;
    }


    public Ingrediente findIngredientById(int id){
        return (Ingrediente) ingredienteDAO.readById(id);
    }

    public boolean createIngredient(Ingrediente ingrediente){
        return ingredienteDAO.create(ingrediente);
    }

    public boolean updateIngredient(Ingrediente ingrediente){
        return ingredienteDAO.update(ingrediente);
    }

    public boolean deleteIngredient(int id){
        return ingredienteDAO.delete(id);
    }
    
}
