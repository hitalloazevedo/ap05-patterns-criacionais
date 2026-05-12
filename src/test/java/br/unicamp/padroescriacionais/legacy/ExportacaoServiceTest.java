package br.unicamp.padroescriacionais.legacy;

import br.unicamp.padroescriacionais.legacy.domain.ConfiguracaoSistema;
import br.unicamp.padroescriacionais.legacy.domain.Environment;
import br.unicamp.padroescriacionais.legacy.domain.FormatoRelatorio;
import br.unicamp.padroescriacionais.legacy.domain.Relatorio;
import br.unicamp.padroescriacionais.legacy.domain.TipoRelatorio;
import br.unicamp.padroescriacionais.legacy.service.ExportacaoService;
import br.unicamp.padroescriacionais.legacy.service.RelatorioService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ExportacaoServiceTest {

    private ExportacaoService exportacaoService;
    private RelatorioService relatorioService;

    private final PrintStream saidaOriginal = System.out;
    private ByteArrayOutputStream saidaCapturada;

    @BeforeEach
    void setUp() {
        resetSingletons();
        ConfiguracaoSistema config = ConfiguracaoSistema.getInstance(Environment.DEV);
        exportacaoService = new ExportacaoService(config);
        relatorioService = new RelatorioService(config);
        saidaCapturada = new ByteArrayOutputStream();
        System.setOut(new PrintStream(saidaCapturada));
    }

    private void resetSingletons() {
        try {
            Field dev = ConfiguracaoSistema.class.getDeclaredField("devInstance");
            dev.setAccessible(true);
            dev.set(null, null);

            Field prod = ConfiguracaoSistema.class.getDeclaredField("prodInstance");
            prod.setAccessible(true);
            prod.set(null, null);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void tearDown() {
        System.setOut(saidaOriginal);
    }

    @Test
    void deveExportarRelatorioEmPdfSemErro() {
        Relatorio relatorio = relatorioService.criarRelatorio(TipoRelatorio.VENDAS);
        assertDoesNotThrow(() -> exportacaoService.exportar(relatorio, FormatoRelatorio.PDF));
    }

    @Test
    void deveExportarRelatorioEmCsvSemErro() {
        Relatorio relatorio = relatorioService.criarRelatorio(TipoRelatorio.ESTOQUE);
        assertDoesNotThrow(() -> exportacaoService.exportar(relatorio, FormatoRelatorio.CSV));
    }

    @Test
    void deveExportarRelatorioEmJsonSemErro() {
        Relatorio relatorio = relatorioService.criarRelatorio(TipoRelatorio.CLIENTES);
        assertDoesNotThrow(() -> exportacaoService.exportar(relatorio, FormatoRelatorio.JSON));
    }

    @Test
    void exportacaoDeveExibirCaminhoDoArquivo() {
        Relatorio relatorio = relatorioService.criarRelatorio(TipoRelatorio.VENDAS);
        exportacaoService.exportar(relatorio, FormatoRelatorio.PDF);

        String saida = saidaCapturada.toString();
        assertTrue(saida.contains("EXPORTACAO"), "Saida deve conter cabecalho de exportacao");
        assertTrue(saida.contains(".pdf"), "Saida deve informar o nome do arquivo com extensao .pdf");
    }

    @Test
    void exportacaoDeveExibirNomeDaEmpresa() {
        Relatorio relatorio = relatorioService.criarRelatorio(TipoRelatorio.CLIENTES);
        exportacaoService.exportar(relatorio, FormatoRelatorio.CSV);

        String saida = saidaCapturada.toString();
        assertFalse(saida.isBlank());
        assertTrue(saida.contains("Empresa"), "Saida deve mencionar a empresa");
    }

    @Test
    void exportacaoDeveExibirConteudoDoRelatorio() {
        Relatorio relatorio = relatorioService.criarRelatorio(TipoRelatorio.ESTOQUE);
        exportacaoService.exportar(relatorio, FormatoRelatorio.JSON);

        String saida = saidaCapturada.toString();
        assertTrue(saida.contains("{"), "Saida JSON deve conter '{'");
    }

    @Test
    void todosFormatosDevemSerExportadosSemErro() {
        for (FormatoRelatorio formato : FormatoRelatorio.values()) {
            Relatorio relatorio = relatorioService.criarRelatorio(TipoRelatorio.VENDAS);
            assertDoesNotThrow(
                    () -> exportacaoService.exportar(relatorio, formato),
                    "Exportacao falhou para formato: " + formato
            );
        }
    }
}
