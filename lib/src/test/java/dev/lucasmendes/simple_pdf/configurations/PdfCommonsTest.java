package dev.lucasmendes.simple_pdf.configurations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PdfCommonsTest {

    @Test
    @DisplayName("Test Default Font and Page Configuration")
    public void testDefaultFontAndPageConfiguration() {
        PageConfiguration pageConfig = new PageConfiguration();
        PdfCommons pdfCommons = new PdfCommons(pageConfig);
        assertEquals(pageConfig, pdfCommons.getPageConfiguration(), "Passed page config must persist.");
        assertNull(pdfCommons.getDefaultFont(), "There is no font registered at start.");
        assertNotNull(pdfCommons.getRegisteredFonts(), "Registered font must not be null.");
        assertEquals(0, pdfCommons.getRegisteredFonts().size(), "RegisteredFonts must start empty.");
    }

    @Test
    @DisplayName("Test Registered Fonts List Initialization")
    public void testRegisteredFontsListInitialization() {
        PageConfiguration pageConfig = new PageConfiguration();
        PdfCommons pdfCommons = new PdfCommons(pageConfig);
        assertNotNull(pdfCommons.getRegisteredFonts());
        assertTrue(pdfCommons.getRegisteredFonts().isEmpty());
    }
}
