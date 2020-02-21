package jpabook.jpashop.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(annotations = RestController.class)
public class GlobalRestControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(GlobalRestControllerAdvice.class);

    @ExceptionHandler
    public @ResponseBody Map<String, String> handleJson(MethodArgumentNotValidException e){
        Map<String, String> result = new HashMap<>();
        result.put("status","ERROR");
        result.put("message", e.getBindingResult().getFieldError().getDefaultMessage());
        return result;
    }


    @ExceptionHandler
    public @ResponseBody Map<String, String> handleJson1(Exception e){
        Map<String, String> result = new HashMap<>();
        result.put("status","ERROR");
        result.put("message", e.getMessage());
        logger.debug(e.getMessage());
        return result;
    }


}
