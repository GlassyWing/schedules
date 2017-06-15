package org.manlier.controllers;

import org.manlier.beans.Preferences;
import org.manlier.beans.Reminder;
import org.manlier.beans.Schedule;
import org.manlier.contracts.ScheduleStatus;
import org.manlier.contracts.SysConst;
import org.manlier.dto.TaskDto;
import org.manlier.dto.base.BaseResult;
import org.manlier.providers.interfaces.*;
import org.manlier.providers.listeners.OnRemindersChangeListener;
import org.manlier.providers.listeners.OnScheduleStatusChangeListener;
import org.manlier.utils.EntityToDtoHelper;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static org.manlier.dto.base.BaseResult.*;

import static org.manlier.utils.EntityToDtoHelper.*;

import static java.lang.Math.*;

/**
 * 日程控制器
 * Created by manlier on 2017/6/8.
 */
@Controller
@RequestMapping("/api/task")
public class TaskController implements
        OnRemindersChangeListener, OnScheduleStatusChangeListener {

    private Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Resource
    private IScheduleService scheduleService;

    @Resource
    private IPreferencesService preferencesService;

    @Resource
    private IUserService userService;

    @Resource
    private INotificationService notificationService;

    /**
     * 添加任务API
     *
     * @param taskDto 任务DTO对象
     * @return 已添加的任务DTO对象
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<TaskDto> addTask(@RequestBody TaskDto taskDto) {
        Schedule schedule = convertToEntity(taskDto);
        schedule = scheduleService.addScheduleForUser(userService.getCurrentUser().getUserUuid(), schedule);
        if (schedule != null)
            return success(convertToDto(schedule));
        return fail(null);
    }

    /**
     * 更新任务API
     *
     * @param taskDto 任务DTO对象
     * @return 已更新的任务DTO对象
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public BaseResult<TaskDto> updateTask(@RequestBody TaskDto taskDto) {
        Schedule schedule = convertToEntity(taskDto);
        schedule = scheduleService.updateSchedule(schedule);
        if (schedule != null)
            return success(convertToDto(schedule));
        return fail(null);
    }

    /**
     * 回收日程
     *
     * @param taskId 任务id
     * @return 已回收的任务DTO对象
     */
    @RequestMapping(value = "/{taskId}/recycle", method = RequestMethod.PUT)
    @ResponseBody
    public BaseResult<TaskDto> recycleTask(@PathVariable("taskId") String taskId) {
        Schedule schedule = scheduleService.recycleSchedule(taskId);
        if (schedule != null)
            return success(convertToDto(schedule));
        return fail(null);
    }

    /**
     * 恢复日程
     *
     * @param taskId 任务id
     * @return 已从回收站恢复的日程
     */
    @RequestMapping(value = "/{taskId}/restore", method = RequestMethod.PUT)
    @ResponseBody
    public BaseResult<TaskDto> restoreTask(@PathVariable("taskId") String taskId) {
        Schedule schedule = scheduleService.restoreSchedule(taskId);
        if (schedule != null)
            return success(convertToDto(schedule));
        return fail(null);
    }

    /**
     * 完成任务
     *
     * @param taskId 任务id
     * @return 已完成的任务
     */
    @RequestMapping(value = "/{taskId}/complete", method = RequestMethod.PUT)
    @ResponseBody
    public BaseResult<TaskDto> completeTask(@PathVariable("taskId") String taskId) {
        Schedule schedule = scheduleService.completeSchedule(taskId);
        if (schedule != null)
            return success(convertToDto(schedule));
        return fail(null);
    }

    /**
     * 设置任务为未完成
     *
     * @param taskId 任务id
     * @return 未完成的任务
     */
    @RequestMapping(value = "/{taskId}/incomplete", method = RequestMethod.PUT)
    @ResponseBody
    public BaseResult<TaskDto> incompleteTask(@PathVariable("taskId") String taskId) {
        Schedule schedule = scheduleService.incompleteSchedule(taskId);
        if (schedule != null)
            return success(convertToDto(schedule));
        return fail(null);
    }

    @RequestMapping(value = "/{taskId}", method = RequestMethod.DELETE)
    @ResponseBody
    public BaseResult<Void> deleteTask(@PathVariable("taskId") String taskId) {
        boolean deleted = scheduleService.deleteSchedule(taskId);
        if (deleted) return success();
        return fail();
    }

    /**
     * 获取任务详情
     *
     * @param taskId 任务id
     * @return 任务
     */
    @RequestMapping(value = "/{taskId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<TaskDto> getTask(@PathVariable("taskId") String taskId) {
        Schedule schedule = scheduleService.getScheduleById(taskId);
        if (schedule != null)
            return success(convertToDto(schedule));
        return fail(null);
    }

    /**
     * 获得已完成的任务
     *
     * @param from  起始时刻
     * @param to    最后时刻
     * @param limit 数量限制
     * @return 任务
     */
    @RequestMapping(value = "/completed", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<List<TaskDto>> getCompletedTask(@RequestParam("from") Instant from
            , @RequestParam("to") Instant to
            , @RequestParam("limit") int limit) {
        limit = min(limit, SysConst.MAX_LIMIT);
        System.out.println("" + from + to + limit);
        String userId = userService.getCurrentUser().getUserUuid();
        List<TaskDto> tasks = scheduleService.getAllSchedulesCompletedForUserFrom(userId, from, to, limit)
                .stream().map(EntityToDtoHelper::convertToDto).collect(Collectors.toList());
        return success(tasks);
    }

    /*---------------------------Callback---------------------------*/

    @Override
    public void onDeleteReminders(String... reminderIds) {
        logger.info("Has received 'delete reminders' action");
    }

    /**
     * 当清空提醒时的回调
     *
     * @param scheduleId 日程id
     */
    @Override
    public void onClearReminders(String scheduleId) {
        logger.info("Has received 'clear reminders' action");
        try {
            logger.info("Try to clear notification jobs for {}", scheduleId);
            notificationService.removeNotificationJob(scheduleId);
            logger.info("Clear notification jobs for {} done!", scheduleId);
        } catch (SchedulerException e) {
            logger.error("Remove notification error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 当设置提醒时的回调
     *
     * @param scheduleId 日程id
     * @param reminders  提醒
     */
    @Override
    public void onSetReminders(String scheduleId, List<Reminder> reminders) {
        logger.info("Has received 'set reminders' action");
        Schedule schedule = scheduleService.getScheduleById(scheduleId);
        if (schedule != null && hasNotifyNecessary(schedule)) {
            try {
                logger.info("Try to add notifications job for schedule: {}", scheduleId);
                notificationService
                        .scheduleNotificationJob(scheduleId
                                , getNotificationBaseDate(schedule)
                                , reminders);
                logger.info("Add notifications job for schedule: {} done!", scheduleId);
            } catch (SchedulerException e) {
                logger.error("Some errors occurs when scheduling notification jobs", e);
                throw new RuntimeException(e);
            }
        }
        logger.info("There is no necessary to create notification job!");
    }

    /**
     * 判断当前的日程是否有必要进行通知
     *
     * @param schedule 日程
     * @return 有必要为true，没有为false
     */
    private boolean hasNotifyNecessary(Schedule schedule) {

        // 如果根本没有设置提醒
        if (schedule.getReminders().size() == 0) {
            logger.info("The schedule has not set reminders");
            return false;
        }

        // 如果日程已经超期或完成，则没有通知的必要
        if (schedule.getStatus() != ScheduleStatus.UNDONE) {
            logger.info("The schedule is completed or due");
            return false;
        }
        // 如果没有设置日期，则没有通知的必要
        if (schedule.isAllDay() == null) {
            logger.info("The schedule has not set date & time");
            return false;
        }
        // 如果日程的开始日期在这之前，则没有通知的必要
        if (schedule.getStartTime().isBefore(Instant.now())) {
            logger.info("The notification time is invalid");
            return false;
        }
        return true;
    }

    /**
     * 获得通知的基准时间
     *
     * @param schedule 日程
     * @return 基准时间
     */
    private Instant getNotificationBaseDate(Schedule schedule) {
        // 如果是全天的话，则以用户偏好设置中的时间为准
        if (schedule.isAllDay()) {
            Preferences preferences = preferencesService.getPreferencesForUser(userService.getCurrentUser().getUserUuid());
            return preferences.getZonedDateTime().toInstant();
        } else {
            return schedule.getStartTime();
        }
    }

    /**
     * 当设置日程为未完成时的回调
     *
     * @param schedule 日程
     */
    @Override
    public void onIncompleteSchedule(Schedule schedule) {
        onSetReminders(schedule.getScheduleUuid(), schedule.getReminders());
    }

    /**
     * 当回收日程时的回调
     *
     * @param schedule 日程
     */
    @Override
    public void onRecycleSchedule(Schedule schedule) {
        onClearReminders(schedule.getScheduleUuid());
    }

    /**
     * 当恢复日程时的回调
     *
     * @param schedule 日程
     */
    @Override
    public void onRestoreSchedule(Schedule schedule) {
        onSetReminders(schedule.getScheduleUuid(), schedule.getReminders());
    }
}
