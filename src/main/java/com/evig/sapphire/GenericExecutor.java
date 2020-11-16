package com.evig.sapphire;

import com.codeborne.selenide.WebDriverRunner;
import com.evig.sapphire.constants.Platform;
import com.evig.sapphire.provider.DriverProvider;
import com.evig.sapphire.utils.CommonUtils;
import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.management.timer.Timer;
import java.net.MalformedURLException;

/**
 * This class is used to execute all generic actions on WebElement
 * <p>
 * Created by ksoni on 01/05/18.
 */
public class GenericExecutor extends ElementInspector {
    public ExtentReporter extentReporter;

    public GenericExecutor(DriverProvider driverProvider) throws MalformedURLException {
        super(driverProvider, driverProvider.capabilitiesBuilder.platform);
        extentReporter = new ExtentReporter(dtextriver, driverProvider.capabilitiesBuilder);
        WebDriverRunner.setWebDriver(driver);
    }

    /**
     * Method to validate if app launch is successful with expected elements
     *
     * @param xPaths
     * @return
     */
    public boolean validateAppLaunchWithElements(By... xPaths) {
        return validateAppLaunchWithElements(defaultWaitTimeInSeconds, xPaths);
    }

    /**
     * Method to validate if app launch is successful with expected elements
     *
     * @param waitTimeInSeconds
     * @param xPaths
     * @return
     */
    public boolean validateAppLaunchWithElements(int waitTimeInSeconds, By... xPaths) {
        boolean status = true;
        if (isElementsDisplayedAtXpath(waitTimeInSeconds, xPaths)) {
            extentReporter.trackReport(LogStatus.PASS, "Application launch successful");
        } else {
            status = false;
            extentReporter.trackReport(LogStatus.FAIL, "Application launch is unsuccessful. All expected elements not visible");
        }
        return status;
    }

    /**
     * Method to validate if app launch is successful with expected elements
     *
     * @param locators
     * @return
     */
    public boolean validateAppLaunchWithElements(String... locators) {
        return validateAppLaunchWithElements(defaultWaitTimeInSeconds, locators);
    }

    /**
     * Method to validate if app launch is successful with expected elements
     *
     * @param waitTimeInSeconds
     * @param locators
     * @return
     */
    public boolean validateAppLaunchWithElements(int waitTimeInSeconds, String... locators) {
        boolean status = true;
        if (isElementsDisplayedAtXpath(waitTimeInSeconds, locators)) {
            extentReporter.trackReport(LogStatus.PASS, "Application launch successful");
        } else {
            status = false;
            extentReporter.trackReport(LogStatus.FAIL, "Application launch is unsuccessful. All expected elements not visible");
        }
        return status;
    }

    /**
     * Method to validate if element displayed contains label
     *
     * @param elementsLabel
     * @return boolean
     */
    public boolean isElementsDisplayedContainsLabel(String... elementsLabel) {
        return isElementsDisplayedContainsLabel(true, elementsLabel);
    }

    /**
     * Method to validate if element displayed contains label
     *
     * @param elementsLabel
     * @return boolean
     */
    public boolean isElementsDisplayedContainsLabel(boolean ignoreCase, String... elementsLabel) {
        boolean status = true;
        for (String elementLabel : elementsLabel) {
            By xPath = getElementXpathContainsLabel(elementLabel, ignoreCase);
            if (!isElementsDisplayedAtXpath(xPath)) {
                status = false;
            }
        }
        return status;
    }

    /**
     * Method to validate if element displayed with label
     *
     * @param elementsLabel
     * @return boolean
     */
    public boolean isElementsDisplayedWithLabel(String... elementsLabel) {
        return isElementsDisplayedWithLabel(true, elementsLabel);
    }

    /**
     * Method to validate if element displayed with label
     *
     * @param elementsLabel
     * @param ignoreCase
     * @return boolean
     */
    public boolean isElementsDisplayedWithLabel(boolean ignoreCase, String... elementsLabel) {
        boolean status = true;
        for (String elementLabel : elementsLabel) {
            By xPath = getElementXpathWithLabel(elementLabel, ignoreCase);
            if (!isElementsDisplayedAtXpath(xPath)) {
                status = false;
            }
        }
        return status;
    }

    /**
     * Method to validate if element is displayed on UI or not
     *
     * @param locators
     * @return
     */
    public boolean isElementsDisplayedAtXpath(String... locators) {
        return isElementsDisplayedAtXpath(defaultWaitTimeInSeconds, locators);
    }

