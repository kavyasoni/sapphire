package com.mcoe.test;

import com.mcoe.common.framework.GenericExecutor;
import com.mcoe.common.framework.constants.Parameter;
import com.mcoe.common.framework.constants.Platform;
import com.mcoe.common.framework.utils.CommonUtils;
import org.openqa.selenium.WebElement;

import java.util.HashMap;

public class CommonSteps implements Parameter, ExcelAttributes, Locators {
    protected GenericExecutor genericExecutor;
    protected int waitTimeInSeconds = 1;
    protected String platform;

    protected void startReporting(HashMap<String, String> data) {
        if (data == null || data.isEmpty() || CommonUtils.isEmpty(data.get(ExcelAttributes.TEST_CASE)))
            startReporting(this.getClass().getSimpleName() + "TestCase", "Test case for class " + this.getClass().getName());
        else
            startReporting(data.get(ExcelAttributes.TEST_CASE), null);
    }

    protected void startReporting(String testName, String testDescription) {
        if (CommonUtils.isEmpty(testName))
            return;
        if (CommonUtils.isEmpty(testDescription))
            testDescription = testName;
        genericExecutor.extentReporter.startReportingForTest(testName, testDescription);
    }

    /**
     * Method to login to app with credentials
     *
     * @param data
     * @return boolean returns true if login is successful and dashboard title bar is visible else false
     */
    protected boolean loginToAppWithCredentials(HashMap<String, String> data) {
        if (data == null || data.isEmpty() || CommonUtils.isEmpty(data.get(ExcelAttributes.TEST_CASE)))
            return false;
        return loginToAppWithCredentials(data.get(ExcelAttributes.EMAIL_ADDRESS), data.get(ExcelAttributes.PASSWORD), true);
    }

    /**
     * Method to login to app with credentials
     *
     * @param data
     * @return boolean returns true if login is successful and dashboard title bar is visible else false
     */
    protected boolean loginToAppWithCredentials(HashMap<String, String> data, boolean validateError) {
        if (data == null || data.isEmpty() || CommonUtils.isEmpty(data.get(ExcelAttributes.TEST_CASE)))
            return false;
        return loginToAppWithCredentials(data.get(ExcelAttributes.EMAIL_ADDRESS), data.get(ExcelAttributes.PASSWORD), validateError);
    }

    /**
     * Method to login to app with credentials
     *
     * @param email
     * @param password
     * @return boolean returns true if login is successful and dashboard title bar is visible else false
     */

    protected boolean loginToAppWithCredentials(String email, String password) {
        return loginToAppWithCredentials(email, password, true);

    }

    /**
     * Method to login to app with credentials
     *
     * @param email
     * @param password
     * @return boolean returns true if login is successful and dashboard title bar is visible else false
     */
    protected boolean loginToAppWithCredentials(String email, String password, boolean validateError) {
        System.out.println("loginToAppWithCredentials: " + email + " | " + password + " | " + validateError);
        if (isAppLaunch()) {
            //Step 1: Click on Member Sign In CTA Button on Landing Page
            genericExecutor.clickOnElement(MEMBER_SIGN_IN_BTN_LOCATOR);

            // Step 2: Enter EMAIL_LABEL and PASSWORD_LABEL in to Member Sign In Anonymous Landing Page
            genericExecutor.waitForOneSecond();
            genericExecutor.setTextInElementsContainsLabel(email, EMAIL_LABEL);
            genericExecutor.waitForOneSecond();
            genericExecutor.setTextInElementsWithLabel(password, PASSWORD_LABEL);

            //Step 3: Click on Sign In Button
            genericExecutor.waitForOneSecond();
            genericExecutor.clickOnElement(SIGN_IN_BUTTON_LOCATOR);

            genericExecutor.waitForSeconds(waitTimeInSeconds);
            if (validateError && isErrorOccurred()) return false;

            refocus();

            genericExecutor.waitForSeconds(waitTimeInSeconds);

            if (platform.equals(Platform.iOS) && genericExecutor.isElementsDisplayedContainsLabel(NOTIFICATION_OPTIN_LABEL)) {
                genericExecutor.clickOnElementContainsLabel(SUBMIT_BUTTON_LABEL);
                genericExecutor.clickOnElementWithLabel(ALLOW_BUTTON_LABEL);
            }

            return genericExecutor.isElementsDisplayedAtXpath(DASHBOARD_TITLE_LOCATOR);
        } else {
            return false;
        }
    }

