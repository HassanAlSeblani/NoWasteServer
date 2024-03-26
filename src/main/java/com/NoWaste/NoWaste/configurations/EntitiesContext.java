package com.NoWaste.NoWaste.configurations;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.NoWaste.NoWaste.entities.Commento;
import com.NoWaste.NoWaste.entities.Ingrediente;
import com.NoWaste.NoWaste.entities.Ricetta;
import com.NoWaste.NoWaste.entities.RicettaIngrediente;
import com.NoWaste.NoWaste.entities.Utente;

@Configuration
public class EntitiesContext {
    @Bean
    @Scope("prototype")
    public Ricetta newRicetta(Map<String, String> params) {
        return new Ricetta(
                Integer.parseInt(params.get("id")),
                params.get("nome"),
                params.get("istruzioni"),
                params.get("portata"),
                Integer.parseInt(params.get("difficolta")),
                Integer.parseInt(params.get("tempoPreparazione")),
                Integer.parseInt(params.get("serving")),
                params.get("linkImmagine"));
    }

    @Bean
    @Scope("prototype")
    public Utente newUtente(Map<String, String> params) {
        return new Utente(
                Integer.parseInt(params.get("id")),
                params.get("nome"),
                params.get("cognome"),
                params.get("user"),
                params.get("password"),
                params.get("ruolo"));
    }

    @Bean
    @Scope("prototype")
    public Commento newCommento(Map<String, String> params) {
        return new Commento(
                Integer.parseInt(params.get("id")),
                Integer.parseInt(params.get("punteggio")),
                params.get("commento"),
                Integer.parseInt(params.get("id_ricetta")));
    }

    @Bean
    @Scope("prototype")
    public RicettaIngrediente newRicettaIngrediente(Map<String, String> params) {
        return new RicettaIngrediente(
                Integer.parseInt(params.get("id")),
                Integer.parseInt(params.get("quantita")),
                params.get("unitaMisura"),
                Integer.parseInt(params.get("id_ricetta")));
    }
    @Bean
    @Scope("prototype")
    public Ingrediente newIngrediente(Map<String, String> params) {
        return new Ingrediente(
                Integer.parseInt(params.get("id")),
                params.get("nome"),
                Boolean.parseBoolean(params.get("vegano")),
                Boolean.parseBoolean(params.get("vegetariano")),
                Boolean.parseBoolean(params.get("senzaGlutine")));
    }

}
