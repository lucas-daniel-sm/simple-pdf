package dev.lucasmendes.simple_pdf.exceptions;

import dev.lucasmendes.simple_pdf.elements.Insertable;
import lombok.experimental.StandardException;

@StandardException
public class CouldNotInsertException extends RuntimeException {
    public CouldNotInsertException(Insertable<?> insertable) {
        super("Could not insert element." + insertable.getName());
    }
}
