package org.manlier.dto.base;

import java.util.ArrayList;
import java.util.List;

import static org.manlier.dto.base.BaseResult.Result.*;


/**
 * 带有子结果的结果类
 *
 * @param <T> 负载
 * @param <R> 子结果的负载
 *            Created by manlier on 2017/6/7.
 */
public class WithChildrenResult<T, R> extends BaseResult<List<BaseResult<R>>> {

    public WithChildrenResult() {
        burden = new ArrayList<>();
        result = SUCCESS;
    }

    public WithChildrenResult(Result result, List<BaseResult<R>> burden) {
        super(result, burden);
    }

    public WithChildrenResult(Result result, String message, List<BaseResult<R>> burden) {
        super(result, message, burden);
    }

    public void addChildResult(BaseResult<R> child) {
        if (child.getResult() == FAIL)
            result = FAIL;
        burden.add(child);
    }
}
