package org.manlier.providers;

import org.manlier.beans.Reminder;
import org.manlier.beans.Schedule;
import org.manlier.models.ReminderDao;
import org.manlier.providers.interfaces.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 提醒服务
 * Created by manlier on 2017/6/6.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class ReminderService implements org.manlier.providers.interfaces.IReminderService {

    private final ReminderDao reminderDao;

    private IScheduleService scheduleService;


    @Autowired
    public ReminderService(ReminderDao reminderDao) {
        this.reminderDao = reminderDao;
    }

    @Autowired
    public void setScheduleService(IScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /**
     * 获得日程的所有提醒
     *
     * @param schedulerId 日程id
     * @return 提醒
     */
    @Override
    public List<Reminder> getAllReminderForSchedule(String schedulerId) {
        return reminderDao.getAllReminderForSchedule(schedulerId);
    }

    /**
     * 为日程添加多个提醒
     *
     * @param scheduleId 日程id
     * @param reminders  提醒
     * @return 添加的结果
     */
    @Override
    @Transactional
    public boolean addRemindersForSchedule(String scheduleId, List<Reminder> reminders) {
        boolean success = true;
        for (Reminder reminder :
                reminders) {
            if (!addSingleReminderForSchedule(scheduleId, reminder)) {
                success = false;
            }
        }
        return success;
    }

    /**
     * 为日程添加单个提醒
     *
     * @param scheduleId 日程id
     * @param reminder   提醒
     * @return 添加的结果
     */
    @Override
    public boolean addSingleReminderForSchedule(String scheduleId, Reminder reminder) {
        Schedule schedule = scheduleService.getScheduleById(scheduleId);
        if (schedule.getStartTime() == null)
            throw new IllegalStateException("The schedule's start time must be set!");
        return reminderDao.addReminderForSchedule(scheduleId, reminder) == 1;
    }

    /**
     * 移除指定的提醒
     *
     * @param ids 提醒id
     * @return 移除结果
     */
    @Override
    public boolean removeReminders(String... ids) {
        return reminderDao.deleteReminder(ids) == ids.length;
    }

    /**
     * 移除日程的所有提醒
     *
     * @param scheduleId 日程id
     * @return 移除结果
     */
    @Override
    public boolean removeAllRemindersForSchedule(String scheduleId) {
        return reminderDao.deleteAllRemindersForSchedule(scheduleId) != -1;
    }

}
