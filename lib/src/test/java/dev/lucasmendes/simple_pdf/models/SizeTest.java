package dev.lucasmendes.simple_pdf.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Tests for Size")
class SizeTest {
    @Test
    @DisplayName("Test for add method")
    void testAdd() {
        // Create two Size objects
        Size size1 = new Size(10, 20);
        Size size2 = new Size(30, 40);

        // Add the two sizes
        Size result = size1.add(size2);

        // Assert that the result is correct
        assertEquals(40, result.getWidth());
        assertEquals(60, result.getHeight());
    }

    @Test
    @DisplayName("Test for subtract method")
    void testSubtract() {
        // Create two Size objects
        Size size1 = new Size(50, 60);
        Size size2 = new Size(30, 40);

        // Subtract the second size from the first size
        Size result = size1.subtract(size2);

        // Assert that the result is correct
        assertEquals(20, result.getWidth());
        assertEquals(20, result.getHeight());
    }
}
