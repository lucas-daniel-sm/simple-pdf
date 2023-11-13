package dev.lucasmendes.simple_pdf.configurations;

import dev.lucasmendes.simple_pdf.models.Edges;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import javax.annotation.Nullable;
import java.awt.*;

@Data
@With
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class SimpleCellStyle {
    @Nullable
    private final Color backgroundColor;
    @Nullable
    private final Edges padding;
}