    /**
     * Method to validate if element is displayed on UI or not
     *
     * @param waitTimeInSeconds
     * @param locators
     * @return
     */
    public boolean isElementsDisplayedAtXpath(int waitTimeInSeconds, String... locators) {
        boolean status = true;
        for (String locator : locators) {
            if (!isElementsDisplayedAtXpath(waitTimeInSeconds, getXpathByLocator(locator))) {
                status = false;
            }
        }
        return status;
    }


    /**
     * Method to validate if element is displayed on UI or not
     *
     * @param xPaths
     * @return
     */
    public boolean isElementsDisplayedAtXpath(By... xPaths) {
        return isElementsDisplayedAtXpath(defaultWaitTimeInSeconds, xPaths);
    }

    /**
     * Method to validate if element is displayed on UI or not
     *
     * @param waitTimeInSeconds
     * @param xPaths
     * @return
     */
    public boolean isElementsDisplayedAtXpath(int waitTimeInSeconds, By... xPaths) {
        boolean status = true;
        for (By xPath : xPaths) {
            waitForSeconds(waitTimeInSeconds);
            WebElement webElement = findElementByXpath(waitTimeInSeconds, xPath);
            if (webElement == null) {
                status = false;
                extentReporter.trackReport(LogStatus.FAIL, "Element not available at xPath: " + xPath);
            } else if (webElement.isDisplayed()) {
                extentReporter.trackReport(LogStatus.PASS, "Element is visible at xPath:" + xPath);
            } else {
                extentReporter.trackReport(LogStatus.FAIL, "Element is not visible at xPath:" + xPath);
                status = false;
            }
        }
        return status;
    }


    /**
     * Method to validate if element is displayed on UI or not
     *
     * @param locators
     * @return
     */
    public boolean isElementsNotDisplayedAtXpath(String... locators) {
        return isElementsNotDisplayedAtXpath(defaultWaitTimeInSeconds, locators);
    }

    /**
     * Method to validate if element is displayed on UI or not
     *
     * @param waitTimeInSeconds
     * @param locators
     * @return
     */
    public boolean isElementsNotDisplayedAtXpath(int waitTimeInSeconds, String... locators) {
        boolean status = true;
        for (String locator : locators) {
            if (!isElementsNotDisplayedAtXpath(waitTimeInSeconds, getXpathByLocator(locator))) {
                status = false;
            }
        }
        return status;
    }

    /**
     * Method to validate if element is displayed on UI or not
     *
     * @param xPaths
     * @return
     */
    public boolean isElementsNotDisplayedAtXpath(By... xPaths) {
        return isElementsNotDisplayedAtXpath(defaultWaitTimeInSeconds, xPaths);

    }

    /**
     * Method to validate if element is displayed on UI or not
     *
     * @param waitTimeInSeconds
     * @param xPaths
     * @return
     */
    public boolean isElementsNotDisplayedAtXpath(int waitTimeInSeconds, By... xPaths) {
        boolean status = true;
        for (By xPath : xPaths) {
            waitForSeconds(waitTimeInSeconds);
            WebElement webElement = findElementByXpath(xPath);
            if (webElement == null) {
                extentReporter.trackReport(LogStatus.PASS, "Element not available at xPath: " + xPath.toString());
            } else if (webElement.isDisplayed()) {
                extentReporter.trackReport(LogStatus.FAIL, "Element is visible at xPath:" + xPath.toString());
                status = false;
            } else {
                extentReporter.trackReport(LogStatus.PASS, "Element is not visible at xPath:" + xPath.toString());
            }
        }
        return status;
    }

    /**
     * Method to validate if text/label of element is as expected or not
     */
    public boolean validateElementText(String expectedString, String... locators) {
        if (expectedString == null)
            expectedString = "";
        boolean status = true;
        for (String locator : locators) {
            waitForOneSecond();
            if (!validateElementText(expectedString, findElementByXpath(locator)))
                status = false;
        }
        return status;
    }

    /**
     * Method to validate if text/label of element is as expected or not
     */
    public boolean validateElementText(String expectedString, By... xPaths) {
        if (expectedString == null)
            expectedString = "";
        boolean status = true;
        for (By xPath : xPaths) {
            waitForOneSecond();
            if (!validateElementText(expectedString, findElementByXpath(xPath)))
                status = false;
        }
        return status;
    }

    /**
     * Method to validate if text/label of element is as expected or not
     */
    public boolean validateElementText(String expectedText, WebElement webElement) {
        if (webElement == null) {
            extentReporter.trackReport(LogStatus.FAIL, "Can't perform validateElementText for expectedText(" + expectedText + "). Required element is null.");
            return false;
        } else {
            String actualString = getElementText(webElement);
            if (CommonUtils.isEmpty(actualString) || !actualString.equals(expectedText)) {
                extentReporter.trackReport(LogStatus.FAIL, "ExpectedText (" + expectedText + ") and ActualText (" + actualString + ") of element is not same.");
                return false;
            } else {
                extentReporter.trackReport(LogStatus.PASS, "ExpectedText (" + expectedText + ") and ActualText (" + actualString + ") of element is same as expected.");
                return true;
            }
        }
    }

