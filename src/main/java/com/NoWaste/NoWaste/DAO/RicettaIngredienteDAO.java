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
import com.NoWaste.NoWaste.entities.RicettaIngrediente;

@Service
public class RicettaIngredienteDAO implements IDAO{

       @Autowired
    private ApplicationContext context;

    @Autowired
    private  DatabaseConnection database;

    @Override
    public void create(Entity e) {
        String query = "INSERT INTO ricettaingrediente (id_ricetta, id_ingrediente, quantita) VALUES (?,?,?)";
        PreparedStatement ps = null;
        try {
            RicettaIngrediente ri = (RicettaIngrediente)e;
            ps = database.getConnection().prepareStatement(query);
            ps.setInt(1, ri.getIdRicetta());
            ps.setInt(2, ri.getIngrediente().getId());
            ps.setInt(3, ri.getQuantita());
            ps.executeUpdate();
            
        } catch (SQLException exc) {
            System.out.println("Errore nell'inserimento: " + exc.getMessage());
        } catch (ClassCastException exc) {
            System.out.println("Errore tipo dato erraro in ricettaingredienteDAO");
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
        String query = "SELECT * FROM ricettaingrediente";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map <Integer, Entity> result = new HashMap<>();

        try {
            ps = database.getConnection().prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, String> params = new HashMap<>();
                params.put("id", rs.getInt(1)+"");
                params.put("id_ricetta", rs.getInt(2)+"");
                params.put("id_ingrediente", rs.getInt(3)+"");
                params.put("quantita", rs.getInt(4)+"");

                RicettaIngrediente ri = context.getBean(RicettaIngrediente.class, params);
                result.put(ri.getId(), ri);
            }
            
        } catch (SQLException exc) {
            System.out.println("Errore nella select in ricetteingredienteDAO");
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
        String query = "UPDATE ricettaingrediente Set id_ricetta =?, id_ingrediente =?, quantita =? WHERE id =?";
        PreparedStatement ps = null;

        try {
            RicettaIngrediente ri = (RicettaIngrediente)e;
            ps = database.getConnection().prepareStatement(query);
            ps.setInt(1, ri.getIdRicetta());
            ps.setInt(2, ri.getIngrediente().getId());
            ps.setInt(3, ri.getQuantita());
            ps.setInt(4, ri.getId());
            ps.executeUpdate();
        } catch (SQLException exc) {
            System.out.println("Errore aggiornamento RicettaIngrediente" + exc.getMessage());
        } catch (ClassCastException exc) {
            System.out.println("Errore tipo dato erraro in ricettaingredienteDAO");
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
        String query = "DELETE FROM ricetteingredienti WHERE id=?";
        PreparedStatement ps = null;
  
          try {
              ps = database.getConnection().prepareStatement(query);
              ps.setInt(1, id);
              ps.executeUpdate();
          } catch (SQLException exc) {
              System.out.println("Errore cancellazione ricetteingredienti" + exc.getMessage());
          } finally {
              try {
              ps.close();
              } catch (Exception exc) {
              System.out.println("Errore chiusura prepared Statement");
          }
          }    
    }
    
}
