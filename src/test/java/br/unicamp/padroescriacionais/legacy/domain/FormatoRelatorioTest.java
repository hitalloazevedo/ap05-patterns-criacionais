package br.unicamp.padroescriacionais.legacy.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FormatoRelatorio")
class FormatoRelatorioTest {

    @ParameterizedTest(name = "{0} -> extensão \"{1}\"")
    @CsvSource({
        "PDF,  pdf",
        "CSV,  csv",
        "JSON, json",
        "XML,  xml",
        "HTML, html"
    })
    @DisplayName("getExtensao retorna a extensão correta para cada formato")
    void extensaoCorreta(String formatoNome, String extensaoEsperada) {
        FormatoRelatorio formato = FormatoRelatorio.valueOf(formatoNome.trim());
        assertEquals(extensaoEsperada.trim(), formato.getExtensao());
    }
}
