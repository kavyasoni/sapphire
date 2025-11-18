package com.relevantcodes.extentreports;

/**
 * Backward compatibility shim for ExtentReports 2.x LogStatus enum.
 * This allows existing test code to continue working with ExtentReports 5.x.
 *
 * Maps to com.aventstack.extentreports.Status internally.
 */
public enum LogStatus {
    PASS,
    FAIL,
    FATAL,
    ERROR,
    WARNING,
    INFO,
    SKIP,
    UNKNOWN
}