    /**
     * Method to set text in any InputFieldElement
     *
     * @param inputText
     * @param elementsLabel
     */
    public void setTextInElementsWithLabel(String inputText, String... elementsLabel) {
        setTextInElementsWithLabel(defaultWaitTimeInSeconds, inputText, false, elementsLabel);
    }

    /**
     * Method to set text in any InputFieldElement
     *
     * @param inputText
     * @param elementsLabel
     */
    public void setTextInElementsWithLabel(String inputText, boolean ignoreCase, String... elementsLabel) {
        setTextInElementsWithLabel(defaultWaitTimeInSeconds, inputText, ignoreCase, elementsLabel);
    }

    /**
     * Method to set text in any InputFieldElement
     *
     * @param inputText
     * @param elementsLabel
     */
    public void setTextInElementsWithLabel(int waitTimeInSeconds, String inputText, String... elementsLabel) {
        setTextInElementsWithLabel(waitTimeInSeconds, inputText, false, elementsLabel);
    }

    /**
     * Method to set text in any InputFieldElement
     *
     * @param inputText
     * @param elementsLabel
     */
    public void setTextInElementsWithLabel(int waitTimeInSeconds, String inputText, boolean ignoreCase, String... elementsLabel) {
        for (String elementLabel : elementsLabel) {
            setTextInElementsAtXpath(waitTimeInSeconds, inputText, getElementXpathWithLabel(elementLabel, ignoreCase));
        }
    }

    /**
     * Method to set text in any InputFieldElement
     *
     * @param inputText
     * @param elementsLabel
     */
    public void setTextInElementsContainsLabel(String inputText, String... elementsLabel) {
        setTextInElementsContainsLabel(defaultWaitTimeInSeconds, inputText, false, elementsLabel);
    }

    /**
     * Method to set text in any InputFieldElement
     *
     * @param inputText
     * @param elementsLabel
     */
    public void setTextInElementsContainsLabel(String inputText, boolean ignoreCase, String... elementsLabel) {
        setTextInElementsContainsLabel(defaultWaitTimeInSeconds, inputText, ignoreCase, elementsLabel);
    }

    /**
     * Method to set text in any InputFieldElement
     *
     * @param inputText
     * @param elementsLabel
     */
    public void setTextInElementsContainsLabel(int waitTimeInSeconds, String inputText, String... elementsLabel) {
        setTextInElementsContainsLabel(waitTimeInSeconds, inputText, false, elementsLabel);
    }

    /**
     * Method to set text in any InputFieldElement
     *
     * @param inputText
     * @param elementsLabel
     */
    public void setTextInElementsContainsLabel(int waitTimeInSeconds, String inputText, boolean ignoreCase, String... elementsLabel) {
        for (String elementLabel : elementsLabel) {
            setTextInElementsAtXpath(waitTimeInSeconds, inputText, getElementXpathContainsLabel(elementLabel, ignoreCase));
        }
    }

    /**
     * Method to set text in any InputFieldElement
     *
     * @param inputText
     * @param locators
     */
    public void setTextInElementsAtXpath(String inputText, String... locators) {
        setTextInElementsAtXpath(defaultWaitTimeInSeconds, inputText, locators);
    }

    /**
     * Method to set text in any InputFieldElement
     *
     * @param waitTimeInSeconds
     * @param inputText
     * @param locators
     */
    public void setTextInElementsAtXpath(int waitTimeInSeconds, String inputText, String... locators) {
        for (String locator : locators) {
            setTextInElementsAtXpath(waitTimeInSeconds, inputText, getXpathByLocator(locator));
        }
    }

    /**
     * Method to set text in any InputFieldElement
     *
     * @param inputText
     */
    public void setTextInElementsAtXpath(String inputText, By... xPaths) {
        setTextInElementsAtXpath(defaultWaitTimeInSeconds, inputText, xPaths);
    }

    /**
     * Method to set text in any InputFieldElement
     *
     * @param waitTimeInSeconds
     * @param inputText
     */
    public void setTextInElementsAtXpath(int waitTimeInSeconds, String inputText, By... xPaths) {
        if (inputText == null)
            inputText = "";
        for (By xPath : xPaths) {
            waitForOneSecond();
            setTextInElement(inputText, findElementByXpath(waitTimeInSeconds, xPath));
        }
    }

