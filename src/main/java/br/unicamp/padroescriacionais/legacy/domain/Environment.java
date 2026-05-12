package br.unicamp.padroescriacionais.legacy.domain;

public enum Environment {
    DEV("DEV"), 
    PROD("PROD");

    private final String value;

    Environment(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}