package dev.lucasmendes.simple_pdf.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum HorizontalAlignment {
    UNDEFINED(-1),
    LEFT(0),
    CENTER(1),
    RIGHT(2),
    JUSTIFY(3),
    JUSTIFY_ALL(8);

    private final int value;

    public static HorizontalAlignment from(int imgAlignment) {
        switch (imgAlignment) {
            case 0:
                return LEFT;
            case 1:
                return CENTER;
            case 2:
                return RIGHT;
            case 3:
                return JUSTIFY;
            case 8:
                return JUSTIFY_ALL;
            default:
                return UNDEFINED;
        }
    }
}
