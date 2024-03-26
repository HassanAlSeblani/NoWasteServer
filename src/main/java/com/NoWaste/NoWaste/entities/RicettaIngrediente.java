package com.NoWaste.NoWaste.entities;

public class RicettaIngrediente extends Entity {
    
    private Ingrediente ingrediente;
    private int quantita;
    private String unitaMisura;
    private int idRicetta;


    public RicettaIngrediente(int id, int quantita, String unitaMisura, int idRicetta) {
        super(id);
       
        this.quantita = quantita;
        this.unitaMisura = unitaMisura;
        this.idRicetta = idRicetta;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }
    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }
    public int getQuantita() {
        return quantita;
    }
    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }
    public String getUnitaMisura() {
        return unitaMisura;
    }
    public void setUnitaMisura(String unitaMisura) {
        this.unitaMisura = unitaMisura;
    }
    public int getIdRicetta() {
        return idRicetta;
    }
    public void setIdRicetta(int idRicetta) {
        this.idRicetta = idRicetta;
    }
    
}
