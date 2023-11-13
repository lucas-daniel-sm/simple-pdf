package dev.lucasmendes.simple_pdf.elements;

import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
import dev.lucasmendes.simple_pdf.configurations.SimpleCellStyle;
import dev.lucasmendes.simple_pdf.default_events.ImageAlignmentInPdfPCellEvent;
import dev.lucasmendes.simple_pdf.enums.HorizontalAlignment;
import dev.lucasmendes.simple_pdf.enums.VerticalAlignment;
import dev.lucasmendes.simple_pdf.exceptions.ElementNotSupportedException;
import dev.lucasmendes.simple_pdf.models.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.With;

import javax.annotation.Nonnull;
import java.util.Optional;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SimpleTableCell<E extends Element> extends AbstractElement<PdfPCell> {
    @Nonnull
    private final SimpleCellStyle style;
    @Nonnull
    private final Insertable<E> content;

    public SimpleTableCell(
            @Nonnull HorizontalAlignment horizontalAlignment,
            @Nonnull VerticalAlignment verticalAlignment,
            @Nonnull SimpleCellStyle style,
            @Nonnull Insertable<E> content
    ) {
        super(horizontalAlignment, verticalAlignment);
        this.style = style;
        this.content = content;
    }

    public SimpleTableCell(@Nonnull Insertable<E> content, @Nonnull SimpleCellStyle style) {
        this(
                HorizontalAlignment.LEFT,
                VerticalAlignment.MIDDLE,
                style,
                content
        );
    }

    public SimpleTableCell(@Nonnull Insertable<E> content) {
        this(
                HorizontalAlignment.LEFT,
                VerticalAlignment.MIDDLE,
                new SimpleCellStyle(),
                content
        );
    }

    @Override
    public SimpleTableCell<E> withHorizontalAlignment(@Nonnull HorizontalAlignment horizontalAlignment) {
        return new SimpleTableCell<>(horizontalAlignment, this.getVerticalAlignment(), this.style, this.content);
    }

    @Override
    public SimpleTableCell<E> withVerticalAlignment(@Nonnull VerticalAlignment verticalAlignment) {
        return new SimpleTableCell<>(this.getHorizontalAlignment(), verticalAlignment, this.style, this.content);
    }

    public <C extends Element> SimpleTableCell<C> withContent(@Nonnull Insertable<C> content) {
        return new SimpleTableCell<>(this.getHorizontalAlignment(), this.getVerticalAlignment(), this.style, content);
    }

    @Override
    public PdfPCell getElement() {
        final var cell = new PdfPCell();

        if (this.content instanceof PdfImage) {
            var image = (PdfImage) this.content;
            var imageElement = (Image) image.getElement();
            var imgHeight = Optional.ofNullable(image.getSize())
                    .map(Size::getHeight)
                    .orElse(imageElement.getPlainHeight());
            cell.setFixedHeight(imgHeight);
            cell.setCellEvent(new ImageAlignmentInPdfPCellEvent(image));
        } else if (this.content instanceof SimpleParagraph) {
            var paragraph = (SimpleParagraph) this.content;
            cell.setPhrase(paragraph.getElement());
            cell.setHorizontalAlignment(paragraph.getHorizontalAlignment().getValue());
            cell.setVerticalAlignment(paragraph.getVerticalAlignment().getValue());
        } else {
            throw new ElementNotSupportedException(String.format("Element %s is not supported", this.content.getName()));
        }

        if (style.getBackgroundColor() != null) {
            cell.setBackgroundColor(style.getBackgroundColor());
        }

        if (style.getPadding() != null) {
            cell.setPaddingTop(style.getPadding().getTop());
            cell.setPaddingBottom(style.getPadding().getBottom());
            cell.setPaddingLeft(style.getPadding().getLeft());
            cell.setPaddingRight(style.getPadding().getRight());
        }

        return cell;
    }
}
