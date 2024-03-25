package com.NoWaste.NoWaste.DAO;

import java.util.Map;

import com.NoWaste.NoWaste.entities.Entity;

public interface IDAO {

    public boolean create(Entity e);

    public Map<Integer, Entity> read();

    public boolean update(Entity e);

    public boolean delete(int id);

    public Entity readById(int id);
    
}
