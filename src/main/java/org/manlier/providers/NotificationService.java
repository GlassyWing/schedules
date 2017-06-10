package org.manlier.providers;

import org.manlier.beans.Reminder;
import org.manlier.providers.interfaces.INotificationService;
import org.manlier.providers.interfaces.IScheduleService;
import org.manlier.providers.interfaces.IWebSocketService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;
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

    private IWebSocketService webSocketService;

    private IScheduleService scheduleService;

    private Scheduler scheduler;

    // 作业的执行，需要知道日程id
    private String scheduleId;

    public NotificationService() {
//        logger.info("The job has be constructed!");
    }

    @PostConstruct
    public void init() {
        logger.info("Notification service startup");
    }

    @Autowired
    public void setScheduleService(IScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Autowired
    public void setWebSocketService(IWebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @Autowired
    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    // 当作业创建时，设置日程id
    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        webSocketService.sendMessage(scheduleService.getScheduleById(scheduleId).getTitle());
    }

    /**
     * 为日程安排提醒
     */
    @Override
    public void scheduleReminderJobForSchedule(String scheduleId, Instant baseDate, List<Reminder> reminders) throws SchedulerException {

        logger.info("Start to schedule reminder job base date: " + baseDate);

        // 新建作业
        JobDetail job = newJob(this.getClass())
                .withIdentity(scheduleId)
                .usingJobData("scheduleId", scheduleId)
                .build();

        // 获取触发器
        List<Trigger> triggers = reminders.stream()
                .map(reminder -> newTrigger().withIdentity(reminder.getReminderUuid())
                        .startAt(Date.from(baseDate.plus(reminder.getDuration())))
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

        logger.info("Schedule jobs done!");
    }

    /**
     * 移除指定作业
     *
     * @param jobKey 作业键
     * @throws SchedulerException 调度异常
     */
    @Override
    public void removeReminderJob(JobKey jobKey) throws SchedulerException {
        if (scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
        }
    }

}
