/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.OffertoryType;
import orm.dao.OffertoryTypeDAO;

/**
 *
 * @author jean pierre
 */
public class OffertoryTypeDAOImpl implements OffertoryTypeDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdateOffertoryType(OffertoryType offertoryType) {
        return dao.saveObject(offertoryType);
    }

    @Override
    public OffertoryType findByName(String offertoryName) {
        return (OffertoryType) dao.getSession().createCriteria(OffertoryType.class).add(Restrictions.eq("offertoryTypeName", offertoryName)).uniqueResult();
    }

    @Override
    public List<OffertoryType> findByStatus(char status) {
        return dao.getSession().createCriteria(OffertoryType.class).add(Restrictions.eq("status", status)).list();
    }

    @Override
    public List<OffertoryType> findAll() {
        return dao.getSession().createCriteria(OffertoryType.class).list();
    }
}
