package com.inventory.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InsufficientStockException.class)
    public RedirectView handleInsufficientStock(
            InsufficientStockException ex,
            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return new RedirectView("/");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public RedirectView handleValidationExceptions(
            ConstraintViolationException ex,
            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error",
                "Validation error: " + ex.getConstraintViolations().iterator().next().getMessage());
        return new RedirectView("/");
    }

    @ExceptionHandler(Exception.class)
    public RedirectView handleGenericException(
            Exception ex,
            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error",
                "An unexpected error occurred: " + ex.getMessage());
        return new RedirectView("/");
    }
}