package br.com.manager.exception;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

class BusinessExceptionTest {

    @Test
    void deveCriarExcecaoComMensagem() {
        BusinessException exception = new BusinessException("Erro de negócio");
        assertEquals("Erro de negócio", exception.getMessage());
    }

    @Test
    void deveCriarExcecaoComMensagemECausa() {
        Throwable causa = new RuntimeException("Causa original");
        BusinessException exception = new BusinessException("Erro de negócio", causa);

        assertEquals("Erro de negócio", exception.getMessage());
        assertEquals(causa, exception.getCause());
    }
}