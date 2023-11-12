package dev.lucasmendes.simple_pdf.default_events;

import dev.lucasmendes.simple_pdf.elements.PdfImage;
import dev.lucasmendes.simple_pdf.models.Edges;
import dev.lucasmendes.simple_pdf.models.Size;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfPTable;

import javax.annotation.Nonnull;
import java.util.Optional;


public class ImageAlignmentInPdfPCellEvent implements PdfPCellEvent {
    @Nonnull
    private final PdfImage image;

    public ImageAlignmentInPdfPCellEvent(@Nonnull PdfImage image) {
        this.image = image;
    }

    private float calculatePosX(Rectangle position, Image imageElement) {
        switch (this.image.getHorizontalAlignment()) {
            case LEFT:
                return position.getLeft();
            case RIGHT:
                return position.getRight() - imageElement.getScaledWidth();
            case CENTER:
            case UNDEFINED:
                return position.getLeft() + (position.getWidth() - imageElement.getScaledWidth()) / 2;
            default:
                throw new IllegalArgumentException("Invalid horizontal alignment");
        }
    }

    private float calculatePosY(Rectangle position, Image imageElement) {
        switch (this.image.getVerticalAlignment()) {
            case TOP:
                return position.getTop() - imageElement.getScaledHeight();
            case BOTTOM:
                return position.getBottom();
            case MIDDLE:
            case UNDEFINED:
                return position.getBottom() + (position.getHeight() - imageElement.getScaledHeight()) / 2;
            default:
                throw new IllegalArgumentException("Invalid horizontal alignment");
        }
    }

    public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases) {
        var imageElement = this.image.getElement();
        var size = Optional.ofNullable(this.image.getSize()).map(desiredSize -> {
                    var width = Math.min(desiredSize.getWidth(), position.getWidth());
                    var height = Math.min(desiredSize.getHeight(), position.getHeight());
                    return new Size(width, height);
                })
                .orElse(new Size(position.getWidth(), position.getHeight()));

        var edges = Optional.ofNullable(this.image.getEdges()).orElse(Edges.zero());
        size = size.subtractEdge(edges);

        imageElement.scaleToFit(size.getWidth(), size.getHeight());

        float poxX = calculatePosX(position, imageElement);
        float poxY = calculatePosY(position, imageElement);
        imageElement.setAbsolutePosition(poxX, poxY);

        PdfContentByte canvas = canvases[PdfPTable.BACKGROUNDCANVAS];
        try {
            canvas.addImage(imageElement);
        } catch (DocumentException ignore) {
        }
    }
}
