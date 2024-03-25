package com.NoWaste.NoWaste.entities;

public class Commento extends Entity {
    private int punteggio;
    private String commento;
    private Utente utente;
    private int idRicetta;


    
    public Commento(int id, int punteggio, String commento, Utente utente, int idRicetta) { 
        super(id);
        this.punteggio = punteggio;
        this.commento = commento;
        this.utente = utente;
        this.idRicetta = idRicetta;
       
    }


    public int getPunteggio() {
        return punteggio;
    }


    public void setPunteggio(int punteggio) {
        if (punteggio > 0 && punteggio < 6){
        this.punteggio = punteggio;
        } else {
            System.out.println("Il punteggio va da 1 a 5");
        }
    }


    public String getCommento() {
        return commento;
    }


    public void setCommento(String commento) {
        this.commento = commento;
    }


    public Utente getUtente() {
        return utente;
    }


    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public int getIdRicetta() {
        return idRicetta;
    }
    public void setIdRicetta(int idRicetta) {
        this.idRicetta = idRicetta;
    }
    

    

}
