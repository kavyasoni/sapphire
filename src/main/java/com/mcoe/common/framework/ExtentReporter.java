package com.mcoe.common.framework;

import com.mcoe.common.framework.provider.DriverProvider;
import com.mcoe.common.framework.utils.CommonUtils;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebDriver;

/**
 * ExtentReporter class is responsible for tracking all testcase and it's steps in report file.
 * <p>
 * Created by ksoni on 01/05/18.
 */
public class ExtentReporter {
    private final DriverProvider.CapabilitiesBuilder capabilitiesBuilder;
    private final WebDriver driver;
    private String reportPath;
    private String reportFileName = "ExecutionReport.html";
    private boolean replaceExisting = false;
    private ExtentReports extentReports;
    private ExtentTest extentTestLogger;
    private String testName;

    ExtentReporter(WebDriver driver, DriverProvider.CapabilitiesBuilder capabilitiesBuilder) {
        this.capabilitiesBuilder = capabilitiesBuilder;
        this.driver = driver;
        this.reportPath = CommonUtils.REPORT_PATH + capabilitiesBuilder.platform + "/";
    }

    private ExtentReports getExtentReports() {
        if (extentReports == null) {
            extentReports = new ExtentReports(reportPath + reportFileName, replaceExisting);
            extentReports.addSystemInfo("Platform", capabilitiesBuilder.platform)
                    .addSystemInfo("PlatformVersion", capabilitiesBuilder.platformVersion)
                    .addSystemInfo("Device Name", capabilitiesBuilder.deviceName)
                    .addSystemInfo("App path", capabilitiesBuilder.app);
        }
        return extentReports;
    }

    /**
     * Method to return active ExtentTestLogger it can be null if called before
     * startReportingForTest
     *
     * @return Requires description
     */
    public ExtentTest getExtentTestLogger() {
        return this.extentTestLogger;
    }

    /**
     * Method to set replaceExisting extent report
     *
     * @param replaceExisting Requires description
     */
    public void setReplaceExisting(boolean replaceExisting) {
        this.replaceExisting = replaceExisting;
    }

    /**
     * Method to set extent report file name
     *
     * @param reportFileName Requires description
     */
    public void setReportFileName(String reportFileName) {
        this.reportFileName = reportFileName;
        stopReportingForTest();
    }

    /**
     * Method to set extent report path
     *
     * @param reportPath Requires description
     */
    public void setReportPath(String reportPath) {
        this.reportPath = reportPath;
        stopReportingForTest();
    }

    /**
     * Track report using extent and System Log
     *
     * @param status Requires description
     * @param detail Requires description
     */
    public void trackReport(boolean status, String detail) {
        System.out.println((CommonUtils.isEmpty(this.testName) ? "" : (this.testName + " | ")) + status + " | " + detail);
        if (this.extentTestLogger != null) {
            if (!status) {
                extentTestLogger.log(LogStatus.FAIL, detail);
                attachScreenShot();
            } else
                extentTestLogger.log(LogStatus.PASS, detail);
        }
    }

    /**
     * Track report using extent and System Log
     *
     * @param status
     * @param detail
     */
    public void trackReport(LogStatus status, String detail) {
        System.out.println((CommonUtils.isEmpty(this.testName) ? "" : (this.testName + " | ")) + status.toString() + " | " + detail);
        if (this.extentTestLogger != null) {
            if (status == LogStatus.FAIL) {
                extentTestLogger.log(LogStatus.FAIL, detail);
                attachScreenShot();
            } else
                extentTestLogger.log(LogStatus.PASS, detail);
        }
    }

    /**
     * Method to start reporting in extent for test with testName
     *
     * @param testName Requires description
     */
    public void startReportingForTest(String testName) {
        startReportingForTest(testName, testName);
    }

    /**
     * Method to start reporting in extent for test with testName and testDescription
     *
     * @param testName        Requires description
     * @param testDescription Requires description
     */
    public void startReportingForTest(String testName, String testDescription) {
        if (CommonUtils.isEmpty(testName) || CommonUtils.isEmpty(testDescription))
            throw new NullPointerException("testName or testDescription can't be empty");
        this.testName = testName.trim().replaceAll(" ", "");
        System.out.println("startReportingForTest: " + testName + " | " + testDescription);
        stopReportingForTest();
        this.extentTestLogger = getExtentReports().startTest(this.testName, testDescription);
    }

    public void stopReportingForTest() {
        if (this.extentReports != null && this.extentTestLogger != null) {
            System.out.println("stopReportingForTest: " + testName);
            this.extentReports.endTest(this.extentTestLogger);
            this.extentTestLogger = null;
            generateReport();
        }
    }

    /**
     * Method to generate extent report
     */
    public void generateReport() {
        System.out.println("generatingReport");
        if (this.extentReports == null)
            return;
        try {
            Thread.sleep(2000);
            this.extentReports.flush();
            this.extentReports.close();
        } catch (Exception exception) {
            System.out.println("Exception: " + exception);
            exception.getStackTrace();
        }
    }

    /**
     * Attach screenshot in report
     */
    public void attachScreenShot() {
        String fileName = reportPath + "screenshots/" + System.currentTimeMillis() + ".png";
        try {
            CommonUtils.captureScreen(driver, fileName);
            extentTestLogger.log(LogStatus.INFO, "Please refer screenshot below or find at path: " + fileName + extentTestLogger.addScreenCapture(fileName));
            this.extentReports.flush();
        } catch (Exception exception) {
            System.out.println("Exception: " + exception);
            exception.getStackTrace();
        }
    }
}