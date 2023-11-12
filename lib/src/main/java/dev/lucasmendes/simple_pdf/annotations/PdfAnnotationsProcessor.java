package dev.lucasmendes.simple_pdf.annotations;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * This class is used to process annotations in a PDF document.
 */
public class PdfAnnotationsProcessor {
    /**
     * Returns the name of the field as specified by the PdfName annotation.
     * If the annotation is not present or its value is empty, the field name is formatted.
     *
     * @param field the field to get the name of
     * @return the name of the field
     */
    public static String getFieldName(Field field) {
        return Optional.ofNullable(field.getAnnotation(PdfName.class))
                .map(PdfName::value)
                .filter(s -> !s.isEmpty())
                .orElse(formatFieldName(field));
    }

    /**
     * Checks if the field should be ignored as specified by the PdfIgnore annotation.
     *
     * @param field the field to check
     * @return true if the field should be ignored, false otherwise
     */
    public static boolean ignore(Field field) {
        return Optional.ofNullable(field.getAnnotation(PdfIgnore.class))
                .map(PdfIgnore::value)
                .orElse(false);
    }

    /**
     * Formats the field name by splitting it into separate words.
     *
     * @param field the field to format the name of
     * @return the formatted name of the field
     */
    private static String formatFieldName(Field field) {
        return splitCamelCase(field.getName());
    }

    /**
     * Splits a camel case string into separate words.
     * The first word is capitalized, and the rest are in lower case.
     *
     * @param value the string to split
     * @return the split string
     */
    private static String splitCamelCase(String value) {
        final var firstFlag = new AtomicBoolean(true);
        return Arrays.stream(StringUtils.splitByCharacterTypeCamelCase(value))
                .map(str -> {
                    str = str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
                    if (firstFlag.get()) {
                        firstFlag.set(false);
                        return str;
                    }
                    return str.toLowerCase();
                })
                .collect(Collectors.joining(" "));
    }
}
