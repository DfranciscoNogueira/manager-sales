package br.com.manager.service;

import br.com.manager.dto.ClientDTO;
import br.com.manager.dto.ProductDTO;
import br.com.manager.dto.SaleItemDTO;
import br.com.manager.dto.SalesDTO;
import br.com.manager.exception.BusinessException;
import br.com.manager.model.Product;
import br.com.manager.repository.ClientRepository;
import br.com.manager.repository.ProductRepository;
import br.com.manager.repository.SaleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SalesServiceTest {

    private SalesService salesService;
    private ClientRepository clientRepository;
    private ProductRepository productRepository;
    private SaleRepository saleRepository;

    @BeforeEach
    void setUp() {
        clientRepository = new ClientRepository();
        productRepository = new ProductRepository();
        saleRepository = new SaleRepository();
        salesService = new SalesService();
    }

    @Test
    void deveRegistrarVendaComSucesso() {
        ClientDTO clientDTO = new ClientDTO("Carlos Silva", new BigDecimal("5000.00"), 10);
        clientRepository.save(clientDTO.toModel());

        Product product = new Product(1L, "Notebook Dell", new BigDecimal("4500.00"));
        productRepository.save(product);

        SaleItemDTO item = new SaleItemDTO(new ProductDTO(product.getId(), product.getDescription(), product.getPrice()), 1);
        SalesDTO venda = salesService.registerSale(clientDTO.getId(), List.of(item));

        assertNotNull(venda);
        assertEquals(clientDTO.getId(), venda.getClient().getId());
        assertEquals(new BigDecimal("4500.00"), venda.getTotal());
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoEncontrado() {
        BusinessException exception = assertThrows(BusinessException.class, () -> salesService.registerSale(99L, List.of()));
        assertEquals("Cliente não encontrado!", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoLimiteCreditoExcedido() {
        ClientDTO clientDTO = new ClientDTO("Ana Souza", new BigDecimal("1000.00"), 5);
        clientRepository.save(clientDTO.toModel());

        Product product = new Product(2L, "Monitor 27\"", new BigDecimal("1200.00"));
        productRepository.save(product);

        SaleItemDTO item = new SaleItemDTO(new ProductDTO(product.getId(), product.getDescription(), product.getPrice()), 1);

        BusinessException exception = assertThrows(BusinessException.class, () -> salesService.registerSale(clientDTO.getId(), List.of(item)));
        assertTrue(exception.getMessage().contains("Limite de crédito excedido!"));
    }

    @Test
    void deveBuscarVendaPorIdComSucesso() {
        ClientDTO clientDTO = new ClientDTO("Mateus Oliveira", new BigDecimal("4500.00"), 8);
        clientRepository.save(clientDTO.toModel());

        Product product = new Product(3L, "Mouse Gamer", new BigDecimal("350.00"));
        productRepository.save(product);

        SaleItemDTO item = new SaleItemDTO(new ProductDTO(product.getId(), product.getDescription(), product.getPrice()), 2);
        SalesDTO venda = salesService.registerSale(clientDTO.getId(), List.of(item));

        assertTrue(salesService.findById(venda.getId()).isPresent());
    }

    @Test
    void deveListarVendasPorCliente() {
        ClientDTO clientDTO = new ClientDTO("Juliana Mendes", new BigDecimal("7000.00"), 12);
        clientRepository.save(clientDTO.toModel());

        Product product = new Product(4L, "Teclado RGB", new BigDecimal("450.00"));
        productRepository.save(product);

        SaleItemDTO item = new SaleItemDTO(new ProductDTO(product.getId(), product.getDescription(), product.getPrice()), 1);
        salesService.registerSale(clientDTO.getId(), List.of(item));

        assertEquals(1, salesService.findByClient(clientDTO.getId()).size());
    }

    @Test
    void deveExcluirVendaComSucesso() {
        ClientDTO clientDTO = new ClientDTO("Gabriel Monteiro", new BigDecimal("6000.00"), 15);
        clientRepository.save(clientDTO.toModel());

        Product product = new Product(5L, "Gabinete ATX", new BigDecimal("500.00"));
        productRepository.save(product);

        SaleItemDTO item = new SaleItemDTO(new ProductDTO(product.getId(), product.getDescription(), product.getPrice()), 1);
        SalesDTO venda = salesService.registerSale(clientDTO.getId(), List.of(item));

        salesService.delete(venda.getId());
        assertFalse(salesService.findById(venda.getId()).isPresent());
    }
}