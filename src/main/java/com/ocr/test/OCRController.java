package com.ocr.test;

import net.sourceforge.tess4j.TesseractException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@RestController
public class OCRController {
    private final OCRService ocrService;
    private final PDFImageExtractor pdfImageExtractor;

    public OCRController(OCRService ocrService, PDFImageExtractor pdfImageExtractor) {
        this.ocrService = ocrService;
        this.pdfImageExtractor = pdfImageExtractor;
    }

    @PostMapping("/perform-ocr")
    public String performOCR(@RequestParam("file") MultipartFile pdfFile) throws TesseractException, IOException {
        if (pdfFile.isEmpty()) {
            return "Please select a PDF file to upload.";
        }

        List<BufferedImage> images = pdfImageExtractor.extractImagesFromPdf(pdfFile);
        StringBuilder result = new StringBuilder();
        int totalImages = images.size();
        int processedImages = 0;

        for (BufferedImage image : images) {
            processedImages++;
            String text = ocrService.performOCR(String.valueOf(image));
            result.append("Processing image ").append(processedImages).append(" of ").append(totalImages).append(":\n");
            result.append(text).append("\n\n");
        }

        return result.toString();
    }
}