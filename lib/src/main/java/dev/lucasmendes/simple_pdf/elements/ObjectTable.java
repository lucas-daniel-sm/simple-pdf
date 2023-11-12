package dev.lucasmendes.simple_pdf.elements;

import dev.lucasmendes.simple_pdf.annotations.PdfAnnotationsProcessor;
import dev.lucasmendes.simple_pdf.configurations.PdfCommons;
import dev.lucasmendes.simple_pdf.configurations.PdfWidth;
import dev.lucasmendes.simple_pdf.enums.VerticalAlignment;
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
public class ObjectTable<T> {


    @Nonnull
    private final List<T> items;
    @Nonnull
    private final Map<String, Function<T, String>> customExtractor;
    @Nonnull
    private final PdfCommons pdfCommons;
    @Nonnull
    private final Class<T> objectClass;

    public ObjectTable(@Nonnull PdfCommons pdfCommons, @Nonnull List<T> items, @Nonnull Class<T> objectClass) {
        this(pdfCommons, items, Map.of(), objectClass);
    }

    public ObjectTable(
            @Nonnull PdfCommons pdfCommons,
            @Nonnull List<T> items,
            @Nonnull Map<String, Function<T, String>> customExtractor,
            @Nonnull Class<T> objectClass
    ) {
        this.customExtractor = customExtractor;
        this.items = items;
        this.pdfCommons = pdfCommons;
        this.objectClass = objectClass;
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
                .map(nome -> new SimpleParagraph(nome).withVerticalAlignment(VerticalAlignment.MIDDLE))
                .forEach(tableEditor::add);
    }

    private void addItemsToTable(List<Field> objectFields, PdfPTableEditor tableEditor) {
        for (T item : this.items) {
            for (Field field : objectFields) {
                var value = this.getFieldValue(item, field);
                tableEditor.add(new SimpleParagraph(value).withVerticalAlignment(VerticalAlignment.MIDDLE));
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