    /**
     * Method to set text in any InputFieldElement
     *
     * @param webElements
     * @param inputText
     * @param webElements
     */
    public boolean setTextInElement(String inputText, WebElement... webElements) {
        if (webElements == null) {
            extentReporter.trackReport(LogStatus.FAIL, "Can't perform setTextInElement for inputText('" + inputText + "'). Required element is null.");
            return false;
        }
        boolean status = true;
        for (WebElement webElement : webElements) {
            waitForOneSecond();
            if (webElement == null) {
                extentReporter.trackReport(LogStatus.FAIL, "Can't perform setTextInElement for inputText('" + inputText + "'). Required element is null.");
                status = false;
            } else if (webElement.isDisplayed()) {
                String elementLabel = getElementText(webElement);
                webElement.click();

                waitForMilliSeconds(200);
                webElement.clear();

                waitForMilliSeconds(200);
                webElement.sendKeys(inputText);
                hideKeyboard();
                extentReporter.trackReport(LogStatus.PASS, "InputText ('" + inputText + "') set to element" + (CommonUtils.isEmpty(elementLabel) ? "!" : " with label '" + elementLabel + "'"));
            } else {
                status = false;
                extentReporter.trackReport(LogStatus.FAIL, "Can't perform setTextInElement for inputText('" + inputText + "'). Required element is not visible.");
            }
        }
        return status;
    }

    private void hideKeyboard() {
        if (platform.equals(Platform.ANDROID))
            return;
        final String RETURN_BUTTON = "//*[@name='Return']";
        WebElement returnElement = findElementByXpath(RETURN_BUTTON, true);
        if (returnElement != null && returnElement.isDisplayed())
            returnElement.click();
    }

    /**
     * Method to clear the text of any InputFieldElement
     *
     * @param locators
     */
    public void clearTextOfInputFieldElements(String... locators) {
        clearTextOfInputFieldElements(defaultWaitTimeInSeconds, locators);
    }

    /**
     * Method to clear the text of any InputFieldElement
     *
     * @param waitTimeInSeconds
     * @param locators
     */
    public void clearTextOfInputFieldElements(int waitTimeInSeconds, String... locators) {
        for (String locator : locators) {
            clearTextOfInputFieldElements(waitTimeInSeconds, getXpathByLocator(locator));
        }
    }

    /**
     * Method to clear the text of any InputFieldElement
     *
     * @param xPaths
     */
    public void clearTextOfInputFieldElements(By... xPaths) {
        clearTextOfInputFieldElements(defaultWaitTimeInSeconds, xPaths);
    }

    /**
     * Method to clear the text of any InputFieldElement
     *
     * @param waitTimeInSeconds
     * @param xPaths
     */
    public void clearTextOfInputFieldElements(int waitTimeInSeconds, By... xPaths) {
        for (By xPath : xPaths) {
            waitForOneSecond();
            clearElementText(findElementByXpath(waitTimeInSeconds, xPath));
        }
    }

    /**
     * Method to clear the text of any InputFieldElement
     *
     * @param webElements
     */
    public void clearElementText(WebElement... webElements) {
        if (webElements == null) {
            extentReporter.trackReport(LogStatus.FAIL, "Can't perform clearElementText. Required element is null!");
            return;
        }
        for (WebElement webElement : webElements) {
            waitForOneSecond();
            if (webElement == null) {
                extentReporter.trackReport(LogStatus.FAIL, "Can't perform clearElementText. Required element is null!");
                return;
            }
            if (webElement.isDisplayed()) {
                String elementLabel = getElementText(webElement);

                webElement.click();

                waitForMilliSeconds(200);
                webElement.clear();

                hideKeyboard();
                extentReporter.trackReport(LogStatus.PASS, "Performed clearElementText on element" + (CommonUtils.isEmpty(elementLabel) ? "!" : " with label '" + elementLabel + "'"));
            } else
                extentReporter.trackReport(LogStatus.FAIL, "Can't perform clearElementText. Required element is not visible!");
        }
    }

    /**
     * Method to click on any element on screen
     *
     * @param locators
     */
    public boolean clickOnElement(String... locators) {
        return clickOnElement(defaultWaitTimeInSeconds, locators);
    }

    /**
     * Method to click on any element on screen
     *
     * @param elementsLabel
     */
    public boolean clickOnElementWithLabel(String... elementsLabel) {
        boolean status = true;
        for (String elementLabel : elementsLabel) {
            if (!clickOnElement(getElementXpathWithLabel(elementLabel))) {
                status = false;
            }
        }
        return status;
    }


