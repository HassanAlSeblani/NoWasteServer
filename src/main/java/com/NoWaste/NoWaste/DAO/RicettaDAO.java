package com.NoWaste.NoWaste.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.NoWaste.NoWaste.entities.Entity;
import com.NoWaste.NoWaste.entities.Ricetta;

@Service
public class RicettaDAO implements IDAO {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private  DatabaseConnection database;

    @Override
    public void create(Entity e) {
        String query = "INSERT INTO ricette (Nome, Istruzioni, Portata, Difficolta, Tempo_preparazione, Serving) VALUES (?,?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            Ricetta r = (Ricetta)e;
            ps = database.getConnection().prepareStatement(query);
            ps.setString(1, r.getNome());
            ps.setString(2, r.getIstruzuoni());
            ps.setString(3, r.getPortata());
            ps.setInt(4, r.getDifficolta());
            ps.setInt(5, r.getTempoPreparazione());
            ps.setInt(6, r.getServing());
            ps.executeUpdate();

        } catch (SQLException exc) {
           System.out.println("Errore inserimento ricetta");
        } catch (ClassCastException exc) {
            System.out.println("Errore tipo dato erraro in ricettaDAO");
        } finally {
            try {
                ps.close();
            } catch (Exception exc) {
             System.out.println("Errore chiusura prepared Statement");
            }
        }
    }

    @Override
    public Map<Integer, Entity> read() {
        String query = "SELECT * FROM ricette";
        PreparedStatement ps = null;
        ResultSet rs = null;

        Map <Integer, Entity> result = new HashMap<>();

        try {
            ps = database.getConnection().prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, String> params = new HashMap<>();
                params.put("id", rs.getInt(1)+"");
                params.put("nome", rs.getString(2));
                params.put("istruzuoni", rs.getString(3));
                params.put("portata", rs.getString(4));
                params.put("difficolta", rs.getInt(5)+"");
                params.put("tempoPreparazione", rs.getInt(6)+"");
                params.put("serving", rs.getInt(7)+"");
                
                Ricetta r = context.getBean(Ricetta.class, params);
                result.put(r.getId(), r);
            }           
        } catch (SQLException exc) {
            System.out.println("Errore nella select in ricettaDAO");
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
    public void update(Entity e) {
        String query = "UPDATE ricetta Set nome =?, istruzioni =?, Portata=?, Difficolta=?, tempo_preparazione=?, serving=? WHERE id=?";
        PreparedStatement ps = null;
        try {
            Ricetta r = (Ricetta)e;
            ps = database.getConnection().prepareStatement(query);
            ps.setString(1, r.getNome());
            ps.setString(2, r.getIstruzuoni());
            ps.setString(3, r.getPortata());
            ps.setInt(4, r.getDifficolta());
            ps.setInt(5, r.getTempoPreparazione());
            ps.setInt(6, r.getServing());
            ps.setInt(7, r.getId());    
            ps.executeUpdate();
        } catch (SQLException exc) {
            System.out.println("Eroore aggiornamento ricetta" + exc.getMessage());
        } catch (ClassCastException exc) {
            System.out.println("Errore tipo dato erraro in ricettaDAO");
        } finally {
            try {
                ps.close();
            } catch (Exception exc) {
                System.out.println("Errore chiusura prepared Statement");
            }
        }
    }

    @Override
    public void delete(int id) {
      String query = "DELETE FROM ricette WHERE id=?";
      PreparedStatement ps = null;

        try {
            ps = database.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException exc) {
            System.out.println("Errore cancellazione ricetta" + exc.getMessage());
        } finally {
            try {
            ps.close();
            } catch (Exception exc) {
            System.out.println("Errore chiusura prepared Statement");
        }
        }    
    }
}
