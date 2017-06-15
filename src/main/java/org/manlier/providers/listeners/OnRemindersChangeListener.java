package org.manlier.providers.listeners;

import org.manlier.beans.Reminder;
import org.manlier.beans.Repetition;
import org.manlier.beans.Schedule;
import org.quartz.SchedulerException;

import java.time.Instant;
import java.util.List;

/**
 * Created by manlier on 2017/6/7.
 */
public interface OnRemindersChangeListener {

    void onDeleteReminders(String... reminderIds);

    void onClearReminders(String scheduleId);

    void onSetReminders(String scheduleId,  List<Reminder> reminders);
}
