package br.unicamp.padroescriacionais.legacy.domain;

public class ConfiguracaoSistema {

    private String nomeEmpresa;
    private String ambiente;
    private String diretorioExportacao;
    private boolean debugAtivo;

    private static ConfiguracaoSistema devInstance = null;
    private static ConfiguracaoSistema prodInstance = null;

    private ConfiguracaoSistema(String nomeEmpresa, String ambiente, String diretorioExportacao, boolean debugAtivo) {
        this.nomeEmpresa = nomeEmpresa;
        this.ambiente = ambiente;
        this.diretorioExportacao = diretorioExportacao;
        this.debugAtivo = debugAtivo;
    }

    public static ConfiguracaoSistema getInstance(Environment environment){
        switch (environment) {
            case DEV:
                if (devInstance == null) {
                    devInstance = new ConfiguracaoSistema("Empresa XPTO Ltda.", "DEV", "/tmp/relatorios", true);
                }
                return devInstance;
            case PROD:
                if (prodInstance == null) {
                    prodInstance = new ConfiguracaoSistema("Empresa XPTO Ltda.", "PROD", "/var/lib/relatorios", false);
                }
                return prodInstance;
            default:
                throw new IllegalArgumentException("Ambiente invalido");
        }
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    public String getDiretorioExportacao() {
        return diretorioExportacao;
    }

    public void setDiretorioExportacao(String diretorioExportacao) {
        this.diretorioExportacao = diretorioExportacao;
    }

    public boolean isDebugAtivo() {
        return debugAtivo;
    }

    public void setDebugAtivo(boolean debugAtivo) {
        this.debugAtivo = debugAtivo;
    }
}
