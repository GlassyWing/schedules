package org.manlier.models;

import org.junit.Assert;
import org.junit.Test;
import org.manlier.beans.Repetition;
import org.manlier.contracts.ScheduleStatus;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

/**
 * Created by manlier on 2017/6/5.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
public class RepetitionModelTest extends DBConnectTest {

    @Autowired
    private RepetitionDao repetitionDao;

    @Test
    public void addRepetition() throws ParseException {
        String scheduleId = "67eb600f4d2b11e7ac0754a050631fca";
        Repetition repetition = new Repetition(ScheduleStatus.DUE, "0/1 0 20-23 * * ?");
        int affected = repetitionDao.addRepetitionForSchedule(scheduleId, repetition);
        Assert.assertEquals(1, affected);
    }

    @Test
    public void getRepetition() {
        String scheduleId = "67eb600f4d2b11e7ac0754a050631fca";
        Repetition repetition = repetitionDao.getRepetitionByScheduleId(scheduleId);
        System.out.println(repetition);
    }

    @Test
    public void updateRepetition() throws ParseException {
        String scheduleId = "67eb600f4d2b11e7ac0754a050631fca";
        Repetition repetition = repetitionDao.getRepetitionByScheduleId(scheduleId);
        System.out.println(repetition);
        repetition.setCron(new CronExpression("5 0 23 5/1 * ?"));
        int affected = repetitionDao.updateRepetitionForSchedule(scheduleId, repetition);
        Assert.assertEquals(1, affected);
    }

    @Test
    public void deleteRepetition() {
        int affected = repetitionDao.deleteRepetition("67eb600f4d2b11e7ac0754a050631fca");
        Assert.assertEquals(1, affected);
    }

}
