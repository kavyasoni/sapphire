package com.mcoe.test;

import com.mcoe.common.framework.constants.Platform;
import com.mcoe.common.framework.provider.TestDataProvider;
import com.mcoe.common.framework.utils.CommonUtils;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by ksoni on 01/05/18.
 */
public class TestDataProviderTest {
    @DataProvider(name = "Authentication")
    public static Object[][] credentials() {
        return new Object[][]{{"testuser_1", "Test@123"}, {"testuser_1", "Test@123"}};
    }

    @DataProvider
    public Object[][] ExcelDataProvider(ITestContext context) throws Exception {
        return TestDataProvider.provideDataFromSheetInExcelFile(CommonUtils.RESOURCE_PATH + "Sample.xlsx", Platform.ANDROID);
    }

    @DataProvider
    public Object[][] getSignInDataForHappyPath(ITestContext context) throws Exception {
        final String dataFilePath = CommonUtils.RESOURCE_PATH + "SignInToAppWithoutAnyConflict_509_944_2_1372.xlsx";
        return TestDataProvider.provideDataFromSheetInExcelFile(dataFilePath, Platform.ANDROID, VerificationKeys.SIGN_IN_HAPPY_PATH);
    }

    @DataProvider
    public Object[][] ExcelDataProvider2(ITestContext context) throws Exception {
        return TestDataProvider.provideDataFromSheetInExcelFile(CommonUtils.RESOURCE_PATH + "Sample2.xlsx", Platform.ANDROID, "ExistingPNMAAccount");
    }

    @DataProvider
    public Object[][] ExcelDataProvider3(ITestContext context) throws Exception {
        return TestDataProvider.provideDataFromExcelFile(CommonUtils.RESOURCE_PATH + "Sample2.xlsx", "ExistingPNMAAccount");
    }

    @DataProvider
    public Object[][] JSONDataProvider(ITestContext context) throws Exception {
        return TestDataProvider.provideDataFromJSONFile(CommonUtils.RESOURCE_PATH + "test.json");
    }

    @DataProvider
    public Object[][] JSONDataProvider2(ITestContext context) throws Exception {
        return TestDataProvider.provideDataFromJSONFile(CommonUtils.RESOURCE_PATH + "test2.json");
    }

    @DataProvider
    public Object[][] JSONDataProvider3(ITestContext context) throws Exception {
        return TestDataProvider.provideDataFromJSONFile(CommonUtils.RESOURCE_PATH + "test3.json");
    }

    @Test
    public void testDataProvider() throws Exception {
        System.out.println("user.dir: " + System.getProperty("user.dir"));
        Object[][] test = TestDataProvider.provideDataFromSheetInExcelFile(CommonUtils.RESOURCE_PATH + "Sample.xlsx", "Android");
        System.out.println("test: " + test.length);
        System.out.println(Arrays.deepToString(test));
    }

    @Test
    public void testReadDataFromExcel() throws Exception {
        TestDataProvider.provideDataFromSheetInExcelFile(CommonUtils.RESOURCE_PATH + "Sample.xlsx", "Android");
    }

    @Test
    public void testReadDataFromJSON() throws Exception {
        TestDataProvider.provideDataFromJSONFile(CommonUtils.RESOURCE_PATH + "test.json");
    }

    @Test(dataProvider = "JSONDataProvider")
    public void testJSONDataProvider(HashMap data) throws Exception {
        System.out.println("testJSONDataProviderData: " + data);
    }

    @Test(dataProvider = "JSONDataProvider2")
    public void testJSONDataProvider2(HashMap data) throws Exception {
        System.out.println("testJSONDataProvider2Data: " + data);
    }

    @Test(dataProvider = "JSONDataProvider3")
    public void testJSONDataProvider3(HashMap data) throws Exception {
        System.out.println("testJSONDataProvider3Data: " + data);
    }

    @Test(dataProvider = "ExcelDataProvider")
    public void testExcelDataProvider(HashMap data) throws Exception {
        System.out.println("testExcelDataProviderData: " + data);
    }

    @Test(dataProvider = "ExcelDataProvider3")
    public void testExcelDataProvider2(HashMap data) throws Exception {
        System.out.println("testExcelDataProvider2Data: " + data);
    }

    @Test(dataProvider = "getSignInDataForHappyPath")
    public void testSignInToApp_HappyPath(HashMap<String, String> data) {
        System.out.println("testSignInToApp_HappyPath: " + data);
    }
}