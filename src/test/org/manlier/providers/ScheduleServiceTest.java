package org.manlier.providers;

import com.mchange.util.AssertException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.manlier.beans.Reminder;
import org.manlier.beans.Repetition;
import org.manlier.beans.Schedule;
import org.manlier.beans.User;
import org.manlier.contracts.ScheduleStatus;
import org.manlier.providers.interfaces.IReminderService;
import org.manlier.providers.interfaces.IScheduleService;
import org.manlier.providers.interfaces.IUserService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
            "53cca3ce50c011e79a9054a050631fca";

    private String defaultUserId =
            "97177576174256135";

    @After
    public void teardown() throws InterruptedException {
    }

    @Test
    public void addSchedule() throws SchedulerException {

        Schedule schedule = new Schedule("学习");
        schedule.setStartTime(Instant.now());
        Schedule isAdded = scheduleService.addScheduleForUser(defaultUserId, schedule);
        Assert.assertNotNull(isAdded);
    }

    @Test
    public void deleteSchedule() {
        boolean isDeleted = scheduleService.deleteSchedule(defaultScheduleId);
        Assert.assertTrue(isDeleted);
    }

    @Test
    public void getAllSchedulesForUser() {
        List<Schedule> schedules = scheduleService.getAllSchedulesForUser(defaultUserId);
        schedules.forEach(schedule -> {
            System.out.println(schedule.getScheduleUuid());
            System.out.println(schedule.getReminders());
            System.out.println(schedule.getRepetition());
        });
    }

    public void testSetRemindersForSchedule() throws SchedulerException {

        // 提醒列表，为提前5s，提前10s开始提醒
        List<Reminder> reminders = Arrays.asList(
                new Reminder("PT-5S"),
                new Reminder("PT-10S")
        );
        Schedule schedule = scheduleService.getScheduleById(defaultScheduleId);

        // 设置日程开始时间，为当前时间之后15S后
        schedule.setStartTime(Instant.now().plus(15,ChronoUnit.SECONDS));
        schedule.setReminders(reminders);

        // 设置提醒
        scheduleService.updateSchedule(schedule);
    }

    @Test
    public void testClearRemindersForSchedule() throws SchedulerException {
        // 首先设置提醒
        testSetRemindersForSchedule();

        // 然后清除所有提醒
        reminderService.removeAllRemindersForSchedule(defaultScheduleId);
    }




    @Test
    public void testSetRepetition() throws ParseException, SchedulerException {
        Repetition repetition = new Repetition(ScheduleStatus.DUE ,"0 0 0 1/1 * ?");
        Schedule schedule = scheduleService.getScheduleById(defaultScheduleId);
        schedule.setRepetition(repetition);
        scheduleService.updateSchedule(schedule);
    }

    /**
     * @throws SchedulerException
     */
    @Test
    public void testCompleteSchedule() throws SchedulerException {
        scheduleService.completeSchedule(defaultScheduleId);
    }

    /**
     * 测试取消完成日程，若存在提醒设置，则应添加通知
     * @throws SchedulerException
     */
    @Test
    public void testIncompleteSchedule() throws SchedulerException {
        scheduleService.incompleteSchedule(defaultScheduleId);
    }

    /**
     * 测试回收日程，若回收成功，则相应的提醒也应当被删除
     */
    @Test
    public void testRecycleSchedule() {

    }

    @Test
    public void testRestoreSchedule() {
        Schedule affected = scheduleService.restoreSchedule(defaultScheduleId);
        Assert.assertNotNull(affected);
    }

}
