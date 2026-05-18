package br.unicamp.padroescriacionais.legacy.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ConfiguracaoSistema (Singleton)")
class ConfiguracaoSistemaTest {

    @BeforeEach
    void resetSingletons() throws Exception {
        Field dev  = ConfiguracaoSistema.class.getDeclaredField("devInstance");
        Field prod = ConfiguracaoSistema.class.getDeclaredField("prodInstance");
        dev.setAccessible(true);
        prod.setAccessible(true);
        dev.set(null, null);
        prod.set(null, null);
    }

    @Test
    @DisplayName("getInstance(DEV) retorna a mesma instância sempre")
    void devInstanceIsSingleton() {
        ConfiguracaoSistema a = ConfiguracaoSistema.getInstance(Environment.DEV);
        ConfiguracaoSistema b = ConfiguracaoSistema.getInstance(Environment.DEV);
        assertSame(a, b);
    }

    @Test
    @DisplayName("getInstance(PROD) retorna a mesma instância sempre")
    void prodInstanceIsSingleton() {
        ConfiguracaoSistema a = ConfiguracaoSistema.getInstance(Environment.PROD);
        ConfiguracaoSistema b = ConfiguracaoSistema.getInstance(Environment.PROD);
        assertSame(a, b);
    }

    @Test
    @DisplayName("Instâncias DEV e PROD são objetos distintos")
    void devAndProdAreDistinct() {
        ConfiguracaoSistema dev  = ConfiguracaoSistema.getInstance(Environment.DEV);
        ConfiguracaoSistema prod = ConfiguracaoSistema.getInstance(Environment.PROD);
        assertNotSame(dev, prod);
    }

    @Test
    @DisplayName("Instância DEV possui debug ativo")
    void devHasDebugAtivo() {
        ConfiguracaoSistema dev = ConfiguracaoSistema.getInstance(Environment.DEV);
        assertTrue(dev.isDebugAtivo());
    }

    @Test
    @DisplayName("Instância PROD possui debug inativo")
    void prodHasDebugInativo() {
        ConfiguracaoSistema prod = ConfiguracaoSistema.getInstance(Environment.PROD);
        assertFalse(prod.isDebugAtivo());
    }

    @Test
    @DisplayName("Instância DEV usa diretório /tmp/relatorios")
    void devDiretorio() {
        assertEquals("/tmp/relatorios", ConfiguracaoSistema.getInstance(Environment.DEV).getDiretorioExportacao());
    }

    @Test
    @DisplayName("Instância PROD usa diretório /var/lib/relatorios")
    void prodDiretorio() {
        assertEquals("/var/lib/relatorios", ConfiguracaoSistema.getInstance(Environment.PROD).getDiretorioExportacao());
    }

    @Test
    @DisplayName("Getters e setters funcionam corretamente")
    void gettersSetters() {
        ConfiguracaoSistema cfg = ConfiguracaoSistema.getInstance(Environment.DEV);
        cfg.setNomeEmpresa("Teste Ltda.");
        cfg.setAmbiente("STAGING");
        cfg.setDiretorioExportacao("/tmp/test");
        cfg.setDebugAtivo(false);

        assertEquals("Teste Ltda.", cfg.getNomeEmpresa());
        assertEquals("STAGING", cfg.getAmbiente());
        assertEquals("/tmp/test", cfg.getDiretorioExportacao());
        assertFalse(cfg.isDebugAtivo());
    }
}
