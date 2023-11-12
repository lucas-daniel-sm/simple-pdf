package dev.lucasmendes.simple_pdf.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Tests for Edges")
class EdgesTest {
    @Test
    @DisplayName("Test for add method")
    void testAdd() {
        // Create two Edges objects
        Edges edges1 = new Edges(10, 20, 30, 40);
        Edges edges2 = new Edges(50, 60, 70, 80);

        // Add the two edges
        Edges result = edges1.add(edges2);

        // Assert that the result is correct
        assertEquals(60, result.getTop());
        assertEquals(80, result.getRight());
        assertEquals(100, result.getBottom());
        assertEquals(120, result.getLeft());
    }

    @Test
    @DisplayName("Test for subtract method")
    void testSubtract() {
        // Create two Edges objects
        Edges edges1 = new Edges(100, 200, 300, 400);
        Edges edges2 = new Edges(50, 60, 70, 80);

        // Subtract the second edges from the first edges
        Edges result = edges1.subtract(edges2);

        // Assert that the result is correct
        assertEquals(50, result.getTop());
        assertEquals(140, result.getRight());
        assertEquals(230, result.getBottom());
        assertEquals(320, result.getLeft());
    }
}
