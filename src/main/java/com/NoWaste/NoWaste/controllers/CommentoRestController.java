package com.NoWaste.NoWaste.controllers;

import java.util.List;

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

import com.NoWaste.NoWaste.entities.Commento;
import com.NoWaste.NoWaste.services.CommentoService;
import com.NoWaste.NoWaste.services.LoginService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/commenti")
public class CommentoRestController {

    //controllare i token!!!

    @Autowired
    private CommentoService commentoService;

    @Autowired
    private LoginService loginService;

    @GetMapping("/commentsByUser")
    public List<Commento> getCommentiByUser(@RequestHeader("token") String token, @RequestParam("userId") int userId) {
        if (token != null) {
            if (loginService.checkLoginUtente(token)) {
                return commentoService.getCommentiByUser(userId);
            }
        }
        return null;
    }

    @GetMapping("/commentsByRecipe")
    public List<Commento> getCommentiByRecipe(@RequestHeader("token") String token,
            @RequestParam("recipeId") int recipeId) {

        if (token != null) {
            if (loginService.checkLoginUtente(token)) {
                return commentoService.getCommentiByRecipe(recipeId);
            }
        }
        return null;
    }

    @PostMapping("/deleteComment")
    public boolean deleteCommento(@RequestHeader("token") String token, @RequestParam("commentId") int commentId) {

        if (token != null) {
            if (!token.equalsIgnoreCase("") && !token.split("-")[0].equalsIgnoreCase("NONE")) {
                return commentoService.deleteCommento(commentId);
            }
        }
        return false;
    }

    @PostMapping("/updateComment")
    public boolean updateCommento(@RequestHeader("token") String token, @RequestBody Commento commento) {

        if (token != null) {
            if (!token.equalsIgnoreCase("") && !token.split("-")[0].equalsIgnoreCase("NONE")) {
                return commentoService.updateCommento(commento);
            }
        }
        return false;
    }

    @GetMapping("/allComments")
    public List<Commento> getAllCommenti(@RequestHeader("token") String token) {

        if (token != null) {
            if (!token.equalsIgnoreCase("") && !token.split("-")[0].equalsIgnoreCase("NONE")) {
                return commentoService.getAllCommenti();
            }
        }
        return null;
    }
}
