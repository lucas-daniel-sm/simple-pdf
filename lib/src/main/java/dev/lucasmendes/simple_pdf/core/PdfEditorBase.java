package dev.lucasmendes.simple_pdf.core;

import dev.lucasmendes.simple_pdf.configurations.DataTableStyle;
import dev.lucasmendes.simple_pdf.configurations.PdfCommons;
import dev.lucasmendes.simple_pdf.elements.DataTable;
import dev.lucasmendes.simple_pdf.elements.Insertable;
import dev.lucasmendes.simple_pdf.elements.SimpleFont;
import dev.lucasmendes.simple_pdf.elements.SimpleParagraph;
import dev.lucasmendes.simple_pdf.enums.VerticalAlignment;
import dev.lucasmendes.simple_pdf.exceptions.CouldNotInsertException;
import lombok.Data;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;
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
     * Add a data table to the PDF.
     * This table is already configured with the default style, but you need to provide
     * at least <b>{@link List<T>} items</b> and <b>{@link Class<T>} objectClass</b>.
     * @param buildTable A consumer that receives a {@link DataTable.DataTableBuilder} and builds the table.
     * @return the current instance of the PDF editor
     */
    public <D> T addDataTable(@Nonnull Consumer<DataTable.DataTableBuilder<D>> buildTable) {
        final var builder = DataTable.<D>builder()
                .pdfCommons(this.pdfCommons)
                        .style(DataTableStyle.defaults(this.pdfCommons.getDefaultFont()));
        buildTable.accept(builder);
        return add(builder.build());
    }

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
            if (!paragraph.hasFont()) {
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
}
