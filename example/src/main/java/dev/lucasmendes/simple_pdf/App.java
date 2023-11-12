package dev.lucasmendes.simple_pdf;


import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import dev.lucasmendes.simple_pdf.configurations.PageConfiguration;
import dev.lucasmendes.simple_pdf.configurations.PdfWidth;
import dev.lucasmendes.simple_pdf.core.PdfEditor;
import dev.lucasmendes.simple_pdf.core.PdfWrapper;
import dev.lucasmendes.simple_pdf.elements.ObjectTable;
import dev.lucasmendes.simple_pdf.elements.PdfImage;
import dev.lucasmendes.simple_pdf.elements.SimpleFont;
import dev.lucasmendes.simple_pdf.elements.SimpleParagraph;
import dev.lucasmendes.simple_pdf.enums.FontStyle;
import dev.lucasmendes.simple_pdf.enums.HorizontalAlignment;
import dev.lucasmendes.simple_pdf.enums.VerticalAlignment;
import dev.lucasmendes.simple_pdf.models.Edges;
import lombok.SneakyThrows;

import java.awt.*;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class App {

    @SneakyThrows
    public static void main(String[] args) {
        var servidorService = new EmployeeService(50);
        // create directory to store pdf that is on gitignore, so it won't be commited
        var pdfDir = Paths.get("pdf-out");
        // ensure directory exists
        if (!pdfDir.toFile().exists()) {
            var result = pdfDir.toFile().mkdirs();
            if (!result) {
                throw new RuntimeException("Could not create directory " + pdfDir);
            }
        }
        var pdfFile = pdfDir.resolve("HelloWorld.pdf").toFile();

        try (var wrapper = PdfWrapper.fromFile(pdfFile, new PageConfiguration(PageSize.A4))) {
            var fontRoboto = createFontFromResources("fonts/Roboto-Regular.ttf");
            var fontRobotoItalic = createFontFromResources("fonts/Roboto-Italic.ttf");
            var fontRobotoBold = createFontFromResources("fonts/Roboto-Bold.ttf");
            var fontHelvetica = new SimpleFont(FontFactory.getFont(FontFactory.HELVETICA));

            wrapper
                    .registerFont(fontRoboto.withSize(8f), true)
                    .registerFont(fontRobotoItalic)
                    .registerFont(fontRobotoBold)
                    .registerFont(fontHelvetica)
                    .setHeaderEvent(App::createHeader)
                    .registerEvents()
                    .open()
                    .toEditor()
                    .add(new ObjectTable<>(
                            wrapper.getPdfCommons(),
                            servidorService.getEmployees(),
                            Employee.class
                    ).generateTable())
                    .newPage()
                    .add(new SimpleParagraph("Roboto ITALIC RED", fontRoboto
                            .withSize(20f)
                            .withStyle(FontStyle.ITALIC)
                            .withColor(Color.RED)
                    ))
                    .add(new SimpleParagraph("Helvetica BOLD CYAN", fontHelvetica
                            .withSize(20f)
                            .withStyle(FontStyle.BOLD)
                            .withColor(Color.CYAN)
                    ))
                    .add(new SimpleParagraph("Roboto BOLD_ITALIC ORANGE", fontRoboto
                            .withSize(20f)
                            .withStyle(FontStyle.BOLD_ITALIC)
                            .withColor(Color.ORANGE)
                    ))
            ;
        }
    }

    @SneakyThrows
    private static void createHeader(PdfEditor pdfEditor) {
        final var pageConf = pdfEditor.getPdfCommons().getPageConfiguration();
        final var logoSize = 100f;
        //noinspection UnnecessaryLocalVariable
        final var endSize = logoSize;
        final var centerSize = pageConf.getUtilizableWidth() - (logoSize + endSize);
        final var logoUrl = ClassLoader.getSystemResource("images/simple_pdf_logo.jpg");
        final var todayFormatted = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        pdfEditor.addTable(3, tableEditor -> tableEditor
                .setWidth(PdfWidth.max())
                .setColumnsWidth(new float[]{logoSize, centerSize, endSize})
                .add(
                        new PdfImage(logoUrl)
                                .withHorizontalAlignment(HorizontalAlignment.CENTER)
                                .withVerticalAlignment(VerticalAlignment.MIDDLE)
                                .withSize(40, 40)
                                .withEdges(Edges.all(4))
                )
                .add(new SimpleParagraph("Report of Employees")
                        .withHorizontalAlignment(HorizontalAlignment.CENTER)
                        .withVerticalAlignment(VerticalAlignment.MIDDLE))
                .add(new SimpleParagraph("  Date: " + todayFormatted)
                        .withVerticalAlignment(VerticalAlignment.MIDDLE))
        ).add(new SimpleParagraph(" "));
    }

    @SneakyThrows
    public static SimpleFont createFontFromResources(String path) {
        var fontFile = Paths.get(ClassLoader.getSystemResource(path).toURI());
        return SimpleFont.fromFile(fontFile);
    }
}
