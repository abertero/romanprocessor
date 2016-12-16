package com.metamodelprocessor.beans;

import com.metamodelprocessor.enums.Keywords;
import com.metamodelprocessor.enums.ProcessorType;
import org.apache.commons.lang.StringUtils;

public class Sentence {
    private String sentence;
    private ProcessorType type;

    public Sentence(final String sentence) {
        this.sentence = sentence;
        this.type = checkType(sentence);
    }

    private ProcessorType checkType(final String sentence) {
        if (StringUtils.isNotBlank(sentence)) {
            if (sentence.contains(Keywords.HOW_MANY.getWord()) || sentence.contains(Keywords.HOW_MUCH.getWord())) {
                return ProcessorType.QUERY;
            } else if (sentence.contains(Keywords.CREDITS.getWord()) || sentence.contains(Keywords.IS.getWord())) {
                return ProcessorType.DEFINITION;
            }
        }
        return null;
    }

    public String getSentenceAsString() {
        return sentence;
    }

    public ProcessorType getType() {
        return type;
    }
}
