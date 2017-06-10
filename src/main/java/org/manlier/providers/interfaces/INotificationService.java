package org.manlier.providers.interfaces;

import org.manlier.beans.Reminder;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
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

    void scheduleReminderJobForSchedule(String scheduleId, Instant baseDate, List<Reminder> reminders) throws SchedulerException;

    void removeReminderJob(JobKey jobKey) throws SchedulerException;
}