    protected boolean openNavigationBar() {
        genericExecutor.waitForOneSecond();
        if (!genericExecutor.isElementsDisplayedAtXpath(DOWN_ARROW_LOCATOR))
            return false;

        //Step: Click on down arrow button in Navigation strip
        genericExecutor.waitForOneSecond();
        genericExecutor.clickOnElement(DOWN_ARROW_LOCATOR);

        genericExecutor.waitForOneSecond();
        return genericExecutor.isElementsDisplayedAtXpath(ME_NAV_ITEM_LOCATOR, BRA_LOVE_NAV_ITEM_LOCATOR, HOME_NAV_ITEM_LOCATOR, SHOP_NAV_ITEM_LOCATOR);
    }

    protected boolean logout() {
        if (navigateToMePage()) {

            genericExecutor.waitForOneSecond();
            genericExecutor.performScrollToBottom(2);

            genericExecutor.waitForOneSecond();
            genericExecutor.clickOnElement(genericExecutor.findElementContainsLabel(LOG_OUT_LABEL));

            genericExecutor.waitForOneSecond();
            genericExecutor.clickOnElement(genericExecutor.findElementContainsLabel(LOG_OUT_LABEL));

            return isAppLaunch();
        }
        return false;
    }


    protected boolean navigateToHome() {
        boolean status = openNavigationBar();
        if (status) {
            genericExecutor.waitForOneSecond();
            genericExecutor.clickOnElement(HOME_NAV_ITEM_LOCATOR);

            genericExecutor.waitForOneSecond();
            return genericExecutor.isElementsDisplayedAtXpath(DASHBOARD_TITLE_LOCATOR);
        }
        return false;
    }

    protected boolean navigateToBraLove(HashMap<String, String> data) {
        boolean status = loginToAppWithCredentials(data, false);
        return status && navigateToBraLove();
    }

    protected boolean navigateToBraLove() {
        genericExecutor.waitForOneSecond();
        openNavigationBar();

        //Step: Click on "Bra Love" nav element and validate
        genericExecutor.waitForOneSecond();
        genericExecutor.clickOnElement(BRA_LOVE_NAV_ITEM_LOCATOR);

        genericExecutor.waitForOneSecond();
        return genericExecutor.isElementsDisplayedAtXpath(BRA_LOVE_LOCATOR);
    }


    protected boolean navigateToShop(HashMap<String, String> data) {
        boolean status = loginToAppWithCredentials(data, false);
        return status && navigateToShop();
    }

    protected boolean navigateToShop() {
        genericExecutor.waitForOneSecond();
        openNavigationBar();

        //Step: Click on "Bra Love" nav element and validate
        genericExecutor.waitForOneSecond();
        genericExecutor.clickOnElement(SHOP_NAV_ITEM_LOCATOR);

        genericExecutor.waitForOneSecond();
        return genericExecutor.isElementsDisplayedAtXpath(BRA_LOVE_LOCATOR);
    }


    protected boolean navigateToMePage(HashMap<String, String> data) {
        boolean status = loginToAppWithCredentials(data, false);
        return status && navigateToMePage();
    }

    protected boolean navigateToMePage() {
        genericExecutor.waitForOneSecond();
        openNavigationBar();

        //Step: Click on "Me Page" nav element and validate
        genericExecutor.waitForOneSecond();
        genericExecutor.clickOnElement(ME_NAV_ITEM_LOCATOR);

        genericExecutor.waitForOneSecond();
        return genericExecutor.isElementsDisplayedWithLabel(MY_BADGES_LABEL);
    }

    public boolean navigateToPinkViewer(HashMap<String, String> data) {
        boolean status = loginToAppWithCredentials(data, false);
        return status && navigateToPinkViewer();
    }

    public boolean navigateToPinkViewer() {
        genericExecutor.waitForOneSecond();
        openNavigationBar();

        //Step: Click on "Bra Love" nav element and validate
        genericExecutor.waitForOneSecond();
        genericExecutor.clickOnElement(PINK_VIEWER_IN_NAV_LOCATOR);

        genericExecutor.waitForOneSecond();
        return genericExecutor.isElementsDisplayedAtXpath(PINK_VIEWER_TITLE_LOCATOR);
    }

    /**
     * Method to create account
     *
     * @param data
     * @return
     */
    protected boolean createAccountWithData(HashMap<String, String> data) {
        if (data == null || data.isEmpty())
            return false;
        return createAccountWithData(CommonUtils.generateRandomEmailId(), data);
    }

    /**
     * Method to create account
     *
     * @param email
     * @param data
     * @return
     */
    protected boolean createAccountWithData(String email, HashMap<String, String> data) {
        if (data == null || data.isEmpty())
            return false;
        return createAccountWithData(email,
                data.get(CREATE_PASSWORD_ATTRIBUTE), data.get(FIRST_NAME_ATTRIBUTE), data.get(LAST_NAME_ATTRIBUTE), data.get(ADDRESS_ATTRIBUTE),
                data.get(CITY_ATTRIBUTE), data.get(STATE_ATTRIBUTE), data.get(ZIPCODE_ATTRIBUTE), data.get(BIRTHDAY_ATTRIBUTE), data.get(COLLEGE_ATTRIBUTE), data.get(GRAD_YEAR_ATTRIBUTE),
                data.get(MOBILE_ATTRIBUTE));
    }

