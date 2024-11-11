package com.achobeta.types.exception;

import static com.achobeta.types.enums.GlobalServiceStatusCode.PARAM_FAILED_VALIDATE;

import com.achobeta.types.Response;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author chensongmin
 * @description 全局异常处理器
 * @date 2024/11/11
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    /**
     * 自定义验证异常 MethodArgumentNotValidException
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public <T> Response<T> handleValidationException(MethodArgumentNotValidException ex) {
        log.error("参数校验异常", ex);
        String message = ex.getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .filter(Objects::nonNull)
            .collect(Collectors.joining("\n"));

        return Response.CUSTOMIZE_MSG_ERROR(PARAM_FAILED_VALIDATE, message);
    }

    /**
     *
     * 自定义验证异常 ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public <T> Response<T> constraintViolationException(ConstraintViolationException e, HttpServletRequest request, HttpServletResponse response) {
        log.error("参数校验异常", e);
        String message = e.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .filter(Objects::nonNull)
            .collect(Collectors.joining("\n"));

        return Response.CUSTOMIZE_MSG_ERROR(PARAM_FAILED_VALIDATE, message);
    }


}
