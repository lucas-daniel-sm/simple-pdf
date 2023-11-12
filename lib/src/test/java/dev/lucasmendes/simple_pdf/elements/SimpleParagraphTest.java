package dev.lucasmendes.simple_pdf.elements;

import com.lowagie.text.FontFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for PdfParagraph")
class SimpleParagraphTest {
    private SimpleFont fontHelvetica;

    @BeforeEach
    public void setUp() {
        this.fontHelvetica = new SimpleFont(FontFactory.getFont(FontFactory.HELVETICA));
    }

    @Test
    @DisplayName("Test for getElement method")
    void testGetElement() {
        var simpleParagraph = new SimpleParagraph("Test paragraph", this.fontHelvetica);

        assertEquals(
                "Test paragraph",
                simpleParagraph.getElement().getContent(),
                "The content of the paragraph should be 'Test paragraph'"
        );

        assertEquals(
                FontFactory.getFont(FontFactory.HELVETICA).getFamilyname(),
                simpleParagraph.getElement().getFont().getFamilyname(),
                "The font of the paragraph should be Helvetica"
        );
    }

    @Test
    @DisplayName("Test for getName method")
    void testGetName() {
        var simpleParagraph = new SimpleParagraph("Test paragraph", this.fontHelvetica);
        assertEquals(
                SimpleParagraph.class.getName(),
                simpleParagraph.getName(),
                "The name should be the class name"
        );
    }

    @Test
    @DisplayName("Test if the addition of a font is occurring correctly")
    void testFontAddition() {
        //Create a paragraph without a font
        var simpleParagraph = new SimpleParagraph("Test paragraph");

        assertNull(simpleParagraph.getFont(), "The font should be null");
        assertFalse(simpleParagraph.hasFont(), "Don't has a font");

        // add a font
        simpleParagraph = simpleParagraph.withFont(this.fontHelvetica);

        assertNotNull(simpleParagraph.getFont(), "The font should not be null");
        assertTrue(simpleParagraph.hasFont(), "The paragraph has a font");
        assertEquals(
                "Test paragraph",
                simpleParagraph.getElement().getContent(),
                "The content of the paragraph should be 'Test paragraph'"
        );
        assertEquals(
                FontFactory.getFont(FontFactory.HELVETICA).getFamilyname(),
                simpleParagraph.getElement().getFont().getFamilyname(),
                "The font of the paragraph should be Helvetica"
        );
    }
}
