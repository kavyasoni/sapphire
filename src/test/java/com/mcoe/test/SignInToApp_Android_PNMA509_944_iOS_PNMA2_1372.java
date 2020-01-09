package com.mcoe.test;

import com.mcoe.common.framework.GenericExecutor;
import com.mcoe.common.framework.constants.ExcelAttributes;
import com.mcoe.common.framework.provider.DriverProvider;
import com.mcoe.common.framework.provider.TestDataProvider;
import com.mcoe.common.framework.utils.CommonUtils;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;

import javax.management.timer.Timer;
import java.net.MalformedURLException;
import java.util.HashMap;


/* Author: Nischal Sinha
 * 	1.PNMA-509_944_2_1372 | iOS |Android | Sign in to PNMA Mobile app (happy Path)
 * 	2.PNMA-509_944_2_1372 | iOS |Android | SignIn to PNMA Mobile app using invalid credentials
 * 	3.PNMA-509_944_2_1372 | iOS |Android | SignIn to PNMA Mobile app with invalid email format
 * 	4.PNMA-509_944_2_1372 | iOS |Android | SignIn to PNMA Mobile app without password
 * 	5.PNMA-509_944_2_1372 | iOS |Android | Lock PNMA Mobile app account with incorrect username and password (5 Invalid attempts)
 * 	6.PNMA-509_944_2_1372 | iOS |Android | SignIn to PNMA Mobile app without Email
 */

public class SignInToApp_Android_PNMA509_944_iOS_PNMA2_1372 {
    private final String dataFilePath = CommonUtils.RESOURCE_PATH + "SignInToAppWithoutAnyConflict_509_944_2_1372.xlsx";
    private int waitTimeInSeconds = 1;
    private long waitTimeInMiliSeconds = Timer.ONE_SECOND;
    private GenericExecutor genericExecutor;
    private String platform;


    @DataProvider
    public Object[][] FormsData(ITestContext context) {
        return TestDataProvider.provideDataFromSheetInExcelFile(dataFilePath, platform);
    }

    @DataProvider
    public Object[][] getSignInDataForHappyPath(ITestContext context) {
        this.platform = context.getCurrentXmlTest().getParameter("platform");
        return TestDataProvider.provideDataFromSheetInExcelFile(dataFilePath, platform, VerificationKeys.SIGN_IN_HAPPY_PATH);
    }

    @SuppressWarnings("rawtypes")
    @Parameters({"app", "platform", "platformVersion", "deviceName", "appiumServerURL", "waitTimeInSeconds"})
    @BeforeMethod
    public void methodSetUp(String app, String platform, String platformVersion, String deviceName, String appiumServerURL, int waitTimeInSeconds) throws InterruptedException, MalformedURLException {
        this.platform = platform;
        this.waitTimeInSeconds = waitTimeInSeconds;
        this.waitTimeInMiliSeconds = waitTimeInSeconds * 1000;
        Thread.sleep(waitTimeInMiliSeconds);
        genericExecutor = new GenericExecutor(new DriverProvider.CapabilitiesBuilder(app, platform, platformVersion, deviceName).setAppiumServerURL(appiumServerURL).build());
    }

    @Test(dataProvider = "getSignInDataForHappyPath")
    public void testSignInToApp_HappyPath(HashMap<String, String> data) {
        System.out.println("testSignInToApp_HappyPath: " + data);
        genericExecutor.extentReporter.startReportingForTest(data.get(ExcelAttributes.TEST_CASE));
        boolean isAppLaunchFlag = genericExecutor.validateAppLaunchWithElements(Locators.MEMBER_SIGN_IN, Locators.SHOP_PINK_NOW);
        if (isAppLaunchFlag) {
            //Step 1: Click on Member Sign In CTA Button on Landing Page
            genericExecutor.clickOnElement(Locators.MEMBER_SIGN_IN);

            // Step 2: Enter Email and Password in to Member Sign In Anonymous Landing Page
            genericExecutor.setTextInElementsWithLabel(data.get(ExcelAttributes.EMAIL_ADDRESS), Locators.EMAIL);
            genericExecutor.setTextInElementsWithLabel(data.get(ExcelAttributes.PASSWORD), Locators.PASSWORD);

            //Step 3: Click on Sign In Button
            genericExecutor.clickOnElement(Locators.SIGN_IN_BUTTON);

            Assert.assertTrue(isAppLaunchFlag);
        } else {
            Assert.assertTrue(isAppLaunchFlag);
        }

    }

    @AfterMethod
    public void methodTeardown() {
        genericExecutor.quitDriver();
        genericExecutor.extentReporter.stopReportingForTest();
        genericExecutor = null;
    }
}
