package org.manlier.models;

import org.apache.ibatis.annotations.Param;
import org.manlier.beans.Reminder;

import java.util.List;

/**
 * 用于操作提醒记录的数据库访问类
 * Created by manlier on 2017/6/6.
 */
public interface ReminderDao {

    /**
     * 为指定日程添加提醒
     *
     * @param scheduleId 日程id
     * @param reminder   提醒
     * @return 添加数量
     */
    int addReminderForSchedule(@Param("scheduleId") String scheduleId, @Param("reminder") Reminder reminder);

    /**
     * 删除指定的提醒
     *
     * @param ids 提醒id
     * @return 删除数量
     */
    int deleteReminder(@Param("reminderIds") String... ids);

    /**
     * 删除日程所有的提醒
     *
     * @param scheduleId 提醒id
     * @return 删除数量
     */
    int deleteAllRemindersForSchedule(@Param("scheduleId") String scheduleId);

    /**
     * 更新提醒
     *
     * @param reminder 提醒
     * @return 更新数量
     */
    int updateReminder(@Param("reminder") Reminder reminder);

    /**
     * 获得指定的提醒
     *
     * @param reminderId 提醒id
     * @return 提醒
     */
    Reminder getReminderByReminderId(@Param("reminderId") String reminderId);

    /**
     * 获取指定日程的所有提醒
     *
     * @param scheduleId 日程id
     * @return 提醒
     */
    List<Reminder> getAllReminderForSchedule(@Param("scheduleId") String scheduleId);
}
