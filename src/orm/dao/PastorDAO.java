/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.List;
import orm.Pastor;

/**
 *
 * @author jean pierre
 */
public interface PastorDAO {

    public boolean saveOrUpdatePastor(Pastor pastor);

    public Pastor findByCode(String code);

    public Pastor findByNames(String firstName, String lastName);

    public List<Pastor> findByGender(char sex);

    public List<Pastor> findAll();
}
