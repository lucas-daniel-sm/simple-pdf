package dev.lucasmendes.simple_pdf.models;

import lombok.Data;
import lombok.With;

/**
 * This is a record class that represents the edges of a shape.
 * It has a top, right, bottom, and left edge.
 * It provides methods to create edges with specific values and to add and subtract edges.
 */
@With
@Data
public final class Edges {
    private final float top;
    private final float right;
    private final float bottom;
    private final float left;

    /**
     *
     */
    public Edges(
            float top,
            float right,
            float bottom,
            float left
    ) {
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;
    }

    /**
     * This method is used to create edges with zero values.
     *
     * @return Edges This returns edges with zero values.
     */
    public static Edges zero() {
        return new Edges(0f, 0f, 0f, 0f);
    }

    /**
     * This method is used to create edges with the same value.
     *
     * @param value The value for all edges.
     * @return Edges This returns edges with the same value.
     */
    public static Edges all(float value) {
        return new Edges(value, value, value, value);
    }

    /**
     * This method is used to create edges with the same vertical value.
     *
     * @param vertical The value for the top and bottom edges.
     * @return Edges This returns edges with the same vertical value.
     */
    public static Edges symmetricVertical(float vertical) {
        return new Edges(vertical, 0, vertical, 0);
    }

    /**
     * This method is used to create edges with the same horizontal value.
     *
     * @param horizontal The value for the right and left edges.
     * @return Edges This returns edges with the same horizontal value.
     */
    public static Edges symmetricHorizontal(float horizontal) {
        return new Edges(0, horizontal, 0, horizontal);
    }

    /**
     * This method is used to create edges with only a top value.
     *
     * @param top The value for the top edge.
     * @return Edges This returns edges with only a top value.
     */
    public static Edges onlyTop(float top) {
        return new Edges(top, 0, 0, 0);
    }

    /**
     * This method is used to create edges with only a right value.
     *
     * @param right The value for the right edge.
     * @return Edges This returns edges with only a right value.
     */
    public static Edges onlyRight(float right) {
        return new Edges(0, right, 0, 0);
    }

    /**
     * This method is used to create edges with only a bottom value.
     *
     * @param bottom The value for the bottom edge.
     * @return Edges This returns edges with only a bottom value.
     */
    public static Edges onlyBottom(float bottom) {
        return new Edges(0, 0, bottom, 0);
    }

    /**
     * This method is used to create edges with only a left value.
     *
     * @param left The value for the left edge.
     * @return Edges This returns edges with only a left value.
     */
    public static Edges onlyLeft(float left) {
        return new Edges(0, 0, 0, left);
    }

    /**
     * This method is used to add another edges to these edges.
     *
     * @param other The other edges to be added.
     * @return Edges This returns a new edges which is the sum of these edges and the other edges.
     */
    public Edges add(Edges other) {
        return new Edges(
                this.top + other.top,
                this.right + other.right,
                this.bottom + other.bottom,
                this.left + other.left
        );
    }

    /**
     * This method is used to subtract another edges from these edges.
     *
     * @param other The other edges to be subtracted.
     * @return Edges This returns a new edges which is the difference between these edges and the other edges.
     */
    public Edges subtract(Edges other) {
        return new Edges(
                this.top - other.top,
                this.right - other.right,
                this.bottom - other.bottom,
                this.left - other.left
        );
    }
}
