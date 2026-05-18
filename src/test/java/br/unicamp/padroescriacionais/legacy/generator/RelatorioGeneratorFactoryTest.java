package br.unicamp.padroescriacionais.legacy.generator;

import br.unicamp.padroescriacionais.legacy.domain.FormatoRelatorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RelatorioGeneratorFactory (Factory Method)")
class RelatorioGeneratorFactoryTest {

    private RelatorioGeneratorFactory factory;

    @BeforeEach
    void setUp() {
        factory = new RelatorioGeneratorFactory();
    }

    @Test
    @DisplayName("criarGerador(PDF) retorna PdfRelatorioGenerator")
    void criarGeradorPdf() {
        assertInstanceOf(PdfRelatorioGenerator.class, factory.criarGerador(FormatoRelatorio.PDF));
    }

    @Test
    @DisplayName("criarGerador(CSV) retorna CsvRelatorioGenerator")
    void criarGeradorCsv() {
        assertInstanceOf(CsvRelatorioGenerator.class, factory.criarGerador(FormatoRelatorio.CSV));
    }

    @Test
    @DisplayName("criarGerador(JSON) retorna JsonRelatorioGenerator")
    void criarGeradorJson() {
        assertInstanceOf(JsonRelatorioGenerator.class, factory.criarGerador(FormatoRelatorio.JSON));
    }

    @Test
    @DisplayName("criarGerador(XML) retorna XmlRelatorioGenerator")
    void criarGeradorXml() {
        assertInstanceOf(XmlRelatorioGenerator.class, factory.criarGerador(FormatoRelatorio.XML));
    }

    @Test
    @DisplayName("criarGerador(HTML) retorna HtmlRelatorioGenerator")
    void criarGeradorHtml() {
        assertInstanceOf(HtmlRelatorioGenerator.class, factory.criarGerador(FormatoRelatorio.HTML));
    }

    @Test
    @DisplayName("Cada chamada cria uma nova instância do gerador")
    void cadaChamadaCriaNovaInstancia() {
        RelatorioGenerator g1 = factory.criarGerador(FormatoRelatorio.PDF);
        RelatorioGenerator g2 = factory.criarGerador(FormatoRelatorio.PDF);
        assertNotSame(g1, g2);
    }
}
