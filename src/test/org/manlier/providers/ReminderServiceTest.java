package org.manlier.providers;

import org.junit.Assert;
import org.junit.Test;
import org.manlier.beans.Reminder;
import org.manlier.providers.interfaces.IReminderService;
import org.manlier.providers.interfaces.IScheduleService;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by manlier on 2017/6/6.
 */
public class ReminderServiceTest extends BaseServiceTest {

    Logger logger = LoggerFactory.getLogger(ReminderServiceTest.class);

    @Autowired
    private IReminderService reminderService;

    @Autowired
    private IScheduleService scheduleService;

    private String defaultScheduleId = "47e2e65e4dec11e7ac0754a050631fca";

    @Test
    public void getAllReminders() {
        List<Reminder> reminders = reminderService.getAllReminderForSchedule(defaultScheduleId);
        logger.info("reminders size: " + reminders.size());
    }

    @Test
    public void addReminders() throws ParseException, SchedulerException {
        List<Reminder> reminders = Arrays.asList(new Reminder("PT3M"), new Reminder("PT2S"));
        List<Reminder> added = reminderService.addRemindersForSchedule(defaultScheduleId, reminders);
        Assert.assertNotNull(added);
    }

    @Test
    public void removeReminders() throws SchedulerException {
        String[] ids = new String[]{"f16cb96b4b7611e7ac0754a050631fca", "f17a161c4b7611e7ac0754a050631fca"};
        boolean removed = reminderService.removeReminders(ids);
        Assert.assertTrue(removed);

    }

    @Test
    public void removeAllReminders() throws SchedulerException {
        boolean removed = reminderService.removeAllRemindersForSchedule("fc809b874b6811e7ac0754a050631fca");
        Assert.assertTrue(removed);

    }

}
