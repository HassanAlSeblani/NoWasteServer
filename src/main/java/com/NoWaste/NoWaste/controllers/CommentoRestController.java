package com.NoWaste.NoWaste.controllers;

import java.lang.annotation.Repeatable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("api/comment")
public class CommentoRestController {

    //controllare i token!!!

    @Autowired
    private CommentoService commentoService;

    @Autowired
    private LoginService loginService;

    @GetMapping("/commentsByUser")
    public List<Commento> getCommentiByUser(@RequestHeader("token") String token, @RequestParam("userId") int userId) {
        if (token != null) {
            if (loginService.checkLogin(token)) {
                return commentoService.getCommentiByUser(userId);
            }
        }
        return null;
    }

    @GetMapping("/commentsByRecipe")
    public List<Commento> getCommentiByRecipe(@RequestHeader("token") String token,
            @RequestParam("recipeId") int recipeId) {

        if (token != null) {
            if (loginService.checkLogin(token)) {
                return commentoService.getCommentiByRecipe(recipeId);
            }
        }
        return null;
    }

    @PostMapping("/deleteComment")
    public boolean deleteCommento(@RequestHeader("token") String token, @RequestParam("commentId") int commentId) {

        if (token != null) {
            if (loginService.checkLoginAdmin(token)) {
                return commentoService.deleteCommento(commentId);
            }
        }
        return false;
    }

    @PatchMapping("/updateComment")
    public boolean updateCommento(@RequestHeader("token") String token, @RequestBody Commento commento) {

        if (token != null) {
            if (loginService.checkLoginAdmin(token)) {
                return commentoService.updateCommento(commento);
            }
        }
        return false;
    }

    @GetMapping("/allComments")
    public List<Commento> getAllCommenti(@RequestHeader("token") String token) {

            if (loginService.checkLoginAdmin(token)) {
                return commentoService.getAllCommenti();
            }
        return null;
    }

    @PostMapping("/addComment")
    public ResponseEntity<Boolean> createComment(@RequestHeader("token") String token, @RequestBody Commento commento)
    {
        if (loginService.checkLoginAdmin(token)) {
            return commentoService.createCommento(commento);
        }
        else
        {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