    /**
     * Method to click on any element on screen
     *
     * @param elementsLabel
     */
    public boolean clickOnElementContainsLabel(String... elementsLabel) {
        boolean status = true;
        for (String elementLabel : elementsLabel) {
            if (!clickOnElement(getElementXpathContainsLabel(elementLabel))) {
                status = false;
            }
        }
        return status;
    }

    /**
     * Method to click on any element on screen
     *
     * @param waitTimeInSeconds
     * @param locators
     */
    public boolean clickOnElement(int waitTimeInSeconds, String... locators) {
        boolean status = true;
        for (String locator : locators) {
            if (!clickOnElement(waitTimeInSeconds, getXpathByLocator(locator))) {
                status = false;
            }
        }
        return status;
    }

    /**
     * Method to click on any element on screen
     *
     * @param xPaths
     */
    public boolean clickOnElement(By... xPaths) {
        return clickOnElement(defaultWaitTimeInSeconds, xPaths);
    }

    /**
     * Method to click on any element on screen
     *
     * @param waitTimeInSeconds
     * @param xPaths
     */
    public boolean clickOnElement(int waitTimeInSeconds, By... xPaths) {
        if (xPaths == null)
            return false;
        boolean status = true;
        for (By by : xPaths) {
            if (!clickOnElement(findElementByXpath(waitTimeInSeconds, by, true))) {
                status = false;
            }
        }
        return status;
    }

    /**
     * Method to click on any element on screen
     *
     * @param webElements
     */
    public boolean clickOnElement(WebElement... webElements) {
        return clickOnElement(defaultWaitTimeInSeconds, webElements);
    }

    /**
     * Method to click on any element on screen
     *
     * @param waitTimeInSeconds
     * @param webElements
     */
    public boolean clickOnElement(int waitTimeInSeconds, WebElement... webElements) {
        if (webElements == null) {
            extentReporter.trackReport(LogStatus.FAIL, "Can't perform clickOnElement. Required element is null!");
            return false;
        }
        boolean status = true;
        for (WebElement webElement : webElements) {
            waitForSeconds(waitTimeInSeconds);
            if (webElement == null) {
                extentReporter.trackReport(LogStatus.FAIL, "Can't perform clickOnElement. Required element is null!");
                status = false;
                continue;
            }
            if (webElement.isDisplayed()) {
                String elementLabel = getElementText(webElement);
                webElement.click();
                extentReporter.trackReport(LogStatus.PASS, "Click performed on element" + (CommonUtils.isEmpty(elementLabel) ? "!" : " with label '" + elementLabel + "'"));
            } else {
                status = false;
                extentReporter.trackReport(LogStatus.FAIL, "Can't perform clickOnElement. Required element is not visible!");
            }
        }
        return status;
    }


    /**
     * Method to perform Scroll on screen
     *
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     */
    public void performScroll(int fromX, int fromY, int toX, int toY) {
        extentReporter.trackReport(true, "Performing scroll from (" + fromX + "," + fromY + ") to (" + toX + "," + toY + ").");
        TouchAction action = new TouchAction((MobileDriver<WebElement>) driver);
        if (platform.equals(Platform.ANDROID))
            action.press(fromX, fromY).waitAction((int) Timer.ONE_SECOND).moveTo(toX, toY).release().perform();
        else
            action.press(fromX, fromY).moveTo(toX, toY).release().perform();
    }

    /**
     * Method to perform Scroll to bottom on screen
     */
    public void performScrollToBottom() {
        performScrollToBottom(3);
    }


    /**
     * Method to perform Scroll to bottom on screen
     *
     * @param pages
     */
    public void performScrollToBottom(int pages) {
        int x = getScreenWidth() / 2;
        performScrollToBottom(x, pages);
    }

    /**
     * Method to perform Scroll to bottom on screen
     *
     * @param pages
     */
    public void performScrollToBottom(int x, int pages) {
        int startPosition = (int) (getScreenHeight() * .9);
        int endPosition = (int) (getScreenHeight() * .5);
        for (int i = 0; i < pages; i++) {
            waitForOneSecond();
            performScroll(x, startPosition, x, endPosition);
        }
    }

    /**
     * Method to perform Scroll to top on screen
     */
    public void performScrollToTop() {
        performScrollToTop(3);
    }

    /**
     * Method to perform Scroll to top on screen
     *
     * @param pages
     */
    public void performScrollToTop(int pages) {
        int x = getScreenWidth() / 2;
        performScrollToTop(x, pages);
    }

    /**
     * Method to perform Scroll to top on screen
     *
     * @param pages
     */
    public void performScrollToTop(int x, int pages) {
        int startPosition = (int) (getScreenHeight() * .1);
        int endPosition = (int) (getScreenHeight() * .5);
        for (int i = 0; i < pages; i++) {
            performScroll(x, startPosition, x, endPosition);
            waitForOneSecond();
        }
    }

