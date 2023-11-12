package dev.lucasmendes.simple_pdf.annotations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class PdfAnnotationsProcessorTest {

    @Test
    @DisplayName("Test Split Camel Case Private Method")
    public void testSplitCamelCase() throws Exception {
        // Get the splitCamelCase method
        Method method = PdfAnnotationsProcessor.class.getDeclaredMethod("splitCamelCase", String.class);

        // Make it accessible
        method.setAccessible(true);

        // Invoke the method and check the result
        String result = (String) method.invoke(null, "camelCase");
        assertEquals("Camel case", result);
    }


    @Test
    @DisplayName("Test Get Field Name Without Annotation")
    public void testGetFieldNameWithoutAnnotation() throws NoSuchFieldException {
        Field field = SampleClass.class.getDeclaredField("fieldWithoutAnnotation");
        String fieldName = PdfAnnotationsProcessor.getFieldName(field);
        assertEquals("Field without annotation", fieldName);
    }

    @Test
    @DisplayName("Test Get Field Name With Annotation")
    public void testGetFieldNameWithAnnotation() throws NoSuchFieldException {
        Field field = SampleClass.class.getDeclaredField("fieldWithAnnotation");
        String fieldName = PdfAnnotationsProcessor.getFieldName(field);
        assertEquals("Annotated Field", fieldName);
    }

    @Test
    @DisplayName("Test Ignore Field Without Annotation")
    public void testIgnoreFieldWithoutAnnotation() throws NoSuchFieldException {
        Field field = SampleClass.class.getDeclaredField("fieldWithoutAnnotation");
        boolean ignore = PdfAnnotationsProcessor.ignore(field);
        assertFalse(ignore);
    }

    @Test
    @DisplayName("Test Ignore Field With Annotation")
    public void testIgnoreFieldWithAnnotation() throws NoSuchFieldException {
        Field field = SampleClass.class.getDeclaredField("fieldWithAnnotation");
        boolean ignore = PdfAnnotationsProcessor.ignore(field);
        assertTrue(ignore);
    }

    private static class SampleClass {
        private String fieldWithoutAnnotation;

        @PdfName("Annotated Field")
        @PdfIgnore
        private String fieldWithAnnotation;
    }
}
