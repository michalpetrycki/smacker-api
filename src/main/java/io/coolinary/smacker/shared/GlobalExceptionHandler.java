package io.coolinary.smacker.shared;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(ElementNotFoundException.class);

    @ResponseBody
    @ExceptionHandler(value = { ElementNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<ErrorMessage> handleElementNotFound(ElementNotFoundException ex, WebRequest request) {

        this.logger.error(ex.getMessage());

        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false),
                ex.getEntityType().name());
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);

    }

    @ResponseBody
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ResponseEntity<ErrorMessage> handleDuplicate(DataIntegrityViolationException ex, WebRequest request) {

        this.logger.error(ex.getMessage());

        String entity = "";

        if (ex.getCause() != null && ex.getCause().getMessage().contains("tool_tool_name_key")) {
            entity = "tool";
        }
        ErrorMessage message = new ErrorMessage(
                HttpStatus.CONFLICT.value(),
                new Date(),
                "Tool with given name already exists",
                request.getDescription(false), entity);
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.CONFLICT);
    }

    // @ExceptionHandler(Exception.class)
    // public ResponseEntity<ErrorResponse> handleOtherExceptions(Exception ex) {
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    // .body(new ErrorResponse("Wystąpił nieoczekiwany błąd."));
    // }
}