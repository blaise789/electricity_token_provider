package com.vehicle_tracking.vehicle_tracking.controllers;


import com.vehicle_tracking.vehicle_tracking.dtos.InvoiceData;
import com.vehicle_tracking.vehicle_tracking.services.standalone.PdfBoxGenerationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/pdf")
public class PdfDownloadController {
    @Autowired
    private PdfBoxGenerationService pdfBoxGenerationService;

    @GetMapping("/download/invoice-pdfbox")
    public void downloadInvoicePdfBox(HttpServletResponse response) {
        try {
            InvoiceData data = pdfBoxGenerationService.getSampleInvoiceData(); // Fetch or create your data
            pdfBoxGenerationService.generateInvoicePdf(response, data);
        } catch (IOException e) {
            // Handle exception, e.g., log it or set an error status on the response
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.err.println("Error generating PDF with PDFBox: " + e.getMessage());
        }
    }
}