    /**
     * Method to perform Scroll to top on screen
     */
    public void performScrollLeftToRight() {
        performScrollLeftToRight(defaultPageScroll);
    }

    /**
     * Method to perform Scroll to top on screen
     *
     * @param pages
     */
    public void performScrollLeftToRight(int pages) {
        int y = (int) (getScreenHeight() * .2);
        performScrollLeftToRight(y, pages);
    }

    /**
     * Method to perform Scroll to top on screen
     *
     * @param pages
     */
    public void performScrollLeftToRight(int y, int pages) {
        int startPosition = (int) (getScreenWidth() * .1);
        int endPosition = (int) (getScreenWidth() * .9);
        for (int i = 0; i < pages; i++) {
            performScroll(startPosition, y, endPosition, y);
            waitForOneSecond();
        }
    }

    /**
     * Method to perform Scroll to top on screen
     */
    public void performScrollRightToLeft() {
        performScrollLeftToRight(defaultPageScroll);
    }

    /**
     * Method to perform Scroll to top on screen
     *
     * @param pages
     */
    public void performScrollRightToLeft(int pages) {
        int y = (int) (getScreenHeight() * .2);
        performScrollRightToLeft(y, pages);
    }


    /**
     * Method to perform Scroll to top on screen
     *
     * @param pages
     */
    public void performScrollRightToLeft(int y, int pages) {
        int startPosition = (int) (getScreenWidth() * .9);
        int endPosition = (int) (getScreenWidth() * .1);
        for (int i = 0; i < pages; i++) {
            performScroll(startPosition, y, endPosition, y);
            waitForOneSecond();
        }
    }

    /**
     * Method to open Android Notification Bar
     */
    public void openNotifications() {
        if (platform.equals(Platform.ANDROID)) {
            AndroidDriver<AndroidElement> androidDriver = (AndroidDriver<AndroidElement>) driver;
            androidDriver.openNotifications();
        }
    }


    /**
     * Method to perform bottom scroll and find element
     */
    public WebElement performBottomScrollAndFindElementWithLabel(String elementLabel) {
        return performBottomScrollAndFindElementAtXpath(defaultPageScroll, getElementXpathWithLabel(elementLabel, false));
    }

    /**
     * Method to perform bottom scroll and find element
     */
    public WebElement performBottomScrollAndFindElementWithLabel(String elementLabel, boolean ignoreCase) {
        return performBottomScrollAndFindElementAtXpath(defaultPageScroll, getElementXpathWithLabel(elementLabel, ignoreCase));
    }

    /**
     * Method to perform bottom scroll and find element
     */
    public WebElement performBottomScrollAndFindElementWithLabel(int pages, String elementLabel) {
        return performBottomScrollAndFindElementAtXpath(pages, getElementXpathWithLabel(elementLabel, false));
    }

    /**
     * Method to perform bottom scroll and find element
     */
    public WebElement performBottomScrollAndFindElementWithLabel(int pages, String elementLabel, boolean ignoreCase) {
        return performBottomScrollAndFindElementAtXpath(pages, getElementXpathWithLabel(elementLabel, ignoreCase));
    }

    /**
     * Method to perform bottom scroll and find element
     */
    public WebElement performBottomScrollAndFindElementContainLabel(String elementLabel) {
        return performBottomScrollAndFindElementAtXpath(defaultPageScroll, getElementXpathContainsLabel(elementLabel, false));
    }

    /**
     * Method to perform bottom scroll and find element
     */
    public WebElement performBottomScrollAndFindElementContainLabel(String elementLabel, boolean ignoreCase) {
        return performBottomScrollAndFindElementAtXpath(defaultPageScroll, getElementXpathContainsLabel(elementLabel, ignoreCase));
    }

    /**
     * Method to perform bottom scroll and find element
     */
    public WebElement performBottomScrollAndFindElementContainLabel(int pages, String elementLabel) {
        return performBottomScrollAndFindElementAtXpath(pages, getElementXpathContainsLabel(elementLabel, false));
    }

    /**
     * Method to perform bottom scroll and find element
     */
    public WebElement performBottomScrollAndFindElementContainLabel(int pages, String elementLabel, boolean ignoreCase) {
        return performBottomScrollAndFindElementAtXpath(pages, getElementXpathContainsLabel(elementLabel, ignoreCase));
    }

    /**
     * Method to perform bottom scroll and find element
     */
    public WebElement performBottomScrollAndFindElementAtXpath(String locator) {
        return performBottomScrollAndFindElementAtXpath(defaultPageScroll, getXpathByLocator(locator));
    }

