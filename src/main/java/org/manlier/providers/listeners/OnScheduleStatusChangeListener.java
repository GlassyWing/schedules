package org.manlier.providers.listeners;

import org.manlier.beans.Schedule;

/**
 * 日程状态变更监听器
 * Created by manlier on 2017/6/14.
 */
public interface OnScheduleStatusChangeListener {

    // 当日程未完成时的回调
    void onIncompleteSchedule(Schedule schedule);

    // 当回收日程时的回调
    void onRecycleSchedule(Schedule schedule);

    // 当恢复日程时的回调
    void onRestoreSchedule(Schedule schedule);
}
