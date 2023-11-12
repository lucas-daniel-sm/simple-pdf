package dev.lucasmendes.simple_pdf.elements;

import dev.lucasmendes.simple_pdf.enums.HorizontalAlignment;
import dev.lucasmendes.simple_pdf.enums.VerticalAlignment;
import com.lowagie.text.Element;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;

import javax.annotation.Nonnull;

/**
 * This is an abstract class that represents an element that can be inserted.
 * It implements the Insertable interface and provides a basic implementation of the getName method.
 * It also defines the horizontal and vertical alignment of the element.
 *
 * @param <E> This is a type parameter that extends Element class from com.lowagie.text package.
 */
@Getter
@RequiredArgsConstructor
public abstract class AbstractElement<E extends Element> implements Insertable<E> {

    /**
     * The horizontal alignment of the element.
     */
    @With
    @Nonnull
    private final HorizontalAlignment horizontalAlignment;

    /**
     * The vertical alignment of the element.
     */
    @With
    @Nonnull
    private final VerticalAlignment verticalAlignment;

    /**
     * This method is used to get the name of the element.
     *
     * @return String This returns the name of the element.
     */
    @Override
    public String getName() {
        return this.getClass().getName();
    }
}
