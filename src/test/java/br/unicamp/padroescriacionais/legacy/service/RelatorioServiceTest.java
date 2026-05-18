package br.unicamp.padroescriacionais.legacy.service;

import br.unicamp.padroescriacionais.legacy.domain.ConfiguracaoSistema;
import br.unicamp.padroescriacionais.legacy.domain.Environment;
import br.unicamp.padroescriacionais.legacy.domain.FormatoRelatorio;
import br.unicamp.padroescriacionais.legacy.domain.Relatorio;
import br.unicamp.padroescriacionais.legacy.domain.TipoRelatorio;
import br.unicamp.padroescriacionais.legacy.generator.RelatorioGeneratorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RelatorioService")
class RelatorioServiceTest {

    private RelatorioService service;

    @BeforeEach
    void setUp() throws Exception {
        Field dev = ConfiguracaoSistema.class.getDeclaredField("devInstance");
        dev.setAccessible(true);
        dev.set(null, null);

        ConfiguracaoSistema config = ConfiguracaoSistema.getInstance(Environment.DEV);
        service = new RelatorioService(config, new RelatorioGeneratorFactory());
    }

    @Test
    @DisplayName("criarRelatorio(VENDAS) retorna Relatorio com tipo VENDAS")
    void criarRelatorioVendas() {
        Relatorio r = service.criarRelatorio(TipoRelatorio.VENDAS);
        assertEquals(TipoRelatorio.VENDAS, r.getTipo());
        assertEquals("Relatorio de Vendas", r.getTitulo());
        assertNotNull(r.getConteudo());
        assertNotNull(r.getDataGeracao());
    }

    @Test
    @DisplayName("criarRelatorio(ESTOQUE) retorna Relatorio com tipo ESTOQUE")
    void criarRelatorioEstoque() {
        Relatorio r = service.criarRelatorio(TipoRelatorio.ESTOQUE);
        assertEquals(TipoRelatorio.ESTOQUE, r.getTipo());
        assertEquals("Relatorio de Estoque", r.getTitulo());
    }

    @Test
    @DisplayName("criarRelatorio(CLIENTES) retorna Relatorio com tipo CLIENTES")
    void criarRelatorioClientes() {
        Relatorio r = service.criarRelatorio(TipoRelatorio.CLIENTES);
        assertEquals(TipoRelatorio.CLIENTES, r.getTipo());
        assertEquals("Relatorio de Clientes", r.getTitulo());
    }

    @Test
    @DisplayName("gerarRelatorio(VENDAS, PDF) retorna String não vazia")
    void gerarRelatorioVendasPdf() {
        String resultado = service.gerarRelatorio(TipoRelatorio.VENDAS, FormatoRelatorio.PDF);
        assertNotNull(resultado);
        assertFalse(resultado.isBlank());
    }

    @Test
    @DisplayName("gerarRelatorio(ESTOQUE, CSV) contém cabeçalho CSV")
    void gerarRelatorioEstoqueCsv() {
        String resultado = service.gerarRelatorio(TipoRelatorio.ESTOQUE, FormatoRelatorio.CSV);
        assertTrue(resultado.contains("titulo,tipo,dataGeracao,conteudo"));
    }

    @Test
    @DisplayName("gerarRelatorio(CLIENTES, JSON) retorna objeto JSON válido")
    void gerarRelatorioClientesJson() {
        String resultado = service.gerarRelatorio(TipoRelatorio.CLIENTES, FormatoRelatorio.JSON).trim();
        assertTrue(resultado.startsWith("{"));
        assertTrue(resultado.endsWith("}"));
    }

    @Test
    @DisplayName("gerarRelatorio(VENDAS, XML) contém tag <relatorio>")
    void gerarRelatorioVendasXml() {
        String resultado = service.gerarRelatorio(TipoRelatorio.VENDAS, FormatoRelatorio.XML);
        assertTrue(resultado.contains("<relatorio>"));
    }

    @Test
    @DisplayName("gerarRelatorio(VENDAS, HTML) contém <!DOCTYPE html>")
    void gerarRelatorioVendasHtml() {
        String resultado = service.gerarRelatorio(TipoRelatorio.VENDAS, FormatoRelatorio.HTML);
        assertTrue(resultado.contains("<!DOCTYPE html>"));
    }

    @Test
    @DisplayName("Construtor sem factory instancia RelatorioService sem erros")
    void constructorWithoutFactory() throws Exception {
        Field dev = ConfiguracaoSistema.class.getDeclaredField("devInstance");
        dev.setAccessible(true);
        dev.set(null, null);
        ConfiguracaoSistema config = ConfiguracaoSistema.getInstance(Environment.DEV);
        assertDoesNotThrow(() -> new RelatorioService(config));
    }
}
