package br.com.manager.dto;

import br.com.manager.model.SaleItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SaleItemDTOTest {

    @Test
    void deveCriarSaleItemDTOComValoresValidos() {
        ProductDTO product = new ProductDTO(1L, "Notebook Dell", new BigDecimal("4500.00"));
        SaleItemDTO saleItem = new SaleItemDTO(product, 2);
        assertEquals(1L, saleItem.getProduct().getId());
        assertEquals("Notebook Dell", saleItem.getProduct().getDescription());
        assertEquals(2, saleItem.getAmount());
        assertEquals(new BigDecimal("9000.00"), saleItem.getTotalValue());
    }

    @Test
    void deveConverterSaleItemDTOParaSaleItemModel() {
        ProductDTO product = new ProductDTO(3L, "Mouse Gamer", new BigDecimal("350.00"));
        SaleItemDTO saleItemDTO = new SaleItemDTO(product, 4);
        SaleItem saleItemModel = saleItemDTO.toModel();
        assertEquals(3L, saleItemModel.getProduct().getId());
        assertEquals("Mouse Gamer", saleItemModel.getProduct().getDescription());
        assertEquals(4, saleItemModel.getAmount());
        assertEquals(new BigDecimal("1400.00"), saleItemModel.getTotalValue());
    }

    @Test
    void deveAtualizarTotalValueAoModificarAmount() {
        ProductDTO product = new ProductDTO(4L, "Teclado Mecânico RGB", new BigDecimal("450.00"));
        SaleItemDTO saleItem = new SaleItemDTO(product, 2);
        saleItem.setAmount(5);
        assertEquals(5, saleItem.getAmount());
        assertEquals(new BigDecimal("2250.00"), saleItem.getTotalValue());
    }

}