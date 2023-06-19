package com.bepay.application.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class FileUtil {
    public static InputStream exportMapToExcel(Map<String, String> listRecord) throws IOException {
        File file = new File("userlist-upload.xlsx");
        FileOutputStream out = new FileOutputStream(file);
        InputStream inp = new FileInputStream(file);
        int rowNo=0;
        XSSFWorkbook workbook=new XSSFWorkbook();
        XSSFSheet sheet=workbook.createSheet("sheet1");
        for(HashMap.Entry entry:listRecord.entrySet()) {
            XSSFRow row=sheet.createRow(rowNo++);
            row.createCell(0).setCellValue((String)entry.getKey());
            row.createCell(1).setCellValue((String)entry.getValue());
        }
        workbook.write(out);
        out.close();
        return inp;
    }




    private static void createHeaderCell(Row rowHeader, int i, CellStyle boldCellStyle, String headerValue) {
        Cell empIdHeader = rowHeader.createCell(i);
        empIdHeader.setCellStyle(boldCellStyle);
        empIdHeader.setCellValue(headerValue);
    }
    private static CellStyle getBoldCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = getBoldFont(workbook);
        cellStyle.setFont(font);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        return cellStyle;
    }

    private static Font getBoldFont(Workbook workbook) {
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setFontName("Times New Roman");
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setBold(true);
        font.setItalic(false);
        return font;
    }
}
