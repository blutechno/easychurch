/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl;

import java.util.List;
import org.hibernate.criterion.Restrictions;
import orm.User;
import orm.UserLog;
import orm.dao.UserLogDAO;

/**
 *
 * @author jean pierre
 */
public class UserLogDAOImpl implements UserLogDAO {

    private DAOImpl dao = DAOImpl.getInstance();

    @Override
    public void saveOrUpdateLog(UserLog userLog) {
        dao.saveObject(userLog);
    }

    @Override
    public UserLog findUnClosedByUser(String username) {
        UserLog log = new UserLog();
        for (UserLog l : findByUser(username)) {
            if (l.getLogout() == null) {
                log = l;
                break;
            }
        }
        return log;
    }

    @Override
    public List<UserLog> findByUser(String username) {
        User user = (User) dao.getSession().createCriteria(User.class).add(Restrictions.eq("username", username)).uniqueResult();
        return dao.getSession().createCriteria(UserLog.class).add(Restrictions.eq("user", user)).list();
    }

    @Override
    public List<UserLog> findAll() {
        return dao.getSession().createCriteria(UserLog.class).list();
    }

    @Override
    public UserLog findLastLog(String username) {
        List<UserLog> logs = findByUser(username);
        if (logs.isEmpty()) {
            return null;
        } else {
            UserLog log = logs.get(0);
            for (UserLog l : logs) {
                if (l.getLogin().after(log.getLogin())) {
                    log = l;
                }
            }
            return log;
        }
    }
}
