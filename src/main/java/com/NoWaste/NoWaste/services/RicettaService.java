package com.NoWaste.NoWaste.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.NoWaste.NoWaste.DAO.RicettaDAO;
import com.NoWaste.NoWaste.entities.Entity;
import com.NoWaste.NoWaste.entities.Ricetta;

@Service
public class RicettaService {

    @Autowired
    private RicettaDAO ricettaDAO;

    @Autowired
    private ApplicationContext context;

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
        List<Ricetta> racetta = new ArrayList<>();
         for (Entity ricetta : ricettaDAO.readByRecipeType(body).values()) {
            racetta.add((Ricetta) ricetta);
        }
        return racetta;
    }

    public List<Ricetta> findRecipeByIngredients(List<String> values) {
        List<Ricetta> racette = new ArrayList<>();
        for (Entity ricetta : ricettaDAO.readByIngredients(values).values()) {
            racette.add((Ricetta) ricetta);
        }
        return racette;
    }

    public boolean createRecipe(Map<String, String> params) {
        Ricetta r = context.getBean(Ricetta.class, params);
        return ricettaDAO.create(r);
    }

    public boolean updateRecipe(Map<String, String> params) {
        Ricetta r = context.getBean(Ricetta.class, params);
        return ricettaDAO.update(r);
    }

    public boolean deleteRecipe(int id) {
        return ricettaDAO.delete(id);
    }

}
