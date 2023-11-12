package dev.lucasmendes.simple_pdf.core;

import dev.lucasmendes.simple_pdf.configurations.PdfCommons;
import dev.lucasmendes.simple_pdf.elements.Insertable;
import dev.lucasmendes.simple_pdf.elements.SimpleFont;
import dev.lucasmendes.simple_pdf.elements.SimpleParagraph;
import lombok.Data;

import java.util.function.Supplier;

/**
 * Base class for PDF editors.
 *
 * @param <T> the type of the PDF editor
 */
@Data
public abstract class PdfEditorBase<T extends PdfEditorBase<T>> {
    private final PdfCommons pdfCommons;

    /**
     * Inserts an element into the PDF.
     *
     * @param insertable the element to be inserted
     * @return true if the element was inserted successfully, false otherwise
     */
    protected abstract boolean insertElement(Insertable<?> insertable);

    /**
     * Returns the current instance of the PDF editor.
     *
     * @return the current instance
     */
    protected abstract T getT();

    /**
     * Adds an insertable element to the PDF.
     *
     * @param insertable the element to be added
     * @return the current instance of the PDF editor
     * @throws CouldNotInsertException if the element could not be inserted
     */
    public T add(Insertable<?> insertable) {
        if (insertable instanceof SimpleParagraph) {
            var paragraph = (SimpleParagraph) insertable;
            if(!paragraph.hasFont()){
                insertable = paragraph.withFont(this.pdfCommons.getDefaultFont());
            }
        }
        final var insertableFinal = insertable;
        throwIfFalse(
                this.insertElement(insertable),
                () -> new CouldNotInsertException(insertableFinal)
        );
        return getT();
    }

    /**
     * Throws an exception if the result is false.
     *
     * @param result           the result to check
     * @param exceptionBuilder the supplier of the exception to be thrown
     * @throws RuntimeException if the result is false
     */
    protected void throwIfFalse(boolean result, Supplier<? extends RuntimeException> exceptionBuilder) {
        if (result) {
            return;
        }
        throw exceptionBuilder.get();
    }

    /**
     * Get the default font
     *
     * @return the default font
     */
    public SimpleFont getDefaultFont() {
        return this.pdfCommons.getDefaultFont();
    }


    /**
     * Exception thrown when a PDF could not be created.
     */
    public static class CouldNotCreateException extends RuntimeException {
        public CouldNotCreateException() {
            super("Could not create.");
        }
    }

    /**
     * Exception thrown when an element could not be inserted into a PDF.
     */
    public static class CouldNotInsertException extends RuntimeException {
        public CouldNotInsertException(Insertable<?> insertable) {
            super("Could not insert element." + insertable.getName());
        }
    }

    /**
     * Exception thrown when a font is not registered.
     */
    public static class FontNotRegisteredException extends RuntimeException {
        public FontNotRegisteredException() {
            super("Font not registered, register your font before try to use it.");
        }
    }
}
