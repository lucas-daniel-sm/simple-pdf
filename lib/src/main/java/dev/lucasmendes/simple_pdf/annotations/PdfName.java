package dev.lucasmendes.simple_pdf.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation represents the name of a PDF element.
 * It can be applied to fields and is retained at runtime.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PdfName {
    /**
     * The value of the PDF name.
     * If not specified, the default value is an empty string.
     *
     * @return the value of the PDF name
     */
    String value() default "";
}
