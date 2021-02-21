/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.List;
import orm.Christian;

/**
 *
 * @author jean pierre
 */
public interface ChristianDAO {

    public boolean saveOrUpdate(Christian christian);

    public Christian findById(int id);

    public Christian findByChristianPin(String christianPin);

    public List<Christian> findByFirstName(String firsName);

    public List<Christian> findByFullName(String name);

    public List<Christian> findByLastName(String lastName);

    public Christian findByNames(String firstName, String lastName);

    public List<Christian> findByFatherName(String fatherName);

    public List<Christian> findByMotherName(String motherName);

    public Christian findBySpouseName(String firstName, String lastname);

    public List<Christian> findByGender(char sex);

    public List<Christian> findByEducationLevel(String educationLevel);

    public List<Christian> findBaptised();

    public List<Christian> findActive();

    public List<Christian> findAll();
}
