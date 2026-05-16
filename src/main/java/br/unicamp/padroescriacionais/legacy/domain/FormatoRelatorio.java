package br.unicamp.padroescriacionais.legacy.domain;

public enum FormatoRelatorio {
    PDF,
    CSV,
    JSON,
    XML,
    HTML;

    public String getExtensao() {
        return switch (this) {
            case PDF -> "pdf";
            case CSV -> "csv";
            case JSON -> "json";
            case XML -> "xml";
            case HTML -> "html";
        };
    }
}
