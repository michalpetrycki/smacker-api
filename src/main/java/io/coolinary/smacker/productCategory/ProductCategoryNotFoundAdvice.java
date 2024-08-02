package io.coolinary.smacker.productCategory;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class ProductCategoryNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ProductCategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String productCategoryNotFoundHandler(ProductCategoryNotFoundException ex) {
        return ex.getMessage();
    }

}
