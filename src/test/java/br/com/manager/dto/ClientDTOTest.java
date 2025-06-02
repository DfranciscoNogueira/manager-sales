package br.com.manager.dto;

import br.com.manager.model.Client;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ClientDTOTest {

    @Test
    void deveCriarClientDTOComValoresValidos() {
        ClientDTO client = new ClientDTO(1L, "Carlos Silva", new BigDecimal("5000.00"), 10);
        assertEquals(1L, client.getId());
        assertEquals("Carlos Silva", client.getName());
        assertEquals(new BigDecimal("5000.00"), client.getLimitSales());
        assertEquals(10, client.getDayClosingInvoice());
    }

    @Test
    void deveAtribuirValoresPadraoQuandoNull() {
        ClientDTO client = new ClientDTO(null, null, null, null);
        assertNull(client.getId());
        assertNull(client.getName());
        assertEquals(BigDecimal.ZERO, client.getLimitSales());
        assertEquals(0, client.getDayClosingInvoice());
    }

    @Test
    void deveConverterClientDTOParaClientModel() {
        ClientDTO clientDTO = new ClientDTO(2L, "Ana Souza", new BigDecimal("3000.00"), 5);
        Client clientModel = clientDTO.toModel();
        assertEquals(2L, clientModel.getId());
        assertEquals("Ana Souza", clientModel.getName());
        assertEquals(new BigDecimal("3000.00"), clientModel.getLimitSales());
        assertEquals(5, clientModel.getDayClosingInvoice());
    }

    @Test
    void toStringDeveRetornarNomeDoCliente() {
        ClientDTO client = new ClientDTO(3L, "José Ferreira", new BigDecimal("4500.00"), 15);
        assertEquals("José Ferreira", client.toString());
    }

}