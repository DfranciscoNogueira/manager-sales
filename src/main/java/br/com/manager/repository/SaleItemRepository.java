package br.com.manager.repository;


import br.com.manager.model.SaleItem;

public class SaleItemRepository extends GenericRepository<SaleItem, Long> {

    public SaleItemRepository() {
        super(SaleItem.class);
    }

}
