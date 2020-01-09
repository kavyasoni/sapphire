package com.mcoe.common.framework.utils;

import com.mcoe.common.framework.constants.Platform;
import org.apache.commons.io.FileUtils;
import org.aspectj.util.FileUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This class provides common utility methods
 * <p>
 * Created by ksoni on 01/05/18.
 */
public class CommonUtils {
    public static final String RESOURCE_PATH = System.getProperty("user.dir") + "/src/test/resources/";
    public static final String REPORT_PATH = System.getProperty("user.dir") + "/report/";

    /**
     * Method to delete existing reports
     *
     * @return boolean
     */
    public static boolean deleteExistingReports() {
        return deleteDir(REPORT_PATH);
    }

    /**
     * Method to delete existing android report
     *
     * @return boolean
     */
    public static boolean deleteExistingAndroidReport() {
        return deleteDir(REPORT_PATH + Platform.ANDROID + "/");
    }

    /**
     * Method to delete existing ios report
     *
     * @return boolean
     */
    public static boolean deleteExistingIOSReport() {
        return deleteDir(REPORT_PATH + Platform.IOS + "/");
    }

    /**
     * Method to delete directory
     *
     * @param path
     * @return
     */
    public static boolean deleteDir(String path) {
        System.out.println("deleteDir: " + path);
        if (isEmpty(path))
            return true;
        File reportDir = new File(path);
        if (!reportDir.exists() || !reportDir.isDirectory())
            return true;
        try {
            FileUtil.deleteContents(reportDir);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException while deleteExistingReports");
            return false;
        }
    }

    /**
     * Method to capture screen
     *
     * @param driver
     * @param fileName
     * @throws IOException
     */
    public static void captureScreen(WebDriver driver, String fileName) {
        try {
            Thread.sleep(3000);
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(fileName));
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            e.printStackTrace();
        }
    }

    public static boolean isEmpty(String string) {
        return string == null || string.trim().isEmpty();
    }

    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

    public String getFormattedDate() {
        Date date = new Date();
        //Pattern for showing milliseconds in the time yyyy-MM-dd HH:mm:ss.SSS
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String stringDate = sdf.format(date);
        //Using Calendar class
        Calendar cal = Calendar.getInstance();
        return sdf.format(cal.getTime());
    }

    /**
     * Method to generate random email id
     *
     * @return String returns random email id
     */
    public static String generateRandomEmailId() {
        return "automation_" + System.currentTimeMillis() + "@test.com";
    }

    public static boolean isValidEmail(String email) {
        final String emailPattern = "^[\\w\\_\\-]" + // Email can be started with character, _ or - eg: _test@test.com
                "+([\\+]{0,1})+([\\.]{0,1})" + // Email can include . and + eg: test+test@com.com
                "+([\\w\\_\\-\\+]+)*" + // Email can include character, _, -, ., + eg: _test.te+st@test.com
                "@[\\w\\-]" + //Company domain can include character and - eg: _test.te+st@te-st.com
                "+(\\.[\\w]+)*" + // Can contain subdomain _test.te+st@test.co.in
                "(\\.[A-Za-z]{2,})$"; // Can end only with string _test.te+st@test.co.in
        return email.matches(emailPattern);
    }
}