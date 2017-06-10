package org.manlier.integration;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.manlier.beans.Reminder;
import org.manlier.providers.interfaces.INotificationService;
import org.manlier.providers.interfaces.IReminderService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by manlier on 2017/6/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-providers.xml"
        , "classpath:spring/spring-model.xml"
        , "classpath:spring/spring-quartz-config.xml"})
public class ScheduleNotificationTest {

    @Autowired
    private IReminderService reminderService;

    @Autowired
    private INotificationService notificationService;

    private String defaultScheduleId = "700b84aa4df011e7ac0754a050631fca";

    @Test
    public void scheduleReminders() throws SchedulerException {
        List<Reminder> reminders = Arrays.asList(new Reminder("PT-5S"), new Reminder("PT-10S"));

        boolean affected = reminderService.addRemindersForSchedule(defaultScheduleId, reminders);

        Assert.assertTrue(affected);

        notificationService.scheduleReminderJobForSchedule(defaultScheduleId, Instant.now().plus(15, ChronoUnit.SECONDS), reminders);
    }

    @After
    public void tearDown() throws InterruptedException {
        reminderService.removeAllRemindersForSchedule(defaultScheduleId);
        TimeUnit.SECONDS.sleep(14);
    }
}
