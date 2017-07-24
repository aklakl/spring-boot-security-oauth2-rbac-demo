package com.leewaiho.ExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by leewaiho on 2017/7/23.
 */
@RestControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalErrorHandler.class);
    
    
    @ExceptionHandler(value = ServiceException.class)
    void handleServicesException(HttpServletResponse response, ServiceException serviceException) throws IOException {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (serviceException.getHttpStatus() != null) {
            httpStatus = serviceException.getHttpStatus();
        }
        logger.info("HttpStatus: {} - {}, Message: {}", httpStatus.name(), httpStatus.value(), serviceException.getMessage());
        response.sendError(serviceException.getHttpStatus().value(), serviceException.getMessage());
    }
}
