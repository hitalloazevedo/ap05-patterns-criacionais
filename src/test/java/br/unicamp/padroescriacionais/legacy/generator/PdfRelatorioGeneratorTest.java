package br.unicamp.padroescriacionais.legacy.generator;

import br.unicamp.padroescriacionais.legacy.domain.Relatorio;
import br.unicamp.padroescriacionais.legacy.domain.TipoRelatorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PdfRelatorioGenerator")
class PdfRelatorioGeneratorTest {

    private PdfRelatorioGenerator generator;
    private Relatorio relatorio;

    @BeforeEach
    void setUp() {
        generator = new PdfRelatorioGenerator();
        relatorio = new Relatorio("Relatorio de Vendas", "conteudo", TipoRelatorio.VENDAS, LocalDateTime.of(2025, 6, 1, 10, 0));
    }

    @Test
    @DisplayName("Saída contém o título do relatório")
    void outputContainsTitulo() {
        String resultado = generator.gerar(relatorio);
        assertTrue(resultado.contains("Relatorio de Vendas"));
    }

    @Test
    @DisplayName("Saída contém o tipo do relatório")
    void outputContainsTipo() {
        String resultado = generator.gerar(relatorio);
        assertTrue(resultado.contains("VENDAS"));
    }

    @Test
    @DisplayName("Saída contém o conteúdo do relatório")
    void outputContainsConteudo() {
        String resultado = generator.gerar(relatorio);
        assertTrue(resultado.contains("conteudo"));
    }

    @Test
    @DisplayName("Saída contém marcação visual de formato PDF")
    void outputContainsPdfMark() {
        String resultado = generator.gerar(relatorio);
        assertTrue(resultado.contains("[PDF]"));
    }

    @Test
    @DisplayName("Saída não é nula nem vazia")
    void outputNotNullOrEmpty() {
        String resultado = generator.gerar(relatorio);
        assertNotNull(resultado);
        assertFalse(resultado.isBlank());
    }
}
