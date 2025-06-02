package br.com.manager.dto;

import br.com.manager.model.SaleItem;

import java.math.BigDecimal;
import java.util.Objects;

public class SaleItemDTO {

    private Long id;

    private SalesDTO sales;

    private ProductDTO product;

    private int amount;

    private BigDecimal totalValue;

    public SaleItemDTO() {
    }

    public SaleItemDTO(ProductDTO product, int amount) {
        this.product = product;
        this.amount = amount;
        this.totalValue = (Objects.isNull(product) || Objects.isNull(product.getPrice())) ? BigDecimal.ZERO : product.getPrice().multiply(BigDecimal.valueOf(amount));
    }

    public SaleItem toModel() {
        SaleItem saleItem = new SaleItem();
        saleItem.setId(this.id);
        saleItem.setProduct(Objects.isNull(this.product) ? null : this.product.toModel());
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

    public SalesDTO getSales() {
        return this.sales;
    }

    public void setSales(SalesDTO sales) {
        this.sales = sales;
    }

    public ProductDTO getProduct() {
        return this.product;
    }

    public void setProduct(ProductDTO product) {
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
