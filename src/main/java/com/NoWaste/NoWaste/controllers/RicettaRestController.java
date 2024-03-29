package com.NoWaste.NoWaste.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.NoWaste.NoWaste.DTO.LoginStatus;
import com.NoWaste.NoWaste.entities.Ricetta;
import com.NoWaste.NoWaste.services.LoginService;
import com.NoWaste.NoWaste.services.RicettaService;

     //cercare immagini esistenti per ricette

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("api/recipe")
public class RicettaRestController {

    @Autowired
    private RicettaService ricettaService;

    @Autowired
    private LoginService loginService;

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
        if (loginService.checkLogin(token))
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
        if (loginService.checkLogin(token))
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
    @PostMapping("/filterRecipes")
    public List<Ricetta> filterRecipes(@RequestHeader String token, @RequestBody Map<String, String> body) {
        
        //mettersi d'accordo con frontend su nomi parametri e range tempo di preparazione della ricetta
        if (loginService.checkLogin(token))
            return ricettaService.findRecipeFilter(body);

        return new ArrayList<>();
    }

    /*
     * Ricetta vegana/vegetariana/glutine
     * Endpoint:
     * Tipo di ritorno:
     * Parametri richiesti:
     */

     //da fare nel front end
    @PostMapping("/filterTypeRecipes")
    public List<Ricetta> filterTypeRecipes(@RequestHeader String token, @RequestBody Map<String, String> body) {
        if (loginService.checkLogin(token))
            return ricettaService.findTypeRecipeFilter(body);

        return new ArrayList<>();
    }

    /*
     * Ricette By Ingredienti
     * Endpoint: /RecipesByIngredients
     * Tipo di ritorno: List<Ricetta>
     * Parametri richiesti: List<String>, token ( Arriverà un json, da trasformare in mappa in TS )
     */
    @PostMapping("/filterIngredientRecipes")
    public List<Ricetta> filterIngredientRecipes(@RequestHeader String token, @RequestBody Map<String, String> body) {
        if (loginService.checkLogin(token))
            return ricettaService.findRecipeByIngredients(new ArrayList<String>(body.keySet()));

        return new ArrayList<>();
    }

    /*
     * Crea Ricetta
     * Endpoint: /addRecipe
     * Tipo di ritorno: boolean
     * Parametri richiesti: token admin, Ricetta
     */@PostMapping("/addRecipe")
    public boolean addRecipe(@RequestHeader String token, @RequestBody Ricetta ricetta) {
        if (loginService.checkLoginAdmin(token))
            return ricettaService.createRecipe(ricetta);

        return false;
    }

    /*
     * PATCH:
     * Modifica Ricetta
     * Endpoint: /updateRicetta
     * Tipo di ritorno: boolean
     * Parametri richiesti: Ricetta, token admin
     */
    @PostMapping("/updateRecipe")
    public boolean updateRecipe(@RequestHeader String token, @RequestBody Ricetta ricetta) {
        if (loginService.checkLoginAdmin(token))
            return ricettaService.updateRecipe(ricetta);

        return false;
    }

    /*
     * DELETE:
     * Elimina Ricetta
     * Endpoint: /deleteRicetta
     * Tipo di ritorno: boolean
     * Parametri richiesti: token admin, id Ricetta
     * 
     */
    @DeleteMapping("/deleteRecipe")
    public boolean deleteRecipe(@RequestHeader String token, @RequestParam("id") int id) {
        if (loginService.checkLoginAdmin(token))
            return ricettaService.deleteRecipe(id);

        return false;
    }
}
