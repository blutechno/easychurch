/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.List;
import orm.Supplier;

/**
 *
 * @author jean pierre
 */
public interface SupplierDAO {

    public boolean saveOrUpdateSupplier(Supplier supplier);

    public Supplier findByDesignation(String designation);

    public List<Supplier> findAll();
}
