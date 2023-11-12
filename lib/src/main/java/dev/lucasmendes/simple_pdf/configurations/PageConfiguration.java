package dev.lucasmendes.simple_pdf.configurations;

import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * This class represents the configuration of a page in a PDF document.
 */
@Data
@RequiredArgsConstructor
public class PageConfiguration {
    private final Rectangle pageSize;
    private final float marginLeft;
    private final float marginRight;
    private final float marginTop;
    private final float marginBottom;

    /**
     * Default constructor that creates a PageConfiguration with A4 page size and default margins.
     */
    public PageConfiguration() {
        this(PageSize.A4);
    }

    /**
     * Constructor that creates a PageConfiguration with specified page size and default margins.
     *
     * @param pageSize the size of the page
     */
    public PageConfiguration(Rectangle pageSize) {
        this(pageSize, 36.0F, 36.0F, 36.0F, 36.0F);
    }

    /**
     * Calculates the total horizontal margin.
     *
     * @return the sum of the left and right margins
     */
    public float getHorizontalTotalMargin() {
        return this.marginLeft + this.marginRight;
    }

    /**
     * Calculates the total vertical margin.
     *
     * @return the sum of the top and bottom margins
     */
    public float getVerticalTotalMargin() {
        return this.marginTop + this.marginBottom;
    }

    /**
     * Gets the total width of the page.
     *
     * @return the width of the page
     */
    public float getTotalWidth() {
        return this.pageSize.getWidth();
    }

    /**
     * Calculates the utilizable width of the page.
     *
     * @return the total width of the page minus the horizontal margins
     */
    public float getUtilizableWidth() {
        return getTotalWidth() - this.getHorizontalTotalMargin();
    }
}
