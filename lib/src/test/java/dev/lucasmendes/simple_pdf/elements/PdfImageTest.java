package dev.lucasmendes.simple_pdf.elements;

import dev.lucasmendes.simple_pdf.enums.HorizontalAlignment;
import dev.lucasmendes.simple_pdf.enums.VerticalAlignment;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * This is a test class for PdfImage.
 * It uses JUnit 5 for testing.
 */
@DisplayName("Tests for PdfImage")
class PdfImageTest {

    /**
     * Test for the getElement method of PdfImage.
     */
    @Test
    @DisplayName("Test for getElement method")
    @SneakyThrows
    void testGetElement() {
        // Create an instance of PdfImage
        var pdfImage = new PdfImage(
                new URL("https://picsum.photos/200"),
                HorizontalAlignment.CENTER,
                VerticalAlignment.MIDDLE
        );

        // Assert that the element is correct
        assertEquals(
                "https://picsum.photos/200",
                pdfImage.getElement().getUrl().toString(),
                "The URL of the image should be 'https://picsum.photos/200'"
        );
    }

    /**
     * Test for the getName method of PdfImage.
     */
    @Test
    @DisplayName("Test for getName method")
    @SneakyThrows
    void testGetName() {
        // Create an instance of PdfImage
        var pdfImage = new PdfImage(
                new URL("https://picsum.photos/200"),
                HorizontalAlignment.CENTER,
                VerticalAlignment.MIDDLE
        );

        // Assert that the name is correct
        assertEquals(PdfImage.class.getName(), pdfImage.getName(), "The name should be the class name");
    }

    /**
     * Test for the withSize method of PdfImage.
     */
    @Test
    @DisplayName("Test for withSize method")
    @SneakyThrows
    void testWithSize() {
        // Create an instance of PdfImage
        var pdfImage = new PdfImage(
                new URL("https://picsum.photos/200"),
                HorizontalAlignment.CENTER,
                VerticalAlignment.MIDDLE
        );

        // Create a new instance of PdfImage with changed size
        var newPdfImage = pdfImage.withSize(200, 200);

        // Assert that the size has changed
        assertNotNull(newPdfImage.getSize());
        assertEquals(200, newPdfImage.getSize().getWidth(), "The width should be 200");
        assertEquals(200, newPdfImage.getSize().getHeight(), "The height should be 200");
    }
}
