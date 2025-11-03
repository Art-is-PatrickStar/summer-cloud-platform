package com.wsw.cloud.product.interfaces.advice;

import com.wsw.cloud.product.interfaces.response.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ApiResponse<Void> handleBiz(RuntimeException ex) {
        return ApiResponse.fail(400, ex.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ApiResponse<Void> handleValidation(Exception ex) {
        String msg = ex instanceof MethodArgumentNotValidException m ?
                m.getBindingResult().getFieldErrors().stream().findFirst().map(f -> f.getField() + ": " + f.getDefaultMessage()).orElse("参数校验失败")
                : ex.getMessage();
        return ApiResponse.fail(422, msg);
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleOther(Exception ex) {
        return ApiResponse.fail(500, "服务器内部错误");
    }
}


