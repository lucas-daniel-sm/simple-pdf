package dev.lucasmendes.simple_pdf.elements;

import dev.lucasmendes.simple_pdf.enums.HorizontalAlignment;
import dev.lucasmendes.simple_pdf.enums.VerticalAlignment;
import com.lowagie.text.Paragraph;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This is a test class for AbstractElement.
 * It uses JUnit 5 for testing.
 */
@DisplayName("Tests for AbstractElement")
class AbstractElementTest {

    /**
     * Test for the getElement method of ParagraphElement.
     */
    @Test
    @DisplayName("Test for getElement method")
    void testGetElement() {
        // Create a paragraph
        Paragraph paragraph = new Paragraph("Test paragraph");

        // Create an instance of ParagraphElement
        ParagraphElement element = new ParagraphElement(HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE, paragraph);

        // Assert that the element is correct
        assertEquals(paragraph, element.getElement(), "The element should be the same as the paragraph we created");
    }

    /**
     * Test for the getName method of ParagraphElement.
     */
    @Test
    @DisplayName("Test for getName method")
    void testGetName() {
        // Create a paragraph
        Paragraph paragraph = new Paragraph("Test paragraph");

        // Create an instance of ParagraphElement
        ParagraphElement element = new ParagraphElement(HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE, paragraph);

        // Assert that the name is correct
        assertEquals(ParagraphElement.class.getName(), element.getName(), "The name should be the class name");
    }

    /**
     * Test for the With annotation functionality of ParagraphElement.
     */
    @Test
    @DisplayName("Test for With annotation functionality")
    void testWithAnnotation() {
        // Create a paragraph
        Paragraph paragraph = new Paragraph("Test paragraph");

        // Create an instance of ParagraphElement
        ParagraphElement element = new ParagraphElement(HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE, paragraph);

        // Create a new instance of ParagraphElement with changed alignments
        ParagraphElement newElement = element.withHorizontalAlignment(HorizontalAlignment.LEFT).withVerticalAlignment(VerticalAlignment.TOP);

        // Assert that the horizontal alignment has changed
        assertEquals(HorizontalAlignment.LEFT, newElement.getHorizontalAlignment(), "The horizontal alignment should be LEFT");

        // Assert that the vertical alignment has changed
        assertEquals(VerticalAlignment.TOP, newElement.getVerticalAlignment(), "The vertical alignment should be TOP");
    }

    public static class ParagraphElement extends AbstractElement<Paragraph> {
        private final Paragraph paragraph;

        public ParagraphElement(HorizontalAlignment horizontalAlignment, VerticalAlignment verticalAlignment, Paragraph paragraph) {
            super(horizontalAlignment, verticalAlignment);
            this.paragraph = paragraph;
        }

        @Override
        public Paragraph getElement() {
            return this.paragraph;
        }

        @Override
        public ParagraphElement withHorizontalAlignment(@Nonnull HorizontalAlignment horizontalAlignment) {
            return new ParagraphElement(horizontalAlignment, this.getVerticalAlignment(), this.paragraph);
        }

        @Override
        public ParagraphElement withVerticalAlignment(@Nonnull VerticalAlignment verticalAlignment) {
            return new ParagraphElement(this.getHorizontalAlignment(), verticalAlignment, this.paragraph);
        }
    }
}
