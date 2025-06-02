package br.com.manager.dto;

import br.com.manager.model.Client;

import java.math.BigDecimal;
import java.util.Objects;

public class ClientDTO {

    private Long id;

    private String name;

    private BigDecimal limitSales;

    private int dayClosingInvoice;

    public ClientDTO(Long id) {
        this.id = id;
    }

    public ClientDTO(Long id, String name, BigDecimal limitSales, Integer dayClosingInvoice) {
        this.id = id;
        this.name = name;
        this.limitSales = Objects.isNull(limitSales) ? BigDecimal.ZERO : limitSales;
        this.dayClosingInvoice = Objects.isNull(dayClosingInvoice) ? 0 : dayClosingInvoice;
    }

    public ClientDTO(String name, BigDecimal limitSales, Integer dayClosingInvoice) {
        this.name = name;
        this.limitSales = Objects.isNull(limitSales) ? BigDecimal.ZERO : limitSales;
        this.dayClosingInvoice = Objects.isNull(dayClosingInvoice) ? 0 : dayClosingInvoice;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public Client toModel() {
        return new Client(this.id, this.name, this.limitSales, this.dayClosingInvoice);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getLimitSales() {
        return this.limitSales;
    }

    public void setLimitSales(BigDecimal limitSales) {
        this.limitSales = Objects.isNull(limitSales) ? BigDecimal.ZERO : limitSales;
    }

    public int getDayClosingInvoice() {
        return this.dayClosingInvoice;
    }

    public void setDayClosingInvoice(int dayClosingInvoice) {
        this.dayClosingInvoice = dayClosingInvoice;
    }

}