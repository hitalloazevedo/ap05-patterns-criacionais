package br.unicamp.padroescriacionais.legacy.generator;

import br.unicamp.padroescriacionais.legacy.domain.Relatorio;
import br.unicamp.padroescriacionais.legacy.domain.TipoRelatorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("XmlRelatorioGenerator")
class XmlRelatorioGeneratorTest {

    private XmlRelatorioGenerator generator;
    private Relatorio relatorio;

    @BeforeEach
    void setUp() {
        generator = new XmlRelatorioGenerator();
        relatorio = new Relatorio("Relatorio de Vendas", "Produto A", TipoRelatorio.VENDAS, LocalDateTime.of(2025, 6, 1, 10, 0));
    }

    @Test
    @DisplayName("Saída contém tag raiz <relatorio>")
    void outputContainsRootTag() {
        String resultado = generator.gerar(relatorio);
        assertTrue(resultado.contains("<relatorio>"));
        assertTrue(resultado.contains("</relatorio>"));
    }

    @Test
    @DisplayName("Saída contém tag <titulo> com o valor correto")
    void outputContainsTitulo() {
        String resultado = generator.gerar(relatorio);
        assertTrue(resultado.contains("<titulo>Relatorio de Vendas</titulo>"));
    }

    @Test
    @DisplayName("Saída contém tag <tipo> com o valor correto")
    void outputContainsTipo() {
        String resultado = generator.gerar(relatorio);
        assertTrue(resultado.contains("<tipo>VENDAS</tipo>"));
    }

    @Test
    @DisplayName("Saída contém tag <conteudo>")
    void outputContainsConteudo() {
        assertTrue(generator.gerar(relatorio).contains("<conteudo>"));
    }

    @Test
    @DisplayName("Caracteres especiais XML são escapados: &")
    void ampersandIsEscaped() {
        Relatorio r = new Relatorio("T", "A & B", TipoRelatorio.VENDAS, LocalDateTime.now());
        assertTrue(generator.gerar(r).contains("&amp;"));
    }

    @Test
    @DisplayName("Caracteres especiais XML são escapados: <")
    void lessThanIsEscaped() {
        Relatorio r = new Relatorio("T", "a < b", TipoRelatorio.VENDAS, LocalDateTime.now());
        assertTrue(generator.gerar(r).contains("&lt;"));
    }
}
