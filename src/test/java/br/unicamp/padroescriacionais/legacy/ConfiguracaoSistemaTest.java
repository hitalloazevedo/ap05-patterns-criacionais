package br.unicamp.padroescriacionais.legacy;

import br.unicamp.padroescriacionais.legacy.domain.ConfiguracaoSistema;
import br.unicamp.padroescriacionais.legacy.domain.Environment;
import br.unicamp.padroescriacionais.legacy.service.ConfiguracaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ConfiguracaoSistemaTest {

    @BeforeEach
    void resetSingletons() throws Exception {
        Field dev = ConfiguracaoSistema.class.getDeclaredField("devInstance");
        dev.setAccessible(true);
        dev.set(null, null);

        Field prod = ConfiguracaoSistema.class.getDeclaredField("prodInstance");
        prod.setAccessible(true);
        prod.set(null, null);
    }

    @Test
    void deveCriarConfiguracaoComValoresInformados() {
        ConfiguracaoSistema config = ConfiguracaoSistema.getInstance(Environment.DEV);

        assertEquals("Empresa XPTO Ltda.", config.getNomeEmpresa());
        assertEquals("DEV", config.getAmbiente());
        assertEquals("/tmp/relatorios", config.getDiretorioExportacao());
        assertTrue(config.isDebugAtivo());
    }

    @Test
    void devePermitirAlteracaoDeAmbiente() {
        ConfiguracaoSistema config = ConfiguracaoSistema.getInstance(Environment.DEV);
        config.setAmbiente("PROD");

        assertEquals("PROD", config.getAmbiente());
    }

    @Test
    void devePermitirAlteracaoDeDebug() {
        ConfiguracaoSistema config = ConfiguracaoSistema.getInstance(Environment.DEV);
        config.setDebugAtivo(true);

        assertTrue(config.isDebugAtivo());
    }

    @Test
    void devePermitirAlteracaoDeDiretorio() {
        ConfiguracaoSistema config = ConfiguracaoSistema.getInstance(Environment.DEV);
        config.setDiretorioExportacao("/novo/diretorio");

        assertEquals("/novo/diretorio", config.getDiretorioExportacao());
    }

    @Test
    void duasInstanciasIndependentesPodemTerAmbientesDiferentes() {
        ConfiguracaoSistema configDev = ConfiguracaoSistema.getInstance(Environment.DEV);
        ConfiguracaoSistema configProd = ConfiguracaoSistema.getInstance(Environment.PROD);

        assertNotEquals(configDev.getAmbiente(), configProd.getAmbiente());
        assertNotEquals(configDev.getDiretorioExportacao(), configProd.getDiretorioExportacao());
        assertNotEquals(configDev.isDebugAtivo(), configProd.isDebugAtivo());
    }

    @Test
    void alteracaoEmUmaInstanciaNaoAfetaOutra() {
        ConfiguracaoSistema config1 = ConfiguracaoSistema.getInstance(Environment.DEV);
        ConfiguracaoSistema config2 = ConfiguracaoSistema.getInstance(Environment.PROD);

        config1.setAmbiente("PROD");
        config2.setAmbiente("DEV");

        assertEquals("PROD", config1.getAmbiente());
        assertEquals("DEV", config2.getAmbiente());
    }

    @Test
    void configuracaoServiceDeveRetornarConfiguracaoNaoNula() {
        ConfiguracaoSistema config = ConfiguracaoSistema.getInstance(Environment.DEV);
        ConfiguracaoService service = new ConfiguracaoService(config);
        assertNotNull(service.getConfiguracao());
        assertFalse(service.getConfiguracao().getNomeEmpresa().isBlank());
    }
}
