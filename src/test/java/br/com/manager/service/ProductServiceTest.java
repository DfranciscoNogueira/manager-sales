package br.com.manager.service;

import br.com.manager.dto.ProductDTO;
import br.com.manager.exception.BusinessException;
import br.com.manager.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
        productService = new ProductService();
    }

    @Test
    void deveSalvarProductComSucesso() {
        ProductDTO productDTO = new ProductDTO("Notebook Dell", new BigDecimal("4500.00"));
        ProductDTO savedProduct = productService.save(productDTO);
        assertNotNull(savedProduct);
        assertEquals("Notebook Dell", savedProduct.getDescription());
    }

    @Test
    void deveLancarExcecaoQuandoProductDTOForNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> productService.save(null));
        assertEquals("ProductDTO não pode ser nulo", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoDescricaoForVazia() {
        ProductDTO productDTO = new ProductDTO("", new BigDecimal("350.00"));
        BusinessException exception = assertThrows(BusinessException.class, () -> productService.save(productDTO));
        assertEquals("Descrição do produto não pode ser vazia.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoPrecoForZeroOuMenor() {
        ProductDTO productDTO = new ProductDTO("Teclado Mecânico", new BigDecimal("0"));
        BusinessException exception = assertThrows(BusinessException.class, () -> productService.save(productDTO));
        assertEquals("Preço do produto deve ser maior que zero.", exception.getMessage());
    }

    @Test
    void deveListarTodosOsProductsComSucesso() {
        productService.save(new ProductDTO("Mouse Gamer", new BigDecimal("350.00")));
        productService.save(new ProductDTO("Teclado RGB", new BigDecimal("450.00")));
        assertEquals(2, productService.findAll().size());
    }

    @Test
    void deveExcluirProductComSucesso() {
        ProductDTO productDTO = new ProductDTO("Gabinete ATX", new BigDecimal("500.00"));
        productService.save(productDTO);
        productService.delete(1L);
        Optional<ProductDTO> resultado = productService.findById(1L);
        assertFalse(resultado.isPresent());
    }

}