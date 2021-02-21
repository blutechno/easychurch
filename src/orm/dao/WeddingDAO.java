/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.List;
import orm.Wedding;

/**
 *
 * @author jean pierre
 */
public interface WeddingDAO {

    public boolean saveOrUpdateWedding(Wedding wedding);

    public Wedding findByPartners(String husband, String wife);

    public Wedding findByWeddingCode(String weddingCode);

    public List<Wedding> findAll();
}
