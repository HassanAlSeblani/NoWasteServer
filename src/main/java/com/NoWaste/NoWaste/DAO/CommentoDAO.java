package com.NoWaste.NoWaste.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.NoWaste.NoWaste.entities.Commento;
import com.NoWaste.NoWaste.entities.Entity;
import com.NoWaste.NoWaste.entities.Utente;

@Service
public class CommentoDAO implements IDAO{

      @Autowired
    private ApplicationContext context;

    @Autowired
    private  DatabaseConnection database;

    @Autowired
    UtenteDAO utenteDAO;

    @Override
    public boolean create(Entity e) {
        String query = "INSERT INTO commenti (punteggio, commento, id_utente, id_ricetta) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;

        try {
            Commento c = (Commento)e;
            ps = database.getConnection().prepareStatement(query);
            ps.setInt(1, c.getPunteggio());
            ps.setString(2, c.getCommento());
            ps.setInt(3, c.getUtente().getId());
            ps.setInt(4, c.getIdRicetta());
    
            ps.executeUpdate();
            
        } catch (SQLException exc) {
          System.out.println("Errore inserimento commento");
          return false;
        } catch (ClassCastException exc) {
            System.out.println("Errore tipo dato erraro in commentoDAO");
            return false;
        } finally {
            try {
                ps.close();
            } catch (Exception exc) {
             System.out.println("Errore chiusura prepared Statement");
             
            }
        }
        return true;


    }

    @Override
    public Map<Integer, Entity> read() {
        String query = "SELECT * FROM commenti";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map <Integer, Entity> result = new HashMap<>();

        try {
            ps = database.getConnection().prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, String> params = new HashMap<>();
                params.put("id", rs.getInt(1)+"");
                params.put("id_utente", rs.getInt(2)+"");
                params.put("id_ricetta", rs.getInt(3)+"");
                params.put("punteggio", rs.getInt(4)+"");
                params.put("commento", rs.getString(5));
                
                Commento c = context.getBean(Commento.class, params);
                result.put(c.getId(), c);
                Utente utente = (Utente)utenteDAO.readById(Integer.parseInt(params.get("id_utente")));
                c.setUtente(utente);
            }
        } catch (SQLException exc) {
            System.out.println("Errore nella select in commentoDAO");
        } finally {
            try {
                ps.close();
                rs.close();
            } catch (Exception exc) {
                System.out.println("Errore chiusura prepared Statement");
            }
        }
        return result;
    }

    @Override
    public boolean update(Entity e) {
        String query = "UPDATE commenti SET punteggio=?, commento=?, id_utente=?, id_ricette=?";
        PreparedStatement ps = null;
        try{
            Commento c = (Commento)e;
            ps = database.getConnection().prepareStatement(query);
            ps.setInt(1, c.getPunteggio());
            ps.setString(2, c.getCommento());
            ps.setInt(3, c.getUtente().getId());
            ps.setInt(4, c.getIdRicetta());
            ps.executeUpdate();
        } catch (SQLException exc) {
            System.out.println("Errore aggiornamento commento" + exc.getMessage());
            return false;
        } catch (ClassCastException exc) {
            System.out.println("Errore tipo dato erraro in commentoDAO");
            return false;
        } finally {
            try {
                ps.close();
            } catch (Exception exc) {
                System.out.println("Errore chiusura prepared Statement");
            }
        }
        return true;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM commenti WHERE id=?";
      PreparedStatement ps = null;

        try {
            ps = database.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException exc) {
            System.out.println("Errore cancellazione commento" + exc.getMessage());
            return false;
        } finally {
            try {
            ps.close();
            } catch (Exception exc) {
            System.out.println("Errore chiusura prepared Statement");           
        }
        }   
        return true; 
    }

    @Override
    public Entity readById(int id) {
        String query = "SELECT * FROM commenti WHERE id=?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Commento c = null;

        try {
          
            ps = database.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
           

            while (rs.next()) {
                Map<String, String> params = new HashMap<>();
                params.put("id", rs.getInt(1)+"");
                params.put("id_utente", rs.getInt(2)+"");
                params.put("id_ricetta", rs.getInt(3)+"");
                params.put("punteggio", rs.getInt(4)+"");
                params.put("commento", rs.getString(5));
                
                c = context.getBean(Commento.class, params);
                Utente utente = (Utente)utenteDAO.readById(Integer.parseInt(params.get("id_utente")));
                c.setUtente(utente);
            }
        } catch (SQLException exc) {
            System.out.println("Errore nella select in commentoDAO");
        } finally {
            try {
                ps.close();
                rs.close();
            } catch (Exception exc) {
                System.out.println("Errore chiusura prepared Statement");
            }
        }
        return (Entity)c;
    }

    //-----

    public List<Commento> getCommentoByUser(int userId){
        String query = "SELECT * FROM commenti WHERE id_utente = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Commento> commenti = new ArrayList<>();

        try {
            ps = database.getConnection().prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while(rs.next()){
                Map<String, String> params = new HashMap<>();
                params.put("id", rs.getInt(1)+"");
                params.put("id_utente", rs.getInt(2)+"");
                params.put("id_ricetta", rs.getInt(3)+"");
                params.put("punteggio", rs.getInt(4)+"");
                params.put("commento", rs.getString(5));
                
                Commento c = context.getBean(Commento.class, params);
                Utente utente = (Utente)utenteDAO.readById(userId);
                c.setUtente(utente);
                commenti.add(c);
            }

        } catch (SQLException exc) {
            System.out.println("Errore nella query per i commenti dell'utente: " + exc.getMessage());

        }finally{
            try {
                ps.close();
                rs.close();
            } catch (Exception exc) {
                System.out.println("Errore chiusura prepared Statement");
            }
        }
        return commenti;
    }



    public List<Commento> getCommentoByRecipe(int recipeId){

        String query = "SELECT * FROM commenti WHERE id_ricetta = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Commento> commenti = new ArrayList<>();
        try {
            ps = database.getConnection().prepareStatement(query);
            ps.setInt(1, recipeId);
            rs = ps.executeQuery();

            while(rs.next()){
                Map<String, String> params = new HashMap<>();
                params.put("id", rs.getInt(1)+"");
                params.put("id_utente", rs.getInt(2)+"");
                params.put("id_ricetta", rs.getInt(3)+"");
                params.put("punteggio", rs.getInt(4)+"");
                params.put("commento", rs.getString(5));
                
                Commento c = context.getBean(Commento.class, params);
                Utente utente = (Utente)utenteDAO.readById(Integer.parseInt(params.get("id_utente")));
                c.setUtente(utente);
                commenti.add(c);
        }

        }catch (SQLException exc){
            System.out.println("Errore nella query per i commenti della ricetta: " + exc.getMessage());

        }finally{
            try {
                ps.close();
                rs.close();
            } catch (Exception exc) {
                System.out.println("Errore chiusura prepared Statement");
            }
        }
        return commenti;
    }

}
    

