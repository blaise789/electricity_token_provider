package com.vehicle_tracking.vehicle_tracking.services.standalone;

import com.vehicle_tracking.vehicle_tracking.dtos.InvoiceData;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class PdfBoxGenerationService {

    public void generateInvoicePdf(HttpServletResponse response, InvoiceData data) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"invoice_" + data.getInvoiceNumber() + ".pdf\"");

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 16);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("Invoice: " + data.getInvoiceNumber());
                contentStream.endText();

                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText("Customer: " + data.getCustomerName());
                contentStream.newLine(); // new line needs manual y-offset adjustment
                contentStream.endText();

                // Table Header
                float yPosition = 650;
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 10);
                drawTextLine(contentStream, 50, yPosition, "Item Description");
                drawTextLine(contentStream, 300, yPosition, "Qty");
                drawTextLine(contentStream, 350, yPosition, "Unit Price");
                drawTextLine(contentStream, 450, yPosition, "Total");

                yPosition -= 20;
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10);
                for (InvoiceData.Item item : data.getItems()) {
                    drawTextLine(contentStream, 50, yPosition, item.getDescription());
                    drawTextLine(contentStream, 300, yPosition, String.valueOf(item.getQuantity()));
                    drawTextLine(contentStream, 350, yPosition, String.format("%.2f", item.getUnitPrice()));
                    drawTextLine(contentStream, 450, yPosition, String.format("%.2f", item.getLineTotal()));
                    yPosition -= 15;
                }

                yPosition -= 20; // Space before total
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
                drawTextLine(contentStream, 350, yPosition, "Grand Total:");
                drawTextLine(contentStream, 450, yPosition, String.format("%.2f", data.getTotalAmount()));

            }
            document.save(response.getOutputStream());
        }
    }

    private void drawTextLine(PDPageContentStream contentStream, float x, float y, String text) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(text);
        contentStream.endText();
    }

    // Sample data method
    public InvoiceData getSampleInvoiceData() {
        InvoiceData.Item item1 = new InvoiceData.Item("Laptop X200", 1, 1200.00, 1200.00);
        InvoiceData.Item item2 = new InvoiceData.Item("Wireless Mouse", 2, 25.00, 50.00);
        return new InvoiceData("INV-2023-001", "ACME Corp", Arrays.asList(item1, item2), 1250.00);
    }
}
