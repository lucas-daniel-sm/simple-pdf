package dev.lucasmendes.simple_pdf.elements;

import dev.lucasmendes.simple_pdf.enums.HorizontalAlignment;
import dev.lucasmendes.simple_pdf.enums.VerticalAlignment;
import dev.lucasmendes.simple_pdf.models.Edges;
import dev.lucasmendes.simple_pdf.models.Size;
import com.lowagie.text.Image;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * This is a concrete class that extends AbstractElement.
 * It represents an image in a PDF document.
 * It uses Image from com.lowagie.text package as the insertable element.
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PdfImage extends AbstractElement<Image> {
    @Nonnull
    private final URL imageUrl;
    @Nullable
    private final Size size;
    @Nullable
    private final Edges edges;

    /**
     * Constructor for PdfImage.
     *
     * @param imageUrl The URL of the image.
     * @param horizontalAlignment The horizontal alignment of the image.
     * @param verticalAlignment The vertical alignment of the image.
     * @param size The size of the image.
     * @param edges The edges of the image.
     */
    public PdfImage(
            @Nonnull URL imageUrl,
            @Nonnull HorizontalAlignment horizontalAlignment,
            @Nonnull VerticalAlignment verticalAlignment,
            @Nullable Size size,
            @Nullable Edges edges
    ) {
        super(horizontalAlignment, verticalAlignment);
        this.imageUrl = imageUrl;
        this.size = size;
        this.edges = edges;
    }

    /**
     * Constructor for PdfImage with default size and edges.
     *
     * @param imageUrl The URL of the image.
     * @param horizontalAlignment The horizontal alignment of the image.
     * @param verticalAlignment The vertical alignment of the image.
     */
    public PdfImage(
            @Nonnull URL imageUrl,
            @Nonnull HorizontalAlignment horizontalAlignment,
            @Nonnull VerticalAlignment verticalAlignment
    ) {
        this(imageUrl, horizontalAlignment, verticalAlignment, null, Edges.zero());
    }

    /**
     * Constructor for PdfImage with default alignments, size, and edges.
     *
     * @param imageUrl The URL of the image.
     */
    public PdfImage(@Nonnull URL imageUrl) {
        this(imageUrl, HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);
    }

    /**
     * This method is used to create a PdfImage from a file.
     *
     * @param file The file to be converted to a PdfImage.
     * @return PdfImage This returns a PdfImage created from the file.
     * @throws IOException If an I/O error occurs.
     */
    public static PdfImage fromFile(@Nonnull File file) throws IOException {
        return new PdfImage(file.toURI().toURL());
    }

    /**
     * This method is used to get the image.
     *
     * @return Image This returns an Image created from the imageUrl.
     */
    @Override
    @SneakyThrows
    public Image getElement() {
        return Image.getInstance(imageUrl);
    }

    /**
     * This method is used to set the horizontal alignment.
     *
     * @param horizontalAlignment The horizontal alignment to be set.
     * @return PdfImage This returns a PdfImage with the horizontal alignment set.
     */
    @Override
    public PdfImage withHorizontalAlignment(@Nonnull HorizontalAlignment horizontalAlignment) {
        return new PdfImage(this.imageUrl, horizontalAlignment, this.getVerticalAlignment(), this.size, this.edges);
    }

    /**
     * This method is used to set the vertical alignment.
     *
     * @param verticalAlignment The vertical alignment to be set.
     * @return PdfImage This returns a PdfImage with the vertical alignment set.
     */
    @Override
    public PdfImage withVerticalAlignment(@Nonnull VerticalAlignment verticalAlignment) {
        return new PdfImage(this.imageUrl, this.getHorizontalAlignment(), verticalAlignment, this.size, this.edges);
    }

    /**
     * This method is used to set the size.
     *
     * @param width The width of the image.
     * @param height The height of the image.
     * @return PdfImage This returns a PdfImage with the size set.
     */
    public PdfImage withSize(float width, float height) {
        return this.withSize(new Size(width, height));
    }

    /**
     * This method is used to set the size.
     *
     * @param size The size to be set.
     * @return PdfImage This returns a PdfImage with the size set.
     */
    public PdfImage withSize(@Nonnull Size size) {
        return new PdfImage(this.imageUrl, this.getHorizontalAlignment(), this.getVerticalAlignment(), size, this.edges);
    }

    /**
     * This method is used to set the edges.
     *
     * @param edges The edges to be set.
     * @return PdfImage This returns a PdfImage with the edges set.
     */
    public PdfImage withEdges(@Nonnull Edges edges) {
        return new PdfImage(this.imageUrl, this.getHorizontalAlignment(), this.getVerticalAlignment(), this.size, edges);
    }
}
