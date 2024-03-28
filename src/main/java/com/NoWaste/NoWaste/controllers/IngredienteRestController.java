package com.NoWaste.NoWaste.controllers;

import java.util.ArrayList;
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

import com.NoWaste.NoWaste.entities.Ingrediente;
import com.NoWaste.NoWaste.services.IngredienteService;
import com.NoWaste.NoWaste.services.LoginService;


@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("api/ingredients") 
public class IngredienteRestController {

    @Autowired
    private IngredienteService ingredienteService;
    
    @Autowired
    private LoginService loginService;

    /*
     * GET
     * 
     * Tutte gli ingredienti
     * Endpoint: /allIngredients
     * Tipo di ritorno: List<Ingrediente>
     * Parametri richiesti: token
     */

@GetMapping("/allIngredients")
    public List<Ingrediente> allIngredients(@RequestHeader String token) {
        if (loginService.checkLogin(token))
            return ingredienteService.findAllIngredients();

            return new ArrayList<>();
}

/*
 * Ingrediente singolo by Id
 * Endpoint: /ingredientsById
 * Tipo di ritorno: Ingrediente
 * Parametri richiesti: id Ingrediente, token
 */

@GetMapping("/ingredientById")
    public Ingrediente ingredientById(@RequestHeader String token, @RequestParam("id") int id) {

    if (loginService.checkLogin(token))
        return ingredienteService.findIngredientById(id);

        return null;
}

   /*
     * DA FRONT END
     * POST:
     * Tutte gli ingredienti by nome, senza_glutine, vegano, vegetariano
     * Endpoint: /filterIngredients
     * Tipo di ritorno: List<Ingrediente>
     * Parametri richiesti: Map<String,String>, token
     * 
     */


     /*
     * Crea Ingrediente
     * Endpoint: /addIngrediente
     * Tipo di ritorno: boolean
     * Parametri richiesti: token admin, Ingrediente
     *  */

     @PostMapping("/addIngredient")
     public boolean addIngredient(@RequestHeader String token, @RequestBody Ingrediente ingrediente) {
         if (loginService.checkLoginAdmin(token))
             return ingredienteService.createIngredient(ingrediente);

             return false;
     }

     /*
      * PATCH:
      * Modifica Ingrediente
      * Endpoint: /updateIngredient
      * Tipo di ritorno: boolean
      * Parametri richiesti: Ingrediente, token admin
      */

      @PatchMapping("/updateIngredient")
      public boolean updateIngredient(@RequestHeader String token, @RequestBody Ingrediente ingrediente) {
          if (loginService.checkLoginAdmin(token))
              return ingredienteService.updateIngredient(ingrediente);

              return false;
      }


      /*
       * DELETE:
       * Elimina Ingrediente
       * Endpoint: /deleteIngrediente
       * Tipo di ritorno: boolean
       * Parametri richiesti: token admin, id Ingrediente
       * 
       */

       @DeleteMapping("/deleteIngredient")
       public boolean deleteIngredient(@RequestHeader String token, @RequestParam("id") int id) {
           if (loginService.checkLoginAdmin(token))
               return ingredienteService.deleteIngredient(id);

               return false;
       }




}


