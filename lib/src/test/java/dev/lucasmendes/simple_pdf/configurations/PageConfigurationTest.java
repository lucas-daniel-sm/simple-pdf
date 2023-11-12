package dev.lucasmendes.simple_pdf.configurations;

import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PageConfigurationTest {

    @Test
    @DisplayName("Test Default Constructor and Getters")
    public void testDefaultConstructorAndGetters() {
        PageConfiguration config = new PageConfiguration();
        assertEquals(PageSize.A4, config.getPageSize());
        assertEquals(36.0F, config.getMarginLeft());
        assertEquals(36.0F, config.getMarginRight());
        assertEquals(36.0F, config.getMarginTop());
        assertEquals(36.0F, config.getMarginBottom());
    }

    @Test
    @DisplayName("Test Constructor with Page Size and Getters")
    public void testConstructorWithPageSizeAndGetters() {
        Rectangle customPageSize = new Rectangle(200, 200);
        PageConfiguration config = new PageConfiguration(customPageSize);
        assertEquals(customPageSize, config.getPageSize());
        assertEquals(36.0F, config.getMarginLeft());
        assertEquals(36.0F, config.getMarginRight());
        assertEquals(36.0F, config.getMarginTop());
        assertEquals(36.0F, config.getMarginBottom());
    }

    @Test
    @DisplayName("Test Horizontal and Vertical Total Margin Calculation")
    public void testMarginCalculations() {
        PageConfiguration config = new PageConfiguration();
        assertEquals(72.0F, config.getHorizontalTotalMargin());
        assertEquals(72.0F, config.getVerticalTotalMargin());
    }

    @Test
    @DisplayName("Test Total and Utilizable Width Calculation")
    public void testWidthCalculations() {
        PageConfiguration config = new PageConfiguration();
        assertEquals(PageSize.A4.getWidth(), config.getTotalWidth());
        assertEquals(PageSize.A4.getWidth() - config.getHorizontalTotalMargin(), config.getUtilizableWidth());
    }
}
