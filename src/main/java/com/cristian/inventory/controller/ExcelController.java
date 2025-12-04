package com.cristian.inventory.controller;

import com.cristian.inventory.service.ExcelService;
import com.cristian.inventory.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/excel")
public class ExcelController {
    private final ExcelService excelService;
    private final ProductService productService;

    @GetMapping("/download/product")
    public void downloadProductExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=product.xlsx");
        excelService.writeProductsToExcel(response.getOutputStream(), productService.getCurrentMonthProductsForCurrentUser());
    }
}