    /**
     * Method to create account
     *
     * @param email
     * @param password
     * @param firstName
     * @param lastName
     * @param address
     * @param city
     * @param state
     * @param zipCode
     * @param birthday
     * @param college
     * @param gradYear
     * @param mobile
     * @return
     */

    protected boolean createAccountWithData(String email, String password, String firstName,
                                            String lastName, String address, String city, String state,
                                            String zipCode, String birthday, String college, String gradYear, String mobile) {
        if (CommonUtils.isEmpty(state)) {
            state = STATE_ALABAMA_LABEL;
        }

        if (isAppLaunch()) {
            if (!navigateToJoin2of2Screen(email))
                return false;

            genericExecutor.waitForOneSecond();
            genericExecutor.setTextInElementsWithLabel(password, CREATE_PASSWORD_LABEL);

            genericExecutor.waitForOneSecond();
            genericExecutor.setTextInElement(firstName, genericExecutor.performBottomScrollAndFindElementWithLabel(FIRST_NAME_LABEL));

            genericExecutor.waitForOneSecond();
            genericExecutor.setTextInElement(lastName, genericExecutor.performBottomScrollAndFindElementWithLabel(LAST_NAME_LABEL));

            genericExecutor.waitForOneSecond();
            genericExecutor.setTextInElement(address, genericExecutor.performBottomScrollAndFindElementWithLabel(ADDRESS_LABEL));

            genericExecutor.waitForOneSecond();
            genericExecutor.setTextInElement(city, genericExecutor.performBottomScrollAndFindElementWithLabel(CITY_LABEL));

            genericExecutor.waitForOneSecond();
            genericExecutor.setTextInElement(zipCode, genericExecutor.performBottomScrollAndFindElementWithLabel(ZIP_CODE_LABEL));

            genericExecutor.waitForSeconds(waitTimeInSeconds);
            if (isErrorOccurred()) return false;

            genericExecutor.waitForOneSecond();
            genericExecutor.clickOnElement(genericExecutor.performBottomScrollAndFindElementWithLabel(STATE_LABEL));

            genericExecutor.waitForOneSecond();
            if (platform.equalsIgnoreCase(Platform.ANDROID)) {
                genericExecutor.clickOnElement("//*[@text='" + state + "' or @value='" + state + "']");
            } else {
                genericExecutor.performScroll(205, 707, 207, 629);
                genericExecutor.waitForOneSecond();
                genericExecutor.clickOnElement("//XCUIElementTypeButton[@name='Select']");
            }

            genericExecutor.waitForOneSecond();
            WebElement birthdayWebElement = genericExecutor.performBottomScrollAndFindElementWithLabel(BIRTHDAY_LABEL);
            if (birthdayWebElement != null && birthdayWebElement.isDisplayed()) {
                genericExecutor.clickOnElement(birthdayWebElement);

                if (platform.equals(Platform.ANDROID)) {
                    genericExecutor.clickOnElement(Locators.OK_BUTTON_LOCATOR);
                } else {
                    genericExecutor.performScroll(299, 639, 298, 697);
                    genericExecutor.waitForOneSecond();
                    genericExecutor.clickOnElement("//XCUIElementTypeButton[@name='Select']");
                }
            }

            if (CommonUtils.isNotEmpty(college)) {
                genericExecutor.waitForOneSecond();
                WebElement collegeWebElement = genericExecutor.performBottomScrollAndFindElementWithLabel(COLLEGE_LABEL);
                if (collegeWebElement != null && collegeWebElement.isDisplayed()) {
                    genericExecutor.clickOnElement(collegeWebElement);

                    genericExecutor.waitForOneSecond();
                    genericExecutor.clickOnElement(genericExecutor.performBottomScrollAndFindElementWithLabel(COLLEGE_LABEL));

                    genericExecutor.waitForSeconds(waitTimeInSeconds);
                    genericExecutor.setTextInElementsWithLabel(college, COLLEGE_LABEL);

                    genericExecutor.waitForOneSecond();
                    genericExecutor.isElementsDisplayedWithLabel(SUGGESTIONS_LABEL);

                    genericExecutor.waitForSeconds(waitTimeInSeconds);
                    genericExecutor.clickOnElement(FIRST_COLLEGE_ITEM_LOCATOR);
                }
                genericExecutor.waitForSeconds(waitTimeInSeconds);
            }

            WebElement gradYearWebElement = genericExecutor.performBottomScrollAndFindElementWithLabel(GRAD_YEAR_LABEL);
            if (gradYearWebElement != null && gradYearWebElement.isDisplayed()) {
                genericExecutor.clickOnElement(gradYearWebElement);

                if (platform.equals(Platform.ANDROID)) {
                    genericExecutor.clickOnElement(Locators.OK_BUTTON_LOCATOR);
                } else {
                    genericExecutor.performScroll(299, 639, 298, 697);
                    genericExecutor.waitForOneSecond();
                    genericExecutor.clickOnElement("//XCUIElementTypeButton[@name='Select']");
                }
            }

            refocus();

            if (CommonUtils.isNotEmpty(mobile)) {
                genericExecutor.waitForOneSecond();
                genericExecutor.setTextInElement(mobile, genericExecutor.performBottomScrollAndFindElementWithLabel(MOBILE_LABEL));
            }

            genericExecutor.waitForOneSecond();
            genericExecutor.clickOnElement(genericExecutor.performBottomScrollAndFindElementWithLabel("SUBMIT"));

            genericExecutor.waitForSeconds(waitTimeInSeconds);

            // Check if error dialog is displayed
            if (isErrorOccurred()) return false;

            genericExecutor.waitForOneSecond();
            return genericExecutor.isElementsDisplayedContainsLabel(BADGE_LABEL);
        } else {
            return false;
        }
    }

