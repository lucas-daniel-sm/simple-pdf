package dev.lucasmendes.simple_pdf.elements;

import dev.lucasmendes.simple_pdf.configurations.PageConfiguration;
import dev.lucasmendes.simple_pdf.configurations.PdfCommons;
import dev.lucasmendes.simple_pdf.configurations.PdfWidth;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.parser.PdfTextExtractor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This is a test class for {@link PdfPTableEditor}.
 * It uses JUnit 5 for testing.
 */
@DisplayName("Tests for PdfPTableEditor")
class PdfPTableEditorTest {

    /**
     * Test for the getElement method of PdfPTableEditor.
     */
    @Test
    @DisplayName("Test for getElement method")
    void testGetElement() {
        // Create an instance of PdfPTableEditor
        var pdfTableEditor = new PdfPTableEditor(
                new float[]{1, 2, 3},
                new PdfCommons(new PageConfiguration())
        );

        // Assert that the element is correct
        assertEquals(
                3,
                pdfTableEditor.getElement().getNumberOfColumns(),
                "The number of columns should be 3"
        );
    }

    /**
     * Test for the getName method of PdfPTableEditor.
     */
    @Test
    @DisplayName("Test for getName method")
    void testGetName() {
        // Create an instance of PdfPTableEditor
        var pdfTableEditor = new PdfPTableEditor(
                new float[]{1, 2, 3},
                new PdfCommons(new PageConfiguration())
        );

        // Assert that the name is correct
        assertEquals(
                PdfPTableEditor.class.getName(),
                pdfTableEditor.getName(),
                "The name should be the class name"
        );
    }

    /**
     * Test for the setWidth method of PdfPTableEditor.
     */
    @Test
    @DisplayName("Test for setWidth method")
    void testSetWidth() {
        // Create an instance of PdfPTableEditor
        var pdfTableEditor = new PdfPTableEditor(
                new float[]{1, 2, 3},
                new PdfCommons(new PageConfiguration())
        );

        // Set the width of the table
        pdfTableEditor.setWidth(new PdfWidth(PdfWidth.PdfWidthType.RELATIVE, 100));

        // Assert that the width is correct
        assertEquals(
                100,
                pdfTableEditor.getElement().getWidthPercentage(),
                "The width should be 100"
        );
    }

    /**
     * Test for the setColumnsWidth method of PdfPTableEditor.
     */
    @Test
    @DisplayName("Test for setColumnsWidth method")
    void testSetColumnsWidth() {
        // Create an instance of PdfPTableEditor
        var pdfTableEditor = new PdfPTableEditor(
                new float[]{1, 2, 3},
                new PdfCommons(new PageConfiguration())
        );

        // Set the width of the columns
        pdfTableEditor.setColumnsWidth(new float[]{50, 100, 150});

        // Assert that the columns width is correct
        assertArrayEquals(
                new float[]{50, 100, 150},
                pdfTableEditor.getElement().getAbsoluteWidths(),
                "The columns width should be {50, 100, 150}"
        );
    }

    @Test
    @DisplayName("Test for insertElement method")
    void testInsertElement() throws DocumentException, IOException {
        try (var baos = new ByteArrayOutputStream()) {
            try (var document = new Document()) {
                PdfWriter.getInstance(document, baos);
                document.open();
                var pdfTableEditor = new PdfPTableEditor(1, new PdfCommons(new PageConfiguration()));
                pdfTableEditor.insertElement(new SimpleParagraph(
                        "Test paragraph",
                        new SimpleFont(FontFactory.getFont(FontFactory.HELVETICA))
                ));
                var pdfPTable = pdfTableEditor.getElement();
                document.add(pdfPTable);
            }

            PdfReader reader = new PdfReader(baos.toByteArray());
            var pdfTextExtractor = new PdfTextExtractor(reader);
            String text = pdfTextExtractor.getTextFromPage(1);

            // Assert that the text is correct
            assertEquals(
                    "Test paragraph",
                    text.trim()
            );
        }
    }
}
