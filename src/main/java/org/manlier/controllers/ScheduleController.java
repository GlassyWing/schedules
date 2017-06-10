package org.manlier.controllers;

import org.manlier.beans.Schedule;
import org.manlier.dto.ScheduleResult;
import org.manlier.dto.base.BaseResult;
import org.manlier.providers.interfaces.IScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import static org.manlier.dto.base.BaseResult.Result.*;

/**
 * 日程控制器
 * Created by manlier on 2017/6/8.
 */
@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    private Logger logger = LoggerFactory.getLogger(ScheduleController.class);

    private final IScheduleService scheduleService;

    @Autowired
    public ScheduleController(IScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @RequestMapping(value = "/all/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public ScheduleResult getAllSchedulesForUser(@PathVariable("userId") String userId) {
        logger.info("Get all schedules for user: " + userId);
        List<Schedule> schedules = scheduleService.getAllSchedulesForUser(userId);
        if (schedules != null) {
            logger.info("Schedules number: " + schedules.size());
            return new ScheduleResult(SUCCESS, schedules);
        }
        return new ScheduleResult(FAIL, null);
    }

    @RequestMapping("/recently/{userId}")
    public ScheduleResult getRecent7daysSchedules(@PathVariable("userId") String userId, ZoneId zoneId) {
        return null;
    }

}
