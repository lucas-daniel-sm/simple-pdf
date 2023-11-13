package dev.lucasmendes.simple_pdf.elements;

import dev.lucasmendes.simple_pdf.annotations.PdfAnnotationsProcessor;
import dev.lucasmendes.simple_pdf.configurations.DataTableStyle;
import dev.lucasmendes.simple_pdf.configurations.PdfCommons;
import dev.lucasmendes.simple_pdf.configurations.PdfWidth;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class DataTable<T> {
    @Nonnull
    private final PdfCommons pdfCommons;
    @Nonnull
    private final List<T> items;
    @Nonnull
    private final Map<String, Function<T, String>> customExtractor;
    @Nonnull
    private final Class<T> objectClass;
    @Nonnull
    private final DataTableStyle style;

    public DataTable(@Nonnull PdfCommons pdfCommons, @Nonnull List<T> items, @Nonnull Class<T> objectClass) {
        this(pdfCommons, items, Map.of(), objectClass);
    }

    public DataTable(
            @Nonnull PdfCommons pdfCommons,
            @Nonnull List<T> items,
            @Nonnull Map<String, Function<T, String>> customExtractor,
            @Nonnull Class<T> objectClass
    ) {
        this(pdfCommons, items, customExtractor, objectClass, DataTableStyle.defaults(pdfCommons.getDefaultFont()));
    }

    public PdfPTableEditor generateTable() {
        var objectFields = Arrays.stream(this.objectClass.getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .filter(field -> !PdfAnnotationsProcessor.ignore(field))
                .collect(Collectors.toList());
        var tableEditor = new PdfPTableEditor(objectFields.size(), this.pdfCommons);
        tableEditor.setWidth(PdfWidth.max());

        createHeader(objectFields, tableEditor);
        addItemsToTable(objectFields, tableEditor);

        return tableEditor;
    }

    private void createHeader(List<Field> objectFields, PdfPTableEditor tableEditor) {
        objectFields.stream()
                .map(PdfAnnotationsProcessor::getFieldName)
                .map(nome -> this.style.getHeaderRowCellStyle().apply(nome))
                .forEach(tableEditor::add);
    }

    private void addItemsToTable(List<Field> objectFields, PdfPTableEditor tableEditor) {
        for (int i = 0; i < this.items.size(); i++) {
            T item = this.items.get(i);
            var isEven = i % 2 == 0;
            final var defaultRowInfo = new DataTableStyle.RowInfo("", i, isEven, !isEven);

            for (Field field : objectFields) {
                var value = this.getFieldValue(item, field);
                var rowInfo = defaultRowInfo.withRowData(value);
                tableEditor.add(this.style.getBodyRowCellStyle().apply(rowInfo));
            }
        }
    }

    private String getFieldValue(T item, Field field) {
        var ext = customExtractor.get(field.getName());
        if (ext != null) {
            return ext.apply(item);
        }
        field.setAccessible(true);
        try {
            return Optional.ofNullable(field.get(item))
                    .map(Object::toString)
                    .orElse("NULL");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
