package br.com.manager.model;

import br.com.manager.dto.SalesDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "SALES")
@SequenceGenerator(name = "sales_seq", sequenceName = "sales_seq", allocationSize = 1)
public class Sales {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sales_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "sales", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<SaleItem> items = new HashSet<>();

    @Column(name = "date_sale", nullable = false)
    private LocalDate dateSale;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    public Sales() {
    }

    public Sales(Client client, Set<SaleItem> items, LocalDate dateSale, BigDecimal total) {
        this.client = client;
        this.items = items;
        this.dateSale = dateSale;
        this.total = total;
    }

    public Sales(Client client, LocalDate dateSale) {
        this.client = client;
        this.dateSale = dateSale;
    }

    public SalesDTO toDTO() {

        SalesDTO sales = new SalesDTO();
        sales.setId(this.id);
        sales.setClient(Objects.isNull(this.client) ? null : this.client.toDTO());
        sales.setDateSale(this.dateSale);
        sales.setTotal(this.total);

        if (Objects.nonNull(this.items)) {
            for (SaleItem item : this.items) {
                if (Objects.nonNull(item)) {
                    sales.addItem(item.toDTO());
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

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<SaleItem> getItems() {
        return this.items;
    }

    public void setItems(Set<SaleItem> items) {
        this.items = items;
    }

    public LocalDate getDateSale() {
        return this.dateSale;
    }

    public void setDateSale(LocalDate dateSale) {
        this.dateSale = dateSale;
    }

    public BigDecimal getTotal() {
        return this.total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void addItem(SaleItem item) {
        if (Objects.isNull(this.items)) {
            this.items = new HashSet<>();
        }
        this.items.add(item);
    }

}
