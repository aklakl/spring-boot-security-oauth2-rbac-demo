package com.leewaiho.ExceptionHandler;

import org.springframework.http.HttpStatus;

/**
 * Created by leewaiho on 2017/7/23.
 */
public class ServiceException extends RuntimeException {
    
    private HttpStatus httpStatus;
    
    
    public ServiceException(String message) {
        super(message);
    }
    
    public ServiceException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
    
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    
    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
