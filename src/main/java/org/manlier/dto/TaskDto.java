package org.manlier.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.manlier.beans.Reminder;
import org.manlier.beans.Repetition;
import org.manlier.contracts.ScheduleStatus;
import org.manlier.contracts.SysConst;
import org.manlier.customs.json.ScheduleStatusSerializer;
import org.manlier.customs.temporal.TheBeginOfDayAdjuster;
import org.manlier.customs.temporal.TheEndOfDayAdjuster;

import java.time.Instant;
import java.util.List;

/**
 * 传输日程任务的dto
 * Created by manlier on 2017/6/14.
 */
public class TaskDto {

    private String scheduleUuid;

    private String userUuid;

    private String parentUuid;

    private String groupUuid;

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

    private String summary;

    private String content;

    @JsonSerialize(using = ScheduleStatusSerializer.class)
    private ScheduleStatus status = ScheduleStatus.UNDONE;

    private Repetition repetition;

    // 此提醒时间用于显示延期后的提醒时间
    private Instant remindTime;

    private List<Reminder> reminders;

    public TaskDto() {
    }

    public TaskDto(String title) {
        this.title = title;
    }

    public TaskDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 检查当前任务是否为全天
    public Boolean checkIsAllDay() {
        if (startTime == null) return null;

        Instant beginTime = startTime.with(new TheBeginOfDayAdjuster());
        Instant endTime = startTime.with(new TheEndOfDayAdjuster());
        return startTime.equals(beginTime) && dueTime.equals(endTime);
    }

    /**
     * 基于任务内容创建摘要
     */
    public void setSummaryBaseContent() {
        if (content != null) {
            if (content.length() > SysConst.SUMMARY_LENGTH)
                summary = content.substring(0, SysConst.SUMMARY_LENGTH);
            else summary = content;
        }
    }


    /*--------------Setters & Getters ------------------------*/

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

    public Boolean getAllDay() {
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

    public Boolean getDeleted() {
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


    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public Instant getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(Instant remindTime) {
        this.remindTime = remindTime;
    }

    public List<Reminder> getReminders() {
        return reminders;
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
    }

    @Override
    public String toString() {
        return "TaskDto{" +
                "scheduleUuid='" + scheduleUuid + '\'' +
                ", userUuid='" + userUuid + '\'' +
                ", parentUuid='" + parentUuid + '\'' +
                ", groupUuid='" + groupUuid + '\'' +
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
                ", summary='" + summary + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", repetition=" + repetition +
                ", remindTime=" + remindTime +
                ", reminders=" + reminders +
                '}';
    }
}
