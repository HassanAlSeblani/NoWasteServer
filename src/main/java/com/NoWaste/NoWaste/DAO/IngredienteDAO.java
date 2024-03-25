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
import com.NoWaste.NoWaste.entities.Ingrediente;



@Service
public class IngredienteDAO implements IDAO{
    @Autowired
    private ApplicationContext context;

    @Autowired
    private  DatabaseConnection database;

    @Override
    public void create(Entity e) {
       String query = "Insert INTO ingredienti (nome, senza_glutine, vegano, vegetariano) VALUES (?,?,?,?)";
       PreparedStatement ps = null;
       try {
        Ingrediente i = (Ingrediente)e;
        ps = database.getConnection().prepareStatement(query);
        ps.setString(1, i.getNome());
        ps.setBoolean(2, i.isSenzaGlutine());
        ps.setBoolean(3, i.isVegano());
        ps.setBoolean(4, i.isVegetariano());
        ps.executeUpdate();
       } catch (SQLException exc) {
        System.out.println("Errore nell'inserimento ingrediente: " + exc.getMessage());
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
        String query = "SELECT * FROM ingredienti";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map <Integer, Entity> result = new HashMap<>();

        try {
            ps = database.getConnection().prepareStatement(query);
            rs = ps.executeQuery();

            while(rs.next())    {
                Map<String, String> params = new HashMap<>();
                params.put("id", rs.getInt(1)+"");
                params.put("nome", rs.getString(2));
                params.put("senza_glutine", rs.getBoolean(3)+"");
                params.put("vegano", rs.getBoolean(4)+"");
                params.put("vegetariano", rs.getBoolean(5)+"");

                Ingrediente i = context.getBean(Ingrediente.class, params);
                result.put(i.getId(), i);
            }
        } catch (SQLException exc) {
            System.out.println("Errore nella select in InregdienteDAO");
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
        String query = "UPDATE ingredienti SET nome =?, senza_glutine =?, vegano =?, vegetariano =? WHERE id =?";
        PreparedStatement ps = null;

        try {
            Ingrediente i = (Ingrediente)e;
            ps = database.getConnection().prepareStatement(query);
            ps.setString(1, i.getNome());
            ps.setBoolean(2, i.isSenzaGlutine());
            ps.setBoolean(3, i.isVegano());
            ps.setBoolean(4, i.isVegetariano());
            ps.setInt(5, i.getId());
            ps.executeUpdate();
        } catch (SQLException exc) {
           System.out.println("Errore aggionamento Ingrediente" + exc.getMessage());
        } catch (ClassCastException exc) {
            System.out.println("Errore tipo dato errato in IngredienteDAO");
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
        String query = "DELETE FROM ingredienti WHERE id=?";
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
