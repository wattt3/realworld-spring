package wattt3.realworld.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleException(RuntimeException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
    }

    @ExceptionHandler(CommonException.class)
    public ProblemDetail handleException(CommonException e) {
        return ProblemDetail.forStatusAndDetail(e.getErrorCode().getStatus(),
                e.getMessage() != null ? e.getMessage() : e.getErrorCode().getMessage());
    }
}

