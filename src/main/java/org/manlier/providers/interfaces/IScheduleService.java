package org.manlier.providers.interfaces;

import org.manlier.beans.Reminder;
import org.manlier.beans.Repetition;
import org.manlier.beans.Schedule;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

/**
 * Created by manlier on 2017/6/7.
 */
@Service
public interface IScheduleService {

    Schedule addScheduleForUser(String userId, Schedule schedule);

    Schedule getScheduleById(String scheduleId);

    Schedule updateSchedule(Schedule schedule);

    boolean deleteSchedule(String scheduleId);

    List<Schedule> getAllSchedulesForUser(String userId);

    List<Schedule> getAllSchedulesCompletedForUserFrom(String userId, Instant from, Instant to, int limit);

    List<Schedule> getAllSchedulesUncompletedForUser(String userId);

    Schedule recycleSchedule(String scheduleId);

    Schedule restoreSchedule(String scheduleId);

    Schedule completeSchedule(String scheduleId);

    Schedule incompleteSchedule(String scheduleId);

    List<Schedule> getAllRecycledSchedulesForUser(String userId);

    boolean deleteAllRecycledSchedulesForUser(String userId);

    List<Schedule> getAllSchedulesByProjectId(String projectId);
}