    /**
     * Method to perform bottom scroll and find element
     */
    public WebElement performBottomScrollAndFindElementAtXpath(int pages, String locator) {
        return performBottomScrollAndFindElementAtXpath(pages, getXpathByLocator(locator));
    }

    /**
     * Method to perform bottom scroll and find element
     */
    public WebElement performBottomScrollAndFindElementAtXpath(By xpath) {
        return performBottomScrollAndFindElementAtXpath(defaultPageScroll, xpath);
    }

    /**
     * Method to perform bottom scroll and find element
     */
    public WebElement performBottomScrollAndFindElementAtXpath(int pages, By xpath) {
        WebElement webElement = findElementByXpath(xpath);
        if (webElement == null || !webElement.isDisplayed()) {
            for (int i = 0; i <= pages; i++) {
                performScrollToBottom(1);
                waitForOneSecond();
                webElement = findElementByXpath(xpath);
                if (webElement != null && webElement.isDisplayed())
                    return webElement;
            }
        }
        return webElement;
    }

    /**
     * Method to perform bottom scroll and find element
     */
    public WebElement performTopScrollAndFindElementWithLabel(String elementLabel) {
        return performTopScrollAndFindElementAtXpath(defaultPageScroll, getElementXpathWithLabel(elementLabel, false));
    }

    /**
     * Method to perform bottom scroll and find element
     */
    public WebElement performTopScrollAndFindElementWithLabel(String elementLabel, boolean ignoreCase) {
        return performTopScrollAndFindElementAtXpath(defaultPageScroll, getElementXpathWithLabel(elementLabel, ignoreCase));
    }

    /**
     * Method to perform bottom scroll and find element
     */
    public WebElement performTopScrollAndFindElementWithLabel(int pages, String elementLabel) {
        return performTopScrollAndFindElementAtXpath(pages, getElementXpathWithLabel(elementLabel, false));
    }

    /**
     * Method to perform bottom scroll and find element
     */
    public WebElement performTopScrollAndFindElementWithLabel(int pages, String elementLabel, boolean ignoreCase) {
        return performTopScrollAndFindElementAtXpath(pages, getElementXpathWithLabel(elementLabel, ignoreCase));
    }

    /**
     * Method to perform bottom scroll and find element
     */
    public WebElement performTopScrollAndFindElementContainLabel(String elementLabel) {
        return performTopScrollAndFindElementAtXpath(defaultPageScroll, getElementXpathContainsLabel(elementLabel, false));
    }

    /**
     * Method to perform bottom scroll and find element
     */
    public WebElement performTopScrollAndFindElementContainLabel(String elementLabel, boolean ignoreCase) {
        return performTopScrollAndFindElementAtXpath(defaultPageScroll, getElementXpathContainsLabel(elementLabel, ignoreCase));
    }

    /**
     * Method to perform bottom scroll and find element
     */
    public WebElement performTopScrollAndFindElementContainLabel(int pages, String elementLabel) {
        return performTopScrollAndFindElementAtXpath(pages, getElementXpathContainsLabel(elementLabel, false));
    }

    /**
     * Method to perform bottom scroll and find element
     */
    public WebElement performTopScrollAndFindElementContainLabel(int pages, String elementLabel, boolean ignoreCase) {
        return performTopScrollAndFindElementAtXpath(pages, getElementXpathContainsLabel(elementLabel, ignoreCase));
    }

    /**
     * Method to perform bottom scroll and find element
     */
    public WebElement performTopScrollAndFindElementAtXpath(String locator) {
        return performTopScrollAndFindElementAtXpath(defaultPageScroll, locator);
    }

    /**
     * Method to perform bottom scroll and find element
     */
    public WebElement performTopScrollAndFindElementAtXpath(int pages, String locator) {
        return performTopScrollAndFindElementAtXpath(pages, getXpathByLocator(locator));
    }

    /**
     * Method to perform bottom scroll and find element
     */
    public WebElement performTopScrollAndFindElementAtXpath(By xpath) {
        return performTopScrollAndFindElementAtXpath(defaultPageScroll, xpath);
    }

    /**
     * Method to perform bottom scroll and find element
     */
    public WebElement performTopScrollAndFindElementAtXpath(int pages, By xpath) {
        WebElement webElement = findElementByXpath(xpath);
        if (webElement == null || !webElement.isDisplayed()) {
            for (int i = 0; i <= pages; i++) {
                performScrollToTop(1);
                waitForOneSecond();
                webElement = findElementByXpath(xpath);
                if (webElement != null && webElement.isDisplayed())
                    return webElement;
            }
        }
        return webElement;
    }


