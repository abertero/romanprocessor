package com.metamodelprocessor.enums;

public enum Keywords {
    SPLIT_TOKEN(" ", 0, null), IS("is", 1, ProcessorType.DEFINITION),
    CREDITS("Credits", 1, ProcessorType.DEFINITION), HOW_MUCH("How much is", 3, ProcessorType.QUERY),
    HOW_MANY("How many Credits is", 4, ProcessorType.QUERY);

    private String word;
    private int density;
    private ProcessorType processorType;

    Keywords(final String word, final int density, final ProcessorType processorType) {
        this.word = word;
        this.density = density;
        this.processorType = processorType;
    }

    public String getWord() {
        return word;
    }

    public int getDensity() {
        return density;
    }

    public ProcessorType getProcessorType() {
        return processorType;
    }
}
