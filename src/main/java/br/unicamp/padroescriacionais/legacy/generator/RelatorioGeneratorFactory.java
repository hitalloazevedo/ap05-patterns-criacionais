package br.unicamp.padroescriacionais.legacy.generator;

import br.unicamp.padroescriacionais.legacy.domain.FormatoRelatorio;

public class RelatorioGeneratorFactory {

    public RelatorioGenerator criarGerador(FormatoRelatorio formato) {
        return switch (formato) {
            case PDF -> new PdfRelatorioGenerator();
            case CSV -> new CsvRelatorioGenerator();
            case JSON -> new JsonRelatorioGenerator();
            case XML -> new XmlRelatorioGenerator();
            case HTML -> new HtmlRelatorioGenerator();
        };
    }
}
