package com.example.springreviewhub.adapter.presenter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonInclude;

@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> BaseResponse<T> success(String message, T data) {
        return new BaseResponse<>(true, message, data);
    }

    public static <T> BaseResponse<T> success(String message) {
        return new BaseResponse<>(true, message, null);
    }

    public static <T> BaseResponse<T> failure(String message) {
        return new BaseResponse<>(false, message, null);
    }

    public static <T> BaseResponse<T> failure(String message, T data) {
        return new BaseResponse<>(false, message, data);
    }
}
