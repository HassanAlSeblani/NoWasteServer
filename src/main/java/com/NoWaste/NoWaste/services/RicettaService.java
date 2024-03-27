package com.NoWaste.NoWaste.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NoWaste.NoWaste.DAO.RicettaDAO;
import com.NoWaste.NoWaste.entities.Entity;
import com.NoWaste.NoWaste.entities.Ricetta;

@Service
public class RicettaService {

    @Autowired
    private RicettaDAO ricettaDAO;


    public List<Ricetta> findAllRecipes() {
        List<Ricetta> ricette = new ArrayList<>();

        for (Entity ricetta : ricettaDAO.read().values()) {
            ricette.add((Ricetta) ricetta);
        }

        return ricette;
    }

    public Ricetta findRecipeById(int id) {
        return (Ricetta) ricettaDAO.readById(id);
    }

    public List<Ricetta> findRecipeFilter(Map<String, String> body) {
        List<Ricetta> ricette = new ArrayList<>();

        for (Entity ricetta : ricettaDAO.readByFilter(body).values()) {
            ricette.add((Ricetta) ricetta);
        }

        return ricette;
    }

    public List<Ricetta> findTypeRecipeFilter(Map<String, String> body) {
        List<Ricetta> ricette = new ArrayList<>();
         for (Entity ricetta : ricettaDAO.readByRecipeType(body).values()) {
            ricette.add((Ricetta) ricetta);
        }
        return ricette;
    }

    public List<Ricetta> findRecipeByIngredients(List<String> values) {
        List<Ricetta> ricette = new ArrayList<>();
        for (Entity ricetta : ricettaDAO.readByIngredients(values).values()) {
            ricette.add((Ricetta) ricetta);
        }
        return ricette;
    }

    public boolean createRecipe(Ricetta ricetta) {
        return ricettaDAO.create(ricetta);
    }

    public boolean updateRecipe(Ricetta ricetta) {
        return ricettaDAO.update(ricetta);
    }

    public boolean deleteRecipe(int id) {
        return ricettaDAO.delete(id);
    }

}
