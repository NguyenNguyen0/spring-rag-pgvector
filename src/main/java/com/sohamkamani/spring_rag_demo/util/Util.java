package com.sohamkamani.spring_rag_demo.util;

public class Util {
    public static String sanitizeText(String text) {
        if (text == null) return null;
        return text
                .replace("\u0000", "")
                .replaceAll("\\p{Cntrl}", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }
}
