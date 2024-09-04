package io.coolinary.smacker.image;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import io.coolinary.smacker.shared.ElementNotFoundException;
import io.coolinary.smacker.shared.ErrorMessage;

@ControllerAdvice
class ElementNotFoundAdvice {

    private final Logger logger = LoggerFactory.getLogger(ElementNotFoundException.class);

    @ResponseBody
    @ExceptionHandler(value = { ElementNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<ErrorMessage> elementNotFoundHandler(ElementNotFoundException ex, WebRequest request) {

        this.logger.error(ex.getMessage());

        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false),
                ex.getEntityType().name());
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);

    }

}
