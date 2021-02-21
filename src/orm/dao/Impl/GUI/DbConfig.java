/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl.GUI;

import java.io.Serializable;

/**
 *
 * @author jean pierre
 */
public class DbConfig implements Serializable {

    private String database;
    private String password;

    public DbConfig(String database) {
        this.database = database;
    }

    public DbConfig(String database, String password) {
        this.database = database;
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
