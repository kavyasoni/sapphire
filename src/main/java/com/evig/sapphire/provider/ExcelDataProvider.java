package com.evig.sapphire.provider;

import com.evig.sapphire.constants.ExcelAttributes;
import com.evig.sapphire.utils.CommonUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * This class reads data from xlsx/xls file and provides List<HashMap>.
 * <p>
 * Created by ksoni on 01/05/18.
 */
class ExcelDataProvider {
    /**
     * Method to provide sheet from xlsx/xls workbook
     *
     * @param workbook
     * @param sheetName
     * @return Sheet
     */
    public static Sheet getSheetFromWorkbook(Workbook workbook, String sheetName) {
        if (workbook == null || CommonUtils.isEmpty(sheetName))
            return null;
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null)
            sheet = workbook.getSheet(sheetName.toUpperCase());
        if (sheet == null)
            sheet = workbook.getSheet(sheetName.toLowerCase());
        return sheet;
    }

    /**
     * Method to provide total row count in a sheet
     *
     * @param sheet
     * @return int
     */
    public static int getSheetRowCount(Sheet sheet) {
        if (sheet == null)
            return -1;
        return sheet.getLastRowNum();
    }

    /**
     * Method to provide total column count in a sheet
     *
     * @param sheet
     * @return int
     */
    public static int getSheetColumnCount(Sheet sheet) {
        if (sheet == null)
            return -1;
        Row row = sheet.getRow(0);
        if (row == null)
            return -1;
        return row.getLastCellNum();
    }

    /**
     * Method to provide list column in a sheet. Top row will be considered as a columns.
     *
     * @param sheet
     * @return LinkedList
     */
    public static LinkedList<String> getSheetColumnName(Sheet sheet) {
        LinkedList<String> columnList = new LinkedList<>();
        if (sheet == null)
            return columnList;
        Row row = sheet.getRow(0);
        if (row == null)
            return columnList;

        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            columnList.add(cell.getStringCellValue().trim());
        }
        return columnList;
    }

    /**
     * Method to provide index skip property in a sheet. Skip property in sheet will be column name with enable or execute.
     *
     * @param columnList
     * @return int
     */
    private static int getSkipPropertyIndex(LinkedList<String> columnList) {
        return columnList.contains(ExcelAttributes.EXECUTE) ? columnList.indexOf(ExcelAttributes.EXECUTE)
                : columnList.contains(ExcelAttributes.ENABLE) ? columnList.indexOf(ExcelAttributes.ENABLE) : -1;
    }

    /**
     * Method to provide index skip property in a sheet. Skip property in sheet will be column name with enable or execute.
     *
     * @param columnList
     * @return int
     */
    private static int getVerificationKeyPropertyIndex(LinkedList<String> columnList) {
        return columnList.contains(ExcelAttributes.VALIDATION_KEY) ? columnList.indexOf(ExcelAttributes.VALIDATION_KEY)
                : columnList.contains(ExcelAttributes.VERIFICATION_KEY) ? columnList.indexOf(ExcelAttributes.VERIFICATION_KEY) : -1;
    }

    /**
     * Method to validate weather row is executable or not
     *
     * @param row
     * @param verificationPropertyIndex
     * @param verificationKey
     * @return boolean
     */
    private static boolean isValidRow(Row row, int verificationPropertyIndex, String verificationKey) {
        if (verificationPropertyIndex == -1 || CommonUtils.isEmpty(verificationKey))
            return true;
        String cellData = getCellData(row.getCell(verificationPropertyIndex));
        return CommonUtils.isEmpty(cellData) || cellData.equalsIgnoreCase(verificationKey);
    }

    private static String getCellData(Cell cell) {
        if (cell == null)
            return "";
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue().trim();
            case Cell.CELL_TYPE_BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case Cell.CELL_TYPE_NUMERIC:
            case Cell.CELL_TYPE_FORMULA:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    // format in form of M/D/YY
                    double d = cell.getNumericCellValue();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(HSSFDateUtil.getJavaDate(d));
                    String cellData = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
                    cellData = cal.get(Calendar.DAY_OF_MONTH) + "/"
                            + cal.get(Calendar.MONTH) + 1 + "/"
                            + cellData;
                    return cellData;
                } else
                    return String.valueOf(cell.getNumericCellValue());
        }
        return "";
    }

    /**
     * Method to validate weather row is executable or not
     *
     * @param row
     * @param skipPropertyIndex
     * @return boolean
     */
    private static boolean isExecutableRow(Row row, int skipPropertyIndex) {
        if (skipPropertyIndex == -1)
            return true;
        Cell cell = row.getCell(skipPropertyIndex);
        return cell == null || cell.getStringCellValue().trim().equalsIgnoreCase("0")
                || cell.getStringCellValue().trim().equalsIgnoreCase("true")
                || cell.getStringCellValue().trim().equalsIgnoreCase("y")
                || cell.getStringCellValue().trim().equalsIgnoreCase("yes");
    }

    /**
     * Method to provide total executable row count in a sheet based on skip property.
     *
     * @param sheet
     * @param skipPropertyIndex
     * @return int
     */
    private static int getExecutableRowCount(Sheet sheet, int skipPropertyIndex) {
        if (skipPropertyIndex == -1)
            return getSheetRowCount(sheet);
        int executableRowCount = 0;
        for (Row aSheet : sheet) {
            //Get the row object
            Cell cell = aSheet.getCell(skipPropertyIndex);
            if (cell == null || cell.getStringCellValue().trim().equalsIgnoreCase("0")
                    || cell.getStringCellValue().trim().equalsIgnoreCase("true")
                    || cell.getStringCellValue().trim().equalsIgnoreCase("y")
                    || cell.getStringCellValue().trim().equalsIgnoreCase("yes")) {
                executableRowCount++;
            }
        } //end of rows iterator
        return executableRowCount;
    }

    /**
     * Method to provide test data from xlsx/xls file
     *
     * @param fileName
     * @param sheetName
     * @return Object[][]
     * @throws IOException
     */
    public static List<HashMap> getDataFromExcel(String fileName, String sheetName, String verificationKey) throws IOException {
        return getDataFromExcel(fileName, sheetName, verificationKey, true);
    }

    /**
     * Method to provide test data from xlsx/xls file
     *
     * @param fileName
     * @param sheetName
     * @return Object[][]
     * @throws IOException
     */
    public static List<HashMap> getDataFromExcel(String fileName, String sheetName, String verificationKey, boolean enableSkip) throws IOException {
        File file = new File(fileName);
        if (!file.exists())
            throw new FileNotFoundException(fileName + " is not exist");
        FileInputStream fileInputStream = new FileInputStream(fileName);
        //Create Workbook instance for xlsx/xls file input stream
        Workbook workbook = null;
        if (fileName.toLowerCase().endsWith("xlsx")) {
            workbook = new XSSFWorkbook(fileInputStream);
        } else if (fileName.toLowerCase().endsWith("xls")) {
            workbook = new HSSFWorkbook(fileInputStream);
        }
        fileInputStream.close();
        if (CommonUtils.isEmpty(sheetName) || sheetName.equals("ALL")) {
            //Get the number of sheets in the workbook
            int numberOfSheets = workbook != null ? workbook.getNumberOfSheets() : 0;
            //loop through each of the sheets
            List<HashMap> dataMapList = new ArrayList<>();
            for (int i = 0; i < numberOfSheets; i++) {
                dataMapList.addAll(getDataFromSheet(verificationKey, enableSkip, workbook.getSheetAt(i)));
            }
            return dataMapList;
        } else {
            Sheet sheet = getSheetFromWorkbook(workbook, sheetName);
            return getDataFromSheet(verificationKey, enableSkip, sheet);
        }
    }

    private static List<HashMap> getDataFromSheet(String verificationKey, boolean enableSkip, Sheet sheet) {
        List<HashMap> dataList = new ArrayList<>();
        if (sheet == null)
            return dataList;
        int columnCount = getSheetColumnCount(sheet);
        int rowCount = getSheetRowCount(sheet);
        if (rowCount <= 0 || columnCount <= 0)
            return dataList;
        LinkedList<String> columnList = getSheetColumnName(sheet);
        //every sheet has rows, iterate over them
        for (int rowNum = 1; rowNum <= rowCount; rowNum++) {
            Row row = sheet.getRow(rowNum);
            HashMap<String, String> rowDataMap = new HashMap<>();
            for (int columnNum = 0; columnNum < columnList.size(); columnNum++) {
                rowDataMap.put(columnList.get(columnNum), getCellData(row.getCell(columnNum)));
            }
            if (CommonUtils.isNotEmpty(verificationKey)) {
                if (isValidRow(row, getVerificationKeyPropertyIndex(columnList), verificationKey)) {
                    if (enableSkip) {
                        if (isExecutableRow(row, getSkipPropertyIndex(columnList))) {
                            dataList.add(rowDataMap);
                        }
                    } else {
                        dataList.add(rowDataMap);
                    }
                }
            } else if (enableSkip) {
                if (isExecutableRow(row, getSkipPropertyIndex(columnList))) {
                    dataList.add(rowDataMap);
                }
            } else {
                dataList.add(rowDataMap);
            }
        }
        return dataList;
    }
}

