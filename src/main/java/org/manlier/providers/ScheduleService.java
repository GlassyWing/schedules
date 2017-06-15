package org.manlier.providers;

import org.manlier.beans.Repetition;
import org.manlier.beans.Schedule;
import org.manlier.contracts.ScheduleStatus;
import org.manlier.models.ScheduleDao;
import org.manlier.providers.interfaces.*;
import org.manlier.providers.listeners.OnScheduleStatusChangeListener;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.InvalidParameterException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * 日程服务
 * Created by manlier on 2017/6/6.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class ScheduleService implements IScheduleService {

    // 日程数据库访问对象
    private ScheduleDao scheduleDao;

    // 重复规则数据库访问对象
    @Resource
    private IRepetitionService repetitionService;

    // 清单服务
    @Resource
    private IProjectService projectService;

    // 提醒服务
    @Resource
    private IReminderService reminderService;

    @Resource
    private OnScheduleStatusChangeListener listener;

    // 日志记录
    private Logger logger = LoggerFactory.getLogger(ScheduleService.class);


    @Autowired
    public ScheduleService(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

    /**
     * 为用户添加日程
     *
     * @param userId   用户id
     * @param schedule 日程
     * @return 添加结果
     */
    @Override
    @Transactional
    public Schedule addScheduleForUser(String userId, Schedule schedule) {
        // 如果该日程不属于任何清单，则设置为收集箱
        if (schedule.getGroupUuid() == null) {
            schedule.setGroupUuid(projectService.getInboxPrefix() + userId);
        }

        boolean affected =
                scheduleDao.addScheduleForUser(userId, schedule) == 1;

        if (!affected) return null;

        // 如果当前日程设置了日期（不管有没有设置时间）
        if (schedule.isAllDay() != null) {

            // 如果配置了提醒，则添加提醒
            if (schedule.getReminders() != null) {
                reminderService.addRemindersForSchedule(schedule.getScheduleUuid(), schedule.getReminders());
            }

            // 如果配置了重复，则设置重复
            if (schedule.getRepetition() != null) {
                repetitionService.setRepetitionForSchedule(schedule.getScheduleUuid(), schedule.getRepetition());
            }
        }

        logger.info("Schedule {} has added.", schedule.getScheduleUuid());

        return schedule;
    }

    /**
     * 获得日程
     *
     * @param scheduleId 日程id
     * @return 日程
     */
    @Override
    @Transactional
    public Schedule getScheduleById(String scheduleId) {
        return scheduleDao.getScheduleByScheduleId(scheduleId);
    }

    /**
     * 更新日程
     *
     * @param schedule 日程
     * @return 更新结果
     */
    @Override
    @Transactional
    public Schedule updateSchedule(Schedule schedule) {

        boolean updated = scheduleDao.updateSchedule(schedule) == 1;

        if (!updated) return null;

        // 如果无重复规则
        if (schedule.getRepetition() == null) {
            // 取消重复
            repetitionService
                    .cancelRepetitionForSchedule(schedule.getScheduleUuid());
        } else {
            // 设置重复规则
            repetitionService
                    .setRepetitionForSchedule(schedule.getScheduleUuid()
                            , schedule.getRepetition());
        }

        // 如果取消了所有提醒
        if (schedule.getReminders() == null) {
            // 清空提醒
            reminderService
                    .removeAllRemindersForSchedule(schedule.getScheduleUuid());
        } else {
            // 设置提醒
            reminderService
                    .setRemindersForSchedule(schedule.getScheduleUuid()
                            , schedule.getReminders());
        }

        logger.info("Schedule {} has be updated", schedule.getScheduleUuid());

        return schedule;
    }

    /**
     * 删除日程
     *
     * @param scheduleId 日程id
     * @return 删除结果
     */
    @Override
    @Transactional
    public boolean deleteSchedule(String scheduleId) {
        return scheduleDao.deleteSchedule(scheduleId) == 1;
    }

    /**
     * 获得当前用户的所有日程
     *
     * @param userId 用户id
     * @return 日程
     */
    @Override
    @Transactional
    public List<Schedule> getAllSchedulesForUser(String userId) {
        return scheduleDao.getAllSchedulesForUser(userId);
    }

    /**
     * 根据指定的日期段获取已完成的日程
     *
     * @param userId 用户id
     * @param from   开始日期
     * @param to     结束日期
     * @param limit  数量现在
     * @return 日程
     */
    @Override
    public List<Schedule> getAllSchedulesCompletedForUserFrom(String userId, Instant from, Instant to, int limit) {
        return scheduleDao.getAllSchedulesCompletedForUserFrom(userId, from, to, limit);
    }

    /**
     * 获取所有未完成的日程
     *
     * @param userId 用户id
     * @return 未完成的日程
     */
    @Override
    public List<Schedule> getAllSchedulesUncompletedForUser(String userId) {
        return scheduleDao.getAllSchedulesUncompletedForUser(userId);
    }

    /**
     * 回收日程
     *
     * @param scheduleId 日程id
     * @return 回收结果
     */
    @Override
    @Transactional
    public Schedule recycleSchedule(String scheduleId) {
        // 回收日程
        if (scheduleDao.recycleSchedule(scheduleId) != 1)
            return null;

        Schedule schedule = getScheduleById(scheduleId);

        listener.onRecycleSchedule(schedule);

        return schedule;
    }

    /**
     * 恢复日程
     *
     * @param scheduleId 日程id
     * @return 恢复结果
     */
    @Override
    @Transactional
    public Schedule restoreSchedule(String scheduleId) {
        // 恢复日程
        if (scheduleDao.restoreSchedule(scheduleId) != 1)
            return null;

        Schedule schedule = getScheduleById(scheduleId);

        listener.onRecycleSchedule(schedule);
        return schedule;
    }

    /**
     * 设置日程为已完成
     *
     * @param scheduleId 日程id
     * @return 完成后的日程或者新建的日程
     */
    @Override
    @Transactional
    public Schedule completeSchedule(String scheduleId) {

        boolean completed;

        // 将当前日程设为已完成
        completed = scheduleDao.completeSchedule(scheduleId) == 1;

        if (!completed) {
            return null;
        }

        // 获得当前日程
        Schedule schedule = getScheduleById(scheduleId);

        logger.info("To be complete: {}", schedule);

        // 取消上一次日程的重复与提醒
        repetitionService.cancelRepetitionForSchedule(scheduleId);
        reminderService.removeAllRemindersForSchedule(scheduleId);

        // 获得有关当前日程的重复规则
        Repetition repetition = schedule.getRepetition();

        // 如果需要重复, 试图创建下一个日程
        if (repetition != null) {

            // 试图建立新的日程
            if (buildRepeatSchedule(schedule, repetition)) {
                // 建立成功则添加新的日程
                addScheduleForUser(schedule.getUserUuid(), schedule);
            }
        }

        return schedule;
    }

    /**
     * 基于重复规则建立指定日程后的下一个日程
     *
     * @param schedule   待重复的日程
     * @param repetition 重复规则
     */
    private boolean buildRepeatSchedule(Schedule schedule, Repetition repetition) {

        Instant baseTime;
        switch (repetition.getRepeatOn()) {
            case DUE:
                baseTime = schedule.getStartTime();
                break;
            case DONE:
                baseTime = schedule.getCompleteTime();
                break;
            default:
                throw new InvalidParameterException("The value of repeatOn is only be DUE or DONE");
        }

        Instant newStartTime = repetition
                .getCron()
                .getNextValidTimeAfter(Date.from(baseTime))
                .toInstant();

        Instant newDueTime = newStartTime
                .plus(Duration.between(schedule.getStartTime()
                        , schedule.getDueTime()));

        // 如果新的到期日期超出了终止日期，则不创建
        if (repetition.getUntil() != null
                && newDueTime.isAfter(repetition.getUntil()))
            return false;

        schedule.setCompleteTime(null);
        schedule.setStatus(ScheduleStatus.UNDONE);
        schedule.setStartTime(newStartTime);
        schedule.setDueTime(newDueTime);
        schedule.setProgress(0f);
        return true;
    }

    /**
     * 设置日程为未完成
     *
     * @param scheduleId 日程id
     * @return 设置结果
     */
    @Override
    @Transactional
    public Schedule incompleteSchedule(String scheduleId) {
        if (scheduleDao.incompleteSchedule(scheduleId) != 1)
            return null;

        Schedule schedule = getScheduleById(scheduleId);

        listener.onIncompleteSchedule(schedule);

        return schedule;

    }

    /**
     * 获取所有以回收的日程
     *
     * @param userId 用户id
     * @return 日程
     */
    @Override
    public List<Schedule> getAllRecycledSchedulesForUser(String userId) {
        return scheduleDao.getAllSchedulesForUser(userId);
    }

    /**
     * 删除所有已回收的日程
     *
     * @param userId 用户id
     * @return 删除结果
     */
    @Override
    public boolean deleteAllRecycledSchedulesForUser(String userId) {
        return scheduleDao.deleteAllRecycledSchedulesForUser(userId) != -1;
    }

    /**
     * 获取指定清单下的所有日程
     *
     * @param projectId 清单id
     * @return 日程
     */
    @Override
    public List<Schedule> getAllSchedulesByProjectId(String projectId) {
        return scheduleDao.getAllSchedulesByProjectId(projectId);
    }
}
