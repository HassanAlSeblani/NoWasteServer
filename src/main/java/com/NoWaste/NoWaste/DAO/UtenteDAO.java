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
import com.NoWaste.NoWaste.entities.Utente;

@Service
public class UtenteDAO implements IDAO{

       @Autowired
    private ApplicationContext context;

    @Autowired
    private  DatabaseConnection database;

    @Override
    public boolean create(Entity e) {
        String query = "INSERT INTO Utente (nome, cognome, user, `password`, ruolo) VALUES (?,?,?,?,?)";
        PreparedStatement ps = null;

        try {
            Utente u = (Utente)e;
            ps = database.getConnection().prepareStatement(query);
            ps.setString(1, u.getNome());
            ps.setString(2, u.getCognome());
            ps.setString(3, u.getUser());
            ps.setString(4, u.getPassword());
            ps.setString(5, u.getRuolo());
            ps.executeUpdate();

        } catch (SQLException exc) {
            System.out.println("Errore inserimento utente: " + exc.getMessage());
            return false;
        } catch (ClassCastException exc) {
            System.out.println("Errore tipo dato errato in utenteDAO");
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
        String query = "SELECT * FROM Utente";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map <Integer, Entity> result = new HashMap<>();

        try {
            ps = database.getConnection().prepareStatement(query);
            rs = ps.executeQuery();

            while(rs.next()) {
            Map<String, String> params = new HashMap<>();
            params.put("id", rs.getInt(1)+"");
            params.put("nome", rs.getString(2));
            params.put("cognome", rs.getString(3));
            params.put("user", rs.getString(4));
            params.put("password", rs.getString(5));
            params.put("ruolo", rs.getString(6));

            Utente u = context.getBean(Utente.class, params);
            result.put(u.getId(), u);
            }

        } catch (SQLException exc) {
            System.out.println("Errore nella select in UtenteDAO" + exc.getMessage());
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
        String query = "UPDATE Utente SET nome =?, cognome =?, user =?, `password` =?, ruolo =? WHERE id =?";
        PreparedStatement ps = null;

        try {
            Utente u = (Utente)e;
            ps = database.getConnection().prepareStatement(query);
            ps.setString(1, u.getNome());
            ps.setString(2, u.getCognome());
            ps.setString(3, u.getUser());
            ps.setString(4, u.getPassword());
            ps.setString(5, u.getRuolo());
            ps.setInt(6, u.getId());
            ps.executeUpdate();
        } catch (SQLException exc) {
           System.out.println("Errore aggiornamento Utente");
           return false;
        } catch (ClassCastException exc) {
            System.out.println("Errore tipo di dato errato in utenteDAO");
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
        String query = "DELETE FROM Utente WHERE id =?";
        PreparedStatement ps = null;

        try {
            ps = database.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException exc) {
            System.out.println("Errore eliminazione Utente: " + exc.getMessage());
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
        String query = "SELECT * FROM Utente WHERE id =?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Entity result =  null;

        try {
            ps = database.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while(rs.next()) {
            Map<String, String> params = new HashMap<>();
            params.put("id", rs.getInt(1)+"");
            params.put("nome", rs.getString(2));
            params.put("cognome", rs.getString(3));
            params.put("user", rs.getString(4));
            params.put("password", rs.getString(5));
            params.put("ruolo", rs.getString(6));

            result = context.getBean(Utente.class, params);
            }

        } catch (SQLException exc) {
            System.out.println("Errore nella select in UtenteDAO" + exc.getMessage());
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

    public Entity readFromUsernameAndPassword (String username, String password) {
        String query = "SELECT * FROM utenti WHERE username LIKE ? AND `password` LIKE ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Entity e = null;
        try {
           
            ps = database.getConnection().prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();   
            if (rs.next()) {
                Map <String, String> params = new HashMap<>();
                params.put("username", rs.getString(1));
                params.put("password", rs.getString(2));

                e = context.getBean(Utente.class, params);
            }       
        } catch (SQLException exc) {
            System.out.println("Errore nella select in UtenteDAO" + exc.getMessage());
        }
        return e;
    }
}
