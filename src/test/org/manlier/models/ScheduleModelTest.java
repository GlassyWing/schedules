package org.manlier.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.junit.Assert;
import org.junit.Test;
import org.manlier.beans.Schedule;
import org.manlier.beans.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Created by manlier on 2017/6/4.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
public class ScheduleModelTest extends UserModelTest {

    @Autowired
    private ScheduleDao scheduleDao;

    @Autowired
    private UserDao userDao;

    @Test
    public void addSchedule() {
        Schedule schedule = new Schedule("复习啦");
        scheduleDao.addScheduleForUser("97147179147198497", schedule);
    }

    @Test
    public void getScheduleByScheduleId() throws IOException {
        Schedule schedule = scheduleDao.getScheduleByScheduleId("67eab2234c3b11e7ac0754a050631fca");
        String scheduleJson = new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule())
                .writeValueAsString(schedule);
        System.out.println(scheduleJson);
        //language=JSON
        String json = "{\"createTime\":\"2017-06-08T00:00:00.000Z\", \"status\":2, \"allDay\":false}";
        Schedule schedule1 = new ObjectMapper()
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule())
                .registerModule(new ParameterNamesModule())
                .readerFor(Schedule.class)
                .readValue(json);
        System.out.println(schedule1);
    }

    @Test
    public void updateSchedule() {
        Schedule schedule = scheduleDao.getScheduleByScheduleId("82e1678a491e11e7ac0754a050631fca");
        schedule.setDeleted(true);
        Instant beforeModifyTime = schedule.getModifyTime();

        scheduleDao.updateSchedule(schedule);

        schedule = scheduleDao.getScheduleByScheduleId("82e1678a491e11e7ac0754a050631fca");

        Assert.assertNotEquals(beforeModifyTime, schedule.getModifyTime());
        System.out.println(schedule);
    }

    @Test
    public void deleteSchedule() {
        int affectedNum = scheduleDao.deleteSchedule("9fb365bb491f11e7ac0754a050631fca");
        Assert.assertEquals(affectedNum, 1);
    }



    @Test
    public void selectAllSchedules() {
        String userId = "97147179147198487";
        List<Schedule> schedules = scheduleDao.getAllSchedulesForUser(userId);
        System.out.println(schedules);
    }

    @Test
    public void getAllCompletedSchedules() {
        String userId = "97177576174256135";
        Instant fromInstant, toInstant;
        toInstant = Instant.now();

        List<Schedule> completed = scheduleDao.getAllSchedulesCompletedForUserFrom(userId, null, toInstant, 0);
        System.out.println(completed);
    }

    @Test
    public void getAllUncompletedSchedules() {
        String userId = "97147179147198498";
        List<Schedule> uncompleted = scheduleDao.getAllSchedulesUncompletedForUser(userId);
        System.out.println(uncompleted);
    }

    @Test
    public void selectAllSchedulesByProjectId() {
        Schedule schedule = scheduleDao.getScheduleByScheduleId("82e1678a491e11e7ac0754a050631fca");
        schedule.setGroupUuid("inbox_97147179147198488");
        System.out.println(schedule);
        scheduleDao.updateSchedule(schedule);

        List<Schedule> schedules = scheduleDao.getAllSchedulesByProjectId("inbox_97147179147198488");
        System.out.println(schedules);
    }

    @Test
    public void recycleSchedule() {
        int affected = scheduleDao.recycleSchedule("82e1678a491e11e7ac0754a050631fca");
        Assert.assertEquals(affected,1);
    }

    @Test
    public void restoreSchedule() {
        int affected = scheduleDao.restoreSchedule("82e1678a491e11e7ac0754a050631fca");
        Assert.assertEquals(affected,1);
    }

    @Test
    public void getAllRecycledSchedules() {
        List<Schedule> schedules = scheduleDao.getAllRecycledSchedulesForUser("97147179147198486");
        System.out.println(schedules);
    }

    @Test
    public void deleteAllRecycledSchedules() {
        Schedule schedule1 = new Schedule("复习英语"),
        schedule2 = new Schedule("复习高数");
        scheduleDao.addScheduleForUser("97147179147198488", schedule1);
        scheduleDao.addScheduleForUser("97147179147198488", schedule2);

        // 回收日程
        scheduleDao.recycleSchedule(schedule1.getScheduleUuid());
        scheduleDao.recycleSchedule(schedule2.getScheduleUuid());

        Assert.assertEquals(2, scheduleDao.getAllRecycledSchedulesForUser("97147179147198488").size());

        // 清空回收站
        scheduleDao.deleteAllRecycledSchedulesForUser("97147179147198488");

        Assert.assertEquals(0, scheduleDao.getAllRecycledSchedulesForUser("97147179147198488").size());
    }

    @Test
    public void completeSchedule() {
        int completed = scheduleDao.completeSchedule("fc809b874b6811e7ac0754a050631fca");
        Assert.assertEquals(1, completed);
    }

    @Test
    public void incompleteSchedule() {
        int incompleted = scheduleDao.incompleteSchedule("fc809b874b6811e7ac0754a050631fca");
        Assert.assertEquals(1, incompleted);
    }



}
