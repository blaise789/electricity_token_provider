package com.vehicle_tracking.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceData {
    private String invoiceNumber;
    private String customerName;
    private List<Item> items;
    private double totalAmount;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        private String description;
        private int quantity;
        private double unitPrice;
        private double lineTotal;
    }
}