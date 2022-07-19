package com.soulcode.Servicos.Controllers.Exceptions;

import com.soulcode.Servicos.Services.Exceptions.DataIntegratyViolationException;
import com.soulcode.Servicos.Services.Exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice // trabalha classes de erros
public class ResourceExceptionHandler {

    //HttpServletRequest traz a requisição q estamos utillizando no momento
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandadError> entityNotFound(EntityNotFoundException e, HttpServletRequest request){
        StandadError erro = new StandadError();
        erro.setTimestamp(Instant.now()); //coloca o tempo do erro
        erro.setStatus(HttpStatus.NOT_FOUND.value()); //coloca o status para não encontrado
        erro.setError("Registro não encontrado");
        erro.setMessage(e.getMessage());
        erro.setPath(request.getRequestURI()); // traz p gente o URI da requisição que queremos pegar
        erro.setTrace("EntityNotFoundException");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    public ResponseEntity<StandadError> dataIntegratyViolationException(DataIntegratyViolationException e, HttpServletRequest request){
        StandadError erro = new StandadError();
        erro.setTimestamp(Instant.now()); //coloca o tempo do erro
        erro.setStatus(HttpStatus.CONFLICT.value()); //coloca o status para não encontrado
        erro.setError("Atributo não pode ser duplicado");
        erro.setMessage(e.getMessage());
        erro.setPath(request.getRequestURI()); // traz p gente o URI da requisição que queremos pegar
        erro.setTrace("EntityNotFoundException");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }
}