    protected boolean isErrorOccurred() {
        genericExecutor.waitForOneSecond();
        System.out.println("Validating error scenarios!");

        // Check if error dialog is displayed
        WebElement errorElement = genericExecutor.findElementByXpath(1, genericExecutor.getElementXpathContainsLabel("Oops"));
        if (errorElement != null && errorElement.isDisplayed()) {
            genericExecutor.extentReporter.trackReport(true, CommonUtils.isNotEmpty(genericExecutor.getElementText(errorElement)) ? genericExecutor.getElementText(errorElement) : "Error dialog displayed!");
            return true;
        }

        // Check if email is not valid
        errorElement = genericExecutor.findElementByXpath(1, genericExecutor.getElementXpathContainsLabel("Invalid"));
        if (errorElement != null && errorElement.isDisplayed()) {
            genericExecutor.extentReporter.trackReport(true, CommonUtils.isNotEmpty(genericExecutor.getElementText(errorElement)) ? genericExecutor.getElementText(errorElement) : "Error message displayed with text 'Invalid'");
            return true;
        }

        // Check if password is not valid
        errorElement = genericExecutor.findElementByXpath(1, genericExecutor.getElementXpathContainsLabel("Please enter"));
        if (errorElement != null && errorElement.isDisplayed()) {
            genericExecutor.extentReporter.trackReport(true, CommonUtils.isNotEmpty(genericExecutor.getElementText(errorElement)) ? genericExecutor.getElementText(errorElement) : "Error message displayed with text 'Please enter'");
            return true;
        }

        return false;
    }

    private void refocus() {
        if (!platform.equals(Platform.ANDROID))
            return;
        genericExecutor.waitForOneSecond();
        genericExecutor.openNotifications();

        genericExecutor.waitForOneSecond();
        genericExecutor.goBack();

        genericExecutor.waitForOneSecond();
    }

    protected boolean navigateToJoin2of2Screen(String emailAddress) {
        genericExecutor.clickOnElement(Locators.JOIN_PINK_NATION_BTN_LOCATOR);
        genericExecutor.setTextInElementsContainsLabel(emailAddress, EMAIL_LABEL);
        genericExecutor.clickOnElement(Locators.CONTINUE_BUTTON_LOCATOR);

        genericExecutor.waitForSeconds(waitTimeInSeconds);
        // Check if error dialog is displayed
        if (isErrorOccurred()) return false;

        return genericExecutor.isElementsDisplayedAtXpath(JOIN_PINK_NATION_2_OF_2_NAV_BAR_LABEL_LOCATOR);
    }

    /**
     * Method to launch and validate app landing
     *
     * @return boolean
     */
    protected boolean isAppLaunch() {
        genericExecutor.waitForOneSecond();

        if (platform.equals(Platform.ANDROID)) {
            WebElement upgradeElement = genericExecutor.findElementWithLabel(UPGRADE_BUTTON_LABEL);
            WebElement notNowElement = genericExecutor.findElementWithLabel(NOT_NOW_BUTTON_LABEL);
            if (upgradeElement != null && upgradeElement.isDisplayed() && notNowElement != null && notNowElement.isDisplayed()) {
                genericExecutor.clickOnElementWithLabel(NOT_NOW_BUTTON_LABEL);
            }
        }

        return genericExecutor.validateAppLaunchWithElements(MEMBER_SIGN_IN_BTN_LOCATOR, SHOP_PINK_NOW_BTN_LOCATOR, JOIN_PINK_NATION_BTN_LOCATOR);
    }

}
