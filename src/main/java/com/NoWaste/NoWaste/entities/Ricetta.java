package com.NoWaste.NoWaste.entities;

import java.util.List;

public class Ricetta extends Entity {

    private String nome;
    private String istruzioni;
    private String portata;
    private int difficolta;
    private int tempoPreparazione;
    private int serving;
    private String linkImmagine;
    private List<RicettaIngrediente> ingrediente;
    private List<Commento> commenti;

    public Ricetta(int id, String nome, String istruzioni, String portata, int difficolta, int tempoPreparazione,
            int serving, String linkImmagine) {
        super(id);
        this.nome = nome;
        this.istruzioni = istruzioni;
        this.portata = portata;
        this.difficolta = difficolta;
        this.tempoPreparazione = tempoPreparazione;
        this.serving = serving;
        this.linkImmagine = linkImmagine;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIstruzioni() {
        return istruzioni;
    }

    public void setIstruzioni(String istruzioni) {
        this.istruzioni = istruzioni;
    }

    public String getPortata() {
        return portata;
    }

    public void setPortata(String portata) {
        this.portata = portata;
    }

    public int getDifficolta() {
        return difficolta;
    }

    public void setDifficolta(int difficolta) {
        if (difficolta > 0 && difficolta < 4) {
            this.difficolta = difficolta;
        } else {
            System.out.println("La difficoltà del piatto va da 1 a 3");
        }
    }

    public int getTempoPreparazione() {
        return tempoPreparazione;
    }

    public void setTempoPreparazione(int tempoPreparazione) {
        this.tempoPreparazione = tempoPreparazione;
    }

    public int getServing() {
        return serving;
    }

    public void setServing(int serving) {
        this.serving = serving;
    }

    public String getLinkImmagine() {
        return linkImmagine;
    }

    public void setLinkImmagine(String linkImmagine) {
        this.linkImmagine = linkImmagine;
    }

    public List<RicettaIngrediente> getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(List<RicettaIngrediente> ingrediente) {
        this.ingrediente = ingrediente;
    }

    public List<Commento> getCommenti() {
        return commenti;
    }

    public void setCommenti(List<Commento> commenti) {
        this.commenti = commenti;
    }

}
