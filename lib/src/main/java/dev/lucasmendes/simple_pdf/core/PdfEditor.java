package dev.lucasmendes.simple_pdf.core;

import dev.lucasmendes.simple_pdf.configurations.PdfCommons;
import dev.lucasmendes.simple_pdf.elements.Insertable;
import dev.lucasmendes.simple_pdf.elements.PdfPTableEditor;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.function.Consumer;

/**
 * PdfEditor class extends {@link PdfEditorBase} and provides functionalities to edit a PDF document.
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PdfEditor extends PdfEditorBase<PdfEditor> {
    private final Document document;
    private final PdfWriter writer;

    /**
     * Constructor for PdfEditor.
     *
     * @param pdfCommons {@link PdfCommons} object.
     * @param document   {@link Document} object.
     * @param writer     {@link PdfWriter} object.
     */
    public PdfEditor(PdfCommons pdfCommons, Document document, PdfWriter writer) {
        super(pdfCommons);
        this.document = document;
        this.writer = writer;
    }

    /**
     * Inserts an element into the document.
     *
     * @param insertable {@link Insertable} object.
     * @return boolean indicating if the element was inserted successfully.
     */
    @Override
    protected boolean insertElement(Insertable<?> insertable) {
        return this.document.add(insertable.getElement());
    }

    @Override
    protected PdfEditor getT() {
        return this;
    }

    /**
     * Creates a new page in the document.
     *
     * @return this {@link PdfEditor} object.
     */
    public PdfEditor newPage() {
        throwIfFalse(this.document.newPage(), CouldNotCreateException::new);
        return this;
    }

    /**
     * Adds a table to the document with specified relative widths.
     *
     * @param relativeWidth array of relative widths for the columns.
     * @param editTable     {@link Consumer} functional interface to edit the table.
     * @return this {@link PdfEditor} object.
     */
    public PdfEditor addTable(float[] relativeWidth, Consumer<PdfPTableEditor> editTable) {
        editTable.andThen(this::add).accept(new PdfPTableEditor(relativeWidth, this.getPdfCommons()));
        return this;
    }

    /**
     * Adds a table to the document with specified number of columns.
     *
     * @param numColumns number of columns in the table.
     * @param editTable  {@link Consumer} functional interface to edit the table.
     * @return this {@link PdfEditor} object.
     */
    public PdfEditor addTable(int numColumns, Consumer<PdfPTableEditor> editTable) {
        editTable.andThen(this::add).accept(new PdfPTableEditor(numColumns, this.getPdfCommons()));
        return this;
    }
}
