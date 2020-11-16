package com.evig.sapphire;

import com.evig.sapphire.constants.Platform;
import com.evig.sapphire.constants.UIAttributes;
import com.evig.sapphire.provider.DriverProvider;
import com.evig.sapphire.utils.CommonUtils;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * This class helps to inspect elements using WebDriver.
 * This class is responsible for creating driver using DriverProvider.
 * <p>
 * Created by ksoni on 01/05/18.
 */
public class ElementInspector {
    protected final String platform;
    final DriverProvider driverProvider;
    protected WebDriver driver;
    int defaultWaitTimeInSeconds = 1;
    int defaultPageScroll = 1;

    public ElementInspector(DriverProvider driverProvider, String platform) throws MalformedURLException {
        this.platform = platform;
        this.driverProvider = driverProvider;
        this.driver = driverProvider.getDriver();
        this.driver.manage().timeouts().implicitlyWait(driverProvider.capabilitiesBuilder.waitTimeInSeconds, TimeUnit.SECONDS);
    }

    /**
     * Method to return WebDriver
     *
     * @return WebDriver Requires description
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Method to return Mobile Driver
     *
     * @return MobileDriver Requires description
     */
    public MobileDriver getMobileDriver() {
        return driver == null ? null : (MobileDriver) driver;
    }

    /**
     * Method to return Android Driver
     *
     * @return MobileDriver Requires description
     */
    public AndroidDriver getAndroidDriver() {
        return driver == null ? null : (AndroidDriver) driver;
    }

    /**
     * Method to quit this driver, closing every associated window.
     */
    public void quitDriver() {
        getDriver().quit();
    }

    /**
     * Method to close the current window, quitting the browser if it's the last window currently open.
     */
    public void closeDriver() {
        getDriver().close();
    }

    /**
     * Find WebElement for given locator
     *
     * @param locator Requires description
     * @return Requires description
     */
    public WebElement findElementByXpath(String locator) {
        return findElementByXpath(defaultWaitTimeInSeconds, getXpathByLocator(locator));
    }

    /**
     * Method to find WebElement by Xpath
     *
     * @param clickable Requires description
     * @param locator   Requires description
     * @return Requires description
     */
    public WebElement findElementByXpath(String locator, boolean clickable) {
        return findElementByXpath(defaultWaitTimeInSeconds, getXpathByLocator(locator), clickable);
    }

    /**
     * Method to find WebElement by Xpath
     *
     * @param waitTimeInSeconds Requires description
     * @param locator           Requires description
     * @return Requires description
     */
    public WebElement findElementByXpath(int waitTimeInSeconds, String locator) {
        return findElementByXpath(waitTimeInSeconds, getXpathByLocator(locator), false);
    }

    /**
     * Method to find WebElement by Xpath
     *
     * @param waitTimeInSeconds Requires description
     * @param locator           Requires description
     * @return Requires description
     */
    public WebElement findElementByXpath(int waitTimeInSeconds, String locator, boolean clickable) {
        return findElementByXpath(waitTimeInSeconds, getXpathByLocator(locator), clickable);
    }

    /**
     * Method to find WebElement by Xpath
     *
     * @param xpath Requires description
     * @return WebElement Requires description
     */
    public WebElement findElementByXpath(By xpath) {
        return findElementByXpath(defaultWaitTimeInSeconds, xpath, false);
    }

    /**
     * Method to find WebElement by Xpath
     *
     * @param xpath Requires description
     * @return WebElement Requires description
     */
    public WebElement findElementByXpath(By xpath, boolean clickable) {
        return findElementByXpath(defaultWaitTimeInSeconds, xpath, clickable);
    }

    /**
     * Method to find WebElement by Xpath
     *
     * @param waitTimeInSeconds Requires description
     * @param xpath             Requires description
     * @return WebElement Requires description
     */
    public WebElement findElementByXpath(int waitTimeInSeconds, By xpath) {
        return findElementByXpath(waitTimeInSeconds, xpath, false);
    }

