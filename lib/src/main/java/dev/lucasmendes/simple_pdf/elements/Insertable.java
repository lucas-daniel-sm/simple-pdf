package dev.lucasmendes.simple_pdf.elements;

import com.lowagie.text.Element;

/**
 * This is an interface for elements that can be inserted.
 * It is generic and can work with any class that extends the Element class from com.lowagie.text package.
 *
 * @param <T> This is a type parameter that extends Element class from com.lowagie.text package.
 */
public interface Insertable<T extends Element> {

    /**
     * This method is used to get the element.
     *
     * @return T This returns an instance of the class that extends Element.
     */
    T getElement();

    /**
     * This method is used to get the name of the element.
     *
     * @return String This returns the name of the element.
     */
    String getName();
}
