package br.com.barbertech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();

        // Extrai todos os erros de campo e suas mensagens
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(), // Timestamp atual
                HttpStatus.BAD_REQUEST.value(), // Código de status HTTP
                "Bad Request", // Mensagem de erro
                request.getDescription(false).replace("uri=", ""), // Caminho da requisição
                errors // Detalhes dos erros de validação
        );


        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("exception", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), // Timestamp atual
                HttpStatus.CONFLICT.value(), // Código de status HTTP
                "Conflict", // Mensagem de erro
                request.getDescription(false).replace("uri=", ""), // Caminho da requisição
                errors // Detalhes do erro de duplicação
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}
