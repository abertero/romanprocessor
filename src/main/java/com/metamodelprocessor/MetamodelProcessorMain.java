package com.metamodelprocessor;

import com.metamodelprocessor.beans.Sentence;
import com.metamodelprocessor.processor.MetamodelProcessor;

public class MetamodelProcessorMain {

    public static void main(String[] args) {
        MetamodelProcessor metamodelProcessor = MetamodelProcessor.getInstance();
        String[] sentences = new String[] {
                "glob is I",
                "prok is V",
                "pish is X",
                "tegj is L",
                "glob glob Silver is 34 Credits",
                "glob prok Gold is 57800 Credits",
                "pish pish Iron is 3910 Credits"
        };
        for (String sentence : sentences) {
            System.out.println(String.format("Processing %s", sentence));
            String response = metamodelProcessor.processSentence(new Sentence(sentence));
            System.out.println(String.format("Processor response %s", response));
            System.out.println(String.format("Status %s", metamodelProcessor));
            System.out.println();
        }
    }
}
