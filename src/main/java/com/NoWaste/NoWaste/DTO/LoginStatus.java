package com.NoWaste.NoWaste.DTO;

public class LoginStatus {

    private String token;
    
    public void setToken(String ruolo, int idUtente) {
        token = ruolo + "-"  + idUtente;
    }
    public String getRuolo(){
        return token.split("-")[0];
    }

    public int getIdUtente(){
        return Integer.parseInt(token.split("-")[1]);
    }

    public String getToken(){
        return token;
    }


    
    
}
