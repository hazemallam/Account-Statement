package com.nagarro.assignment.exceptions;

import com.nagarro.assignment.dto.ErrorResponse;
import com.nagarro.assignment.dto.enums.ErrorCodes;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {


    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> onException(Exception ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(ErrorCodes.INVALID_PARAMETER.getCode());
        errorResponse.setErrorMessage(ErrorCodes.INVALID_PARAMETER.getMsg());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