    /**
     * Method to perform bottom scroll and find element
     */
    public WebElement performScrollAndFindElementWithLabel(String elementLabel) {
        return performScrollAndFindElementAtXpath(defaultPageScroll, getElementXpathWithLabel(elementLabel, false));
    }

    /**
     * Method to perform bottom scroll and find element
     */
    public WebElement performScrollAndFindElementWithLabel(String elementLabel, boolean ignoreCase) {
        return performScrollAndFindElementAtXpath(defaultPageScroll, getElementXpathWithLabel(elementLabel, ignoreCase));
    }

    /**
     * Method to perform bottom scroll and find element
     */
    public WebElement performScrollAndFindElementWithLabel(int pages, String elementLabel) {
        return performScrollAndFindElementAtXpath(pages, getElementXpathWithLabel(elementLabel, false));
    }

    /**
     * Method to perform bottom scroll and find element
     */
    public WebElement performScrollAndFindElementWithLabel(int pages, String elementLabel, boolean ignoreCase) {
        return performScrollAndFindElementAtXpath(pages, getElementXpathWithLabel(elementLabel, ignoreCase));
    }

    /**
     * Method to perform bottom scroll and find element
     */
    public WebElement performScrollAndFindElementContainLabel(String elementLabel) {
        return performScrollAndFindElementAtXpath(defaultPageScroll, getElementXpathContainsLabel(elementLabel, false));
    }

    /**
     * Method to perform bottom scroll and find element
     */
    public WebElement performScrollAndFindElementContainLabel(String elementLabel, boolean ignoreCase) {
        return performScrollAndFindElementAtXpath(defaultPageScroll, getElementXpathContainsLabel(elementLabel, ignoreCase));
    }

    /**
     * Method to perform bottom scroll and find element
     */
    public WebElement performScrollAndFindElementContainLabel(int pages, String elementLabel) {
        return performScrollAndFindElementAtXpath(pages, getElementXpathContainsLabel(elementLabel, false));
    }

    /**
     * Method to perform bottom scroll and find element
     */
    public WebElement performScrollAndFindElementContainLabel(int pages, String elementLabel, boolean ignoreCase) {
        return performScrollAndFindElementAtXpath(pages, getElementXpathContainsLabel(elementLabel, ignoreCase));
    }

    /**
     * Method to perform scroll and find element
     */
    public WebElement performScrollAndFindElementAtXpath(String locator) {
        return performScrollAndFindElementAtXpath(defaultPageScroll, locator);
    }

    /**
     * Method to perform scroll and find element
     */
    public WebElement performScrollAndFindElementAtXpath(int pages, String locator) {
        return performScrollAndFindElementAtXpath(pages, getXpathByLocator(locator));
    }

    /**
     * Method to perform scroll and find element
     */
    public WebElement performScrollAndFindElementAtXpath(By xpath) {
        return performScrollAndFindElementAtXpath(defaultPageScroll, xpath);
    }

    /**
     * Method to perform scroll and find element
     */
    public WebElement performScrollAndFindElementAtXpath(int pages, By xpath) {
        WebElement webElement = findElementByXpath(xpath);
        if (webElement == null || !webElement.isDisplayed()) {
            webElement = performBottomScrollAndFindElementAtXpath(pages, xpath);
            if (webElement != null && webElement.isDisplayed())
                return webElement;
            performTopScrollAndFindElementAtXpath(pages * 2, xpath);
            if (webElement != null && webElement.isDisplayed())
                return webElement;
            performScrollToBottom(pages);
        }
        return webElement;
    }

    /**
     * Method to go back
     */
    public void goBack() {
        if (platform.equals(Platform.ANDROID))
            driver.navigate().back();
        else {
            final String BACK_BUTTON = "//*[@name='Back' or @name='Close']";
            clickOnElement(BACK_BUTTON);
        }
    }

    /**
     * Method to go forward
     */
    public void goForward() {
        driver.navigate().forward();
    }

    /**
     * Method to refresh
     */
    public void refresh() {
        driver.navigate().refresh();
    }

    /**
     * Method to wait for 1 second
     */
    public void waitForOneSecond() {
        waitForMilliSeconds(Timer.ONE_SECOND);
    }

    /**
     * Method to wait for given number of seconds
     *
     * @param waitTimeInSeconds
     */
    public void waitForSeconds(int waitTimeInSeconds) {
        long milliseconds = waitTimeInSeconds > 1 ? waitTimeInSeconds * Timer.ONE_SECOND : Timer.ONE_SECOND;
        waitForMilliSeconds(milliseconds);
    }

    /**
     * Method to wait for given number of milli seconds
     *
     * @param milliseconds
     */
    public void waitForMilliSeconds(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException: " + e);
        }
    }
}