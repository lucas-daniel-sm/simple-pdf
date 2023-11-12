package dev.lucasmendes.simple_pdf.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum FontStyle {
    NORMAL(0),
    BOLD(1),
    ITALIC(2),
    UNDERLINE(4),
    STRIKE_THRU(8),
    BOLD_ITALIC(3),
    UNDEFINED(-1);
    private final int value;
}
