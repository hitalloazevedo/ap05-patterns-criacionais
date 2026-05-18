package br.unicamp.padroescriacionais.legacy.generator;

import br.unicamp.padroescriacionais.legacy.domain.Relatorio;
import br.unicamp.padroescriacionais.legacy.domain.TipoRelatorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CsvRelatorioGenerator")
class CsvRelatorioGeneratorTest {

    private CsvRelatorioGenerator generator;
    private Relatorio relatorio;

    @BeforeEach
    void setUp() {
        generator = new CsvRelatorioGenerator();
        relatorio = new Relatorio("Relatorio de Estoque", "Item X: 500 unidades", TipoRelatorio.ESTOQUE, LocalDateTime.of(2025, 6, 1, 10, 0));
    }

    @Test
    @DisplayName("Primeira linha é o cabeçalho CSV")
    void firstLineIsHeader() {
        String resultado = generator.gerar(relatorio);
        String primeiraLinha = resultado.split("\n")[0];
        assertEquals("titulo,tipo,dataGeracao,conteudo", primeiraLinha);
    }

    @Test
    @DisplayName("Segunda linha contém os dados do relatório")
    void secondLineContainsData() {
        String resultado = generator.gerar(relatorio);
        String segundaLinha = resultado.split("\n")[1];
        assertTrue(segundaLinha.contains("Relatorio de Estoque"));
        assertTrue(segundaLinha.contains("ESTOQUE"));
    }

    @Test
    @DisplayName("Conteúdo com aspas duplas é escapado corretamente")
    void quotesAreEscaped() {
        Relatorio r = new Relatorio("Titulo \"Especial\"", "conteudo", TipoRelatorio.CLIENTES, LocalDateTime.now());
        String resultado = generator.gerar(r);
        assertTrue(resultado.contains("\"\""));
    }
}
