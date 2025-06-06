package com.ramseys.api_recommandation.exception;
   import org.springframework.http.HttpStatus;
   import org.springframework.http.ResponseEntity;
   import org.springframework.web.bind.annotation.ControllerAdvice;
   import org.springframework.web.bind.annotation.ExceptionHandler;
   @ControllerAdvice
   public class GlobalExceptionHandler {
       @ExceptionHandler(Exception.class)
       public ResponseEntity<String> handleException(Exception e) {
           // Log l'exception ici si nécessaire
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body("Une erreur est survenue : " + e.getMessage());
       }
   }