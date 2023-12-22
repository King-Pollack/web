package com.king.app.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.king.app.common.exception.ErrorCode.INTERNAL_SERVER_ERROR;
import static com.king.app.common.exception.ErrorCode.INVALID_REQUEST;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GlobalException.class)
    public ErrorResponse handelCustomException(GlobalException e) {
        log.error("error info : {} ", e.getErrorCode());
        return new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("error info : ", e);
        List<FieldError> fieldErrors = e.getFieldErrors();
        Map<String, String> bindingResult = new HashMap<>();
        for (FieldError error : fieldErrors) {
            bindingResult.put(error.getField(), error.getDefaultMessage());
        }
        return new ErrorResponse(INVALID_REQUEST, INVALID_REQUEST.getErrorMessage(), bindingResult);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error("error info : ", e);

        return new ErrorResponse(INVALID_REQUEST, INVALID_REQUEST.getErrorMessage());
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception e) {
        log.error("error info : ", e);
        return new ErrorResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR.getErrorMessage());
    }
}
