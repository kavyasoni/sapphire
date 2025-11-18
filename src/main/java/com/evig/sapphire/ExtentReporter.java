package com.evig.sapphire;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.evig.sapphire.provider.DriverProvider;
import com.evig.sapphire.utils.CommonUtils;
import org.openqa.selenium.WebDriver;

import java.io.File;

/**
 * ExtentReporter class is responsible for tracking all testcase and it's steps in report file.
 * Uses ExtentReports 5.x API (com.aventstack)
 * <p>
 * Created by ksoni on 01/05/18.
 * Updated for ExtentReports 5.x compatibility
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
            // Create report directory if it doesn't exist
            File reportDir = new File(reportPath);
            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }

            // ExtentReports 5.x API
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath + reportFileName);
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setDocumentTitle("Sapphire Test Automation Report");
            sparkReporter.config().setReportName("Test Execution Report");
            sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");

            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);

            // System information
            extentReports.setSystemInfo("Platform", capabilitiesBuilder.platform);
            extentReports.setSystemInfo("Platform Version", capabilitiesBuilder.platformVersion);
            extentReports.setSystemInfo("Device Name", capabilitiesBuilder.deviceName);
            extentReports.setSystemInfo("App Path", capabilitiesBuilder.app);
            extentReports.setSystemInfo("Framework", "Sapphire 2.0");
        }
        return extentReports;
    }

    /**
     * Method to return active ExtentTestLogger it can be null if called before
     * startReportingForTest
     *
     * @return ExtentTest instance
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
     * @param status boolean - true for PASS, false for FAIL
     * @param detail log message detail
     */
    public void trackReport(boolean status, String detail) {
        System.out.println((CommonUtils.isEmpty(this.testName) ? "" : (this.testName + " | ")) + status + " | " + detail);
        if (this.extentTestLogger != null) {
            if (!status) {
                extentTestLogger.log(Status.FAIL, detail);
                attachScreenShot();
            } else {
                extentTestLogger.log(Status.PASS, detail);
            }
        }
    }

    /**
     * Track report using extent and System Log (backward compatible with old LogStatus enum)
     *
     * @param status LogStatus from old ExtentReports 2.x API
     * @param detail log message detail
     */
    public void trackReport(com.relevantcodes.extentreports.LogStatus status, String detail) {
        System.out.println((CommonUtils.isEmpty(this.testName) ? "" : (this.testName + " | ")) + status.toString() + " | " + detail);
        if (this.extentTestLogger != null) {
            Status newStatus = convertLogStatusToStatus(status);
            extentTestLogger.log(newStatus, detail);

            if (status == com.relevantcodes.extentreports.LogStatus.FAIL) {
                attachScreenShot();
            }
        }
    }

    /**
     * Convert old LogStatus to new Status enum for backward compatibility
     */
    private Status convertLogStatusToStatus(com.relevantcodes.extentreports.LogStatus logStatus) {
        switch (logStatus) {
            case PASS:
                return Status.PASS;
            case FAIL:
            case FATAL:
            case ERROR:
                return Status.FAIL;
            case INFO:
                return Status.INFO;
            case WARNING:
                return Status.WARNING;
            case SKIP:
                return Status.SKIP;
            case UNKNOWN:
            default:
                return Status.INFO;
        }
    }

    /**
     * Method to start reporting in extent for test with testName
     *
     * @param testName test name
     */
    public void startReportingForTest(String testName) {
        startReportingForTest(testName, testName);
    }

    /**
     * Method to start reporting in extent for test with testName and testDescription
     *
     * @param testName        test name
     * @param testDescription test description
     */
    public void startReportingForTest(String testName, String testDescription) {
        if (CommonUtils.isEmpty(testName) || CommonUtils.isEmpty(testDescription))
            throw new NullPointerException("testName or testDescription can't be empty");
        this.testName = testName.trim().replaceAll(" ", "");
        System.out.println("startReportingForTest: " + testName + " | " + testDescription);
        stopReportingForTest();
        // ExtentReports 5.x uses createTest() instead of startTest()
        this.extentTestLogger = getExtentReports().createTest(this.testName, testDescription);
    }

    public void stopReportingForTest() {
        if (this.extentReports != null && this.extentTestLogger != null) {
            System.out.println("stopReportingForTest: " + testName);
            // In ExtentReports 5.x, we don't need endTest() - just set to null and flush
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
            // Note: ExtentReports 5.x doesn't have close() method
        } catch (Exception exception) {
            System.out.println("Exception: " + exception);
            exception.printStackTrace();
        }
    }

    /**
     * Attach screenshot in report
     */
    public void attachScreenShot() {
        File screenshotDir = new File(reportPath + "screenshots/");
        if (!screenshotDir.exists()) {
            screenshotDir.mkdirs();
        }

        String fileName = reportPath + "screenshots/" + System.currentTimeMillis() + ".png";
        try {
            CommonUtils.captureScreen(driver, fileName);
            // ExtentReports 5.x API for adding screenshots
            extentTestLogger.addScreenCaptureFromPath(fileName, "Screenshot");
            extentTestLogger.log(Status.INFO, "Screenshot captured at: " + fileName);
            this.extentReports.flush();
        } catch (Exception exception) {
            System.out.println("Exception: " + exception);
            exception.printStackTrace();
        }
    }
}