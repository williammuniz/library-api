package com.library.library.controller.common;

import com.library.library.dto.ErroCampo;
import com.library.library.dto.ErroResposta;
import com.library.library.exceptions.OperacaoNaoPermitidaException;
import com.library.library.exceptions.RegistroDuplicadoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Erro de validação {}", ex.getMessage());
        List<FieldError> fieldError = ex.getFieldErrors();
        List<ErroCampo> listaErros = fieldError
                .stream()
                .map(erro -> new ErroCampo(erro.getField(), erro.getDefaultMessage())).toList();
        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro validação", listaErros);
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleRegistroDuplicadoResposta(RegistroDuplicadoException e) {
        return ErroResposta.conflito(e.getMessage());
    }

    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleOperacaoNaoPermitidaException(OperacaoNaoPermitidaException e) {
        return ErroResposta.respostaPadrao(e.getMessage());
    }


    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErroResposta handleAccessDeniedException(AccessDeniedException ex) {
        return new ErroResposta(HttpStatus.FORBIDDEN.value(), "Acesso Negado.", List.of());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResposta handleErrosNaoTratados(RuntimeException e) {
        log.error("Erro inesperado ", e);
        return new ErroResposta(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocorreu um erro inesperado, " +
                "Entre em contato com o suporte", List.of());
    }
}
