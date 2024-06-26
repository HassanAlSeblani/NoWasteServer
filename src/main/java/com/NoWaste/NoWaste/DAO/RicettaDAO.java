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
import com.NoWaste.NoWaste.entities.Ricetta;
import com.NoWaste.NoWaste.entities.RicettaIngrediente;

@Service
public class RicettaDAO implements IDAO {



    @Autowired
    private RicettaIngredienteDAO ricettaIngredienteDAO;

    @Autowired
    private CommentoDAO commentoDAO;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private DatabaseConnection database;

    @Override
    public boolean create(Entity e) {
        String query = "INSERT INTO ricette (Nome, Istruzioni, Portata, Difficolta, Tempo_preparazione, Serving, link_immagine) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            Ricetta r = (Ricetta) e;
            ps = database.getConnection().prepareStatement(query);
            ps.setString(1, r.getNome());
            ps.setString(2, r.getIstruzioni());
            ps.setString(3, r.getPortata());
            ps.setInt(4, r.getDifficolta());
            ps.setInt(5, r.getTempoPreparazione());
            ps.setInt(6, r.getServing());
            ps.setString(7, r.getLinkImmagine());
            ps.executeUpdate();

        } catch (SQLException exc) {
            System.err.println("Errore inserimento ricetta" + exc.getMessage());
            return false;
        } catch (ClassCastException exc) {
            System.err.println("Errore tipo dato erraro in ricettaDAO");
            return false;
        } finally {
            try {
                ps.close();
            } catch (Exception exc) {
                System.err.println("Errore chiusura prepared Statement");
            }
        }
        return true;
    }

    @Override
    public Map<Integer, Entity> read() {
        String query = "SELECT * FROM ricette";
        PreparedStatement ps = null;
        ResultSet rs = null;

        Map<Integer, Entity> result = new HashMap<>();

        try {
            ps = database.getConnection().prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, String> params = new HashMap<>();
                params.put("id", rs.getInt(1) + "");
                params.put("nome", rs.getString(2));
                params.put("istruzioni", rs.getString(3));
                params.put("portata", rs.getString(4));
                params.put("difficolta", rs.getInt(5) + "");
                params.put("tempoPreparazione", rs.getInt(6) + "");
                params.put("serving", rs.getInt(7) + "");
                params.put("linkImmagine", rs.getString(8));

                Ricetta r = context.getBean(Ricetta.class, params);

                List<RicettaIngrediente> ingredienti = new ArrayList<>();
                for (Entity ricettaIngrediente : ricettaIngredienteDAO.readByIdRicetta(r.getId()).values())
                    ingredienti.add((RicettaIngrediente) ricettaIngrediente);
                r.setIngredienti(ingredienti);

                List<Commento> commenti = commentoDAO.getCommentoByRecipe(Integer.parseInt(params.get("id")));
                r.setCommenti(commenti);

                result.put(r.getId(), r);
            }
        } catch (SQLException exc) {
            System.err.println("Errore nella select in ricettaDAO");
        } finally {
            try {
                ps.close();
                rs.close();
            } catch (Exception exc) {
                System.err.println("Errore chiusura prepared Statement");
            }
        }
        return result;
    }

    @Override
    public boolean update(Entity e) {
        String query = "UPDATE ricette Set nome =?, istruzioni =?, Portata=?, Difficolta=?, tempo_preparazione=?, serving=?, link_immagine =? WHERE id=?";
        PreparedStatement ps = null;
        try {
            Ricetta r = (Ricetta) e;
            ps = database.getConnection().prepareStatement(query);
            ps.setString(1, r.getNome());
            ps.setString(2, r.getIstruzioni());
            ps.setString(3, r.getPortata());
            ps.setInt(4, r.getDifficolta());
            ps.setInt(5, r.getTempoPreparazione());
            ps.setInt(6, r.getServing());
            ps.setString(7, r.getLinkImmagine());
            ps.setInt(8, r.getId());
            ps.executeUpdate();
        } catch (SQLException exc) {
            System.err.println("Eroore aggiornamento ricetta" + exc.getMessage());
            return false;
        } catch (ClassCastException exc) {
            System.err.println("Errore tipo dato erraro in ricettaDAO");
            return false;
        } finally {
            try {
                ps.close();
            } catch (Exception exc) {
                System.err.println("Errore chiusura prepared Statement");
            }
        }
        return true;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM ricette WHERE id=?";
        PreparedStatement ps = null;

        try {
            ps = database.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException exc) {
            System.err.println("Errore cancellazione ricetta" + exc.getMessage());
            return false;
        } finally {
            try {
                ps.close();
            } catch (Exception exc) {
                System.err.println("Errore chiusura prepared Statement");
            }
        }
        return true;
    }

    @Override
    public Entity readById(int id) {
        String query = "SELECT * FROM ricette WHERE id=?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        Entity result = null;

        try {
            ps = database.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, String> params = new HashMap<>();
                params.put("id", rs.getInt(1) + "");
                params.put("nome", rs.getString(2));
                params.put("istruzioni", rs.getString(3));
                params.put("portata", rs.getString(4));
                params.put("difficolta", rs.getInt(5) + "");
                params.put("tempoPreparazione", rs.getInt(6) + "");
                params.put("serving", rs.getInt(7) + "");
                params.put("linkImmagine", rs.getString(8));

                result = context.getBean(Ricetta.class, params);

                List<RicettaIngrediente> ingredienti = new ArrayList<>();
                for (Entity ricettaIngrediente : ricettaIngredienteDAO.readByIdRicetta(id).values())
                    ingredienti.add((RicettaIngrediente) ricettaIngrediente);
                    ((Ricetta) result).setIngredienti(ingredienti);

                List<Commento> commenti = commentoDAO.getCommentoByRecipe(Integer.parseInt(params.get("id")));
                ((Ricetta) result).setCommenti(commenti);

                
            }
        } catch (SQLException exc) {
            System.err.println("Errore nella select in ricettaDAO");
        } finally {
            try {
                ps.close();
                rs.close();
            } catch (Exception exc) {
                System.err.println("Errore chiusura prepared Statement");
            }
        }
        return result;
    }

    public Map<Integer, Entity> readByFilter(Map<String, String> filter) {
        String query = "SELECT * FROM ricette WHERE ";
        PreparedStatement ps = null;
        ResultSet rs = null;

        for (String key : filter.keySet()) {

            if(filter.get(key).matches("-?\\d+")){
                if(key.equalsIgnoreCase("tempo_preparazione"))
                {
                    query += key + " BETWEEN 0 AND " + filter.get(key) + " AND ";
                }
                else
                {
                    query += key + " = " + filter.get(key) + " AND ";
                }
            }
            else
            {
                query += key + " LIKE " + "\"%" + filter.get(key) + "%\"" + " AND ";
            }


        }

        query = query.substring(0, query.length() - 5);


        Map<Integer, Entity> result = new HashMap<>();

        try {
            ps = database.getConnection().prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, String> params = new HashMap<>();
                params.put("id", rs.getInt(1) + "");
                params.put("nome", rs.getString(2));
                params.put("istruzioni", rs.getString(3));
                params.put("portata", rs.getString(4));
                params.put("difficolta", rs.getInt(5) + "");
                params.put("tempoPreparazione", rs.getInt(6) + "");
                params.put("serving", rs.getInt(7) + "");
                params.put("linkImmagine", rs.getString(8));

                Ricetta r = context.getBean(Ricetta.class, params);
                List<RicettaIngrediente> ingredienti = new ArrayList<>();
                for (Entity ricettaIngrediente : ricettaIngredienteDAO.readByIdRicetta(r.getId()).values())
                    ingredienti.add((RicettaIngrediente) ricettaIngrediente);
                    r.setIngredienti(ingredienti);

                List<Commento> commenti = commentoDAO.getCommentoByRecipe(Integer.parseInt(params.get("id")));
                r.setCommenti(commenti);
                result.put(r.getId(), r);

            }
        } catch (SQLException exc) {
            System.err.println("Errore nella select in ricettaDAO" + exc.getMessage());
        } finally {
            try {
                ps.close();
                rs.close();
            } catch (Exception exc) {
                System.err.println("Errore chiusura prepared Statement"+ exc.getMessage());
            }
        }
        return result;
    }

    public Map<Integer, Entity> readByIngredients(List<String> ingredients){
        String query = "SELECT ricette.* FROM ricette JOIN ricette_ingredienti ON ricette.id = ricette_ingredienti.id_ricetta" +
        " JOIN ingredienti ON ricette_ingredienti.id_ingrediente = ingredienti.id WHERE ingredienti.nome IN (";
        for (int i = 0; i < ingredients.size(); i++) {
            query += "'" + ingredients.get(i) + "'";
            if (i!= ingredients.size() - 1)
                query += ",";
        }
        query += ");";
        System.out.println(query);
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<Integer, Entity> result = new HashMap<>();

        try {
            Map <String, String> params = new HashMap<>();
            ps = database.getConnection().prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                params.put("id", rs.getInt(1) + "");
                params.put("nome", rs.getString(2));
                params.put("istruzioni", rs.getString(3));
                params.put("portata", rs.getString(4));
                params.put("difficolta", rs.getInt(5) + "");
                params.put("tempoPreparazione", rs.getInt(6) + "");
                params.put("serving", rs.getInt(7) + "");
                params.put("linkImmagine", rs.getString(8));

                Ricetta r = context.getBean(Ricetta.class, params);

                List<RicettaIngrediente> ingredienti = new ArrayList<>();
                for (Entity ricettaIngrediente : ricettaIngredienteDAO.readByIdRicetta(r.getId()).values())
                ingredienti.add((RicettaIngrediente) ricettaIngrediente);
                r.setIngredienti(ingredienti);
              

                List<Commento> commenti = commentoDAO.getCommentoByRecipe(Integer.parseInt(params.get("id")));
                r.setCommenti(commenti);
                result.put(r.getId(), r);
            }

        } catch (SQLException exc) {
            System.err.println("Errore nela query in RicettaDAO readByIngredients" + exc.getMessage());
        } finally {
            try {
                ps.close();
                rs.close();
            } catch (Exception exc) {
                System.err.println("Errore chiusura prepared Statement in RicettaDAO readByIngredients" + exc.getMessage());
            }
        }
        return result;
    }

    public Map<Integer, Entity> readByRecipeType(Map<String, String> params) {
        String query = "SELECT * FROM ricette r WHERE EXISTS (SELECT * FROM ricette_ingredienti ri" +
        " JOIN ingredienti i ON ri.id_ingrediente = i.id WHERE ri.id_ricetta = r.id AND (i.senza_glutine = ? AND i.vegano = ? AND i.vegetariano = ?)";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<Integer, Entity> result = new HashMap<>();

        try {
            ps = database.getConnection().prepareStatement(query);
            
        if(params.containsKey("senza_glutine")){
            ps.setBoolean(1,Boolean.parseBoolean(params.get("senza_glutine")));
        }else {
            ps.setBoolean(1,false);
        }

        if (params.containsKey("vegano")){
             ps.setBoolean(2,Boolean.parseBoolean(params.get("vegano")));
        } else {
            ps.setBoolean(2,false);
        }

        if (params.containsKey("vegetariano")){
            ps.setBoolean(3,Boolean.parseBoolean(params.get("vegetariano")));
        } else {
            ps.setBoolean(3,false);
        }
        System.out.println(query);
        rs = ps.executeQuery();

        Ricetta r = context.getBean(Ricetta.class, params);

        List<RicettaIngrediente> ingredienti = new ArrayList<>();
        for (Entity ricettaIngrediente : ricettaIngredienteDAO.readByIdRicetta(r.getId()).values())
        ingredienti.add((RicettaIngrediente) ricettaIngrediente);
        r.setIngredienti(ingredienti);
      

        List<Commento> commenti = commentoDAO.getCommentoByRecipe(Integer.parseInt(params.get("id")));
        r.setCommenti(commenti);
        result.put(r.getId(), r);

        } catch (SQLException exc) {
            System.err.println("Errore nela query in RicettaDAO");
        } finally {
            try {
                ps.close();
                rs.close();
            } catch (Exception exc) {
                System.err.println("Errore chiusura prepared Statement");
            }
        }
        return result;
    }
        
    public int lastRicetta()
    {
        String query = "SELECT MAX(id) FROM ricette";
        PreparedStatement ps = null;
        ResultSet rs = null;
        int id = 0;
        try{
            ps = database.getConnection().prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next())
            {
                id = rs.getInt(1);
            }
        }
        catch(SQLException exc)
        {
            System.err.println("Errore nella select in lastRicetta" + exc.getMessage());
        }

        return id;
    }

    public List<String> getAllPortate()
    {
        String query = "SELECT DISTINCT portata FROM ricette;";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<String> portate = new ArrayList<>();
        try{
            ps = database.getConnection().prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next())
            {
                if(rs.getString(1) != null)
                    portate.add(rs.getString(1));
            }
        }
        catch(SQLException exc)
        {
            System.err.println("Errore nella select in getAllRicette() RicettaDAO" + exc.getMessage());
        }

        return portate;
    }

}
