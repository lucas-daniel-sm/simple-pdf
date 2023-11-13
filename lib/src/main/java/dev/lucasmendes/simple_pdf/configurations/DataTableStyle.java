package dev.lucasmendes.simple_pdf.configurations;

import dev.lucasmendes.simple_pdf.elements.SimpleFont;
import dev.lucasmendes.simple_pdf.elements.SimpleParagraph;
import dev.lucasmendes.simple_pdf.elements.SimpleTableCell;
import dev.lucasmendes.simple_pdf.enums.FontStyle;
import dev.lucasmendes.simple_pdf.enums.VerticalAlignment;
import dev.lucasmendes.simple_pdf.models.Edges;
import lombok.Data;
import lombok.With;

import java.awt.*;
import java.util.function.Function;

@Data
public class DataTableStyle {
    private final Function<String, SimpleTableCell<?>> headerRowCellStyle;
    private final Function<RowInfo, SimpleTableCell<?>> bodyRowCellStyle;

    public static DataTableStyle defaults(SimpleFont defaultFont) {
        return new DataTableStyle(
                title -> defaultHeaderRowCell(title, defaultFont),
                rowInfo -> defaultBodyRowCell(rowInfo, defaultFont)
        );
    }

    public static DataTableStyle defaultHeaderAndCustomBody(
            SimpleFont defaultFont,
            Function<RowInfo, SimpleTableCell<?>> bodyRowCellStyle
    ) {
        return new DataTableStyle(
                title -> defaultHeaderRowCell(title, defaultFont),
                bodyRowCellStyle
        );
    }

    public static DataTableStyle defaultBodyAndCustomHeader(
            SimpleFont defaultFont,
            Function<String, SimpleTableCell<?>> headerRowCellStyle
    ) {
        return new DataTableStyle(
                headerRowCellStyle,
                rowInfo -> defaultBodyRowCell(rowInfo, defaultFont)
        );
    }

    @Data
    @With
    public static class RowInfo {
        private final String rowData;
        private final int rowIndex;
        private final boolean isEven;
        private final boolean isOdd;
    }

    private static SimpleTableCell<?> defaultHeaderRowCell(String title, SimpleFont font) {
        return new SimpleTableCell<>(
                new SimpleParagraph(title, font.withStyle(FontStyle.BOLD).withIncreaseSize(2f)),
                new SimpleCellStyle(Color.LIGHT_GRAY, Edges.all(5f))
        );
    }

    private static SimpleTableCell<?> defaultBodyRowCell(RowInfo rowInfo, SimpleFont font) {
        var evenStyle = new SimpleCellStyle(Color.WHITE, Edges.all(2));
        var addStyle = evenStyle.withBackgroundColor(new Color(230, 230, 230));
        return new SimpleTableCell<>(
                new SimpleParagraph(rowInfo.rowData, font).withVerticalAlignment(VerticalAlignment.MIDDLE),
                rowInfo.isEven ? evenStyle : addStyle
        );
    }
}
