package org.manlier.contracts;

/**
 * 该类用于表示日程的状态
 * Created by manlier on 2017/6/4.
 */
public enum ScheduleStatus {

    UNDONE(0), // 未完成
    DUE(1),    // 已过期
    DONE(2);   // 已完成

    private int stateNumber;

    ScheduleStatus(int stateNumber) {
        this.stateNumber = stateNumber;
    }

    /**
     * 通过数值获得状态
     *
     * @param status 数值
     * @return 日程状态
     */
    public static ScheduleStatus resolveStatusByNumber(int status) {
        for (ScheduleStatus state :
                ScheduleStatus.values()
                ) {
            if (state.stateNumber == status)
                return state;
        }
        return null;
    }
}
