package dev.lucasmendes.simple_pdf.configurations;

import dev.lucasmendes.simple_pdf.elements.SimpleFont;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents common configurations for a PDF document.
 */
@Data
public class PdfCommons {
    /**
     * A list of fonts that have been registered for use in the PDF document.
     */
    private final List<SimpleFont> registeredFonts = new ArrayList<>();

    /**
     * The configuration for the pages in the PDF document.
     */
    private final PageConfiguration pageConfiguration;

    /**
     * The default font to be used in the PDF document.
     */
    private SimpleFont defaultFont = null;
}
