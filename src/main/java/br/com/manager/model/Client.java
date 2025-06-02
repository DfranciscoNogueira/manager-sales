package br.com.manager.model;

import br.com.manager.dto.ClientDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "CLIENT")
@SequenceGenerator(name = "client_seq", sequenceName = "client_seq", allocationSize = 1)
public class Client {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_seq")
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "limit_sales", nullable = false)
    private BigDecimal limitSales;

    @Column(name = "day_closing_invoice", nullable = false)
    private int dayClosingInvoice;

    public Client() {
    }

    public Client(Long id, String name, BigDecimal limitSales, int dayClosingInvoice) {
        this.id = id;
        this.name = name;
        this.limitSales = limitSales;
        this.dayClosingInvoice = dayClosingInvoice;
    }

    public Client(String name, BigDecimal limitSales, int dayClosingInvoice) {
        this.name = name;
        this.limitSales = limitSales;
        this.dayClosingInvoice = dayClosingInvoice;
    }

    public ClientDTO toDTO() {
        return new ClientDTO(this.id, this.name, this.limitSales, this.dayClosingInvoice);
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
        this.limitSales = limitSales;
    }

    public int getDayClosingInvoice() {
        return this.dayClosingInvoice;
    }

    public void setDayClosingInvoice(int dayClosingInvoice) {
        this.dayClosingInvoice = dayClosingInvoice;
    }

}