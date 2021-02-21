/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.Date;
import java.util.List;
import orm.SupplierItem;

/**
 *
 * @author jean pierre
 */
public interface SupplierItemDAO {

    public boolean saveOrUpdateSupplierItem(SupplierItem supplierItem);

    public boolean saveOrUpdateSupplierItem(List<SupplierItem> supplierItem);

    public List<SupplierItem> findBySupplier(String designation);

    public List<SupplierItem> findBySupplyDate(Date supplyDate);

    public List<SupplierItem> findAll();
}
