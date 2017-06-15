package org.manlier.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.manlier.contracts.ScheduleStatus;
import org.manlier.customs.json.ScheduleStatusSerializer;
import org.manlier.customs.temporal.TheBeginOfDayAdjuster;
import org.manlier.customs.temporal.TheEndOfDayAdjuster;

import java.time.Instant;
import java.util.List;

/**
 * 日程类
 * Created by manlier on 2017/6/4.
 */
public class Schedule {

    private String scheduleUuid;

    private String userUuid;

    private String parentUuid;

    private String groupUuid;

    private String locationUuid;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant createTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant completeTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant dueTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant modifyTime;

    private Integer sortOrder = 0;

    private Boolean isAllDay;

    private Integer priority = 0;

    private Boolean deleted = false;

    private Float progress = 0f;

    private String title;

    private String content;

    @JsonSerialize(using = ScheduleStatusSerializer.class)
    private ScheduleStatus status = ScheduleStatus.UNDONE;

    private Repetition repetition;

    // 此提醒时间用于显示延期后的提醒时间
    private Instant remindTime;

    private List<Reminder> reminders;

    public Schedule() {
    }

    public Schedule(String title) {
        this.title = title;
    }

    public Schedule(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getScheduleUuid() {
        return scheduleUuid;
    }

    public void setScheduleUuid(String scheduleUuid) {
        this.scheduleUuid = scheduleUuid;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getParentUuid() {
        return parentUuid;
    }

    public void setParentUuid(String parentUuid) {
        this.parentUuid = parentUuid;
    }

    public String getGroupUuid() {
        return groupUuid;
    }

    public void setGroupUuid(String groupUuid) {
        this.groupUuid = groupUuid;
    }

    public String getLocationUuid() {
        return locationUuid;
    }

    public void setLocationUuid(String locationUuid) {
        this.locationUuid = locationUuid;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Instant completeTime) {
        this.completeTime = completeTime;
    }

    public Instant getDueTime() {
        return dueTime;
    }

    public void setDueTime(Instant dueTime) {
        this.dueTime = dueTime;
    }

    public Instant getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean isAllDay() {
        return isAllDay;
    }

    public void setAllDay(Boolean allDay) {
        isAllDay = allDay;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Float getProgress() {
        return progress;
    }

    public void setProgress(Float progress) {
        this.progress = progress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(ScheduleStatus status) {
        this.status = status;
    }

    public Repetition getRepetition() {
        return repetition;
    }

    public void setRepetition(Repetition repetition) {
        this.repetition = repetition;
    }

    public List<Reminder> getReminders() {
        return reminders;
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
    }

    public Instant getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(Instant remindTime) {
        this.remindTime = remindTime;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "scheduleUuid='" + scheduleUuid + '\'' +
                ", userUuid='" + userUuid + '\'' +
                ", parentUuid='" + parentUuid + '\'' +
                ", groupUuid='" + groupUuid + '\'' +
                ", locationUuid='" + locationUuid + '\'' +
                ", createTime=" + createTime +
                ", startTime=" + startTime +
                ", completeTime=" + completeTime +
                ", dueTime=" + dueTime +
                ", modifyTime=" + modifyTime +
                ", sortOrder=" + sortOrder +
                ", isAllDay=" + isAllDay +
                ", priority=" + priority +
                ", deleted=" + deleted +
                ", progress=" + progress +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", repetition=" + repetition +
                ", remindTime=" + remindTime +
                ", reminders=" + reminders +
                '}';
    }
}
