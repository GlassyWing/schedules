package org.manlier.providers.interfaces;

import org.manlier.beans.Reminder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by manlier on 2017/6/7.
 */
@Service
@Transactional
public interface IReminderService {
    List<Reminder> getAllReminderForSchedule(String schedulerId);

    List<Reminder> addRemindersForSchedule(String scheduleId, List<Reminder> reminders);

    boolean removeReminders(String... ids);

    boolean removeAllRemindersForSchedule(String scheduleId);

    List<Reminder> setRemindersForSchedule(String scheduleId, List<Reminder> reminders);
}
