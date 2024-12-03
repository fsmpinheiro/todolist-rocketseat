package br.com.samuelpinheiro.todolist.auxiliares;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class ExceptionHandlerUtil {

	
	@ExceptionHandler
	public ResponseEntity<String> handleHttpMessageNotreadableException(HttpMessageNotReadableException e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMostSpecificCause().getMessage());
	}
	
}
