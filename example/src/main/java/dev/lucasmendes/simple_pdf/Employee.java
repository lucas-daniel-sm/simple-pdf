package dev.lucasmendes.simple_pdf;

import dev.lucasmendes.simple_pdf.annotations.PdfIgnore;
import dev.lucasmendes.simple_pdf.annotations.PdfName;

public record Employee(
        @PdfName("Identification") int id,
        @PdfName String firstName,
        @PdfName String lastName,
        @PdfName String position,
        @PdfName("Active") Boolean isActive,
        @PdfIgnore Double salary
) {
}
