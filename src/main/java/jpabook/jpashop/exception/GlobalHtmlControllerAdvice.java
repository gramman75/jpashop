package jpabook.jpashop.exception;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(annotations = Controller.class)
public class GlobalHtmlControllerAdvice {

    @ExceptionHandler(NotEnoughStockException.class)
    public String handleView(NotEnoughStockException e, Model model, HttpServletRequest req){
        model.addAttribute("customMessage", e.getMessage());
        return "/error/customException";
    }
}
