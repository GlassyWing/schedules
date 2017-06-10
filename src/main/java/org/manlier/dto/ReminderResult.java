package org.manlier.dto;

import org.manlier.beans.Reminder;
import org.manlier.dto.base.BaseResult;
import org.manlier.dto.base.WithChildrenResult;

import java.util.List;

/**
 * 提醒操作的结果
 * Created by manlier on 2017/6/7.
 */
public class ReminderResult extends WithChildrenResult<List<BaseResult<Reminder>>, Reminder> {

}
