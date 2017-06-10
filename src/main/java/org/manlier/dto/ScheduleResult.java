package org.manlier.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.manlier.beans.Schedule;
import org.manlier.dto.base.BaseResult;

import java.util.List;

/**
 * Created by manlier on 2017/6/8.
 */
public class ScheduleResult extends BaseResult<List<Schedule>> {

    public ScheduleResult() {
    }

    public ScheduleResult(Result result, List<Schedule> burden) {
        super(result, burden);
    }

    public ScheduleResult(Result result, String message, List<Schedule> burden) {
        super(result, message, burden);
    }

    @Override
    public Result getResult() {
        return super.getResult();
    }

    @Override
    public List<Schedule> getBurden() {
        return super.getBurden();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
