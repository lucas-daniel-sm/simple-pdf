package dev.lucasmendes.simple_pdf.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to indicate that a field should be ignored when processing a PDF.
 * It can be applied to fields and is retained at runtime.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PdfIgnore {
    /**
     * The value of the PdfIgnore annotation.
     * If not specified, the default value is true, meaning the field will be ignored.
     *
     * @return the value of the PdfIgnore annotation
     */
    boolean value() default true;
}
