package dev.lucasmendes.simple_pdf.models;

import lombok.Data;
import lombok.With;

/**
 * This is a record class that represents a size.
 * It has a width and a height.
 * It provides methods to add and subtract sizes and edges.
 */
@With
@Data
public final class Size {
    private final float width;
    private final float height;

    /**
     * This method is used to add another size to this size.
     *
     * @param other The other size to be added.
     * @return Size This returns a new size which is the sum of this size and the other size.
     */
    public Size add(Size other) {
        return new Size(this.width + other.width, this.height + other.height);
    }

    /**
     * This method is used to subtract another size from this size.
     *
     * @param other The other size to be subtracted.
     * @return Size This returns a new size which is the difference between this size and the other size.
     */
    public Size subtract(Size other) {
        return new Size(this.width - other.width, this.height - other.height);
    }

    /**
     * This method is used to add edges to this size.
     *
     * @param edges The edges to be added.
     * @return Size This returns a new size which is this size increased by the edges.
     */
    public Size addEdge(Edges edges) {
        return new Size(
                this.width + edges.getLeft() + edges.getRight(),
                this.height + edges.getTop() + edges.getBottom()
        );
    }

    /**
     * This method is used to subtract edges from this size.
     *
     * @param edges The edges to be subtracted.
     * @return Size This returns a new size which is this size decreased by the edges.
     */
    public Size subtractEdge(Edges edges) {
        return new Size(
                this.width - edges.getLeft() - edges.getRight(),
                this.height - edges.getTop() - edges.getBottom()
        );
    }
}
