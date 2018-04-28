package com.api.entities.business;

// #region Imports

import java.util.ArrayList;

import java.sql.ResultSet;
import java.sql.SQLException;

// #endregion

public class Role {
    private int id;
    private String roleName;
    private ArrayList<Resource> resources;

    /**
    * Default empty Roles constructor
    */
    public Role() {
        super();
    }

    /**
    * Constructor for MySQL
    */
    public Role(ResultSet rs) {
        super();
        try {
            this.id = rs.getInt("id");
            this.roleName = rs.getString("roleName");
        }
        catch(SQLException e) {

        }
    }

    /**
    * Default Roles constructor
    */
    public Role(int id, String roleName) {
        super();
        this.id = id;
        this.roleName = roleName;
    }

    /**
    * Returns value of id
    * @return
    */
    public int getId() {
        return id;
    }

    /**
    * Sets new value of id
    * @param
    */
    public void setId(int id) {
        this.id = id;
    }

    /**
    * Returns value of roleName
    * @return
    */
    public String getRoleName() {
        return roleName;
    }

    /**
    * Sets new value of roleName
    * @param
    */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
    * Returns value of resources
    * @return
    */
    public ArrayList<Resource> getResources() {
        return resources;
    }

    /**
    * Sets new value of resources
    * @param
    */
    public void setResources(ArrayList<Resource> resources) {
        this.resources = resources;
    }

    public void addResource(Resource rp) {
        if (resources == null)
        resources = new ArrayList<Resource>();

        resources.add(rp);
    }
}
