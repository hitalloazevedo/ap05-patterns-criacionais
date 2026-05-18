package br.unicamp.padroescriacionais.legacy.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Relatorio")
class RelatorioTest {

    private Relatorio buildRelatorio() {
        return new Relatorio(
                "Titulo Teste",
                "Conteudo Teste",
                TipoRelatorio.VENDAS,
                LocalDateTime.of(2025, 1, 1, 12, 0)
        );
    }

    @Test
    @DisplayName("Construtor inicializa todos os campos corretamente")
    void constructorSetsFields() {
        Relatorio r = buildRelatorio();
        assertEquals("Titulo Teste", r.getTitulo());
        assertEquals("Conteudo Teste", r.getConteudo());
        assertEquals(TipoRelatorio.VENDAS, r.getTipo());
        assertEquals(LocalDateTime.of(2025, 1, 1, 12, 0), r.getDataGeracao());
    }

    @Test
    @DisplayName("Setters alteram os valores dos campos")
    void settersUpdateFields() {
        Relatorio r = buildRelatorio();
        r.setTitulo("Novo Titulo");
        r.setConteudo("Novo Conteudo");
        r.setTipo(TipoRelatorio.ESTOQUE);
        LocalDateTime now = LocalDateTime.now();
        r.setDataGeracao(now);

        assertEquals("Novo Titulo", r.getTitulo());
        assertEquals("Novo Conteudo", r.getConteudo());
        assertEquals(TipoRelatorio.ESTOQUE, r.getTipo());
        assertEquals(now, r.getDataGeracao());
    }
}
