package com.ocr.test;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PDFImageExtractor {
    public List<BufferedImage> extractImagesFromPdf(MultipartFile pdfFile) throws IOException {
        List<BufferedImage> images = new ArrayList<>();

        try (PDDocument document = PDDocument.load(pdfFile.getInputStream())) {
            PDPageTree pages = document.getPages();

            for (PDPage page : pages) {
                PDResources resources = page.getResources();
                Iterable<COSName> xObjectNames = resources.getXObjectNames();

                for (COSName xObjectName : xObjectNames) {
                    if (resources.isImageXObject(xObjectName)) {
                        PDImageXObject image = (PDImageXObject) resources.getXObject(xObjectName);
                        images.add(image.getImage());
                    }
                }
            }
        }

        return images;
    }
}
