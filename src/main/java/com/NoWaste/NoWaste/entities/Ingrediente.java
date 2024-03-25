package com.NoWaste.NoWaste.entities;

public class Ingrediente extends Entity {

    private String nome;
    private boolean vegano;
    private boolean vegetariano;
    private boolean senzaGlutine;


    public Ingrediente(int id, String nome, boolean vegano, boolean vegetariano, boolean senzaGlutine) {
        super(id);
        this.nome = nome;
        this.vegano = vegano;
        this.vegetariano = vegetariano;
        this.senzaGlutine = senzaGlutine;
    }


    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    public boolean isVegano() {
        return vegano;
    }


    public void setVegano(boolean vegano) {
        this.vegano = vegano;
    }


    public boolean isVegetariano() {
        return vegetariano;
    }


    public void setVegetariano(boolean vegetariano) {
        this.vegetariano = vegetariano;
    }


    public boolean isSenzaGlutine() {
        return senzaGlutine;
    }


    public void setSenzaGlutine(boolean senzaGlutine) {
        this.senzaGlutine = senzaGlutine;
    }

    

    

}
