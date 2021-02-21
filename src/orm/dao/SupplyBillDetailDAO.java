/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.List;
import orm.SupplyBillDetail;

/**
 *
 * @author jean pierre
 */
public interface SupplyBillDetailDAO {

    public boolean saveOrUpdateSupplyBillDetail(SupplyBillDetail supplyBillDetail);

    public boolean saveOrUpdateSupplyBillDetail(List<SupplyBillDetail> supplyBillDetail);

    public List<SupplyBillDetail> findByBill(String billNumber);

    public List<SupplyBillDetail> findAll();
}
