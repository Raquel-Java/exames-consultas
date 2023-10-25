package br.com.exames.infrastructure.exception;

import java.lang.reflect.Field;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    /* ------------------------------------------------------------------------------------------------------*/

   

    /* ------------------------------------------------------------------------------------------------------*/

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {

        String requiredTypeName =
                ex.getRequiredType() == null
                        ? "Tipo Não Especificado"
                        : Objects.requireNonNull(ex.getRequiredType(), "Erro interno do sevidor!").getName();

        String error = ex.getName() + " deveria ser do tipo " + requiredTypeName;

        ApiErroModel apiError =
                ApiErroModel.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .erro(ex.getLocalizedMessage())
                        .descricao(error)
                        .timestamp(String.valueOf(OffsetDateTime.now().toEpochSecond()))
                        .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /* ------------------------------------------------------------------------------------------------------*/

    @ExceptionHandler({MissingRequestHeaderException.class})
    public ResponseEntity<Object> handleMissingRequestHeader(
            MissingRequestHeaderException ex, WebRequest request) {

        String error = ex.getHeaderName() + " esta faltando;";

        ApiErroModel apiError =
                ApiErroModel.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .erro(ex.getLocalizedMessage())
                        .descricao(error)
                        .timestamp(String.valueOf(OffsetDateTime.now().toEpochSecond()))
                        .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /* ------------------------------------------------------------------------------------------------------*/

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<String> errors = new ArrayList<>();

        String field = "";
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {

            if (field.isEmpty() || !field.equals(error.getField()))
                errors.add(error.getField() + ": " + error.getDefaultMessage());
            field = error.getField();
        }

        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ApiErroModel apiError =
                ApiErroModel.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .erro("Argumento informado não pode ser utilizado.")
                        .erros(errors)
                        .timestamp(String.valueOf(OffsetDateTime.now().toEpochSecond()))
                        .build();

        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    /* ------------------------------------------------------------------------------------------------------*/

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        String error = ex.getParameterName() + " parametro não informado.";

        ApiErroModel apiError =
                ApiErroModel.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .erro(ex.getLocalizedMessage())
                        .descricao(error)
                        .timestamp(String.valueOf(OffsetDateTime.now().toEpochSecond()))
                        .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /* ------------------------------------------------------------------------------------------------------*/

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error =
                "Não foi encontrado um endpoint para:  " + ex.getHttpMethod() + " " + ex.getRequestURL();

        ApiErroModel apiError =
                ApiErroModel.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .erro(ex.getLocalizedMessage())
                        .descricao(error)
                        .timestamp(String.valueOf(OffsetDateTime.now().toEpochSecond()))
                        .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /* ------------------------------------------------------------------------------------------------------*/

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" metodo não suportado para essa requisicao. Os metodos suportados são ");
        Objects.requireNonNull(ex.getSupportedHttpMethods())
                .forEach(t -> builder.append(t).append(" "));

        ApiErroModel apiError =
                ApiErroModel.builder()
                        .status(HttpStatus.METHOD_NOT_ALLOWED)
                        .erro(ex.getLocalizedMessage())
                        .erros(List.of(builder.toString()))
                        .timestamp(String.valueOf(OffsetDateTime.now().toEpochSecond()))
                        .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /* ------------------------------------------------------------------------------------------------------*/

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" tipo de media não suportado. Os tipos de media suportados são ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));

        ApiErroModel apiError =
                ApiErroModel.builder()
                        .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .erro(ex.getLocalizedMessage())
                        .erros(List.of(builder.substring(0, builder.length() - 2)))
                        .timestamp(String.valueOf(OffsetDateTime.now().toEpochSecond()))
                        .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /* ------------------------------------------------------------------------------------------------------*/

    @Override
    @NonNull
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            @NonNull HttpMessageNotReadableException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatus status,
            @NonNull WebRequest request) {

        String error = "Falha ao processar dados recebidos;";
        ApiErroModel apiErro = new ApiErroModel();
        try {

            Field detailMessageField = Throwable.class.getDeclaredField("detailMessage");
            detailMessageField.setAccessible(true);
            String detailMessage = (String) detailMessageField.get(ex);
            apiErro.setErro(detailMessage);
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
        }

        apiErro.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        apiErro.setDescricao(error);
        apiErro.setTimestamp(String.valueOf(OffsetDateTime.now().toEpochSecond()));

        return new ResponseEntity<>(apiErro, new HttpHeaders(), apiErro.getStatus());
    }

    @ResponseBody
    public ResponseEntity<Object> handleHttpClientErrorException(HttpStatusCodeException ex) {

        ApiErroModel erroModel =
                ApiErroModel.builder()
                        .status(ex.getStatusCode())
                        .erro(ex.getMessage())
                        .timestamp(String.valueOf(OffsetDateTime.now().toEpochSecond()))
                        .build();

        return new ResponseEntity<>(erroModel, new HttpHeaders(), ex.getStatusCode());
    }

    /* ------------------------------------------------------------------------------------------------------*/

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            @Nullable Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        if (body == null) {

            body =
                    ApiErroModel.builder()
                            .status(status)
                            .erro(status.getReasonPhrase())
                            .erros(List.of(request.getDescription(false)))
                            .timestamp(String.valueOf(OffsetDateTime.now().toEpochSecond()))
                            .build();

        } else if (body instanceof String) {

            body =
                    ApiErroModel.builder()
                            .status(status)
                            .erro((String) body)
                            .erros(List.of(request.getDescription(false)))
                            .timestamp(String.valueOf(OffsetDateTime.now().toEpochSecond()))
                            .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    /* ------------------------------------------------------------------------------------------------------*/
}
