package org.manlier.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.*;

/**
 * 偏好设置
 * Created by manlier on 2017/6/13.
 */
public class Preferences {

    private String preferencesUuid;

    @JsonIgnore
    private String userUuid;

    // 每周开始于
    private DayOfWeek startDayOfWeek;

    // 每日提醒时间
    private LocalTime dailyRemindTime;

    // 是否开启每日提醒
    private boolean dailyRemindToggle;

    // 默认在开始之前多久提醒
    private Duration defaultRemindBefore;

    // 默认优先级
    private Integer defaultPriority;

    // 用户的时区
    private ZoneId timeZone;

    public String getPreferencesUuid() {
        return preferencesUuid;
    }

    public void setPreferencesUuid(String preferencesUuid) {
        this.preferencesUuid = preferencesUuid;
    }

    public ZoneId getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(ZoneId timeZone) {
        this.timeZone = timeZone;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public void setDefaultPriority(Integer defaultPriority) {
        this.defaultPriority = defaultPriority;
    }

    public DayOfWeek getStartDayOfWeek() {
        return startDayOfWeek;
    }

    public void setStartDayOfWeek(DayOfWeek startDayOfWeek) {
        this.startDayOfWeek = startDayOfWeek;
    }

    public LocalTime getDailyRemindTime() {
        return dailyRemindTime;
    }

    public void setDailyRemindTime(LocalTime dailyRemindTime) {
        this.dailyRemindTime = dailyRemindTime;
    }

    public Duration getDefaultRemindBefore() {
        return defaultRemindBefore;
    }

    public void setDefaultRemindBefore(Duration defaultRemindBefore) {
        this.defaultRemindBefore = defaultRemindBefore;
    }

    public Integer getDefaultPriority() {
        return defaultPriority;
    }

    public void setDefaultPriority(int defaultPriority) {
        this.defaultPriority = defaultPriority;
    }


    public boolean isDailyRemindToggle() {
        return dailyRemindToggle;
    }

    public void setDailyRemindToggle(boolean dailyRemindToggle) {
        this.dailyRemindToggle = dailyRemindToggle;
    }

    public ZonedDateTime getZonedDateTime() {
        return ZonedDateTime.of(LocalDate.now(), dailyRemindTime, timeZone);
    }

    @Override
    public String toString() {
        return "Preferences{" +
                "preferencesUuid='" + preferencesUuid + '\'' +
                ", userUuid='" + userUuid + '\'' +
                ", startDayOfWeek=" + startDayOfWeek +
                ", dailyRemindTime=" + dailyRemindTime +
                ", dailyRemindToggle=" + dailyRemindToggle +
                ", defaultRemindBefore=" + defaultRemindBefore +
                ", defaultPriority=" + defaultPriority +
                ", timeZone=" + timeZone +
                '}';
    }
}
