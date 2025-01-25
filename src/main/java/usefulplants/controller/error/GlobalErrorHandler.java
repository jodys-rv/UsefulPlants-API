package usefulplants.controller.error;

import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler {

  @Data
  private class Error {
    private String message;
    private String reason;
  }

  @ExceptionHandler(NoSuchElementException.class)
  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  public Error handleNoSuchELementException(NoSuchElementException ex) {

    log.error("Sorry, that is not in the database.");
    return buildExceptionMessage(ex, HttpStatus.NOT_FOUND);
  }

  private Error buildExceptionMessage(NoSuchElementException ex, HttpStatus status) {
    String message = ex.toString();
    String reason = status.getReasonPhrase();
    Error error = new Error();
    error.setMessage(message);
    error.setReason(reason);
    return error;
  }
}
