package br.unicamp.padroescriacionais.legacy.generator;

import br.unicamp.padroescriacionais.legacy.domain.Relatorio;
import br.unicamp.padroescriacionais.legacy.domain.TipoRelatorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HtmlRelatorioGenerator")
class HtmlRelatorioGeneratorTest {

    private HtmlRelatorioGenerator generator;
    private Relatorio relatorio;

    @BeforeEach
    void setUp() {
        generator = new HtmlRelatorioGenerator();
        relatorio = new Relatorio("Relatorio de Clientes", "Cliente 001", TipoRelatorio.CLIENTES, LocalDateTime.of(2025, 6, 1, 10, 0));
    }

    @Test
    @DisplayName("Saída contém declaração <!DOCTYPE html>")
    void outputContainsDoctype() {
        assertTrue(generator.gerar(relatorio).contains("<!DOCTYPE html>"));
    }

    @Test
    @DisplayName("Saída contém tag <html>")
    void outputContainsHtmlTag() {
        String resultado = generator.gerar(relatorio);
        assertTrue(resultado.contains("<html"));
        assertTrue(resultado.contains("</html>"));
    }

    @Test
    @DisplayName("Saída contém <title> com o título do relatório")
    void outputContainsTitle() {
        assertTrue(generator.gerar(relatorio).contains("<title>Relatorio de Clientes</title>"));
    }

    @Test
    @DisplayName("Saída contém <h1> com o título do relatório")
    void outputContainsH1() {
        assertTrue(generator.gerar(relatorio).contains("<h1>Relatorio de Clientes</h1>"));
    }

    @Test
    @DisplayName("Saída contém tag <pre> com o conteúdo")
    void outputContainsPre() {
        String resultado = generator.gerar(relatorio);
        assertTrue(resultado.contains("<pre>"));
        assertTrue(resultado.contains("Cliente 001"));
    }

    @Test
    @DisplayName("Caracteres especiais HTML são escapados: <")
    void lessThanIsEscaped() {
        Relatorio r = new Relatorio("T", "a < b", TipoRelatorio.VENDAS, LocalDateTime.now());
        assertTrue(generator.gerar(r).contains("&lt;"));
    }

    @Test
    @DisplayName("Caracteres especiais HTML são escapados: &")
    void ampersandIsEscaped() {
        Relatorio r = new Relatorio("T", "A & B", TipoRelatorio.VENDAS, LocalDateTime.now());
        assertTrue(generator.gerar(r).contains("&amp;"));
    }
}
