package com.mcoe.common.framework.provider;

import java.util.HashMap;
import java.util.List;

/**
 * This class is used in DataProvider to fetch data from xlsx/xls/json file
 * <p>
 * Created by ksoni on 01/05/18.
 */
public class TestDataProvider {

    /**
     * Method to provide test data from xlsx/xls file
     *
     * @param fileName Requires description
     * @return Object[][] Requires description
     */
    public static Object[][] provideDataFromExcelFile(String fileName) {
        return provideDataFromSheetInExcelFile(fileName, null, null, true);
    }

    /**
     * Method to provide test data from xlsx/xls file
     *
     * @param fileName   Requires description
     * @param skipEnable Requires description
     * @return Requires description
     */
    public static Object[][] provideDataFromExcelFile(String fileName, boolean skipEnable) {
        return provideDataFromSheetInExcelFile(fileName, null, null, skipEnable);
    }

    /**
     * Method to provide test data from xlsx/xls file
     *
     * @param fileName        Requires description
     * @param verificationKey Requires description
     * @return Requires description
     */
    public static Object[][] provideDataFromExcelFile(String fileName, String verificationKey) {
        return provideDataFromSheetInExcelFile(fileName, null, verificationKey, true);
    }

    /**
     * Method to provide test data from xlsx/xls file
     *
     * @param fileName        Requires description
     * @param verificationKey Requires description
     * @param skipEnable      Requires description
     * @return Requires description
     */
    public static Object[][] provideDataFromExcelFile(String fileName, String verificationKey, boolean skipEnable) {
        return provideDataFromSheetInExcelFile(fileName, null, verificationKey, skipEnable);
    }

    /**
     * Method to provide test data from xlsx/xls file
     *
     * @param fileName  Requires description
     * @param sheetName Requires description
     * @return Object[][] Requires description
     */
    public static Object[][] provideDataFromSheetInExcelFile(String fileName, String sheetName) {
        return provideDataFromSheetInExcelFile(fileName, sheetName, null, true);
    }

    /**
     * Method to provide test data from xlsx/xls file
     *
     * @param fileName   Requires description
     * @param sheetName  Requires description
     * @param skipEnable Requires description
     * @return Requires description
     */
    public static Object[][] provideDataFromSheetInExcelFile(String fileName, String sheetName, boolean skipEnable) {
        return provideDataFromSheetInExcelFile(fileName, sheetName, null, skipEnable);
    }

    /**
     * Method to provide test data from xlsx/xls file
     *
     * @param fileName        Requires description
     * @param sheetName       Requires description
     * @param verificationKey Requires description
     * @return Requires description
     */
    public static Object[][] provideDataFromSheetInExcelFile(String fileName, String sheetName, String verificationKey) {
        return provideDataFromSheetInExcelFile(fileName, sheetName, verificationKey, true);
    }

    /**
     * Method to provide test data from xlsx/xls file
     *
     * @param fileName        Requires description
     * @param sheetName       Requires description
     * @param verificationKey Requires description
     * @param skipEnable      Requires description
     * @return Requires description
     */
    public static Object[][] provideDataFromSheetInExcelFile(String fileName, String sheetName, String verificationKey,
                                                             boolean skipEnable) {
        try {
            return toObjectArray(ExcelDataProvider.getDataFromExcel(fileName, sheetName, verificationKey, skipEnable));
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            e.printStackTrace();
            return new Object[0][0];
        }
    }

    /**
     * Method to provide test data from JSON file
     *
     * @param fileName Requires description
     * @return Requires description
     */
    public static Object[][] provideDataFromJSONFile(String fileName) {
        try {
            return toObjectArray(JSONDataProvider.getDataFromJSON(fileName));
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            e.printStackTrace();
            return new Object[0][0];
        }
    }

    /**
     * Method to provide test data from objects
     *
     * @param dataList Requires description
     * @return Requires description
     */
    public static Object[][] toObjectArray(List<HashMap> dataList) {
        if (dataList == null || dataList.isEmpty())
            return new Object[0][0];
        Object[][] dataArray = new Object[dataList.size()][1];
        int index = 0;
        for (HashMap data : dataList) {
            dataArray[index][0] = data;
            index++;
        }
        return dataArray;
    }
}
