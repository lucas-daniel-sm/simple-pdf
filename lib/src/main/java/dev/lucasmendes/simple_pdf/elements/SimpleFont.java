package dev.lucasmendes.simple_pdf.elements;

import dev.lucasmendes.simple_pdf.enums.FontStyle;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.With;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This class represents a font that can be used in a PDF document.
 */
@Data
@AllArgsConstructor
@Builder
public class SimpleFont {
    public static final float DEFAULT_FONT_SIZE = -1.0F;

    @Nonnull
    private final Font openPdfFont;
    @Nonnull
    private final String name;
    @With
    @Nonnull
    private final Float size;
    @With
    @Nonnull
    private final FontStyle style;
    @With
    @Nullable
    private final Color color;

    public SimpleFont(@Nonnull Font openPdfFont, @Nonnull String name) {
        this(openPdfFont, name, DEFAULT_FONT_SIZE, FontStyle.UNDEFINED, null);
    }

    public SimpleFont(@Nonnull Font openPdfFont) {
        this(openPdfFont, openPdfFont.getFamilyname());
    }

    public static SimpleFont fromFile(@Nonnull Path fontFile) throws FileSystemException, FileNotFoundException {
        return SimpleFont.fromFile(fontFile, null);
    }

    public static SimpleFont fromFile(@Nonnull Path fontFile, @Nullable String name) throws FileSystemException, FileNotFoundException {
        if (Files.isDirectory(fontFile)) {
            throw new FileSystemException("\"" + fontFile + "\" is not a file");
        }
        if (!Files.exists(fontFile)) {
            throw new FileNotFoundException("\"" + fontFile + "\" not exists");
        }
        var path = fontFile.toAbsolutePath().toString();
        try {
            var baseFont = BaseFont.createFont(path, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            var openPdfFont = new Font(baseFont, 12);
            return name == null ? new SimpleFont(openPdfFont) : new SimpleFont(openPdfFont, name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SimpleFont withIncreaseSize(float size) {
        return this.withSize(this.size + size);
    }

    @Nonnull
    public Font getOpenPdfFont() {
        this.openPdfFont.setStyle(this.style.getValue());
        this.openPdfFont.setSize(this.size);
        this.openPdfFont.setColor(color);
        return openPdfFont;
    }
}
