package br.com.manager.repository;


import br.com.manager.model.Product;

public class ProductRepository extends GenericRepository<Product, Long> {

    public ProductRepository() {
        super(Product.class);
    }

}
