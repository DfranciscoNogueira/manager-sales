package br.com.manager.dto;

import br.com.manager.model.Sales;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SalesDTO {

    private Long id;

    private ClientDTO client;

    private Set<SaleItemDTO> items = new HashSet<>();

    private LocalDate dateSale;

    private BigDecimal total;

    public SalesDTO() {
    }

    public SalesDTO(Long clientId, LocalDate dateSale, Set<SaleItemDTO> items, BigDecimal total) {
        this.client = new ClientDTO(clientId);
        this.dateSale = dateSale;
        this.items = items;
        this.total = total;
    }

    public Sales toModel() {

        Sales sales = new Sales();
        sales.setId(this.id);
        sales.setClient(Objects.isNull(this.client) ? null : this.client.toModel());
        sales.setDateSale(this.dateSale);
        sales.setTotal(this.total);

        if (Objects.nonNull(this.items)) {
            for (SaleItemDTO item : this.items) {
                if (Objects.nonNull(item)) {
                    sales.addItem(item.toModel());
                }
            }
        }

        return sales;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientDTO getClient() {
        return this.client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public Set<SaleItemDTO> getItems() {
        return this.items;
    }

    public void setItems(Set<SaleItemDTO> items) {
        this.items = items;
    }

    public LocalDate getDateSale() {
        return this.dateSale;
    }

    public void setDateSale(LocalDate dateSale) {
        this.dateSale = dateSale;
    }

    public BigDecimal getTotal() {
        if (Objects.isNull(this.total)) {
            this.total = BigDecimal.ZERO;
            if (Objects.nonNull(this.items)) {
                for (SaleItemDTO item : this.items) {
                    if (Objects.nonNull(item) && Objects.nonNull(item.getTotalValue())) {
                        this.total = this.total.add(item.getTotalValue());
                    }
                }
            }
        }
        return this.total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void addItem(SaleItemDTO item) {
        if (Objects.isNull(this.items)) {
            this.items = new HashSet<>();
        }
        this.items.add(item);
    }

}
