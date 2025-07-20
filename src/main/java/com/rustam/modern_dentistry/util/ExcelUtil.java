package com.rustam.modern_dentistry.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class ExcelUtil {
    private static final int MAX_COLUMN_WIDTH = 4500; // Maksimum sütun genişliyi (px ekvivalentində)
    private static final int COLUMN_WIDTH_PADDING = 500; // Sütun genişliyinə əlavə ediləcək piksel sayı


    public static <T> ByteArrayInputStream dataToExcel(List<T> dataList, Class<T> clazz)  {
//        if (dataList == null || dataList.isEmpty()) {
//            throw new IllegalArgumentException("Data list cannot be null or empty");
//        }

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            Field[] fields = clazz.getDeclaredFields(); // Fields array-ini bir dəfə yaradırıq
            Sheet sheet = workbook.createSheet("Sheet1"); //clazz.getSimpleName() Sheet adı entity-nin adına uyğun olsun

            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle bodyStyle = createBodyStyle(workbook);

            createHeaderRow(sheet, fields, headerStyle);
            fillDataRows(sheet, dataList, fields, bodyStyle);
            adjustColumnWidths(sheet, fields.length);

            workbook.write(byteArrayOutputStream);
            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Error generating Excel file", e);
        }
    }

    private static void createHeaderRow(Sheet sheet, Field[] fields, CellStyle style) {
        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < fields.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(getHeaderName(fields[i])); // Field adını başlıq kimi yaz
            cell.setCellStyle(style);
        }
    }

    private static <T> void fillDataRows(Sheet sheet, List<T> dataList, Field[] fields, CellStyle bodyStyle) {
        for (int rowIndex = 0; rowIndex < dataList.size(); rowIndex++) {
            Row row = sheet.createRow(rowIndex + 1);
            T item = dataList.get(rowIndex);

            for (int colIndex = 0; colIndex < fields.length; colIndex++) {
                fields[colIndex].setAccessible(true);
                try {
                    Object value = fields[colIndex].get(item);
                    Cell cell = row.createCell(colIndex);
                    cell.setCellValue(value != null ? value.toString() : ""); // Dəyəri qoy
                    cell.setCellStyle(bodyStyle); // Stil tətbiq et
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Error accessing field value", e);
                }
            }
        }
    }

    private static CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        font.setColor(IndexedColors.WHITE.getIndex()); // Ağ rəngli yazı
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex()); // Tünd mavi arxa plan
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private static CellStyle createBodyStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        return style;
    }

    private static void adjustColumnWidths(Sheet sheet, int size) {
        for (int i = 0; i < size; i++) {
            sheet.autoSizeColumn(i); // Avtomatik ölçü alır
            int currentWidth = sheet.getColumnWidth(i); // Mövcud genişlik
            int newWidth = currentWidth + COLUMN_WIDTH_PADDING; // Genişliyi bir neçə piksel artırırıq
            sheet.setColumnWidth(i, Math.min(newWidth, MAX_COLUMN_WIDTH));
        }
    }

    private static String getHeaderName(Field field) {
        JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
        return (jsonProperty != null) ? jsonProperty.value() : formatHeader(field.getName());
    }

    private static String formatHeader(String header) {
        return header.replaceAll("([A-Z])", " $1").trim().replaceFirst(".",
                Character.toUpperCase(header.charAt(0)) + "");
    }

}
