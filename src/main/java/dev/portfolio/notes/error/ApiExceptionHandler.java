package dev.portfolio.notes.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(NoteNotFoundException.class)
	ProblemDetail missing() {
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Note was not found");
		problem.setTitle("Not Found");
		problem.setProperty("code", "NOT_FOUND");
		return problem;
	}
}
