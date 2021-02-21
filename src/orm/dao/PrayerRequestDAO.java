/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.Date;
import java.util.List;
import orm.PrayerRequest;

/**
 *
 * @author jean pierre
 */
public interface PrayerRequestDAO {

    public boolean saveOrUpdatePrayerRequest(PrayerRequest prayerRequest);

    public List<PrayerRequest> findByRequestor(String firstName, String lastName);

    public List<PrayerRequest> findByRequestor(String christianPin);

    public List<PrayerRequest> findByDates(Date dateFrom, Date dateTo);

    public List<PrayerRequest> findByPreferedDatesAndRequest(Date dateFrom, Date dateTo, String christianPin);

    public List<PrayerRequest> findAll();
}
