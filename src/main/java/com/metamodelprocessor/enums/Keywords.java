package com.metamodelprocessor.enums;

public enum Keywords {
    SPLIT_TOKEN(" ", 0, null), IS("is", 1, ProcessorType.DEFINITION),
    CREDITS("Credits", 1, ProcessorType.DEFINITION), HOW_MUCH("how much is", 3, ProcessorType.QUERY),
    HOW_MANY("how many Credits is", 4, ProcessorType.QUERY), QUESTION_MARK("?", 1, null);

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
