package com.falabella.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Excel {
    public static ArrayList<Map<String, String>> readDataFromExcelSheet(String excelFilePath, String excelSheetName) throws IOException {
        ArrayList<Map<String, String>> dataList = new ArrayList<>();
        Map<String, String> rowData = new HashMap<>();
        File file = new File(excelFilePath);
        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheet(excelSheetName);
        Iterator<Row> rowIterator = sheet.iterator();
        Row headers = rowIterator.next();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                cell.getColumnIndex();
                switch (cell.getCellTypeEnum()) {
                    case STRING:
                        rowData.put(headers.getCell(cell.getColumnIndex()).toString(), cell.getStringCellValue());
                        break;
                    case NUMERIC:
                        rowData.put(headers.getCell(cell.getColumnIndex()).toString(), String.valueOf((long) cell.getNumericCellValue()));
                        break;
                    case BLANK:
                        rowData.put(headers.getCell(cell.getColumnIndex()).toString(), "");
                        break;
                    default:
                }
            }
            dataList.add(new HashMap<>(rowData));
            rowData.clear();
        }
        return dataList;
    }

    public static void writeToExcel(String path, String text, int row, int column) {
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(new FileInputStream(path));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Sheet sheet = workbook.getSheetAt(0);
        Row excelRow = sheet.getRow(row);
        if (excelRow == null) {
            excelRow = sheet.createRow(row);
        }
        Cell cell = excelRow.getCell(column);
        if (cell == null) {
            cell = excelRow.createCell(column);
        }
        cell.setCellValue(text);
        try (FileOutputStream outputStream = new FileOutputStream(path)) {
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Map<String, String>> data = new ArrayList<>();
    public static ArrayList<Map<String, String>> extractTo() {
        try {
            data = Excel.readDataFromExcelSheet("src/test/resources/data/data.xlsx", "Data");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
}