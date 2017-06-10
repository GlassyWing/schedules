package org.manlier.providers;

import org.manlier.beans.Repetition;
import org.manlier.beans.Schedule;
import org.manlier.contracts.ScheduleStatus;
import org.manlier.customs.temporal.TheBeginOfDayAdjuster;
import org.manlier.customs.temporal.TheEndOfDayAdjuster;
import org.manlier.models.RepetitionDao;
import org.manlier.models.ScheduleDao;
import org.manlier.providers.interfaces.IProjectService;
import org.manlier.providers.interfaces.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private ScheduleDao scheduleDao;

    private RepetitionDao repetitionDao;

    private IProjectService projectService;


    @Autowired
    public ScheduleService(ScheduleDao scheduleDao, RepetitionDao repetitionDao) {
        this.scheduleDao = scheduleDao;
        this.repetitionDao = repetitionDao;
    }

    @Autowired
    public void setProjectService(IProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * 为用户添加日程
     *
     * @param userId   用户id
     * @param schedule 日程
     * @return 添加结果
     */
    @Override
    public boolean addScheduleForUser(String userId, Schedule schedule) {
        // 如果该日程不属于任何清单，则设置为收集箱
        if (schedule.getGroupUuid() == null) {
            schedule.setGroupUuid(projectService.getInboxPrefix() + userId);
        }

        if (schedule.isAllDay()) {
            Instant srcStartTime = schedule.getStartTime();
            if (srcStartTime != null) {
                schedule.setDueTime(schedule.getStartTime()
                        .plus(Duration.ofDays(1)));
            }
        }

        return scheduleDao.addScheduleForUser(userId, schedule) == 1;
    }

    /**
     * 设置开始与到期日期
     *
     * @param scheduleId 日程id
     * @param startTime  开始日期
     * @param dueTime    到期日期
     * @return 设置操作结果
     */
    @Override
    @Transactional
    public boolean setStartTimeAndDueTime(String scheduleId, Instant startTime, Instant dueTime) {

        if (!startTime.isBefore(dueTime)) {
            throw new IllegalStateException("The start time: " + startTime + " is after the due time: " + dueTime);
        }

        Schedule schedule = getScheduleById(scheduleId);

        // 如果日程为全天的话，重新配置到期日期
        if (schedule.isAllDay()) {
            dueTime = startTime.with(new TheEndOfDayAdjuster());
        }

        return scheduleDao.setStartAndDueTime(scheduleId, startTime, dueTime) == 1;
    }

    /**
     * 获得日程
     *
     * @param scheduleId 日程id
     * @return 日程
     */
    @Override
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
    public boolean updateSchedule(Schedule schedule) {
        return scheduleDao.updateSchedule(schedule) == 1;
    }

    /**
     * 删除日程
     *
     * @param scheduleId 日程id
     * @return 删除结果
     */
    @Override
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
    public List<Schedule> getAllSchedulesForUser(String userId) {
        return scheduleDao.getAllSchedulesForUser(userId);
    }

    /**
     * 回收日程
     *
     * @param scheduleId 日程id
     * @return 回收结果
     */
    @Override
    public boolean recycleSchedule(String scheduleId) {
        return scheduleDao.recycleSchedule(scheduleId) == 1;
    }

    /**
     * 恢复日程
     *
     * @param scheduleId 日程id
     * @return 恢复结果
     */
    @Override
    public boolean restoreSchedule(String scheduleId) {
        return scheduleDao.restoreSchedule(scheduleId) == 1;
    }

    /**
     * 完成日程
     *
     * @param scheduleId 日程id
     * @return 完成结果
     */
    @Override
    @Transactional
    public boolean completeSchedule(String scheduleId) {

        boolean isCompleted;

        // 将当前日程设为已完成
        isCompleted = scheduleDao.completeSchedule(scheduleId) == 1;

        Repetition repetition = repetitionDao
                .getRepetitionByScheduleId(scheduleId);

        // 如果需要重复, 试图创建下一个日程
        if (repetition != null) {

            // 获得当前日程
            Schedule schedule = getScheduleById(scheduleId);

            // 试图建立新的日程
            if (buildRepeatSchedule(schedule, repetition)) {
                // 建立成功则添加日程
                addScheduleForUser(schedule.getUserUuid(), schedule);
                // 为新的日程设置重复
                setRepetitionForSchedule(schedule.getScheduleUuid()
                        , repetition);
                // 取消上一次日程的重复
                cancelRepetition(scheduleId);
            }
        }
        return isCompleted;
    }

    /**
     * 建立下一次日程
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
        return true;
    }

    /**
     * 设置日程为未完成
     *
     * @param scheduleId 日程id
     * @return 设置结果
     */
    @Override
    public boolean incompleteSchedule(String scheduleId) {
        return scheduleDao.incompleteSchedule(scheduleId) == 1;
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

    /**
     * 查找指定时间间隔内的日程
     *
     * @param userId    用户id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 日程
     */
    @Override
    public List<Schedule> findSchedulesDuringTimePeriod(String userId, Instant beginTime, Instant endTime) {
        return scheduleDao.findSchedulesDuringTimePeriod(userId, beginTime, endTime);
    }


    /**
     * 获得日程的重复规则
     *
     * @param scheduleId 日程id
     * @return 重复规则
     */
    @Override
    public Repetition getRepetitionForSchedule(String scheduleId) {
        return repetitionDao.getRepetitionByScheduleId(scheduleId);
    }

    /**
     * 取消日程的重复
     *
     * @param scheduleId 日程id
     * @return 取消结果
     */
    @Override
    public boolean cancelRepetition(String scheduleId) {
        return repetitionDao.deleteRepetition(scheduleId) == 1;
    }

    /**
     * 变更重复规则
     *
     * @param repetition 重复规则
     * @return 变更结果
     */
    @Override
    public boolean changeRepetition(Repetition repetition) {
        return repetitionDao.updateRepetition(repetition) == 1;
    }

    /**
     * 为日程设置重复
     *
     * @param scheduleId 日程id
     * @param repetition 重复规则
     * @return 设置结果
     */
    @Override
    public boolean setRepetitionForSchedule(String scheduleId, Repetition repetition) {
        Schedule schedule = getScheduleById(scheduleId);
        if (schedule.getStartTime() == null)
            throw new IllegalStateException("The start time of schedule must be set");
        return repetitionDao.addRepetitionForSchedule(scheduleId, repetition) == 1;
    }
}
