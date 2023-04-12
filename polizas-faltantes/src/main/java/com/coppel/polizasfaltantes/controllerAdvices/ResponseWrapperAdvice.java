package com.coppel.polizasfaltantes.controllerAdvices;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coppel.polizasfaltantes.models.Meta;
import com.coppel.polizasfaltantes.models.ResponseWrapper;


@ControllerAdvice
public class ResponseWrapperAdvice implements ResponseBodyAdvice<Object> {

    private static final Logger logger = LoggerFactory.getLogger(ResponseWrapperAdvice.class);

    @Override
    public boolean supports(
        MethodParameter returnType,
        Class<? extends HttpMessageConverter<?>> converterType
    ) {
        return !returnType.getGenericParameterType().equals(ResponseWrapper.class);
    }

    @Override
    @Nullable
    public Object beforeBodyWrite(
        @Nullable Object body,
        MethodParameter returnType,
        MediaType selectedContentType,
        Class<? extends HttpMessageConverter<?>> selectedConverterType,
        ServerHttpRequest request,
        ServerHttpResponse response
    ) {

        if (body instanceof ResponseWrapper) {
            return body;
        }

        return new ResponseWrapper<>(new Meta("OK"), body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseWrapper<?>> handleUsuarioEmailExistException(
        Exception ex,
        WebRequest request
    ) {

        Map<String, String> data = new HashMap<>();

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String reason = "Ha ocurrido un error inesperado";

        if (ex instanceof ResponseStatusException) {
            ResponseStatusException responseStatusException = (ResponseStatusException) ex;

            reason = responseStatusException.getReason();
            status = responseStatusException.getStatus();
        } else {
            logger.error(reason, ex);
        }

        data.put("message", reason);

        return ResponseEntity.status(status)
            .body(
                new ResponseWrapper<>(
                    new Meta("FAILURE"),
                    data
                )
            );
    }

}