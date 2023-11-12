package dev.lucasmendes.simple_pdf.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum VerticalAlignment {
    UNDEFINED(-1),
    TOP(4),
    MIDDLE(5),
    BOTTOM(6),
    BASELINE(7);

    private final int value;


    public static VerticalAlignment from(int imgAlignment) {
        switch (imgAlignment) {
            case 4:
                return TOP;
            case 5:
                return MIDDLE;
            case 6:
                return BOTTOM;
            case 7:
                return BASELINE;
            default:
                return UNDEFINED;
        }
    }
}
