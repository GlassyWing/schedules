package org.manlier.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.manlier.contracts.DateFormatContract;
import org.manlier.contracts.ScheduleStatus;
import org.manlier.customs.json.ScheduleStatusSerializer;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

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

    private boolean isAllDay = true;

    private Integer priority = 0;

    private boolean deleted = false;

    private Float progress = 0f;

    private String title;

    private String summ;

    private String content;

    @JsonSerialize(using = ScheduleStatusSerializer.class)
    private ScheduleStatus status = ScheduleStatus.UNDONE;

    public Schedule() {
    }

    public Schedule(String title) {
        this.title = title;
    }

    public Schedule(String title, String summ, String content) {
        this.title = title;
        this.summ = summ;
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

    public boolean isAllDay() {
        return isAllDay;
    }

    public void setAllDay(boolean allDay) {
        isAllDay = allDay;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
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

    public String getSumm() {
        return summ;
    }

    public void setSumm(String summ) {
        this.summ = summ;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Schedule schedule = (Schedule) o;

        if (isAllDay != schedule.isAllDay) return false;
        if (deleted != schedule.deleted) return false;
        if (!scheduleUuid.equals(schedule.scheduleUuid)) return false;
        if (!userUuid.equals(schedule.userUuid)) return false;
        if (parentUuid != null ? !parentUuid.equals(schedule.parentUuid) : schedule.parentUuid != null) return false;
        if (groupUuid != null ? !groupUuid.equals(schedule.groupUuid) : schedule.groupUuid != null) return false;
        if (locationUuid != null ? !locationUuid.equals(schedule.locationUuid) : schedule.locationUuid != null)
            return false;
        if (!createTime.equals(schedule.createTime)) return false;
        if (!startTime.equals(schedule.startTime)) return false;
        if (completeTime != null ? !completeTime.equals(schedule.completeTime) : schedule.completeTime != null)
            return false;
        if (!dueTime.equals(schedule.dueTime)) return false;
        if (!modifyTime.equals(schedule.modifyTime)) return false;
        if (!sortOrder.equals(schedule.sortOrder)) return false;
        if (!priority.equals(schedule.priority)) return false;
        if (!progress.equals(schedule.progress)) return false;
        if (!title.equals(schedule.title)) return false;
        if (summ != null ? !summ.equals(schedule.summ) : schedule.summ != null) return false;
        if (content != null ? !content.equals(schedule.content) : schedule.content != null) return false;
        return status == schedule.status;
    }

    @Override
    public int hashCode() {
        int result = scheduleUuid.hashCode();
        result = 31 * result + userUuid.hashCode();
        result = 31 * result + (parentUuid != null ? parentUuid.hashCode() : 0);
        result = 31 * result + (groupUuid != null ? groupUuid.hashCode() : 0);
        result = 31 * result + (locationUuid != null ? locationUuid.hashCode() : 0);
        result = 31 * result + createTime.hashCode();
        result = 31 * result + startTime.hashCode();
        result = 31 * result + (completeTime != null ? completeTime.hashCode() : 0);
        result = 31 * result + dueTime.hashCode();
        result = 31 * result + modifyTime.hashCode();
        result = 31 * result + sortOrder.hashCode();
        result = 31 * result + (isAllDay ? 1 : 0);
        result = 31 * result + priority.hashCode();
        result = 31 * result + (deleted ? 1 : 0);
        result = 31 * result + progress.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + (summ != null ? summ.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + status.hashCode();
        return result;
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
                ", summ='" + summ + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                '}';
    }
}
