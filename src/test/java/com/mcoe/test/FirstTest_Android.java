package com.mcoe.test;

import com.mcoe.common.framework.GenericExecutor;
import com.mcoe.common.framework.constants.Platform;
import com.mcoe.common.framework.provider.DriverProvider;
import com.mcoe.common.framework.utils.CommonUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public class FirstTest_Android extends CommonSteps {

    @DataProvider
    public Object[][] getLoginData() {
        return new Object[][]{{"demopn@test.com", "Testing01"}};
    }

//
//    @BeforeTest
//    @Parameters("deleteExistingReport")
//    public void setupBeforeTest(boolean deleteExistingReport) {
//        if (deleteExistingReport) {
//            CommonUtils.deleteExistingReports();
//        }
//    }

    @BeforeMethod
    public void setUp() throws MalformedURLException, InterruptedException {
        Thread.sleep(7000);
        platform = Platform.ANDROID;
        waitTimeInSeconds = 3;
        final String app;
        if (platform.equals(Platform.ANDROID)) {
            app = "/Users/ksoni/Development/Automation/workspace/DemoAutomation/src/test/build/pnma.apk";
            genericExecutor = new GenericExecutor(new DriverProvider.CapabilitiesBuilder(app, platform, "7.1", "Android7").build());
        } else {
            app = "/Users/ksoni/Development/Automation/workspace/pnma-automation/build/pnma.app";
            genericExecutor = new GenericExecutor(new DriverProvider.CapabilitiesBuilder(app, platform, "11.2", "iPhone 8 Plus").build());
        }
    }

    @Test
    public void testSignInToAppForHappyPath() {
        System.out.println("testSignInToApp_HappyPath: ");
        boolean status = loginToAppWithCredentials("nsinha@mast.com", "Iphone123");
        Assert.assertTrue(status);
    }

    @Test
    public void testSignInToAppForUnHappyPath() {
        System.out.println("testSignInToApp_HappyPath: ");
        boolean status = loginToAppWithCredentials("nsinha@mt.com", "Iphone123");
        org.junit.Assert.assertTrue(status);
    }

    @Test
    public void testJoinPNScroll() {
        Assert.assertTrue(isAppLaunch());
        Assert.assertTrue(navigateToJoin2of2Screen(CommonUtils.generateRandomEmailId()));

        genericExecutor.performScrollToBottom(3);

        genericExecutor.clickOnElement(genericExecutor.performBottomScrollAndFindElementWithLabel(SUBMIT_BUTTON_LABEL.toUpperCase()));

        genericExecutor.waitForSeconds(10);
    }

    @Test
    public void testLandingScroll() {
        Assert.assertTrue(isAppLaunch());

        genericExecutor.performScrollRightToLeft(2);

    }

    @Test(dataProvider = "getLoginData")
    public void testApplaunch(String email, String password) {
        genericExecutor.extentReporter.startReportingForTest("UserSignTesting for email: " + email);

        genericExecutor.waitForSeconds(5);

        genericExecutor.waitForOneSecond();


        Assert.assertFalse(genericExecutor.isElementsDisplayedWithLabel("member sign"));

        genericExecutor.waitForSeconds(5);
        Assert.assertTrue(genericExecutor.isElementsDisplayedWithLabel("MEMBER SIGN IN"));

        genericExecutor.waitForOneSecond();
        Assert.assertFalse(genericExecutor.isElementsDisplayedContainsLabel("member sign"));


        Assert.assertTrue(genericExecutor.validateAppLaunchWithElements(Locators.MEMBER_SIGN_IN, Locators.MEMBER_SIGN_IN));

        genericExecutor.waitForOneSecond();
        genericExecutor.clickOnElement(Locators.MEMBER_SIGN_IN);

        genericExecutor.waitForOneSecond();
        genericExecutor.setTextInElementsWithLabel(email, "Email");

        genericExecutor.waitForOneSecond();
        genericExecutor.setTextInElementsWithLabel(password, "Password");

        genericExecutor.clickOnElement(Locators.SIGN_IN_BUTTON);
    }

    @AfterMethod
    public void tearDown() {
        System.out.println("tearDown");
        genericExecutor.quitDriver();
        genericExecutor.extentReporter.stopReportingForTest();
        genericExecutor = null;
    }
}
