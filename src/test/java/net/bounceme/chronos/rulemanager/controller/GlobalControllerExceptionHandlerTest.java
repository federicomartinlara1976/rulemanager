package net.bounceme.chronos.rulemanager.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import net.bounceme.chronos.rulemanager.exception.IncorrectRuleException;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class GlobalControllerExceptionHandlerTest {

    GlobalControllerExceptionHandler globalControllerExceptionHandler = new GlobalControllerExceptionHandler();

    @Test
    void handleException_ReturnsInternalServerError_WhenDataAccessExceptionOccurs() {
        DataAccessException ex = new DataAccessException("Database error") {};

        ResponseEntity<Map<String, String>> response = globalControllerExceptionHandler.handleException(ex);

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Assertions.assertEquals("Database error", response.getBody().get("mensaje"));
    }

    @Test
    void handleValidationException_ReturnsBadRequest_WhenMethodArgumentNotValidExceptionOccurs() {
        // Crear el mock de BindingResult
    	MethodParameter parameter = mock(MethodParameter.class);
        BindingResult bindingResult = mock(BindingResult.class);
        
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(parameter, bindingResult);
        
        FieldError fieldError = new FieldError("objectName", "field", "defaultMessage");
        
        when(ex.getBindingResult().getAllErrors()).thenReturn(List.of(fieldError));

        ResponseEntity<Map<String, String>> response = globalControllerExceptionHandler.handleValidationException(ex);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("defaultMessage", response.getBody().get("field"));
    }

    @Test
    void handleIncorrectRuleException_ReturnsBadRequest_WhenIncorrectRuleExceptionOccurs() {
        IncorrectRuleException ex = new IncorrectRuleException("Rule is incorrect");

        ResponseEntity<Map<String, String>> response = globalControllerExceptionHandler.handleIncorrectRuleException(ex);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Rule is incorrect", response.getBody().get("mensaje"));
    }
}
