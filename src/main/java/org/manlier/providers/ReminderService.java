package org.manlier.providers;

import org.manlier.beans.Reminder;
import org.manlier.models.ReminderDao;
import org.manlier.providers.interfaces.IReminderService;
import org.manlier.providers.listeners.OnRemindersChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 提醒服务
 * Created by manlier on 2017/6/6.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class ReminderService implements IReminderService {

    private final ReminderDao reminderDao;

    private Logger logger = LoggerFactory.getLogger(ReminderService.class);

    @Resource
    private OnRemindersChangeListener listener;

    @Autowired
    public ReminderService(ReminderDao reminderDao) {
        this.reminderDao = reminderDao;
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
    public List<Reminder> addRemindersForSchedule(String scheduleId, List<Reminder> reminders) {
        for (Reminder reminder :
                reminders) {
            addSingleReminderForSchedule(scheduleId, reminder);
        }
        logger.info("All reminders added! Try to notify listener");
        listener.onSetReminders(scheduleId, reminders);
        return reminders;
    }

    /**
     * 为日程添加单个提醒
     *
     * @param scheduleId 日程id
     * @param reminder   提醒
     * @return 添加的结果
     */
    @Transactional
    private boolean addSingleReminderForSchedule(String scheduleId, Reminder reminder) {
//        Schedule schedule = scheduleService.getScheduleById(scheduleId);
//        if (schedule.getStartTime() == null)
//            throw new IllegalStateException("The schedule's start time must be set!");
        return reminderDao.addReminderForSchedule(scheduleId, reminder) == 1;
    }

    /**
     * 移除指定的提醒
     *
     * @param ids 提醒id
     * @return 移除结果
     */
    @Override
    @Transactional
    public boolean removeReminders(String... ids) {
        if (reminderDao.deleteReminder(ids) == ids.length) {
            logger.info("Delete reminders done! Try to notify listener");
            listener.onDeleteReminders(ids);
            return true;
        }
        return false;
    }

    /**
     * 移除日程的所有提醒
     *
     * @param scheduleId 日程id
     * @return 移除结果
     */
    @Override
    @Transactional
    public boolean removeAllRemindersForSchedule(String scheduleId) {
        if (reminderDao.deleteAllRemindersForSchedule(scheduleId) != -1) {
            logger.info("Clear all reminders done! Try to notify listener");
            listener.onClearReminders(scheduleId);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public List<Reminder> setRemindersForSchedule(String scheduleId, List<Reminder> reminders) {
        List<Reminder> oldReminders = reminderDao.getAllReminderForSchedule(scheduleId);
        if (oldReminders != null) {
            removeAllRemindersForSchedule(scheduleId);
        }

        return addRemindersForSchedule(scheduleId, reminders);
    }

}
