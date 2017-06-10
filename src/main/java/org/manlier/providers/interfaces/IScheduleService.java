package org.manlier.providers.interfaces;

import org.manlier.beans.Repetition;
import org.manlier.beans.Schedule;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

/**
 * Created by manlier on 2017/6/7.
 */
@Service
public interface IScheduleService {
    boolean addScheduleForUser(String userId, Schedule schedule);

    @Transactional(rollbackFor = IllegalStateException.class)
    boolean setStartTimeAndDueTime(String scheduleId, Instant startTime, Instant dueTime);

    Schedule getScheduleById(String scheduleId);

    boolean updateSchedule(Schedule schedule);

    boolean deleteSchedule(String scheduleId);

    List<Schedule> getAllSchedulesForUser(String userId);

    boolean recycleSchedule(String scheduleId);

    boolean restoreSchedule(String scheduleId);

    boolean completeSchedule(String scheduleId);

    boolean incompleteSchedule(String scheduleId);

    List<Schedule> getAllRecycledSchedulesForUser(String userId);

    boolean deleteAllRecycledSchedulesForUser(String userId);

    List<Schedule> getAllSchedulesByProjectId(String projectId);

    List<Schedule> findSchedulesDuringTimePeriod(String userId, Instant beginTime, Instant endTime);

    Repetition getRepetitionForSchedule(String scheduleId);

    boolean cancelRepetition(String scheduleId);

    boolean changeRepetition(Repetition repetition);

    boolean setRepetitionForSchedule(String scheduleId, Repetition repetition);
}
