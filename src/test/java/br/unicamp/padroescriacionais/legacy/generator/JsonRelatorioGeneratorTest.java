package br.unicamp.padroescriacionais.legacy.generator;

import br.unicamp.padroescriacionais.legacy.domain.Relatorio;
import br.unicamp.padroescriacionais.legacy.domain.TipoRelatorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JsonRelatorioGenerator")
class JsonRelatorioGeneratorTest {

    private JsonRelatorioGenerator generator;
    private Relatorio relatorio;

    @BeforeEach
    void setUp() {
        generator = new JsonRelatorioGenerator();
        relatorio = new Relatorio("Relatorio de Clientes", "Cliente 001", TipoRelatorio.CLIENTES, LocalDateTime.of(2025, 6, 1, 10, 0));
    }

    @Test
    @DisplayName("Saída começa e termina com chaves")
    void outputIsJsonObject() {
        String resultado = generator.gerar(relatorio).trim();
        assertTrue(resultado.startsWith("{"));
        assertTrue(resultado.endsWith("}"));
    }

    @Test
    @DisplayName("Saída contém chave \"titulo\"")
    void outputContainsTituloKey() {
        assertTrue(generator.gerar(relatorio).contains("\"titulo\""));
    }

    @Test
    @DisplayName("Saída contém chave \"tipo\"")
    void outputContainsTipoKey() {
        assertTrue(generator.gerar(relatorio).contains("\"tipo\""));
    }

    @Test
    @DisplayName("Saída contém chave \"conteudo\"")
    void outputContainsConteudoKey() {
        assertTrue(generator.gerar(relatorio).contains("\"conteudo\""));
    }

    @Test
    @DisplayName("Saída contém o valor do título entre aspas")
    void outputContainsTituloValue() {
        assertTrue(generator.gerar(relatorio).contains("\"Relatorio de Clientes\""));
    }

    @Test
    @DisplayName("Aspas duplas no conteúdo são escapadas")
    void quotesInContentAreEscaped() {
        Relatorio r = new Relatorio("T", "diz \"ola\"", TipoRelatorio.VENDAS, LocalDateTime.now());
        String resultado = generator.gerar(r);
        assertTrue(resultado.contains("\\\""));
    }
}
