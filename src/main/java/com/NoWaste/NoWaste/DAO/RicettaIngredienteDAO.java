package com.NoWaste.NoWaste.DAO;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.NoWaste.NoWaste.entities.Entity;

@Service
public class RicettaIngredienteDAO implements IDAO{

       @Autowired
    private ApplicationContext context;

    @Autowired
    private  DatabaseConnection database;

    @Override
    public void create(Entity e) {
    
    }

    @Override
    public Map<Integer, Entity> read() {
        return null;
       
    }

    @Override
    public void update(Entity e) {
     
    }

    @Override
    public void delete(int id) {
    
    }
    
}
