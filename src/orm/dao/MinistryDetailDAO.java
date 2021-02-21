/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.Date;
import java.util.List;
import orm.MinistryDetail;

/**
 *
 * @author jean pierre
 */
public interface MinistryDetailDAO {

    public boolean saveOrUpdateMinistiryDetail(MinistryDetail ministryDetail);

    public List<MinistryDetail> findByDate(Date heldOn);

    public List<MinistryDetail> findByMinistry(String designation);

    public List<MinistryDetail> findByMainSpeaker(String mainSpeaker);

    public List<MinistryDetail> findAll();
}
