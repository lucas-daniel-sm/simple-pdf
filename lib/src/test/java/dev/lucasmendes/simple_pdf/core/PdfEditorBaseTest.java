package dev.lucasmendes.simple_pdf.core;

import dev.lucasmendes.simple_pdf.configurations.PageConfiguration;
import dev.lucasmendes.simple_pdf.configurations.PdfCommons;
import dev.lucasmendes.simple_pdf.elements.Insertable;
import dev.lucasmendes.simple_pdf.elements.SimpleFont;
import dev.lucasmendes.simple_pdf.elements.SimpleParagraph;
import com.lowagie.text.Chunk;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PdfEditorBaseTest {

    private SimpleFont fontHelvetica;
    private SimpleParagraph emptyParagraphWithFont;
    private SimpleParagraph testParagraphWithFont;
    private SimpleParagraph testParagraphWithoutFont;

    @BeforeEach
    public void setUp() {
        this.fontHelvetica = new SimpleFont(FontFactory.getFont(FontFactory.HELVETICA));
        this.emptyParagraphWithFont = new SimpleParagraph("", this.fontHelvetica);
        this.testParagraphWithFont = new SimpleParagraph("Test paragraph", this.fontHelvetica);
        this.testParagraphWithoutFont = new SimpleParagraph("Test paragraph");
    }

    @Test
    @DisplayName("Test getT method")
    void testGetT() {
        var editor = new PdfEditorBaseExample();
        assertEquals(editor.getClass(), PdfEditorBaseExample.class);
    }

    @Test
    @DisplayName("Test throwIfFalse method")
    void testThrowIfFalse() {
        var editor = new PdfEditorBaseExample();
        assertThrows(RuntimeException.class, () -> editor.throwIfFalse(false, RuntimeException::new));
    }

    @Test
    @DisplayName("Test if the return of insertElement is false the error will be throw")
    void testErrorAtInsertion() {
        var editor = new PdfEditorBaseExampleWithBuggedInsertion();
        assertThrows(RuntimeException.class, () -> editor.add(this.emptyParagraphWithFont));
    }

    @Test
    @DisplayName("Test the insertion of a valid element")
    void testInsertValidElement() {
        var editor = new PdfEditorBaseExample();
        var insertResult = editor.insertElement(this.testParagraphWithFont);
        assertTrue(insertResult);
        assertEquals(1, editor.elements.size(), "Array must have only one element.");

        var readElement = editor.elements.get(0);
        assertEquals(readElement.getClass(), Paragraph.class);

        var chunk = (Chunk) readElement.getChunks().get(0);
        assertEquals(
                this.testParagraphWithFont.getText(),
                chunk.getContent(),
                "Read element must be equals to the inserted element."
        );
    }

    @Test
    @DisplayName("Test the insertion of a invalid element")
    void testInsertInvalidElement() {
        var editor = new PdfEditorBaseExample();
        assertThrows(RuntimeException.class, () -> editor.insertElement(this.testParagraphWithoutFont));
        assertEquals(0, editor.elements.size(), "Array must have no element.");
    }


    @Test
    @DisplayName("Test the addition of a valid element")
    void testAddValidElement() {
        var editor = new PdfEditorBaseExample();
        var insertResult = editor.add(this.testParagraphWithFont);
        assertNotNull(insertResult);

        var readElement = editor.elements.get(0);
        assertEquals(
                1,
                editor.elements.size(),
                "Array must have only one element."
        );
        assertEquals(readElement.getClass(), Paragraph.class);

        var chunk = (Chunk) readElement.getChunks().get(0);
        assertEquals(
                this.testParagraphWithFont.getText(),
                chunk.getContent(),
                "Text of the read element must be equals to the inserted element."
        );
        assertEquals(
                this.fontHelvetica.getOpenPdfFont().getFamilyname(),
                chunk.getFont().getFamilyname(),
                "Font of the read element must be equals to the inserted element."
        );
    }

    @Test
    @DisplayName("Test the addition of a paragraph without a font with default font set at pdfCommons")
    void testAddParagraphWithoutFont() {
        var editor = new PdfEditorBaseExample();
        editor.getPdfCommons().setDefaultFont(this.fontHelvetica);

        var insertResult = editor.add(this.testParagraphWithoutFont);
        assertNotNull(insertResult);
        assertEquals(
                1,
                editor.elements.size(),
                "Array must have only one element."
        );

        var readElement = editor.elements.get(0);
        assertEquals(readElement.getClass(), Paragraph.class, "Element must be a paragraph");
        assertNotEquals(0, readElement.getChunks().size(), "read element must have chunk");

        var chunk = (Chunk) readElement.getChunks().get(0);
        assertNotNull(chunk);
        assertEquals(
                fontHelvetica.getOpenPdfFont().getFamilyname(),
                chunk.getFont().getFamilyname(),
                "Read element font must be equals to the inserted element."
        );
        assertEquals(
                this.testParagraphWithoutFont.getText(),
                chunk.getContent(),
                "Read element must have the same text to the inserted element."
        );
    }


    private static class PdfEditorBaseExampleWithBuggedInsertion extends PdfEditorBase<PdfEditorBaseExampleWithBuggedInsertion> {
        public PdfEditorBaseExampleWithBuggedInsertion() {
            super(new PdfCommons(new PageConfiguration()));
        }

        @Override
        protected boolean insertElement(Insertable<?> insertable) {
            return false;
        }

        @Override
        protected PdfEditorBaseExampleWithBuggedInsertion getT() {
            return this;
        }
    }

    private static class PdfEditorBaseExample extends PdfEditorBase<PdfEditorBaseExample> {
        private final List<Element> elements = new ArrayList<>();

        public PdfEditorBaseExample() {
            super(new PdfCommons(new PageConfiguration()));
        }

        @Override
        protected boolean insertElement(Insertable<?> insertable) {
            var el = insertable.getElement();
            return this.elements.add(el);
        }

        @Override
        protected PdfEditorBaseExample getT() {
            return this;
        }
    }
}
