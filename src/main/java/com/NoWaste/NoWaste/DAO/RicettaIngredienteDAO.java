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
import com.NoWaste.NoWaste.entities.RicettaIngrediente;

@Service
public class RicettaIngredienteDAO implements IDAO{

    @Autowired
    private IngredienteDAO ingredienteDAO;

       @Autowired
    private ApplicationContext context;

    @Autowired
    private  DatabaseConnection database;

    @Override
    public boolean create(Entity e) {
        String query = "INSERT INTO ricette_ingredienti (id_ricetta, id_ingrediente, quantita, unita_misura) VALUES (?,?,?,?)";
        PreparedStatement ps = null;
        try {
            RicettaIngrediente ri = (RicettaIngrediente)e;
            ps = database.getConnection().prepareStatement(query);
            ps.setInt(1, ri.getIdRicetta());
            ps.setInt(2, ri.getIngrediente().getId());
            ps.setInt(3, ri.getQuantita());
            ps.setString(4, ri.getUnitaMisura());
            ps.executeUpdate();
            
        } catch (SQLException exc) {
            System.out.println("Errore nell'inserimento: " + exc.getMessage());
            return false;
        } catch (ClassCastException exc) {
            System.out.println("Errore tipo dato erraro in ricettaingredienteDAO");
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
        String query = "SELECT * FROM ricette_ingredienti";
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

                params.put("quantita", rs.getInt(4)+"");
                params.put("unitaMisura", rs.getString(5));

                RicettaIngrediente ri = context.getBean(RicettaIngrediente.class, params);
                ri.setIngrediente((Ingrediente) ingredienteDAO.readById(rs.getInt(3)));
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
    public boolean update(Entity e) {
        String query = "UPDATE ricette_ingredienti Set id_ricetta =?, id_ingrediente =?, quantita =?, unita_misura =? WHERE id =?";
        PreparedStatement ps = null;

        try {
            RicettaIngrediente ri = (RicettaIngrediente)e;
            ps = database.getConnection().prepareStatement(query);
            ps.setInt(1, ri.getIdRicetta());
            ps.setInt(2, ri.getIngrediente().getId());
            ps.setInt(3, ri.getQuantita());
            ps.setString(4, ri.getUnitaMisura());
            ps.setInt(5, ri.getId());
            
            ps.executeUpdate();
        } catch (SQLException exc) {
            System.out.println("Errore aggiornamento RicettaIngrediente" + exc.getMessage());
            return false;
        } catch (ClassCastException exc) {
            System.out.println("Errore tipo dato erraro in ricettaingredienteDAO");
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
        String query = "DELETE FROM ricette_ingredienti WHERE id=?";
        PreparedStatement ps = null;
  
          try {
              ps = database.getConnection().prepareStatement(query);
              ps.setInt(1, id);
              ps.executeUpdate();
          } catch (SQLException exc) {
              System.out.println("Errore cancellazione ricetteingredienti" + exc.getMessage());
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
        String query = "SELECT * FROM ricette_ingredienti WHERE id=?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Entity result = null;

        try {
            ps = database.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, String> params = new HashMap<>();
                params.put("id", rs.getInt(1)+"");
                params.put("id_ricetta", rs.getInt(2)+"");
                params.put("quantita", rs.getInt(4)+"");
                params.put("unitaMisura", rs.getString(5));

                result = context.getBean(RicettaIngrediente.class, params);
                ((RicettaIngrediente) result).setIngrediente((Ingrediente) ingredienteDAO.readById(rs.getInt(3)));
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

    //Creare metodo ReadByIDRicetta.
    public Map<Integer, Entity> readByIdRicetta(int id) {
        String query = "SELECT * FROM ricette_ingredienti WHERE id_ricetta = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map <Integer, Entity> result = new HashMap<>();

        try {
            ps = database.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, String> params = new HashMap<>();
                params.put("id", rs.getInt(1)+"");
                params.put("id_ricetta", rs.getInt(2)+"");

                params.put("quantita", rs.getInt(4)+"");
                params.put("unitaMisura", rs.getString(5));

                RicettaIngrediente ri = context.getBean(RicettaIngrediente.class, params);
                ri.setIngrediente((Ingrediente) ingredienteDAO.readById(rs.getInt(3)));
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

}
