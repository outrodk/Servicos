package com.soulcode.Servicos.Models;

public enum StatusChamado {

    // cria os valores que serão armazenados no atributo conteudo

    RECEBIDO("Recebido"),
    ATRIBUIDO("Atribuido"),
    CONCLUIDO("Concluido"),
    ARQUIVADO("Arquivado");

    private String conteudo;

    // cria-se o construtor
    StatusChamado(String conteudo){

        this.conteudo = conteudo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}
