package org.manlier.dto.base;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sun.istack.internal.Nullable;
import org.manlier.customs.json.ResultSerializer;

/**
 * 基本结果类
 * Created by manlier on 2017/6/7.
 */
public class BaseResult<T> implements IResult<T> {

    @JsonSerialize(using = ResultSerializer.class)
    protected Result result;

    protected String message;

    protected T burden;

    public BaseResult() {
    }

    public BaseResult(Result result, @Nullable T burden) {
        this.result = result;
        this.burden = burden;
    }

    public BaseResult(Result result, String message, @Nullable T burden) {
        this.result = result;
        this.message = message;
        this.burden = burden;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBurden() {
        return burden;
    }

    public void setBurden(T burden) {
        this.burden = burden;
    }

    public enum Result {
        SUCCESS, FAIL
    }

    @Override
    public String toString() {
        return "BaseResult{" +
                "result=" + result +
                ", message='" + message + '\'' +
                ", burden=" + burden +
                '}';
    }

    public static <R> BaseResult<R> success() {
        return new BaseResult<>(Result.SUCCESS, null);
    }

    public static <R> BaseResult<R> fail() {
        return new BaseResult<>(Result.FAIL, null);
    }

    public static <R> BaseResult<R> success(R burden) {
        return new BaseResult<>(Result.SUCCESS, burden);
    }

    public static <R> BaseResult<R> fail(R burden) {
        return new BaseResult<>(Result.FAIL, burden);
    }
}
