/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.List;
import orm.Ministry;

/**
 *
 * @author jean pierre
 */
public interface MinistryDAO {

    public boolean saveOrUpdateMinistry(Ministry ministry);

    public Ministry findByDesignation(String designation);

    public List<Ministry> findByStatus(char status);

    public List<Ministry> findAll();
}
