package dev.lucasmendes.simple_pdf.elements;

import dev.lucasmendes.simple_pdf.configurations.PdfCommons;
import dev.lucasmendes.simple_pdf.configurations.PdfWidth;
import dev.lucasmendes.simple_pdf.core.PdfEditorBase;
import dev.lucasmendes.simple_pdf.default_events.ImageAlignmentInPdfPCellEvent;
import dev.lucasmendes.simple_pdf.models.Size;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

import java.util.Optional;

/**
 * This is a concrete class that extends {@link PdfEditorBase}.
 * It represents a table in a PDF document.
 * It uses {@link PdfPTable} from {@link com.lowagie.text.pdf} package as the insertable element.
 */
public class PdfPTableEditor extends PdfEditorBase<PdfPTableEditor> implements Insertable<PdfPTable> {

    private final PdfPTable pdfTable;

    /**
     * Constructor for PdfPTableEditor.
     *
     * @param pdfTable The {@link PdfPTable} to be edited.
     * @param pdfCommons The common configurations for the PDF.
     */
    private PdfPTableEditor(PdfPTable pdfTable, PdfCommons pdfCommons) {
        super(pdfCommons);
        this.pdfTable = pdfTable;
    }

    /**
     * Constructor for PdfPTableEditor with relative width.
     *
     * @param relativeWidth The relative width of the columns.
     * @param pdfCommons The common configurations for the PDF.
     */
    public PdfPTableEditor(float[] relativeWidth, PdfCommons pdfCommons) {
        this(new PdfPTable(relativeWidth), pdfCommons);
    }

    /**
     * Constructor for PdfPTableEditor with a specific number of columns.
     *
     * @param numColumns The number of columns in the table.
     * @param pdfCommons The common configurations for the PDF.
     */
    public PdfPTableEditor(int numColumns, PdfCommons pdfCommons) {
        this(new PdfPTable(numColumns), pdfCommons);
    }

    /**
     * This method is used to insert an element into the table.
     *
     * @param insertable The element to be inserted.
     * @return boolean This returns true if the element is inserted successfully.
     */
    @Override
    protected boolean insertElement(Insertable<?> insertable) {
        // cannot be final
        var cell = new PdfPCell();

        if (insertable instanceof PdfImage) {
            var image = (PdfImage) insertable;
            var imageElement = (Image) image.getElement();
            var imgHeight = Optional.ofNullable(image.getSize())
                    .map(Size::getHeight)
                    .orElse(imageElement.getPlainHeight());
            cell.setFixedHeight(imgHeight);
            cell.setCellEvent(new ImageAlignmentInPdfPCellEvent(image));
        } else if (insertable instanceof SimpleParagraph) {
            var paragraph = (SimpleParagraph) insertable;
            cell.setPhrase(paragraph.getElement());
            cell.setHorizontalAlignment(paragraph.getHorizontalAlignment().getValue());
            cell.setVerticalAlignment(paragraph.getVerticalAlignment().getValue());
        } else {
            throw new IllegalStateException("Unexpected value: " + insertable.getName());
        }
        this.pdfTable.addCell(cell);
        return true;
    }

    /**
     * This method is used to get the current instance of {@link PdfPTableEditor}.
     *
     * @return PdfPTableEditor This returns the current instance of {@link PdfPTableEditor}.
     */
    @Override
    protected PdfPTableEditor getT() {
        return this;
    }

    /**
     * This method is used to set the width of the table.
     *
     * @param width The width to be set.
     * @return PdfPTableEditor This returns a {@link PdfPTableEditor} with the width set.
     */
    public PdfPTableEditor setWidth(PdfWidth width) {
        switch (width.getType()) {
            case RELATIVE:
                this.pdfTable.setWidthPercentage(width.getValue());
                break;
            case ABSOLUTE:
                this.pdfTable.setTotalWidth(width.getValue());
                break;
            default:
                throw new UnsupportedOperationException("Type not supported");
        }
        return this;
    }

    /**
     * This method is used to set the width of the columns.
     *
     * @param columnsWidth The width of the columns to be set.
     * @return PdfPTableEditor This returns a {@link PdfPTableEditor} with the columns width set.
     */
    public PdfPTableEditor setColumnsWidth(float[] columnsWidth) {
        this.pdfTable.setTotalWidth(columnsWidth);
        return this;
    }

    /**
     * This method is used to get the {@link PdfPTableEditor}.
     *
     * @return PdfPTable This returns the {@link PdfPTableEditor} being edited.
     */
    @Override
    public PdfPTable getElement() {
        return this.pdfTable;
    }

    /**
     * This method is used to get the name of the {@link PdfPTableEditor}.
     *
     * @return String This returns the name of the {@link PdfPTableEditor}.
     */
    @Override
    public String getName() {
        return this.getClass().getName();
    }
}
