package dev.lucasmendes.simple_pdf.elements;

import com.lowagie.text.pdf.PdfPTable;
import dev.lucasmendes.simple_pdf.annotations.PdfAnnotationsProcessor;
import dev.lucasmendes.simple_pdf.configurations.DataTableStyle;
import dev.lucasmendes.simple_pdf.configurations.PdfCommons;
import dev.lucasmendes.simple_pdf.configurations.PdfWidth;
import dev.lucasmendes.simple_pdf.enums.HorizontalAlignment;
import dev.lucasmendes.simple_pdf.enums.VerticalAlignment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This class is used to create a table with the data of a list of objects.
 *
 * @param <T> The type of the objects in the list.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class DataTable<T> extends AbstractElement<PdfPTable> {
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

    public DataTable(
            @Nonnull PdfCommons pdfCommons,
            @Nonnull List<T> items,
            @Nonnull Map<String, Function<T, String>> customExtractor,
            @Nonnull Class<T> objectClass,
            @Nonnull DataTableStyle style,
            @Nonnull HorizontalAlignment horizontalAlignment,
            @Nonnull VerticalAlignment verticalAlignment
    ) {
        super(horizontalAlignment, verticalAlignment);
        this.pdfCommons = pdfCommons;
        this.items = items;
        this.customExtractor = customExtractor;
        this.objectClass = objectClass;
        this.style = style;
    }

    /**
     * This method is used to generate the table.
     *
     * @return A table with the data of the list of objects.
     */
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

    /**
     * This method is used to create the header of the table.
     *
     * @param objectFields The fields of the object.
     * @param tableEditor  A table editor to add the header.
     */
    private void createHeader(List<Field> objectFields, PdfPTableEditor tableEditor) {
        objectFields.stream()
                .map(PdfAnnotationsProcessor::getFieldName)
                .map(nome -> this.style.getHeaderRowCellStyle().apply(nome))
                .forEach(tableEditor::add);
    }

    /**
     * This method is used to add the items of the list to the table.
     *
     * @param objectFields The fields of the object.
     * @param tableEditor  A table editor to add the items.
     */
    private void addItemsToTable(List<Field> objectFields, PdfPTableEditor tableEditor) {
        for (int i = 0; i < this.items.size(); i++) {
            T item = this.items.get(i);
            final var defaultRowInfo = new DataTableStyle.CellInfo("", i);
            for (Field field : objectFields) {
                var value = this.getFieldValue(item, field);
                var rowInfo = defaultRowInfo.withRowData(value);
                tableEditor.add(this.style.getBodyRowCellStyle().apply(rowInfo));
            }
        }
    }

    /**
     * This method is used to get the value of a field of an object.
     *
     * @param item  The object.
     * @param field The field.
     * @return The value of the field.
     */
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

    @Override
    public AbstractElement<PdfPTable> withHorizontalAlignment(@Nonnull HorizontalAlignment horizontalAlignment) {
        return new DataTable<>(
                this.pdfCommons,
                this.items,
                this.customExtractor,
                this.objectClass,
                this.style,
                horizontalAlignment,
                this.getVerticalAlignment()
        );
    }

    @Override
    public AbstractElement<PdfPTable> withVerticalAlignment(@Nonnull VerticalAlignment verticalAlignment) {
        return new DataTable<>(
                this.pdfCommons,
                this.items,
                this.customExtractor,
                this.objectClass,
                this.style,
                this.getHorizontalAlignment(),
                verticalAlignment
        );
    }

    @Override
    public PdfPTable getElement() {
        return generateTable().getElement();
    }


    protected static abstract class DataTableBuilderBase<T, C extends DataTable<T>, B extends DataTableBuilderBase<T, C, B>> extends AbstractElement.AbstractElementBuilder<PdfPTable, C, B> {
        private PdfCommons pdfCommons;
        private List<T> items;
        private Map<String, Function<T, String>> customExtractor;
        private Class<T> objectClass;
        private DataTableStyle style;

        /**
         * @return {@code this}.
         */
        public B pdfCommons(@Nonnull final PdfCommons pdfCommons) {
            if (pdfCommons == null) {
                throw new NullPointerException("pdfCommons is marked non-null but is null");
            }
            this.pdfCommons = pdfCommons;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B items(@Nonnull final List<T> items) {
            if (items == null) {
                throw new NullPointerException("items is marked non-null but is null");
            }
            this.items = items;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B customExtractor(@Nonnull final Map<String, Function<T, String>> customExtractor) {
            if (customExtractor == null) {
                throw new NullPointerException("customExtractor is marked non-null but is null");
            }
            this.customExtractor = customExtractor;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B objectClass(@Nonnull final Class<T> objectClass) {
            if (objectClass == null) {
                throw new NullPointerException("objectClass is marked non-null but is null");
            }
            this.objectClass = objectClass;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B style(@Nonnull final DataTableStyle style) {
            if (style == null) {
                throw new NullPointerException("style is marked non-null but is null");
            }
            this.style = style;
            return self();
        }

        @Override
        protected abstract B self();

        @Override
        public abstract C build();

        @Override
        public String toString() {
            return "DataTable.DataTableBuilder(super=" + super.toString() + ", pdfCommons=" + this.pdfCommons + ", items=" + this.items + ", customExtractor=" + this.customExtractor + ", objectClass=" + this.objectClass + ", style=" + this.style + ")";
        }
    }


    public static final class DataTableBuilder<T> extends DataTableBuilderBase<T, DataTable<T>, DataTableBuilder<T>> {
        private DataTableBuilder() {
        }

        @Override
        protected DataTableBuilder<T> self() {
            return this;
        }

        @Override
        public DataTable<T> build() {
            return new DataTable<T>(this);
        }
    }

    protected DataTable(final DataTableBuilderBase<T, ?, ?> b) {
        super(b);
        this.pdfCommons = b.pdfCommons;
        if (pdfCommons == null) {
            throw new NullPointerException("pdfCommons is marked non-null but is null");
        }
        this.items = b.items;
        if (items == null) {
            throw new NullPointerException("items is marked non-null but is null");
        }
        this.customExtractor = b.customExtractor;
        if (customExtractor == null) {
            throw new NullPointerException("customExtractor is marked non-null but is null");
        }
        this.objectClass = b.objectClass;
        if (objectClass == null) {
            throw new NullPointerException("objectClass is marked non-null but is null");
        }
        this.style = b.style;
        if (style == null) {
            throw new NullPointerException("style is marked non-null but is null");
        }
    }

    public static <T> DataTableBuilder<T> builder() {
        return new DataTableBuilder<T>()
                .customExtractor(Map.of())
                .verticalAlignment(VerticalAlignment.TOP)
                .horizontalAlignment(HorizontalAlignment.LEFT);
    }
}
