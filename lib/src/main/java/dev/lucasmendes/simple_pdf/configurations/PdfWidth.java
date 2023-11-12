package dev.lucasmendes.simple_pdf.configurations;

import lombok.Data;

/**
 * This class represents the width of a PDF. The width can be either relative or absolute.
 */
@Data
@SuppressWarnings("ClassCanBeRecord")
public class PdfWidth {
    /**
     * Enum representing the type of PDF width - RELATIVE or ABSOLUTE.
     */
    public enum PdfWidthType {RELATIVE, ABSOLUTE}

    private final PdfWidthType type;
    private final float value;

    /**
     * Creates an absolute width for the PDF.
     *
     * @param value the absolute width value
     * @return a PdfWidth object with absolute width
     */
    public static PdfWidth absolute(float value) {
        return new PdfWidth(PdfWidthType.ABSOLUTE, value);
    }

    /**
     * Creates a relative width for the PDF.
     *
     * @param percentage the relative width value in percentage
     * @return a PdfWidth object with relative width
     */
    public static PdfWidth relative(float percentage) {
        return new PdfWidth(PdfWidthType.RELATIVE, percentage);
    }

    /**
     * Creates a PdfWidth object with maximum width (100%).
     *
     * @return a PdfWidth object with maximum width
     */
    public static PdfWidth max() {
        return PdfWidth.relative(100.0f);
    }
}
