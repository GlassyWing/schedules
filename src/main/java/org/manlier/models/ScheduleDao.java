package org.manlier.models;

import org.apache.ibatis.annotations.Param;
import org.manlier.beans.Schedule;

import java.time.Instant;
import java.util.List;

/**
 * Created by manlier on 2017/6/4.
 */
public interface ScheduleDao {

    /**
     * 添加日程
     *
     * @param userId   用户Id
     * @param schedule 日程
     * @return 插入数量
     */
    int addScheduleForUser(@Param("userId") String userId, @Param("schedule") Schedule schedule);

    /**
     * 通过日程id获得日程
     *
     * @param scheduleId 日程id
     * @return 日程
     */
    Schedule getScheduleByScheduleId(@Param("scheduleId") String scheduleId);

    /**
     * 更新日程
     *
     * @param schedule 日程
     * @return 更新数量
     */
    int updateSchedule(@Param("schedule") Schedule schedule);

    /**
     * 删除日程
     *
     * @param scheduleId 日程id
     * @return 删除数量
     */
    int deleteSchedule(@Param("scheduleId") String scheduleId);

    /**
     * 回收日程（把日程置入垃圾桶中）
     *
     * @param scheduleId 日程id
     * @return 更新数量
     */
    int recycleSchedule(@Param("scheduleId") String scheduleId);

    /**
     * 恢复日程（从垃圾箱中恢复）
     *
     * @param scheduleId 日程id
     * @return 更新数量
     */
    int restoreSchedule(@Param("scheduleId") String scheduleId);

    /**
     * 设置日程为完成
     *
     * @param scheduleId 日程id
     * @return 更新数量
     */
    int completeSchedule(@Param("scheduleId") String scheduleId);

    /**
     * 设置日程为未完成
     *
     * @param scheduleId 日程id
     * @return 更新数量
     */
    int incompleteSchedule(@Param("scheduleId") String scheduleId);

    /**
     * 查找所有已回收的日程
     *
     * @param userId 用户id
     * @return 日程
     */
    List<Schedule> getAllRecycledSchedulesForUser(@Param("userId") String userId);

    /**
     * 移除所有回收的日程
     *
     * @param userId 用户id
     * @return 删除数量
     */
    int deleteAllRecycledSchedulesForUser(@Param("userId") String userId);

    /**
     * 查找指定时间间隔内的日程
     * 时间以时间戳格式提供
     *
     * @param userId    用户Id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 日程
     */
    List<Schedule> findSchedulesDuringTimePeriod(@Param("userId") String userId, @Param("beginTime") Instant beginTime, @Param("endTime") Instant endTime);

    /**
     * 选择指定用户的所有日程
     *
     * @param userId 用户Id
     * @return 日程
     */
    List<Schedule> getAllSchedulesForUser(String userId);

    /**
     * 查找指定清单下的所有日程
     *
     * @param projectId 清单id
     * @return 日程
     */
    List<Schedule> getAllSchedulesByProjectId(String projectId);

    /**
     * 设置开始和完成时间
     *
     * @param scheduleId 日程id
     * @param startTime 开始时间
     * @param dueTime 完成时间
     * @return 更新数量
     */
    int setStartAndDueTime(@Param("scheduleId") String scheduleId, @Param("startTime") Instant startTime, @Param("dueTime") Instant dueTime);
}
