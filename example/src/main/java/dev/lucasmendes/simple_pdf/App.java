package dev.lucasmendes.simple_pdf;

import java.nio.file.Paths;

public class App {
    public static void main(String[] args) {
        // create directory to store pdf that is on gitignore, so it won't be commited
        var pdfDir = Paths.get("pdf-out");
        // ensure directory exists
        if (!pdfDir.toFile().exists()) {
            var result = pdfDir.toFile().mkdirs();
            if (!result) {
                throw new RuntimeException("Could not create directory " + pdfDir);
            }
        }
        DataTableExample.generate(pdfDir);
    }
}
