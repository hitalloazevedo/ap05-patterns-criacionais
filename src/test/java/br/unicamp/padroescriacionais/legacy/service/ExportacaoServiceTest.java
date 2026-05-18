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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ExportacaoService")
class ExportacaoServiceTest {

    private ExportacaoService service;
    private Relatorio relatorio;

    @BeforeEach
    void setUp() throws Exception {
        Field prod = ConfiguracaoSistema.class.getDeclaredField("prodInstance");
        prod.setAccessible(true);
        prod.set(null, null);

        ConfiguracaoSistema config = ConfiguracaoSistema.getInstance(Environment.PROD);
        service = new ExportacaoService(config, new RelatorioGeneratorFactory());
        relatorio = new Relatorio("Relatorio de Vendas", "Produto A", TipoRelatorio.VENDAS, LocalDateTime.of(2025, 6, 1, 10, 0));
    }

    private String capturarSaida(Runnable acao) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(baos));
        try {
            acao.run();
        } finally {
            System.setOut(original);
        }
        return baos.toString();
    }

    @Test
    @DisplayName("exportar imprime o nome da empresa na saída")
    void exportarImprimeEmpresa() {
        String saida = capturarSaida(() -> service.exportar(relatorio, FormatoRelatorio.PDF));
        assertTrue(saida.contains("Empresa XPTO Ltda."));
    }

    @Test
    @DisplayName("exportar imprime o ambiente PROD na saída")
    void exportarImprimeAmbiente() {
        String saida = capturarSaida(() -> service.exportar(relatorio, FormatoRelatorio.PDF));
        assertTrue(saida.contains("PROD"));
    }

    @Test
    @DisplayName("exportar imprime o caminho completo do arquivo com extensão correta")
    void exportarImprimeCaminhoComExtensao() {
        String saida = capturarSaida(() -> service.exportar(relatorio, FormatoRelatorio.CSV));
        assertTrue(saida.contains(".csv"));
        assertTrue(saida.contains("/var/lib/relatorios"));
    }

    @Test
    @DisplayName("exportar imprime o nome do arquivo derivado do título")
    void exportarImprimeNomeArquivo() {
        String saida = capturarSaida(() -> service.exportar(relatorio, FormatoRelatorio.JSON));
        assertTrue(saida.contains("relatorio_de_vendas.json"));
    }

    @Test
    @DisplayName("exportar imprime o conteúdo gerado")
    void exportarImprimeConteudo() {
        String saida = capturarSaida(() -> service.exportar(relatorio, FormatoRelatorio.XML));
        assertTrue(saida.contains("<relatorio>"));
    }

    @Test
    @DisplayName("Construtor sem factory instancia ExportacaoService sem erros")
    void constructorWithoutFactory() throws Exception {
        Field prod = ConfiguracaoSistema.class.getDeclaredField("prodInstance");
        prod.setAccessible(true);
        prod.set(null, null);
        ConfiguracaoSistema config = ConfiguracaoSistema.getInstance(Environment.PROD);
        assertDoesNotThrow(() -> new ExportacaoService(config));
    }
}
