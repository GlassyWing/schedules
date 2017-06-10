package org.manlier.dto;

import org.manlier.dto.base.BaseResult;
import org.manlier.dto.base.WithChildrenResult;

import java.util.List;

/**
 * 通知操作的结果
 * Created by manlier on 2017/6/7.
 */
public class NotificationResult extends WithChildrenResult<List<BaseResult<String>>, String> {
}
