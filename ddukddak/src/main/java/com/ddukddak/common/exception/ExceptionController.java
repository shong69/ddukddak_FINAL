package com.ddukddak.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(NoResourceFoundException.class) 
	public String notFound() {
		return "common/error/404";
	}
	
//	@ExceptionHandler(Exception.class)
//	public String allExceptionHandler(Exception e, Model model) {
//		
//		model.addAttribute("e", e);
//		
//		return "error/500";
//	}
	
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(MaxUploadSizeExceededException exc, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "파일 크기가 너무 큽니다. 최대 허용 크기는 10MB입니다.");
        return "redirect:/uploadStatus";
    }
	
}
