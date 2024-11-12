package com.example.demo.mapper;

import com.example.demo.dto.CommonResponse;
import com.example.demo.dto.PagingResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ResponseMapper {
    private static final String DEFAULT_SUCCESS_MESSAGE = "Success";
    private static final String DEFAULT_ERROR_MESSAGE = "Failed";

    private <T> CommonResponse<T> buildResponse(int statusCode, String message, T data, PagingResponse paging) {
        return CommonResponse.<T>builder()
                .statusCode(statusCode)
                .message(message != null ? message : (statusCode >= 400 ? DEFAULT_ERROR_MESSAGE : DEFAULT_SUCCESS_MESSAGE))
                .data(data)
                .paging(paging)
                .build();
    }

    public <T> CommonResponse<T> toSuccessResponse(T data, PagingResponse paging, String successMessage) {
        return buildResponse(HttpStatus.OK.value(), successMessage, data, paging);
    }

    public <T> CommonResponse<T> toErrorResponse(String errorMessage, int errorCode) {
        return buildResponse(errorCode, errorMessage, null, null);
    }

    public <T> CommonResponse<T> toResponse(T data, PagingResponse paging, String successMessage, String errorMessage, int errorCode) {
        return Optional.ofNullable(data)
                .map(response -> toSuccessResponse(response, paging, successMessage))
                .orElseGet(() -> toErrorResponse(errorMessage, errorCode));
    }

    public <T> CommonResponse<T> toResponse(T data, PagingResponse paging) {
        return toResponse(data, paging, DEFAULT_SUCCESS_MESSAGE, DEFAULT_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public <T> CommonResponse<T> toResponse(T data, String successMessage, String errorMessage) {
        return toResponse(data, null, successMessage, errorMessage, HttpStatus.BAD_REQUEST.value());
    }

    public <T> CommonResponse<T> toErrorResponse(String errorMessage) {
        int errorCode;
        if (errorMessage.contains("not found")) {
            errorCode = HttpStatus.NOT_FOUND.value();
        } else if (errorMessage.contains("bad request")) {
            errorCode = HttpStatus.BAD_REQUEST.value();
        } else if (errorMessage.contains("unauthorized")) {
            errorCode = HttpStatus.UNAUTHORIZED.value();
        } else {
            errorCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        }
        return toErrorResponse(errorMessage, errorCode);
    }
}
