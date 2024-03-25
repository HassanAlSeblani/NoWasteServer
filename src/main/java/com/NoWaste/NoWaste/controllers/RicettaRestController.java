package com.NoWaste.NoWaste.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.NoWaste.NoWaste.DTO.LoginStatus;
import com.NoWaste.NoWaste.entities.Ricetta;
import com.NoWaste.NoWaste.services.RicettaService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("api/recipe")
public class RicettaRestController {
    @Autowired
    private RicettaService ricettaService;

    /*
     * GET
     * 
     * Tutte le ricette
     * Endpoint: /allRecipes
     * Tipo di ritorno: List<Ricetta>
     * Parametri richiesti: token
     */
    @GetMapping("/allRecipes")
    public List<Ricetta> allRecipes(@RequestHeader String token) {
        if (token != null)
            return ricettaService.findAllRecipes();

        return new ArrayList<>();
    }

    /*
     * Ricetta singola by Id
     * Endpoint: /recipeById
     * Tipo di ritorno: Ricetta
     * Parametri richiesti: id Ricetta, token
     */

    @GetMapping("/recipeById")
    public Ricetta recipeById(@RequestHeader String token, @RequestParam("id") int id) {
        if (token != null)
            return ricettaService.findRecipeById(id);

        return null;
    }

    /*
     * 
     * POST:
     * Tutte le ricette by portata, difficoltà, tempo preparazione, nome, serving
     * Endpoint: /filterRecipes
     * Tipo di ritorno: List<Ricetta>
     * Parametri richiesti: Map<String,String>, token
     * 
     */
    @GetMapping("/recipeById")
    public List<Ricetta> filterRecipes(@RequestHeader String token, @RequestBody Map<String, String> body) {
        if (token != null)
            return ricettaService.findRecipeFilter(body);

        return new ArrayList<>();
    }
    /*
     * Ricetta vegana/vegetariana/glutine
     * Endpoint:
     * Tipo di ritorno:
     * Parametri richiesti:
     */
    /*
     * Ricette By Ingredienti
     * Endpoint: /RecipesByIngredients
     * Tipo di ritorno: List<Ricetta>
     * Parametri richiesti: List<String>, token
     */
    /*
     * Crea Ricetta
     * Endpoint: /addRecipe
     * Tipo di ritorno: boolean
     * Parametri richiesti: token admin, Ricetta
     */
    /*
     * PATCH:
     * Modifica Ricetta
     * Endpoint: /updateRicetta
     * Tipo di ritorno: Ricetta
     * Parametri richiesti: Ricetta, token admin
     */
    /*
     * DELETE:
     * Elimina Ricetta
     * Endpoint: /deleteRicetta
     * Tipo di ritorno: boolean
     * Parametri richiesti: token admin, id Ricetta
     * 
     */
}
