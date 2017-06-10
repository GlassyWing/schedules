package org.manlier.beans;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.manlier.contracts.ScheduleStatus;
import org.manlier.customs.json.CronDeserializer;
import org.manlier.customs.json.CronSerializer;
import org.manlier.customs.json.ScheduleStatusSerializer;
import org.quartz.CronExpression;

import java.text.ParseException;
import java.time.Instant;

/**
 * Created by manlier on 2017/6/4.
 */

/**
 * 描述一个日程下一次重复执行时的日期
 * 规则
 */
public class Repetition {

    private String repetitionUuid;

    private String scheduleUuid;

    private Instant until;

    @JsonSerialize(using = ScheduleStatusSerializer.class)
    private ScheduleStatus repeatOn;

    @JsonSerialize(using = CronSerializer.class)
    @JsonDeserialize(using = CronDeserializer.class)
    private CronExpression cron;

    public Repetition() {
    }

    public Repetition(ScheduleStatus repeatOn, String cron) throws ParseException {
        this.repeatOn = repeatOn;
        this.cron = new CronExpression(cron);
    }

    public Repetition(ScheduleStatus repeatOn, String cron, Instant until) throws ParseException {
        this.until = until;
        this.repeatOn = repeatOn;
        this.cron = new CronExpression(cron);
    }

    public String getRepetitionUuid() {
        return repetitionUuid;
    }

    public void setRepetitionUuid(String repetitionUuid) {
        this.repetitionUuid = repetitionUuid;
    }

    public String getScheduleUuid() {
        return scheduleUuid;
    }

    public void setScheduleUuid(String scheduleUuid) {
        this.scheduleUuid = scheduleUuid;
    }

    public Instant getUntil() {
        return until;
    }

    public void setUntil(Instant until) {
        this.until = until;
    }

    public ScheduleStatus getRepeatOn() {
        return repeatOn;
    }

    public void setRepeatOn(ScheduleStatus repeatOn) {
        this.repeatOn = repeatOn;
    }

    public CronExpression getCron() {
        return cron;
    }

    public void setCron(CronExpression cron) {
        this.cron = cron;
    }

    @Override
    public String toString() {
        return "Repetition{" +
                "repetitionUuid='" + repetitionUuid + '\'' +
                ", scheduleUuid='" + scheduleUuid + '\'' +
                ", until=" + until +
                ", repeatOn=" + repeatOn +
                ", cron='" + cron + '\'' +
                '}';
    }
}
