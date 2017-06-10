package org.manlier.beans;

/**
 * 日程提醒
 * Created by manlier on 2017/6/4.
 */

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Duration;

/**
 * 描述日程的一个提醒时间
 */
public class Reminder {

    private String reminderUuid;

    private String scheduleUuid;

    private Duration duration;

    public Reminder() {
    }

    public Reminder(String duration) {
        this.duration = Duration.parse(duration);
    }

    public String getReminderUuid() {
        return reminderUuid;
    }

    public void setReminderUuid(String reminderUuid) {
        this.reminderUuid = reminderUuid;
    }

    public String getScheduleUuid() {
        return scheduleUuid;
    }

    public void setScheduleUuid(String scheduleUuid) {
        this.scheduleUuid = scheduleUuid;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "reminderUuid='" + reminderUuid + '\'' +
                ", scheduleUuid='" + scheduleUuid + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
