package br.com.manager.dto;

import br.com.manager.model.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductDTOTest {

    @Test
    void deveCriarProductDTOComValoresValidos() {
        ProductDTO product = new ProductDTO(1L, "Notebook Dell", new BigDecimal("4500.00"));
        assertEquals(1L, product.getId());
        assertEquals("Notebook Dell", product.getDescription());
        assertEquals(new BigDecimal("4500.00"), product.getPrice());
    }

    @Test
    void deveAtribuirValorPadraoQuandoPriceForNull() {
        ProductDTO product = new ProductDTO(2L, "Monitor 27\"", null);
        assertEquals(2L, product.getId());
        assertEquals("Monitor 27\"", product.getDescription());
        assertEquals(BigDecimal.ZERO, product.getPrice());
    }

    @Test
    void deveConverterProductDTOParaProductModel() {
        ProductDTO productDTO = new ProductDTO(3L, "Mouse Gamer", new BigDecimal("350.00"));
        Product productModel = productDTO.toModel();
        assertEquals(3L, productModel.getId());
        assertEquals("Mouse Gamer", productModel.getDescription());
        assertEquals(new BigDecimal("350.00"), productModel.getPrice());
    }

    @Test
    void toStringDeveRetornarDescricaoDoProduto() {
        ProductDTO product = new ProductDTO(4L, "Teclado Mecânico RGB", new BigDecimal("450.00"));
        assertEquals("Teclado Mecânico RGB", product.toString());
    }

}