package org.manlier.providers;

import org.manlier.beans.Reminder;
import org.manlier.providers.interfaces.INotificationService;
import org.manlier.providers.interfaces.IScheduleService;
import org.manlier.providers.interfaces.IWebSocketService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.quartz.JobBuilder.*;

import static org.quartz.TriggerBuilder.*;

/**
 * 通知服务
 * Created by manlier on 2017/6/7.
 */
@Service
public class NotificationService implements Job, INotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Resource
    private IWebSocketService webSocketService;

    @Resource
    private IScheduleService scheduleService;

    @Resource
    private Scheduler scheduler;

    // 作业的执行，需要知道日程id
    private String scheduleId;

    public NotificationService() {
        //        logger.info("The notification job has be constructed!");
    }

    @PostConstruct
    public void init() {
        logger.info("Notification service startup");
    }


    // 当作业创建时，传入日程id
    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        webSocketService.sendMessage(scheduleService.getScheduleById(scheduleId).getTitle());
    }

    /**
     * 安排通知作业
     */
    @Override
    public void scheduleNotificationJob(String jobId, Instant baseDate, List<Reminder> reminders) throws SchedulerException {

        logger.info("Start to schedule notification job: {}", jobId);

        // 新建作业
        JobDetail job = newJob(this.getClass())
                .withIdentity(jobId)
                .usingJobData("scheduleId", jobId)
                .build();

        // 获取触发器
        List<Trigger> triggers = reminders.stream()
                .map(reminder -> newTrigger()
                        .withIdentity(reminder.getReminderUuid())
                        .startAt(Date.from(baseDate
                                .plus(reminder.getDuration())))
                        .forJob(job)
                        .build())
                .collect(Collectors.toList());

        // 安排作业
        for (Trigger trigger :
                triggers) {
            if (!scheduler.checkExists(job.getKey())) {
                scheduler.scheduleJob(job, trigger);
            } else {
                scheduler.scheduleJob(trigger);
            }
        }

        logger.info("Schedule notification job {} done!", jobId);
    }

    /**
     * 移除指定的通知作业
     *
     * @param jobId 日程id
     * @throws SchedulerException 调度器异常
     */
    @Override
    public void removeNotificationJob(String jobId) throws SchedulerException {
        JobKey jobKey = new JobKey(jobId);
        if (scheduler.checkExists(jobKey))
            scheduler.deleteJob(jobKey);
    }

    /**
     * 移除指定的提醒通知
     *
     * @param ids 触发器id
     * @throws SchedulerException 调度器异常
     */
    public void removeTriggers(String... ids) throws SchedulerException {
        List<TriggerKey> triggerKeys = Arrays.stream(ids)
                .map(TriggerKey::new).collect(Collectors.toList());
        scheduler.unscheduleJobs(triggerKeys);
    }

    /**
     * 基于新的时间调整触发器的触发时间
     *
     * @param jobId    作业id
     * @param baseDate 新的基准时间
     * @throws SchedulerException 调度器异常
     */
    @SuppressWarnings("unchecked")
    @Override
    public void rescheduleNotificationJobBaseNewDate(String jobId, Instant baseDate) throws SchedulerException {

        JobKey jobKey = new JobKey(jobId);
        if (!scheduler.checkExists(jobKey)) {
            return;
        }

        logger.info("Try to update triggers for job: " + jobId);
        List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);

        for (Trigger trigger : triggers) {
            Trigger newTrigger = trigger.getTriggerBuilder()
                    .startAt(Date.from(baseDate))
                    .build();
            scheduler.rescheduleJob(trigger.getKey(), newTrigger);
        }
        logger.info("Update triggers done.");
    }
}
