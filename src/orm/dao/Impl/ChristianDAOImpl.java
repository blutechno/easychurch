/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.Christian;
import orm.dao.ChristianDAO;

/**
 *
 * @author jean pierre
 */
public class ChristianDAOImpl implements ChristianDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public boolean saveOrUpdate(Christian christian) {
        return dao.saveObject(christian);
    }

    @Override
    public List<Christian> findByFirstName(String firstName) {
        return dao.getSession().createCriteria(Christian.class).add(Restrictions.eq("firstName", firstName)).list();
    }

    @Override
    public List<Christian> findByLastName(String lastName) {
        return dao.getSession().createCriteria(Christian.class).add(Restrictions.eq("lastName", lastName)).list();
    }

    @Override
    public List<Christian> findByFatherName(String fatherName) {
        List<Christian> result = new ArrayList<Christian>();
        List<Christian> temp = new ArrayList<Christian>();
        result.addAll(dao.getSession().createCriteria(Christian.class).add(Restrictions.like("fatherName", "%" + fatherName + "%")).list());
        temp.addAll(dao.getSession().createCriteria(Christian.class).add(Restrictions.like("fullName", "%" + fatherName + "%")).list());
        for (Christian c : temp) {
            int age = ((new Date()).getYear() - (c.getDateOfBirth().getYear()));
            if (c.getGender() == 'M' && age >= 18) {
                result.add(c);
            }
        }
        return result;
    }

    @Override
    public List<Christian> findByMotherName(String motherName) {
        List<Christian> result = new ArrayList<Christian>();
        List<Christian> temp = new ArrayList<Christian>();
        result.addAll(dao.getSession().createCriteria(Christian.class).add(Restrictions.like("motherName", "%" + motherName + "%")).list());
        temp.addAll(dao.getSession().createCriteria(Christian.class).add(Restrictions.like("fullName", "%" + motherName + "%")).list());
        for (Christian c : temp) {
            int age = ((new Date()).getYear() - (c.getDateOfBirth().getYear()));
            if (c.getGender() == 'F' && age >= 18) {
                result.add(c);
            }
        }
        return result;
    }

    @Override
    public Christian findBySpouseName(String firstName, String lastName) {
        return (Christian) dao.getSession().createCriteria(Christian.class).add(Restrictions.and(Restrictions.eq("spouseFirstName", firstName), Restrictions.eq("spouseLastName", lastName))).uniqueResult();
    }

    @Override
    public List<Christian> findByGender(char sex) {
        return dao.getSession().createCriteria(Christian.class).add(Restrictions.eq("gender", sex)).list();
    }

    @Override
    public List<Christian> findByEducationLevel(String educationLevel) {
        return dao.getSession().createCriteria(Christian.class).add(Restrictions.eq("educationLevel", educationLevel)).list();
    }

    @Override
    public List<Christian> findBaptised() {
        return dao.getSession().createCriteria(Christian.class).add(Restrictions.ne("baptismDate", null)).list();
    }

    @Override
    public List<Christian> findActive() {
        List<Christian> result = new ArrayList<Christian>();
        for (Christian c : findAll()) {
            if (c.getIsActive() == 'Y') {
                result.add(c);
            }
        }
        return result;
    }

    @Override
    public List<Christian> findAll() {
        return dao.getSession().createCriteria(Christian.class).list();
    }

    @Override
    public Christian findById(int id) {
        return (Christian) dao.getSession().get(Christian.class, id);
    }

    @Override
    public Christian findByChristianPin(String christianPin) {
        return (Christian) dao.getSession().createCriteria(Christian.class).add(Restrictions.eq("christianPin", christianPin)).uniqueResult();
    }

    @Override
    public Christian findByNames(String firstName, String lastName) {
        return (Christian) dao.getSession().createCriteria(Christian.class).add(Restrictions.and(Restrictions.eq("firstName", firstName), Restrictions.eq("lastName", lastName))).uniqueResult();
    }

    @Override
    public List<Christian> findByFullName(String name) {
        return dao.getSession().createCriteria(Christian.class).add(Restrictions.like("fullName", "%" + name + "%")).list();
    }
}
