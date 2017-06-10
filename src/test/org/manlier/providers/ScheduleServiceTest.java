package org.manlier.providers;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.manlier.beans.Repetition;
import org.manlier.beans.Schedule;
import org.manlier.beans.User;
import org.manlier.contracts.ScheduleStatus;
import org.manlier.providers.interfaces.IReminderService;
import org.manlier.providers.interfaces.IScheduleService;
import org.manlier.providers.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.time.Instant;
import java.util.List;

/**
 * Created by manlier on 2017/6/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-*.xml")
public class ScheduleServiceTest {

    @Autowired
    private IReminderService reminderService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IScheduleService scheduleService;

    private String defaultScheduleId =
            "47e2e65e4dec11e7ac0754a050631fca";

    private String defaultUserId =
            "97147179147198498";

    @Test
    public void addSchedule() {

        Schedule schedule = new Schedule("搞事情");
        boolean isAdded = scheduleService.addScheduleForUser(defaultUserId, schedule);
        Assert.assertTrue(isAdded);
    }

    @Test
    public void deleteSchedule() {
        boolean isDeleted = scheduleService.deleteSchedule("59c8dabd4a9811e7ac0754a050631fca");
        Assert.assertTrue(isDeleted);
    }

    @Test
    public void getAllSchedulesForUser() {
        List<Schedule> schedules = scheduleService.getAllSchedulesForUser("97147179147198497");
        System.out.println(schedules);
    }

    @Test
    public void setStartAndDueTime() {
        Instant startTime, dueTime;
        startTime = Instant.parse("2017-06-10T12:00:00.000Z");
        dueTime = Instant.parse("2017-06-10T16:00:00.000Z");
        try {
            boolean affected = scheduleService.setStartTimeAndDueTime(defaultScheduleId, startTime, dueTime);
            Assert.assertTrue(affected);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSetRepetition() throws ParseException {
        Repetition repetition = new Repetition(ScheduleStatus.DUE ,"0 0 0 1/1 * ?");
        boolean affected = scheduleService.setRepetitionForSchedule(defaultScheduleId, repetition);
        Assert.assertTrue(affected);
    }

    @Test
    public void testCompleteSchedule() {
        boolean affected = scheduleService.completeSchedule(defaultScheduleId);
        Assert.assertTrue(affected);
    }

}
