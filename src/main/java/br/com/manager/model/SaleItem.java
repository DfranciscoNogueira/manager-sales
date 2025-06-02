package br.com.manager.model;

import br.com.manager.dto.SaleItemDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "SALE_ITEM")
@SequenceGenerator(name = "sales_item_seq", sequenceName = "sales_item_seq", allocationSize = 1)
public class SaleItem {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sales_item_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_id", nullable = false)
    private Sales sales;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "total_value", nullable = false)
    private BigDecimal totalValue;

    public SaleItem() {
    }

    public SaleItem(Product product, int amount) {
        this.product = product;
        this.amount = amount;
        this.totalValue = (Objects.isNull(product) || Objects.isNull(product.getPrice())) ? BigDecimal.ZERO : product.getPrice().multiply(BigDecimal.valueOf(amount));
    }

    public SaleItemDTO toDTO() {
        SaleItemDTO saleItem = new SaleItemDTO();
        saleItem.setId(this.id);
        saleItem.setProduct(Objects.isNull(this.product) ? null : this.product.toDTO());
        saleItem.setAmount(this.amount);
        saleItem.setTotalValue(this.totalValue);
        return saleItem;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sales getSales() {
        return this.sales;
    }

    public void setSales(Sales sales) {
        this.sales = sales;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
        this.totalValue = (Objects.isNull(this.product) || Objects.isNull(this.product.getPrice())) ? BigDecimal.ZERO : this.product.getPrice().multiply(BigDecimal.valueOf(amount));
    }

    public BigDecimal getTotalValue() {
        return this.totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

}
