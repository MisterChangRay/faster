package com.github.misterchangray.exceptions;

import com.github.misterchangray.common.base.BaseEnum;
import com.github.misterchangray.common.base.BaseResponse;
import com.github.misterchangray.common.exceptions.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author ray.chang
 * @ClassName: ParamsExceptionHandler
 * @Description: 参数异常处理器
 * @date 2021/7/30 13:57
 */

@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    /**
     * ValidationException
     */
    @ExceptionHandler(BizException.class)
    public BaseResponse<Void> handleValidationException(BizException e) {
        logger.error(e.getMessage(), e);
        return BaseResponse.<Void>ofFail(e.getRes());
    }



    /**
     * ValidationException
     */
    @ExceptionHandler(Exception.class)
    public BaseResponse<Void> handleValidationException(Exception e) {
        logger.error(e.getMessage(), e);
        return BaseResponse.<Void>ofFail(BaseEnum.SYSTEM_ERROR);
    }



}