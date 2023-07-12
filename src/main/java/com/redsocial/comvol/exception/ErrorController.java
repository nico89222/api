package com.redsocial.comvol.exception;

import com.redsocial.comvol.dto.error.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDto> handleBadRequestException(BadRequestException badRequestException) {

        return new ResponseEntity<>(ErrorDto.builder().descripcion(badRequestException.getMessage()).build(),HttpStatus.BAD_REQUEST);
    }
}
