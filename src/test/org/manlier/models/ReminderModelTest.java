package org.manlier.models;

import org.junit.Assert;
import org.junit.Test;
import org.manlier.beans.Reminder;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by manlier on 2017/6/6.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
public class ReminderModelTest extends DBConnectTest {

    @Autowired
    private ReminderDao reminderDao;

    private String defaultScheduleId = "47e2e65e4dec11e7ac0754a050631fca";


    @Test
    public void addReminders() throws ParseException {
        Reminder reminder = new Reminder("-PT12H");
        int affected = reminderDao.addReminderForSchedule(defaultScheduleId, reminder);
        Assert.assertEquals(1, affected);
    }

    @Test
    public void getReminder() throws ParseException {
        Reminder reminder = new Reminder("-PT5M");
        reminderDao.addReminderForSchedule(defaultScheduleId, reminder);
        reminder = reminderDao.getReminderByReminderId(reminder.getReminderUuid());
        System.out.println(reminder);
    }

    @Test
    public void deleteReminder() {
        int affected = reminderDao.deleteReminder("0977a6b44df611e7ac0754a050631fca", "94e26b854df511e7ac0754a050631fca");
        Assert.assertEquals(2, affected);
    }

    @Test
    public void updateReminder() throws ParseException {
        String scheduleId = "82e1678a491e11e7ac0754a050631fca";
        Reminder reminder = new Reminder("-PT5M");
        reminderDao.addReminderForSchedule(scheduleId, reminder);
        reminder.setDuration(Duration.parse("-PT5M"));
        int affected = reminderDao.updateReminder(reminder);
        Assert.assertEquals(1, affected);
    }
}
