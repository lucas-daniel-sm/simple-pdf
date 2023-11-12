package dev.lucasmendes.simple_pdf.core;

import dev.lucasmendes.simple_pdf.configurations.PageConfiguration;
import dev.lucasmendes.simple_pdf.configurations.PdfCommons;
import dev.lucasmendes.simple_pdf.elements.SimpleFont;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;
import lombok.Data;
import lombok.SneakyThrows;

import java.io.*;
import java.util.function.Consumer;

@Data
public class PdfWrapper implements Closeable {
    private final Document document;
    private final PdfWriter writer;
    private final Events events = new Events();
    private final PdfCommons pdfCommons;

    public static PdfWrapper fromFile(File pdfFile) throws FileNotFoundException {
        return PdfWrapper.fromFile(pdfFile, new PageConfiguration());
    }

    public static PdfWrapper fromFile(File pdfFile, PageConfiguration pageConfiguration) throws FileNotFoundException {
        return new PdfWrapper(new FileOutputStream(pdfFile), pageConfiguration);
    }

    public PdfWrapper(OutputStream pdfStream, PageConfiguration pageConfiguration) {
        this.pdfCommons = new PdfCommons(pageConfiguration);
        this.document = new Document(
                pageConfiguration.getPageSize(),
                pageConfiguration.getMarginLeft(),
                pageConfiguration.getMarginRight(),
                pageConfiguration.getMarginTop(),
                pageConfiguration.getMarginBottom()
        );
        this.writer = PdfWriter.getInstance(document, pdfStream);
    }

    public PdfWrapper open() {
        this.document.open();
        return this;
    }

    @Override
    public void close() {
        this.document.close();
        this.writer.close();
    }

    public PdfWrapper registerFont(SimpleFont simpleFont) {
        return this.registerFont(simpleFont, false);
    }

    @SneakyThrows
    public PdfWrapper registerFont(SimpleFont simpleFont, boolean setAsDefault) {
        this.pdfCommons.getRegisteredFonts().add(simpleFont);
        if (setAsDefault) {
            this.pdfCommons.setDefaultFont(simpleFont);
        }
        return this;
    }

    public PdfWrapper setHeaderEvent(Consumer<PdfEditor> headerBuilder) {
        this.events.setHeader(headerBuilder);
        return this;
    }

    public PdfWrapper registerEvents() {
        var pdfWrapper = this;
        this.writer.setPageEvent(new PdfPageEventHelper() {
            @Override
            public void onStartPage(PdfWriter writer, Document document) {
                var headerFunc = pdfWrapper.events.header;
                if (headerFunc != null) {
                    headerFunc.accept(new PdfEditor(pdfWrapper.pdfCommons, document, writer));
                }
            }
        });
        return this;
    }

    public PdfEditor toEditor() {
        return new PdfEditor(this.pdfCommons, this.document, this.writer);
    }

    @Data
    private static class Events {
        private Consumer<PdfEditor> header;
    }
}
