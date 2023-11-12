package dev.lucasmendes.simple_pdf.configurations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PdfWidthTest {

    @Test
    @DisplayName("Test Absolute Width Creation")
    public void testAbsolute() {
        PdfWidth pdfWidth = PdfWidth.absolute(50.0f);
        assertEquals(PdfWidth.PdfWidthType.ABSOLUTE, pdfWidth.getType());
        assertEquals(50.0f, pdfWidth.getValue());
    }

    @Test
    @DisplayName("Test Relative Width Creation")
    public void testRelative() {
        PdfWidth pdfWidth = PdfWidth.relative(75.0f);
        assertEquals(PdfWidth.PdfWidthType.RELATIVE, pdfWidth.getType());
        assertEquals(75.0f, pdfWidth.getValue());
    }

    @Test
    @DisplayName("Test Max Width Creation")
    public void testMax() {
        PdfWidth pdfWidth = PdfWidth.max();
        assertEquals(PdfWidth.PdfWidthType.RELATIVE, pdfWidth.getType());
        assertEquals(100.0f, pdfWidth.getValue());
    }
}
