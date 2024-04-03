package com.NoWaste.NoWaste.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.NoWaste.NoWaste.DAO.IngredienteDAO;
import com.NoWaste.NoWaste.DAO.RicettaDAO;
import com.NoWaste.NoWaste.DAO.RicettaIngredienteDAO;
import com.NoWaste.NoWaste.entities.Entity;
import com.NoWaste.NoWaste.entities.Ingrediente;
import com.NoWaste.NoWaste.entities.Ricetta;
import com.NoWaste.NoWaste.entities.RicettaIngrediente;
import org.springframework.http.ResponseEntity;

@Service
public class RicettaService {

    @Autowired
    private RicettaDAO ricettaDAO;

    @Autowired
    private IngredienteDAO ingredienteDAO;

    @Autowired
    private RicettaIngredienteDAO ricettaIngredienteDAO;

    public List<Ricetta> findAllRecipes() {
        List<Ricetta> ricette = new ArrayList<>();

        for (Entity ricetta : ricettaDAO.read().values()) {
            ricette.add((Ricetta) ricetta);
        }

        return ricette;
    }

    public ResponseEntity findRecipeById(int id) {
        Ricetta r = (Ricetta)ricettaDAO.readById(id);
        ResponseEntity response = null;
        if(r != null){
            response = new ResponseEntity<>(r, HttpStatus.OK);
        }
        else {
            response = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
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

    public List<Ricetta> findRecipeByIngredients(Map<String, String> body) {
        List<Ricetta> ricette = new ArrayList<>();
        List<String> values = new ArrayList<>();

        for (String booleano : body.keySet()) {
            if (Boolean.parseBoolean(body.get(booleano))) {
                values.add(booleano);
            }
        }
        if(values.size() > 0) {
            for (Entity ricetta : ricettaDAO.readByIngredients(values).values()) {
                ricette.add((Ricetta) ricetta);
            }
        }
        return ricette;
    }

    public boolean createRecipe(Ricetta ricetta) {

        boolean result = ricettaDAO.create(ricetta);
        if (result && ricetta.getIngredienti() != null) {
            for (RicettaIngrediente ingrediente : ricetta.getIngredienti()) {
                boolean conta = false;
                for (Entity e : ingredienteDAO.read().values()) {
                    Ingrediente i = (Ingrediente) e;
                    if (ingrediente.getIngrediente().getNome().equalsIgnoreCase(i.getNome())) {
                        conta = true;
                    }
                }
                if (conta == false)
                {
                    ingredienteDAO.create(ingrediente.getIngrediente());
                    ingrediente.setIngrediente((Ingrediente) ingredienteDAO.readById(ingredienteDAO.lastIngrediente()));
                }

                ingrediente.setIdRicetta(ricettaDAO.lastRicetta());
                ricettaIngredienteDAO.create(ingrediente);
            }
        }

        return result;
    }

    public boolean updateRecipe(Ricetta ricetta) {
        return ricettaDAO.update(ricetta);
    }

    public boolean deleteRecipe(int id) {
        return ricettaDAO.delete(id);
    }

}
