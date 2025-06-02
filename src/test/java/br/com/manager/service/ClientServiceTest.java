package br.com.manager.service;

import br.com.manager.dto.ClientDTO;
import br.com.manager.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ClientServiceTest {

    private ClientService clientService;
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        clientRepository = new ClientRepository();
        clientService = new ClientService();
    }

    @Test
    void deveListarTodosOsClientsComSucesso() {
        clientService.save(new ClientDTO("Mateus Oliveira", new BigDecimal("4500.00"), 8));
        clientService.save(new ClientDTO("Juliana Mendes", new BigDecimal("7000.00"), 12));
        assertEquals(2, clientService.findAll().size());
    }

    @Test
    void deveExcluirClientComSucesso() {
        ClientDTO clientDTO = new ClientDTO("Gabriel Monteiro", new BigDecimal("6000.00"), 15);
        clientService.save(clientDTO);
        clientService.delete(1L);
        Optional<ClientDTO> resultado = clientService.findById(1L);
        assertFalse(resultado.isPresent());
    }

}