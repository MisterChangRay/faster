package com.github.misterchangray.validation;

import com.github.misterchangray.common.base.BaseEnum;
import com.github.misterchangray.common.base.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

/**
 * @author ray.chang
 * @ClassName: ParamsExceptionHandler
 * @Description: 参数异常处理器
 * @date 2021/7/30 13:57
 */

@ControllerAdvice
@RestControllerAdvice
public class ParamsExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ParamsExceptionHandler.class);

    /**
     * 方法参数校验
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error(e.getMessage(), e);
        return BaseResponse.<Void>ofFail(BaseEnum.INVALID_PARAM).setMsg(e.getMessage());
    }

    /**
     * ValidationException
     */
    @ExceptionHandler(ValidationException.class)
    public BaseResponse<Void> handleValidationException(ValidationException e) {
        logger.error(e.getMessage(), e);
        return BaseResponse.<Void>ofFail(BaseEnum.INVALID_PARAM).setMsg(e.getCause().getMessage());
    }

    /**
     * ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public BaseResponse<Void> handleConstraintViolationException(ConstraintViolationException e) {
        logger.error(e.getMessage(), e);
        return BaseResponse.<Void>ofFail(BaseEnum.INVALID_PARAM).setMsg(e.getMessage());
    }

}