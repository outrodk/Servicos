package com.soulcode.Servicos.Controllers.Exceptions;

import java.time.Instant;

public class StandadError {

    private Instant timestamp; // data e horário atual
    private Integer status;
    private String error;
    private String trace;
    private String message;
    private String path;

    public StandadError(){} // cria-se o construtor vazio

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

//cria-se um package de exceptions no controller e no service

