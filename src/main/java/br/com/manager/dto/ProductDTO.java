package br.com.manager.dto;


import br.com.manager.model.Product;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductDTO {

    private Long id;

    private String description;

    private BigDecimal price;

    public ProductDTO(Long id, String description, BigDecimal price) {
        this.id = id;
        this.description = description;
        this.price = Objects.isNull(price) ? BigDecimal.ZERO : price;
    }

    public ProductDTO(String description, BigDecimal price) {
        this.description = description;
        this.price = Objects.isNull(price) ? BigDecimal.ZERO : price;
    }

    @Override
    public String toString() {
        return this.getDescription();
    }

    public Product toModel() {
        return new Product(this.id, this.description, this.price);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = Objects.isNull(price) ? BigDecimal.ZERO : price;
    }

}