    /**
     * Method to find WebElement by Xpath
     *
     * @param waitTimeInSeconds Requires description
     * @param xpath             Requires description
     * @return WebElement Requires description
     */
    public WebElement findElementByXpath(int waitTimeInSeconds, By xpath, boolean clickable) {
        if (waitTimeInSeconds < driverProvider.capabilitiesBuilder.waitTimeInSeconds)
            waitTimeInSeconds = driverProvider.capabilitiesBuilder.waitTimeInSeconds;

        if (xpath == null) {
            System.out.println("xpath is null in findElementByXpath");
            return null;
        }
        if (waitTimeInSeconds > 0) {
            try {
                WebDriverWait webDriverWait = new WebDriverWait(driver, waitTimeInSeconds);
                if (clickable) {
                    return webDriverWait.until(ExpectedConditions.elementToBeClickable(xpath));
                }
                return webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(xpath));
            } catch (TimeoutException e) {
                System.out.println("Element not found due to TimeoutException at xpath: " + xpath.toString());
                return null;
            } catch (NoSuchElementException e) {
                System.out.println("Element not found due to NoSuchElementException at xpath: " + xpath.toString());
                return null;
            }
        }
        return driver.findElement(xpath);
    }


    /**
     * Method to find all WebElement by Xpath
     *
     * @param locator Requires description
     * @return Requires descriptiont
     */
    public List<WebElement> findAllElementsByXpath(String locator) {
        return findAllElementsByXpath(defaultWaitTimeInSeconds, getXpathByLocator(locator));
    }

    /**
     * Method to find all WebElement by Xpath
     *
     * @param waitTimeInSeconds Requires description
     * @param locator           Requires description
     * @return Requires description
     */
    public List<WebElement> findAllElementsByXpath(int waitTimeInSeconds, String locator) {
        return findAllElementsByXpath(waitTimeInSeconds, getXpathByLocator(locator));
    }

    /**
     * Method to find all WebElement by Xpath
     *
     * @param xpath Requires description
     * @return Requires description
     */
    public List<WebElement> findAllElementsByXpath(By xpath) {
        return findAllElementsByXpath(defaultWaitTimeInSeconds, xpath);
    }

    /**
     * Method to find all WebElement by Xpath
     *
     * @param waitTimeInSeconds Requires description
     * @param xpath             Requires description
     * @return Requires description
     */
    public List<WebElement> findAllElementsByXpath(int waitTimeInSeconds, By xpath) {
        if (waitTimeInSeconds < driverProvider.capabilitiesBuilder.waitTimeInSeconds)
            waitTimeInSeconds = driverProvider.capabilitiesBuilder.waitTimeInSeconds;

        if (xpath == null) {
            System.out.println("xpath is null in findAllElementsByXpath");
            return null;
        }
        if (waitTimeInSeconds > 0) {
            try {
                WebDriverWait webDriverWait = new WebDriverWait(driver, waitTimeInSeconds);
                return webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath));
            } catch (TimeoutException e) {
                System.out.println("Element not found due to TimeoutException at xpath: " + xpath.toString());
                return null;
            } catch (NoSuchElementException e) {
                System.out.println("Element not found due to NoSuchElementException at xpath: " + xpath.toString());
                return null;
            }
        }
        return driver.findElements(xpath);
    }

    /**
     * Method to find child WebElement by Xpath
     *
     * @param parentWebElement Requires description
     * @param locator          Requires description
     * @return Requires description
     */
    public WebElement findChildElementsByXpath(WebElement parentWebElement, String locator) {
        if (parentWebElement == null || CommonUtils.isEmpty(locator))
            return null;
        return parentWebElement.findElement(getXpathByLocator(locator));
    }

    /**
     * Method to find child WebElement by Xpath
     *
     * @param parentWebElement Requires description
     * @param xpath            Requires description
     * @return Requires description
     */
    public WebElement findChildElementsByXpath(WebElement parentWebElement, By xpath) {
        if (parentWebElement == null)
            return null;
        return parentWebElement.findElement(xpath);
    }

    /**
     * Method to find all child WebElement by Xpath
     *
     * @param parentWebElement Requires description
     * @param locator          Requires description
     * @return Requires description
     */
    public List<WebElement> findAllChildElementsByXpath(WebElement parentWebElement, String locator) {
        if (parentWebElement == null || CommonUtils.isEmpty(locator))
            return null;
        return parentWebElement.findElements(getXpathByLocator(locator));
    }


    /**
     * Method to find all child WebElement by Xpath
     *
     * @param parentWebElement Requires description
     * @param xpath            Requires description
     * @return Requires description
     */
    public List<WebElement> findAllChildElementsByXpath(WebElement parentWebElement, By xpath) {
        if (parentWebElement == null)
            return null;
        return parentWebElement.findElements(xpath);
    }

    /**
     * Method to find WebElement for given elementLabel
     *
     * @param elementLabel Requires description
     * @return WebElement Requires description
     */
    public WebElement findElementContainsLabel(String elementLabel) {
        return findElementContainsLabel(defaultWaitTimeInSeconds, elementLabel, false, false);
    }

    /**
     * Method to find WebElement for given elementLabel
     *
     * @param waitTimeInSeconds Requires description
     * @param elementLabel      Requires description
     * @return Requires description
     */
    public WebElement findElementContainsLabel(int waitTimeInSeconds, String elementLabel) {
        return findElementContainsLabel(waitTimeInSeconds, elementLabel, false, false);
    }


    /**
     * Method to find WebElement for given elementLabel
     *
     * @param elementLabel Requires description
     * @param ignoreCase   Requires description
     * @return WebElement Requires description
     */
    public WebElement findElementContainsLabel(String elementLabel, boolean ignoreCase) {
        return findElementContainsLabel(defaultWaitTimeInSeconds, elementLabel, false, ignoreCase);
    }

    /**
     * Method to find WebElement for given elementLabel
     *
     * @param elementLabel  Requires description
     * @param ignoreCase    Requires description
     * @param isSecureField Requires description
     * @return Requires description
     */
    public WebElement findElementContainsLabel(String elementLabel, boolean ignoreCase, boolean isSecureField) {
        return findElementContainsLabel(defaultWaitTimeInSeconds, elementLabel, isSecureField, ignoreCase);
    }

    /**
     * Method to find WebElement for given elementLabel
     *
     * @param waitTimeInSeconds Requires description
     * @param ignoreCase        Requires description
     * @param isSecureField     Requires description
     * @param elementLabel      Requires description
     * @return Requires description
     */
    public WebElement findElementContainsLabel(int waitTimeInSeconds, String elementLabel, boolean isSecureField, boolean ignoreCase) {
        By xpath = getElementXpathContainsLabel(elementLabel, ignoreCase, isSecureField);
        return findElementByXpath(waitTimeInSeconds, xpath);
    }

    /**
     * Method to find all WebElement for given elementLabel
     *
     * @param elementLabel Requires description
     * @return Requires description
     */
    public List<WebElement> findAllElementsContainsLabel(String elementLabel) {
        return findAllElementsContainsLabel(defaultWaitTimeInSeconds, elementLabel, false, false);
    }

    /**
     * Method to find all WebElement for given elementLabel
     *
     * @param waitTimeInSeconds Requires description
     * @param elementLabel      Requires description
     * @return Requires description
     */
    public List<WebElement> findAllElementsContainsLabel(int waitTimeInSeconds, String elementLabel) {
        return findAllElementsContainsLabel(waitTimeInSeconds, elementLabel, false, false);
    }

    /**
     * Method to find all WebElement for given elementLabel
     *
     * @param elementLabel Requires description
     * @param ignoreCase   Requires description
     * @return Requires description
     */
    public List<WebElement> findAllElementsContainsLabel(String elementLabel, boolean ignoreCase) {
        return findAllElementsContainsLabel(defaultWaitTimeInSeconds, elementLabel, false, ignoreCase);
    }

    /**
     * Method to find all WebElement for given elementLabel
     *
     * @param elementLabel  Requires description
     * @param ignoreCase    Requires description
     * @param isSecureField Requires description
     * @return Requires description
     */
    public List<WebElement> findAllElementsContainsLabel(String elementLabel, boolean ignoreCase, boolean isSecureField) {
        return findAllElementsContainsLabel(defaultWaitTimeInSeconds, elementLabel, isSecureField, ignoreCase);
    }

    /**
     * Method to find all WebElement for given elementLabel
     *
     * @param waitTimeInSeconds Requires description
     * @param elementLabel      Requires description
     * @param isSecureField     Requires description
     * @param ignoreCase        Requires description
     * @return Requires description
     */
    public List<WebElement> findAllElementsContainsLabel(int waitTimeInSeconds, String elementLabel, boolean isSecureField, boolean ignoreCase) {
        By xpath = getElementXpathContainsLabel(elementLabel, ignoreCase, isSecureField);
        return findAllElementsByXpath(waitTimeInSeconds, xpath);
    }

    /**
     * Method to find WebElement for given elementLabel
     *
     * @param elementLabel Requires description
     * @return WebElement Requires description
     */
    public WebElement findElementWithLabel(String elementLabel) {
        return findElementWithLabel(defaultWaitTimeInSeconds, elementLabel, false, false);
    }

    /**
     * Find WebElement for given elementLabel
     *
     * @param waitTimeInSeconds Requires description
     * @param elementLabel      Requires description
     * @return Requires description
     */
    public WebElement findElementWithLabel(int waitTimeInSeconds, String elementLabel) {
        return findElementWithLabel(waitTimeInSeconds, elementLabel, false, false);
    }


    /**
     * Method to find WebElement for given elementLabel
     *
     * @param elementLabel Requires description
     * @param ignoreCase   Requires description
     * @return WebElement Requires description
     */
    public WebElement findElementWithLabel(String elementLabel, boolean ignoreCase) {
        return findElementWithLabel(defaultWaitTimeInSeconds, elementLabel, false, ignoreCase);
    }

    /**
     * Method to find WebElement for given elementLabel
     *
     * @param elementLabel  Requires description
     * @param ignoreCase    Requires description
     * @param isSecureField Requires description
     * @return Requires description
     */
    public WebElement findElementWithLabel(String elementLabel, boolean ignoreCase, boolean isSecureField) {
        return findElementWithLabel(defaultWaitTimeInSeconds, elementLabel, isSecureField, ignoreCase);
    }

    /**
     * Find WebElement for given elementLabel
     *
     * @param waitTimeInSeconds Requires description
     * @param ignoreCase        Requires description
     * @param isSecureField     Requires description
     * @param elementLabel      Requires description
     * @return Requires description
     */
    public WebElement findElementWithLabel(int waitTimeInSeconds, String elementLabel, boolean isSecureField, boolean ignoreCase) {
        By xpath = getElementXpathWithLabel(elementLabel, ignoreCase, isSecureField);
        return findElementByXpath(waitTimeInSeconds, xpath);
    }

    /**
     * Method to find all WebElement for given elementLabel
     *
     * @param elementLabel Requires description
     * @return Requires description
     */
    public List<WebElement> findAllElementsWithLabel(String elementLabel) {
        return findAllElementsWithLabel(defaultWaitTimeInSeconds, elementLabel, false, false);
    }

    /**
     * Method to find all WebElement for given elementLabel
     *
     * @param waitTimeInSeconds Requires description
     * @param elementLabel      Requires description
     * @return Requires description
     */
    public List<WebElement> findAllElementsWithLabel(int waitTimeInSeconds, String elementLabel) {
        return findAllElementsWithLabel(waitTimeInSeconds, elementLabel, false, false);
    }


    /**
     * Method to find all WebElement for given elementLabel
     *
     * @param elementLabel Requires description
     * @param ignoreCase   Requires description
     * @return Requires description
     */
    public List<WebElement> findAllElementsWithLabel(String elementLabel, boolean ignoreCase) {
        return findAllElementsWithLabel(defaultWaitTimeInSeconds, elementLabel, false, ignoreCase);
    }

    /**
     * Method to find all WebElement for given elementLabel
     *
     * @param elementLabel  Requires description
     * @param ignoreCase    Requires description
     * @param isSecureField Requires description
     * @return Requires description
     */
    public List<WebElement> findAllElementsWithLabel(String elementLabel, boolean ignoreCase, boolean isSecureField) {
        return findAllElementsWithLabel(defaultWaitTimeInSeconds, elementLabel, isSecureField, ignoreCase);
    }

    /**
     * Method to find all WebElement for given elementLabel
     *
     * @param waitTimeInSeconds Requires description
     * @param elementLabel      Requires description
     * @param isSecureField     Requires description
     * @param ignoreCase        Requires description
     * @return Requires description
     */
    public List<WebElement> findAllElementsWithLabel(int waitTimeInSeconds, String elementLabel, boolean isSecureField, boolean ignoreCase) {
        if (CommonUtils.isEmpty(elementLabel))
            return null;
        By xpath = getElementXpathWithLabel(elementLabel, ignoreCase, isSecureField);
        return findAllElementsByXpath(waitTimeInSeconds, xpath);
    }

    /**
     * Method to find SecureText WebElement for given elementLabel
     *
     * @param waitTimeInSeconds Requires description
     * @param elementLabel      Requires description
     * @param ignoreCase        Requires description
     * @return Requires description
     */
    public WebElement findSecureTextElementWithLabel(int waitTimeInSeconds, String elementLabel, boolean ignoreCase) {
        return findElementWithLabel(waitTimeInSeconds, elementLabel, true, ignoreCase);
    }

    /**
     * Method to find SecureText WebElement for given elementLabel
     *
     * @param waitTimeInSeconds Requires description
     * @param ignoreCase        Requires description
     * @param elementLabel      Requires description
     * @return Requires description
     */
    public WebElement findSecureTextElementContainsLabel(int waitTimeInSeconds, String elementLabel, boolean ignoreCase) {
        return findElementContainsLabel(waitTimeInSeconds, elementLabel, true, ignoreCase);
    }

    /**
     * Method to find if element enable or not
     *
     * @param locator Requires description
     * @return boolean Requires description
     */
    public boolean isElementEnable(String locator) {
        return isElementEnable(defaultWaitTimeInSeconds, getXpathByLocator(locator));
    }

    /**
     * Method to find if element enable or not
     *
     * @param xpath Requires description
     * @return boolean Requires description
     */
    public boolean isElementEnable(By xpath) {
        return isElementEnable(defaultWaitTimeInSeconds, xpath);
    }


    /**
     * Method to find if element enable or not
     *
     * @param waitTimeInSeconds Requires description
     * @param xpath             Requires description
     * @return Requires description
     */
    public boolean isElementEnable(int waitTimeInSeconds, By xpath) {
        WebElement element = findElementByXpath(waitTimeInSeconds, xpath);
        return element != null && element.getAttribute(UIAttributes.ENABLE).equals(UIAttributes.TRUE);
    }


    /**
     * Method to get element xPAth using elementLabel
     *
     * @param elementLabel Requires description
     * @return Requires description
     */
    public By getElementXpathContainsLabel(String elementLabel) {
        return getElementXpathContainsLabel(elementLabel, false, false);
    }

    /**
     * Method to get element xPAth using elementLabel
     *
     * @param elementLabel Requires description
     * @param ignoreCase   Requires description
     * @return Requires description
     */
    public By getElementXpathContainsLabel(String elementLabel, boolean ignoreCase) {
        return getElementXpathContainsLabel(elementLabel, ignoreCase, false);
    }

    /**
     * Method to get element xPAth using elementLabel
     *
     * @param elementLabel  Requires description
     * @param isSecureField Requires description
     * @param ignoreCase    Requires description
     * @return Requires description
     */
    public By getElementXpathContainsLabel(String elementLabel, boolean ignoreCase, boolean isSecureField) {
        if (CommonUtils.isEmpty(elementLabel)) {
            System.out.println("elementLabel string is null in getElementXpathContainsLabel");
            return null;
        }
        By xpath = getXpathByLocator("//*[contains(@label,'" + elementLabel + "') or contains(@text,'" + elementLabel + "')]");
        if (isSecureField) {
            xpath = platform.equalsIgnoreCase(Platform.IOS)
                    ? getXpathByLocator("//XCUIElementTypeSecureTextField[contains(@label,'" + elementLabel + "')]")
                    : getXpathByLocator("//*[contains(@text,'" + elementLabel + "')]");
        }
        if (ignoreCase) {
            xpath = getXpathByLocator("//*[contains(@label,'" + elementLabel + "') " +
                    "or contains(@label,'" + elementLabel.toUpperCase() + "') " +
                    "or contains(@label,'" + elementLabel.toLowerCase() + "') " +
                    "or contains(@text,'" + elementLabel + "') " +
                    "or contains(@text,'" + elementLabel.toUpperCase() + "') " +
                    "or contains(@text,'" + elementLabel.toLowerCase() + "')]");
            if (isSecureField) {
                xpath = platform.equalsIgnoreCase(Platform.IOS)
                        ? getXpathByLocator("//XCUIElementTypeSecureTextField[contains(@label,'" + elementLabel + "') " +
                        "or contains(@label,'" + elementLabel.toUpperCase() + "') " +
                        "or contains(@label,'" + elementLabel.toLowerCase() + "')]")
                        : getXpathByLocator("//*[contains(@text,'" + elementLabel + "') " +
                        "or contains(@text,'" + elementLabel.toUpperCase() + "') " +
                        "or contains(@text,'" + elementLabel.toLowerCase() + "')]");
            }
        }
        return xpath;
    }

    /**
     * Method to get element xPAth using elementLabel
     *
     * @param elementLabel Requires description
     * @return Requires description
     */
    public By getElementXpathWithLabel(String elementLabel) {
        return getElementXpathWithLabel(elementLabel, false, false);
    }

    /**
     * Method to get element xPAth using elementLabel
     *
     * @param elementLabel Requires description
     * @param ignoreCase   Requires description
     * @return Requires description
     */
    public By getElementXpathWithLabel(String elementLabel, boolean ignoreCase) {
        return getElementXpathWithLabel(elementLabel, ignoreCase, false);
    }

    /**
     * Method to get element xPAth using elementLabel
     *
     * @param elementLabel  Requires description
     * @param isSecureField Requires description
     * @param ignoreCase    Requires description
     * @return Requires description
     */
    public By getElementXpathWithLabel(String elementLabel, boolean ignoreCase, boolean isSecureField) {
        if (CommonUtils.isEmpty(elementLabel)) {
            System.out.println("elementLabel string is null in getElementXpathWithLabel");
            return null;
        }
        By xpath = getXpathByLocator("//*[@label ='" + elementLabel + "' or @text='" + elementLabel + "' or @name='" + elementLabel + "']");
        if (isSecureField) {
            xpath = platform.equalsIgnoreCase(Platform.IOS)
                    ? getXpathByLocator("//XCUIElementTypeSecureTextField[@label='" + elementLabel + "']")
                    : getXpathByLocator("//*[@text='" + elementLabel + "']");
        }
        if (ignoreCase) {
            xpath = getXpathByLocator("//*[@label='" + elementLabel + "' " +
                    "or @label='" + elementLabel.toUpperCase() + "' " +
                    "or @label='" + elementLabel.toLowerCase() + "' " +
                    "or @text='" + elementLabel + "' " +
                    "or @text='" + elementLabel.toUpperCase() + "' " +
                    "or @text='" + elementLabel.toLowerCase() + "' " +
                    "or @name='" + elementLabel + "' " +
                    "or @name='" + elementLabel.toUpperCase() + "' " +
                    "or @name='" + elementLabel.toLowerCase() + "']");
            if (isSecureField) {
                xpath = platform.equalsIgnoreCase(Platform.IOS)
                        ? getXpathByLocator("//XCUIElementTypeSecureTextField[contains(@label='" + elementLabel + "') " +
                        "or @label='" + elementLabel.toUpperCase() + "' " +
                        "or @label='" + elementLabel.toLowerCase() + "']")
                        : getXpathByLocator("//*[contains(@text='" + elementLabel + "') " +
                        "or @text='" + elementLabel.toUpperCase() + "' " +
                        "or @text='" + elementLabel.toLowerCase() + "']");
            }
        }
        return xpath;
    }

    /**
     * Return label for given WebElement
     *
     * @param webElement Requires description
     * @return String Requires description
     */
    public String getElementText(WebElement webElement) {
        if (webElement == null)
            return null;
        try {
            return CommonUtils.isNotEmpty(webElement.getText()) ? webElement.getText()
                    : CommonUtils.isNotEmpty(webElement.getAttribute(UIAttributes.TEXT)) ? webElement.getAttribute(UIAttributes.TEXT)
                    : CommonUtils.isNotEmpty(webElement.getAttribute(UIAttributes.LABEL)) ? webElement.getAttribute(UIAttributes.LABEL)
                    : CommonUtils.isNotEmpty(webElement.getAttribute(UIAttributes.VALUE)) ? webElement.getAttribute(UIAttributes.VALUE)
                    : CommonUtils.isNotEmpty(webElement.getAttribute(UIAttributes.NAME)) ? webElement.getAttribute(UIAttributes.NAME)
                    : null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Method to get xpath (By) from locator
     *
     * @param locator Requires description
     * @return Requires description
     */
    public By getXpathByLocator(String locator) {
        if (CommonUtils.isEmpty(locator)) {
            System.out.println("locator string is null in getXpathByLocator");
            return null;
        }
        return By.xpath(locator);
    }

    /**
     * get global element wait time in seconds
     *
     * @return int
     */
    public int getDefaultWaitTimeInSeconds() {
        return defaultWaitTimeInSeconds;
    }

    /**
     * set global element wait time in seconds
     */
    public void setDefaultWaitTimeInSeconds(int defaultWaitTimeInSeconds) {
        if (defaultWaitTimeInSeconds > 1) {
            if (defaultWaitTimeInSeconds > 1000)
                this.defaultWaitTimeInSeconds = defaultWaitTimeInSeconds / 1000;
            this.defaultWaitTimeInSeconds = defaultWaitTimeInSeconds;
            this.driver.manage().timeouts().implicitlyWait(this.defaultWaitTimeInSeconds, TimeUnit.SECONDS);
        }
    }

    /**
     * get default page scroll
     *
     * @return int
     */
    public int getDefaultPageScroll() {
        return defaultPageScroll;
    }

    /**
     * set default page scroll
     *
     * @param defaultPageScroll
     */
    public void setDefaultPageScroll(int defaultPageScroll) {
        if (defaultPageScroll > 1)
            this.defaultPageScroll = defaultPageScroll;
    }

    public int getScreenHeight() {
        return driver.manage().window().getSize().getHeight();
    }

    public int getScreenWidth() {
        return driver.manage().window().getSize().getWidth();
    }
}
