package org.manlier.models;

import org.junit.Assert;
import org.junit.Test;
import org.manlier.beans.Preferences;

import javax.annotation.Resource;
import java.time.DayOfWeek;
import java.time.ZoneId;

/**
 * Created by manlier on 2017/6/13.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
public class PreferencesModelTest extends DBConnectTest {

    @Resource
    private PreferencesDao preferencesDao;

    private String defaultUserId = "97177576174256135";

    @Test
    public void testGetSettings() {
        Preferences preferences = preferencesDao.getPreferencesForUser(defaultUserId);
        System.out.println(preferences);
    }

    @Test
    public void testUpdateSettings() {
        Preferences preferences = preferencesDao.getPreferencesForUser(defaultUserId);
        preferences.setTimeZone(ZoneId.of("UTC"));
        preferences.setStartDayOfWeek(DayOfWeek.SUNDAY);
        preferences.setDailyRemindToggle(false);
        int affected = preferencesDao.updatePreferences(preferences);
        Assert.assertEquals(1, affected);
    }
}
