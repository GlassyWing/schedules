package org.manlier.providers.interfaces;

import org.manlier.beans.Reminder;
import org.manlier.beans.Schedule;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * Created by manlier on 2017/6/7.
 */
@Service
@Transactional
public interface INotificationService {


    void scheduleNotificationJob(String jobId, Instant baseDate, List<Reminder> reminders) throws SchedulerException;

    void removeNotificationJob(String jobId) throws SchedulerException;

    @SuppressWarnings("unchecked")
    void rescheduleNotificationJobBaseNewDate(String jobId, Instant baseDate) throws SchedulerException;
}
