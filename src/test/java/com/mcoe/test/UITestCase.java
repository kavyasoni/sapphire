package com.mcoe.test;

import com.mcoe.common.framework.GenericExecutor;
import com.mcoe.common.framework.constants.Parameter;
import com.mcoe.common.framework.provider.DriverProvider;
import com.mcoe.common.framework.utils.CommonUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

/**
 * Created by ksoni on 01/06/18.
 */
public class UITestCase extends CommonSteps {
    final String reportPath = System.getProperty("user.dir") + "/report/";
    private GenericExecutor genericExecutor;
    private WebDriver driver;

    @SuppressWarnings("rawtypes")
    @Parameters({Parameter.APP, Parameter.PLATFORM, Parameter.PLATFORM_VERSION, Parameter.DEVICE_NAME, Parameter.WAIT_TIME_IN_SECONDS})
    @BeforeMethod
    public void setUpTest(String app, String platform, String platformVersion, String deviceName, int waitTimeInSeconds) throws InterruptedException {
        if (CommonUtils.isEmpty(app) || CommonUtils.isEmpty(platform) || CommonUtils.isEmpty(platformVersion) || CommonUtils.isEmpty(deviceName)) {
            throw new NullPointerException("Required properties are null or empty in testNG config file");
        }
        Thread.sleep(waitTimeInSeconds * 1000);
        try {
            DriverProvider driverProvider = new DriverProvider.CapabilitiesBuilder(app, platform, platformVersion, deviceName).build();
            genericExecutor = new GenericExecutor(driverProvider);
            driver = genericExecutor.getDriver();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            e.printStackTrace();
        }
    }

    /*
        @Test
        public void testIfScreenIsValid() throws InterruptedException {
            genericExecutor.extentReporter.startReportingForTest("Validate Screen");
            Assert.assertEquals(genericExecutor.isElementsDisplayedAtXpath(2, Locators.MemberSignIn, Locators.ShopPinkNow), true);
        }

        @Test
        public void testAppLaunch() throws InterruptedException {
            genericExecutor.extentReporter.startReportingForTest("testAppLaunch");
            Assert.assertEquals(genericExecutor.validateAppLaunchWithElements(2, Locators.MemberSignIn, Locators.ShopPinkNow), true);
        }

        @Test
        public void testNavigateToMemberSignIN() throws InterruptedException {
            genericExecutor.extentReporter.startReportingForTest("Validate NavigateToMemberSignIN");
            genericExecutor.clickOnElement(Locators.MemberSignIn);
            Assert.assertEquals(genericExecutor.findElementContainsLabel(2, "SIGN IN TO PINK NATION").isDisplayed(), true);
        }
    */
    @AfterMethod
    public void endReport() {
        genericExecutor.extentReporter.stopReportingForTest();
    }


}
