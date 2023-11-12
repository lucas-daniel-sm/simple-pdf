package dev.lucasmendes.simple_pdf.elements;

import dev.lucasmendes.simple_pdf.enums.FontStyle;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleFontTest {
    @Test
    @DisplayName("Test if is not possible to create a font from a directory")
    public void testCreateFontWithDirectory() {
        assertThrows(
                FileSystemException.class,
                () -> SimpleFont.fromFile(Paths.get("."), "dir"),
                "Should throw a FileSystemException if is not a file"
        );
    }

    @Test
    @DisplayName("Test if is not possible to create a font from a not existing file")
    public void testCreateFontWithNotExistingFile() {
        assertThrows(
                FileNotFoundException.class,
                () -> SimpleFont.fromFile(
                        Paths.get("thisFileNotExists.ttf"),
                        "thisFileNotExists"
                ),
                "Should throw a FileSystemException if is not a file"
        );
    }

    @Test
    @DisplayName("Test if is possible to create a font from a file")
    public void testCreateFontWithFile() throws Exception {
        final var robotoFile = Paths.get(
                ClassLoader.getSystemResource("fonts/Roboto-Regular.ttf").toURI()
        );
        assertDoesNotThrow(
                () -> SimpleFont.fromFile(robotoFile, "Roboto"),
                "Should not throw a FileSystemException if is a file"
        );
    }

    // test color
    @Test
    @DisplayName("Test the creation of fonts that is already shipped with openpdf library")
    public void testCreateFromEmbeddedFonts() {
        var font = FontFactory.getFont(FontFactory.HELVETICA);
        var simpleFont = new SimpleFont(font);
        assertNotNull(simpleFont.getOpenPdfFont(), "Font must not be null");
        assertEquals(
                FontFactory.HELVETICA,
                simpleFont.getOpenPdfFont().getFamilyname(),
                "Created font must be Helvetica"
        );
    }

    @Test
    @DisplayName("Test the creation of a font setting the color, style and size at constructor")
    public void testCreateFontWithColorStyleAndSize() {
        var font = FontFactory.getFont(FontFactory.HELVETICA);
        var simpleFont = new SimpleFont(
                font,
                FontFactory.HELVETICA,
                20f,
                FontStyle.BOLD,
                Color.RED
        );
        assertNotNull(simpleFont.getOpenPdfFont(), "Font must not be null");
        assertEquals(
                "Helvetica",
                simpleFont.getOpenPdfFont().getFamilyname(),
                "Created font must be Helvetica"
        );
        assertEquals(
                Color.RED,
                simpleFont.getOpenPdfFont().getColor(),
                "Created font must have color red"
        );
        assertEquals(
                20f,
                simpleFont.getOpenPdfFont().getSize(),
                "Created font must have size of 20"
        );
        assertEquals(
                Font.BOLD,
                simpleFont.getOpenPdfFont().getStyle(),
                "Created font must be bold"
        );
    }

    @Test
    @DisplayName("Test changing the size of a font using with")
    public void testChangeFontSize() {
        var font = FontFactory.getFont(FontFactory.HELVETICA);
        var simpleFont = new SimpleFont(font);
        assertNotNull(simpleFont.getOpenPdfFont(), "Font must not be null");
        assertEquals(
                FontFactory.HELVETICA,
                simpleFont.getOpenPdfFont().getFamilyname(),
                "Created font must be Helvetica"
        );
        assertNull(
                simpleFont.getOpenPdfFont().getColor(),
                "Created font must not have color"
        );
        assertEquals(
                SimpleFont.DEFAULT_FONT_SIZE,
                simpleFont.getOpenPdfFont().getSize(),
                "Created font must not have size"
        );
        assertEquals(
                Font.UNDEFINED,
                simpleFont.getOpenPdfFont().getStyle(),
                "Created font must not have style"
        );

        var size20Font = simpleFont.withSize(20f);
        assertEquals(
                FontFactory.HELVETICA,
                size20Font.getOpenPdfFont().getFamilyname(),
                "Created font must stay Helvetica"
        );
        assertEquals(
                20f,
                size20Font.getOpenPdfFont().getSize(),
                "Created font must have size 20"
        );
        assertNull(
                size20Font.getOpenPdfFont().getColor(),
                "Created font must stay with no color"
        );
        assertEquals(
                Font.UNDEFINED,
                size20Font.getOpenPdfFont().getStyle(),
                "Created font must stay with no style"
        );
    }

    @Test
    @DisplayName("Test changing the color of a font using with")
    public void testChangeFontColor() {
        var font = FontFactory.getFont(FontFactory.HELVETICA);
        var simpleFont = new SimpleFont(font);
        assertNotNull(simpleFont.getOpenPdfFont(), "Font must not be null");
        assertEquals(
                FontFactory.HELVETICA,
                simpleFont.getOpenPdfFont().getFamilyname(),
                "Created font must be Helvetica"
        );
        assertNull(
                simpleFont.getOpenPdfFont().getColor(),
                "Created font must not have color"
        );
        assertEquals(
                SimpleFont.DEFAULT_FONT_SIZE,
                simpleFont.getOpenPdfFont().getSize(),
                "Created font must not have size"
        );
        assertEquals(
                Font.UNDEFINED,
                simpleFont.getOpenPdfFont().getStyle(),
                "Created font must not have style"
        );

        var redFont = simpleFont.withColor(Color.RED);
        assertEquals(
                FontFactory.HELVETICA,
                redFont.getOpenPdfFont().getFamilyname(),
                "Created font must stay Helvetica"
        );
        assertEquals(
                SimpleFont.DEFAULT_FONT_SIZE,
                redFont.getOpenPdfFont().getSize(),
                "Created font must stay with no size"
        );
        assertEquals(
                Color.RED,
                redFont.getOpenPdfFont().getColor(),
                "Created font must have color red"
        );
        assertEquals(
                Font.UNDEFINED,
                redFont.getOpenPdfFont().getStyle(),
                "Created font must stay with no style"
        );
    }

    @Test
    @DisplayName("Test changing the style of a font using with")
    public void testChangeFontStyle() {
        var font = FontFactory.getFont(FontFactory.HELVETICA);
        var simpleFont = new SimpleFont(font);
        assertNotNull(simpleFont.getOpenPdfFont(), "Font must not be null");
        assertEquals(
                FontFactory.HELVETICA,
                simpleFont.getOpenPdfFont().getFamilyname(),
                "Created font must be Helvetica"
        );
        assertNull(
                simpleFont.getOpenPdfFont().getColor(),
                "Created font must not have color"
        );
        assertEquals(
                SimpleFont.DEFAULT_FONT_SIZE,
                simpleFont.getOpenPdfFont().getSize(),
                "Created font must not have size"
        );
        assertEquals(
                Font.UNDEFINED,
                simpleFont.getOpenPdfFont().getStyle(),
                "Created font must not have style"
        );

        var boldFont = simpleFont.withStyle(FontStyle.BOLD);
        assertEquals(
                FontFactory.HELVETICA,
                boldFont.getOpenPdfFont().getFamilyname(),
                "Created font must stay Helvetica"
        );
        assertEquals(
                SimpleFont.DEFAULT_FONT_SIZE,
                boldFont.getOpenPdfFont().getSize(),
                "Created font must stay with no size"
        );
        assertNull(
                boldFont.getOpenPdfFont().getColor(),
                "Created font must stay with no color"
        );
        assertEquals(
                Font.BOLD,
                boldFont.getOpenPdfFont().getStyle(),
                "Created font must have style bold"
        );
    }
}
