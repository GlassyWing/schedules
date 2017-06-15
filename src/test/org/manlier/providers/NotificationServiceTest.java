package org.manlier.providers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.manlier.beans.Reminder;
import org.manlier.beans.Schedule;
import org.manlier.providers.interfaces.INotificationService;
import org.manlier.providers.interfaces.IReminderService;
import org.manlier.providers.interfaces.IScheduleService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by manlier on 2017/6/11.
 */
public class NotificationServiceTest extends BaseServiceTest {

    private String defaultScheduleId = "700b84aa4df011e7ac0754a050631fca";


    @Autowired
    private INotificationService notificationService;

    @Autowired
    private IReminderService reminderService;


    @After
    public void teardown() throws InterruptedException, SchedulerException {
        TimeUnit.SECONDS.sleep(15);
        reminderService.removeAllRemindersForSchedule(defaultScheduleId);

    }

    @Test
    public void testAddReminders() throws SchedulerException {
        List<Reminder> reminders = Arrays.asList(new Reminder("PT-5S"), new Reminder("PT-10S"));
        reminderService.addRemindersForSchedule(defaultScheduleId, reminders);
        reminders = reminderService.getAllReminderForSchedule(defaultScheduleId);
        notificationService.scheduleNotificationJob(defaultScheduleId
                , Instant.now().plus(15, ChronoUnit.SECONDS)
                , reminders);
    }
}
