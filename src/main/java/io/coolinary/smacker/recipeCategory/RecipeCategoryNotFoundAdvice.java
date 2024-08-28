package io.coolinary.smacker.recipeCategory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
class RecipeCategoryNotFoundAdvice {

    private final Logger logger = LoggerFactory.getLogger(RecipeCategoryNotFoundAdvice.class);

    @ResponseBody
    @ExceptionHandler(RecipeCategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<?> recipeCategoryNotFoundHandler(RecipeCategoryNotFoundException ex) {
        this.logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
