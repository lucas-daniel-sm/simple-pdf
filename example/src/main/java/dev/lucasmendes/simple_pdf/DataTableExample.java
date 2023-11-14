package dev.lucasmendes.simple_pdf;


import com.lowagie.text.PageSize;
import dev.lucasmendes.simple_pdf.configurations.PageConfiguration;
import dev.lucasmendes.simple_pdf.configurations.PdfWidth;
import dev.lucasmendes.simple_pdf.configurations.SimpleCellStyle;
import dev.lucasmendes.simple_pdf.core.PdfEditor;
import dev.lucasmendes.simple_pdf.core.PdfWrapper;
import dev.lucasmendes.simple_pdf.elements.*;
import dev.lucasmendes.simple_pdf.enums.HorizontalAlignment;
import dev.lucasmendes.simple_pdf.enums.VerticalAlignment;
import dev.lucasmendes.simple_pdf.models.Edges;
import lombok.SneakyThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataTableExample {

    @SneakyThrows
    public static void generate(Path pdfDir) {
        var employeeService = new EmployeeService(70);
        var pdfFile = pdfDir.resolve("DataTableExample.pdf").toFile();

        var fontRoboto = createFontFromResources("fonts/Roboto-Regular.ttf");
        var fontRobotoItalic = createFontFromResources("fonts/Roboto-Italic.ttf");
        var fontRobotoBold = createFontFromResources("fonts/Roboto-Bold.ttf");

        try (var wrapper = PdfWrapper.fromFile(pdfFile, new PageConfiguration(PageSize.A4))) {
            wrapper
                    .registerFont(fontRoboto.withSize(8f), true)
                    .registerFont(fontRobotoItalic)
                    .registerFont(fontRobotoBold)
                    .setHeaderEvent(DataTableExample::createHeader)
                    .registerEvents()
                    .open()
                    .toEditor()
                    .<Employee>addDataTable(
                            dataTableBuilder -> dataTableBuilder
                                    .items(employeeService.getEmployees())
                                    .objectClass(Employee.class)
                    );
        }
    }

    @SneakyThrows
    private static void createHeader(PdfEditor pdfEditor) {
        final var pageConf = pdfEditor.getPdfCommons().getPageConfiguration();
        final var logoSize = 100f;
        final var robotoBold = createFontFromResources("fonts/Roboto-Bold.ttf");
        //noinspection UnnecessaryLocalVariable
        final var endSize = logoSize;
        final var centerSize = pageConf.getUtilizableWidth() - (logoSize + endSize);
        final var logoUrl = ClassLoader.getSystemResource("images/simple_pdf_logo.png");
        final var todayFormatted = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        pdfEditor.addTable(3, tableEditor -> tableEditor
                .setWidth(PdfWidth.max())
                .setColumnsWidth(new float[]{logoSize, centerSize, endSize})
                .add(new PdfImage(logoUrl)
                        .withHorizontalAlignment(HorizontalAlignment.CENTER)
                        .withVerticalAlignment(VerticalAlignment.MIDDLE)
                        .withSize(40, 40)
                        .withEdges(Edges.all(4)))
                .add(new SimpleParagraph("Report of Employees")
                        .withHorizontalAlignment(HorizontalAlignment.CENTER)
                        .withVerticalAlignment(VerticalAlignment.MIDDLE)
                        .withFont(robotoBold.withSize(14f)))
                .add(new SimpleTableCell<>(
                        new SimpleParagraph("Date: " + todayFormatted).withVerticalAlignment(VerticalAlignment.MIDDLE),
                        new SimpleCellStyle(null, Edges.onlyLeft(2))
                ))
        ).add(new SimpleParagraph(" "));
    }

    @SneakyThrows
    public static SimpleFont createFontFromResources(String path) {
        var fontFile = Paths.get(ClassLoader.getSystemResource(path).toURI());
        return SimpleFont.fromFile(fontFile);
    }
}
