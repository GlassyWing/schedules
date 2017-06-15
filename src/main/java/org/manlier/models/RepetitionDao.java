package org.manlier.models;

import org.apache.ibatis.annotations.Param;
import org.manlier.beans.Repetition;

/**
 * Created by manlier on 2017/6/4.
 */
public interface RepetitionDao {

    /**
     * 添加重复规则
     *
     * @param scheduleId 日程Id
     * @param repetition 重复规则
     * @return 插入数量
     */
    int addRepetitionForSchedule(@Param("scheduleId") String scheduleId, @Param("repetition") Repetition repetition);

    /**
     * 获得日程的重复规则
     *
     * @param scheduleId 日程Id
     * @return 重复规则
     */
    Repetition getRepetitionByScheduleId(@Param("scheduleId") String scheduleId);

    /**
     * 删除日程的重复规则
     *
     * @param scheduleId 日程Id
     * @return 删除数量
     */
    int deleteRepetition(@Param("scheduleId") String scheduleId);

    /**
     * 更新日程的重复规则
     *
     * @param repetition 重复规则
     * @return 更新数量
     */
    int updateRepetitionForSchedule(@Param("scheduleId") String scheduleId, @Param("repetition") Repetition repetition);
}
