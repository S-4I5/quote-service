package xyz.s4i5.quoteservice.exceptionhandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import xyz.s4i5.quoteservice.exceptions.QuoteServiceCoreException;
import xyz.s4i5.quoteservice.exceptions.SecretValidationFailedException;
import xyz.s4i5.quoteservice.exceptions.VkApiException;

import java.util.Locale;

@ControllerAdvice
@RequiredArgsConstructor
public class QuoteServiceExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;
    private final ObjectMapper mapper;

    @ExceptionHandler(value = SecretValidationFailedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Object> handleSecretValidationFailedException(SecretValidationFailedException ex, WebRequest request) {
        return createDto(ex, "Secret validation failed", request, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = QuoteServiceCoreException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleQuoteServiceCoreException(QuoteServiceCoreException ex, WebRequest request, Locale locale) {
        return createDto(ex, messageSource.getMessage(ex.getMessageCode(), null, locale), request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = VkApiException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleVkApiException(VkApiException ex, WebRequest request) {
        String message;
        try {
            message = mapper.writeValueAsString(ex.getExceptionTree());
        } catch (JsonProcessingException exception) {
            message = "Unprocessable response: " + exception.getMessage();
        }
        return createDto(ex, message, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = FeignException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleVkApiException(FeignException ex, WebRequest request) {
        return createDto(ex, ex.getMessage(), request, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> createDto(Exception ex, String message, WebRequest request, HttpStatus status) {
        ProblemDetail body = createProblemDetail(ex, status, message, null, null, request);
        return handleExceptionInternal(ex, body, null, status, request);
    }
}
