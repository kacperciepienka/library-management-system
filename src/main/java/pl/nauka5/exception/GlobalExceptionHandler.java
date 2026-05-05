package pl.nauka5.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.nauka5.dto.ErrorResponse;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 400 - Bad Request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidHandler(MethodArgumentNotValidException ex){
        String message = ex.getBindingResult()
                        .getFieldErrors()
                                .getFirst()
                                        .getDefaultMessage();

        log.error("Bad Request Exception: {}", message);

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Not Found")
                .message(message)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 404 - Not Found
    @ExceptionHandler({
            AuthorNotFoundException.class,
            BookNotFoundException.class,
            BorrowerNotFoundException.class,
            BorrowerNotFoundExceptionByEmail.class,
            LoanNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> notFoundExceptionHandler(RuntimeException ex){
        log.error("Not Found Exception: {}", ex.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Not Found")
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // 409 - Conflict
    @ExceptionHandler({
            BookAlreadyExistException.class,
            BorrowerAlreadyExistException.class,
            BorrowerEmailAlreadyExistException.class,
            CantLoanNextBookException.class
    })
    public ResponseEntity<ErrorResponse> conflictExceptionHandler(RuntimeException ex){
        log.error("Conflict Exception: {}", ex.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error("Conflict")
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // 422 - Unprocessable Content
    @ExceptionHandler({
            InvalidBookNewPriceException.class,
            InvalidBorrowerActiveLoansCount.class,
            BorrowerCantReturnBookException.class
    })
    public ResponseEntity<ErrorResponse> unprocessableContentExceptionHandler(RuntimeException ex){
        log.error("Unprocessable Content Exception: {}", ex.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNPROCESSABLE_CONTENT.value())
                .error("Unprocessable Content")
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_CONTENT);
    }
}
