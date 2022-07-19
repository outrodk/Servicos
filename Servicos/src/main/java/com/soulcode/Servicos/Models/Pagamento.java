package com.soulcode.Servicos.Models;

import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;

@Entity
public class Pagamento {

    @Id
    private Integer idPagamento;

    @NumberFormat(pattern = "#.##0,00") //o # significa q se ele entrar na casa do milhar, vai ser dividido com o ponto
    @Column(nullable = false)
    private double valor;

    @Column(nullable = true)
    private String formaDePagamento;

    @Enumerated(EnumType.STRING)
    private StatusPagamento statusPagamento;

    public Integer getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(Integer idPagamento) {
        this.idPagamento = idPagamento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getFormaDePagamento() {
        return formaDePagamento;
    }

    public void setFormaDePagamento(String formaDePagamento) {
        this.formaDePagamento = formaDePagamento;
    }

    public StatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(StatusPagamento statusPagamento) {
        this.statusPagamento = statusPagamento;
    }
}
