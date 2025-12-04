package com.cristian.inventory.service;

import com.cristian.inventory.dto.ProductDto;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class ExcelService {

    public void writeProductsToExcel(OutputStream os, List<ProductDto> products) throws IOException {
        try(Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Products");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("S.No");
            header.createCell(1).setCellValue("Name");
            header.createCell(2).setCellValue("Category");
            header.createCell(3).setCellValue("Stock");
            header.createCell(4).setCellValue("Production Price");
            header.createCell(5).setCellValue("P.V.P");
            header.createCell(6).setCellValue("Status");
            header.createCell(7).setCellValue("Packaging");
            header.createCell(8).setCellValue("Notes");
            IntStream.range(0, products.size())
                    .forEach(i -> {
                        ProductDto product = products.get(i);
                        Row row = sheet.createRow(i + 1);
                        row.createCell(0).setCellValue(i + 1);
                        row.createCell(1).setCellValue(product.getName() != null ? product.getName() : "N/A");
                        row.createCell(2).setCellValue(product.getCategoryId() != null ? product.getCategoryName() : "N/A");
                        row.createCell(3).setCellValue(product.getStock() != null ? product.getStock() : 0);
                        row.createCell(4).setCellValue(product.getProductionPrice() != null ? product.getProductionPrice() : 0);
                        row.createCell(5).setCellValue(product.getPvp() != null ? product.getPvp() : 0);
                        row.createCell(6).setCellValue(product.getStatus() != null ? product.getStatus().name() : "N/A");
                        row.createCell(7).setCellValue(product.getPackaging() != null ? product.getPackaging() : 0);
                        row.createCell(8).setCellValue(product.getNotes() != null ? product.getNotes() : "N/A");
                    });
            workbook.write(os);
        }
    }
}
