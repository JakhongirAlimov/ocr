package com.ocr.test;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class OCRService {
    private final Tesseract tesseract;

    public OCRService() {
        tesseract = new Tesseract();
        tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata\\tessdata-main");
    }

    public String performOCR(String imagePath) throws TesseractException {
        return tesseract.doOCR(new File(imagePath));
    }
}
