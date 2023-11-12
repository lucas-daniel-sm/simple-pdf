package dev.lucasmendes.simple_pdf.elements;

import dev.lucasmendes.simple_pdf.enums.HorizontalAlignment;
import dev.lucasmendes.simple_pdf.enums.VerticalAlignment;
import com.lowagie.text.Paragraph;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * This is a concrete class that extends AbstractElement.
 * It represents a paragraph in a PDF document.
 * It uses Paragraph from com.lowagie.text package as the insertable element.
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class SimpleParagraph extends AbstractElement<Paragraph> {
    private final String text;
    private final SimpleFont font;

    /**
     * Constructor for PdfParagraph.
     *
     * @param text                The text of the paragraph.
     * @param font                The font of the paragraph.
     * @param horizontalAlignment The horizontal alignment of the paragraph.
     * @param verticalAlignment   The vertical alignment of the paragraph.
     */
    public SimpleParagraph(
            @Nonnull String text,
            @Nullable SimpleFont font,
            @Nonnull HorizontalAlignment horizontalAlignment,
            @Nonnull VerticalAlignment verticalAlignment
    ) {
        super(horizontalAlignment, verticalAlignment);
        this.text = text;
        this.font = font;
    }

    /**
     * Constructor for PdfParagraph with default alignments.
     *
     * @param text The text of the paragraph.
     * @param font The font of the paragraph.
     */
    public SimpleParagraph(@Nonnull String text, @Nullable SimpleFont font) {
        this(text, font, HorizontalAlignment.LEFT, VerticalAlignment.TOP);
    }

    /**
     * Constructor for PdfParagraph with default font and alignments.
     *
     * @param text The text of the paragraph.
     */
    public SimpleParagraph(@Nonnull String text) {
        this(text, null);
    }

    /**
     * Default constructor for PdfParagraph.
     */
    public SimpleParagraph() {
        this("");
    }


    /**
     * This method is used to get the paragraph.
     *
     * @return Paragraph This returns a Paragraph with the text, font, and alignment set.
     */
    @Override
    public Paragraph getElement() {
        if (this.font == null) {
            // TODO: Create a custom exception
            // TODO: Add the exception to method signature and javadocs
            throw new RuntimeException("Cannot create a paragraph without a font");
        }
        var paragraph = new Paragraph(this.text, this.font.getOpenPdfFont());
        paragraph.setAlignment(this.getVerticalAlignment().getValue());
        return paragraph;
    }

    /**
     * This method is used to set the vertical alignment.
     *
     * @param alignment The vertical alignment to be set.
     * @return PdfParagraph This returns a PdfParagraph with the vertical alignment set.
     */
    @Override
    public SimpleParagraph withVerticalAlignment(@Nonnull VerticalAlignment alignment) {
        return new SimpleParagraph(this.text, this.font, this.getHorizontalAlignment(), alignment);
    }

    /**
     * This method is used to set the horizontal alignment.
     *
     * @param alignment The horizontal alignment to be set.
     * @return PdfParagraph This returns a PdfParagraph with the horizontal alignment set.
     */
    @Override
    public SimpleParagraph withHorizontalAlignment(@Nonnull HorizontalAlignment alignment) {
        return new SimpleParagraph(this.text, this.font, alignment, this.getVerticalAlignment());
    }

    /**
     * This method is used to set the font.
     *
     * @param font The font to be set.
     * @return PdfParagraph This returns a PdfParagraph with the font set.
     */
    public SimpleParagraph withFont(@Nonnull SimpleFont font) {
        return new SimpleParagraph(this.text, font, this.getHorizontalAlignment(), this.getVerticalAlignment());
    }

    /**
     * Verify if a font was set
     *
     * @return true if it has a font, otherwise returns false.
     */
    public boolean hasFont() {
        return this.font != null;
    }
}
