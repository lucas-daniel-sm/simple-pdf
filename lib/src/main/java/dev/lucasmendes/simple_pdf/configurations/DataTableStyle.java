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

/**
 * This class is used to configure the style of the table.
 * It is possible to configure the style of the header and the body of the table.
 */
@Data
public class DataTableStyle {
    private final Function<String, SimpleTableCell<?>> headerRowCellStyle;
    private final Function<CellInfo, SimpleTableCell<?>> bodyRowCellStyle;

    /**
     * This method is used to create the default style for the table.
     *
     * @param defaultFont The default font to be used in the table.
     * @return The default style for the table.
     */
    public static DataTableStyle defaults(SimpleFont defaultFont) {
        return new DataTableStyle(
                title -> defaultHeaderRowCell(title, defaultFont),
                cellInfo -> defaultBodyRowCell(cellInfo, defaultFont)
        );
    }

    /**
     * This method is used to create a style for the table with a custom style for the body
     *
     * @param defaultFont      The default font to be used in the table.
     * @param bodyRowCellStyle The style to be used in the body of the table.
     * @return The style for the table with a custom style for the body.
     */
    public static DataTableStyle defaultHeaderAndCustomBody(
            SimpleFont defaultFont,
            Function<CellInfo, SimpleTableCell<?>> bodyRowCellStyle
    ) {
        return new DataTableStyle(
                title -> defaultHeaderRowCell(title, defaultFont),
                bodyRowCellStyle
        );
    }

    /**
     * This method is used to create a style for the table with a custom style for the header
     *
     * @param defaultFont        The default font to be used in the table.
     * @param headerRowCellStyle The style to be used in the header of the table.
     * @return The style for the table with a custom style for the header.
     */
    public static DataTableStyle defaultBodyAndCustomHeader(
            SimpleFont defaultFont,
            Function<String, SimpleTableCell<?>> headerRowCellStyle
    ) {
        return new DataTableStyle(
                headerRowCellStyle,
                cellInfo -> defaultBodyRowCell(cellInfo, defaultFont)
        );
    }

    /**
     * This method is used to create a style for the table with a custom style for the header and the body
     *
     * @param title The title of the column.
     * @param font  The font to be used in the title.
     * @return The default style for the header of the table.
     */
    private static SimpleTableCell<?> defaultHeaderRowCell(String title, SimpleFont font) {
        return new SimpleTableCell<>(
                new SimpleParagraph(title, font.withStyle(FontStyle.BOLD).withIncreaseSize(2f)),
                new SimpleCellStyle(Color.LIGHT_GRAY, Edges.all(5f))
        );
    }

    /**
     * This method is used to create a style for the table with a custom style for the header and the body
     *
     * @param cellInfo Contains information about the row.
     * @param font     The font to be used in the row.
     * @return The default style for the body of the table.
     */
    private static SimpleTableCell<?> defaultBodyRowCell(CellInfo cellInfo, SimpleFont font) {
        var evenStyle = new SimpleCellStyle(Color.WHITE, Edges.all(2));
        var addStyle = evenStyle.withBackgroundColor(new Color(230, 230, 230));
        return new SimpleTableCell<>(
                new SimpleParagraph(cellInfo.rowData, font).withVerticalAlignment(VerticalAlignment.MIDDLE),
                cellInfo.isEven() ? evenStyle : addStyle
        );
    }

    /**
     * A class that contains information about a row.
     */
    @Data
    @With
    public static class CellInfo {
        private final String rowData;
        private final int rowIndex;

        private boolean isEven() {
            return rowIndex % 2 == 0;
        }

        private boolean isOdd() {
            return !isEven();
        }
    }
}
