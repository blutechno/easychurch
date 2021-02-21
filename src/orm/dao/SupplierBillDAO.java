/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.List;
import orm.SupplyBill;

/**
 *
 * @author jean pierre
 */
public interface SupplierBillDAO {

    public boolean saveOrUpdateSupplierBill(SupplyBill supplierBill);

    public SupplyBill findByBill(String billNumber);

    public List<SupplyBill> findBySupplier(String designation);

    public List<SupplyBill> findAll();
}
