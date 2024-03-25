package com.NoWaste.NoWaste.entities;

public class Utente extends Entity{
    private String nome; 
    private String cognome;
    private String user;
    private String password;
    private String ruolo;

    public Utente(int id, String nome, String cognome, String user, String password, String ruolo) {
        super(id);
        this.nome = nome;
        this.cognome = cognome;
        this.user = user;
        this.password = password;
        this.ruolo = ruolo;
    }

    public Utente(int id) {
        super(id);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    } 

    
    
}
