package com.evig.sapphire.provider;

import com.evig.sapphire.constants.Platform;
import com.evig.sapphire.utils.CommonUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for creating WebDriver using CapabilitiesBuilder
 * <p>
 * Created by ksoni on 01/05/18.
 */
public class DriverProvider {
    public final CapabilitiesBuilder capabilitiesBuilder;
    private final ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();

    public DriverProvider(CapabilitiesBuilder capabilitiesBuilder) {
        this.capabilitiesBuilder = capabilitiesBuilder;
    }

    /**
     * Method to provide Driver based on modern Appium 2.x Options (UiAutomator2Options / XCUITestOptions)
     *
     * @return WebDriver instance configured with appropriate options
     * @throws MalformedURLException if Appium server URL is malformed
     */
    public WebDriver getDriver() throws MalformedURLException {
        URL serverUrl;

        if (capabilitiesBuilder.isSauceLab && CommonUtils.isNotEmpty(capabilitiesBuilder.sauceUsername)
                && CommonUtils.isNotEmpty(capabilitiesBuilder.sauceAccessKey)) {
            serverUrl = new URL("http://" + capabilitiesBuilder.sauceUsername + ":" +
                    capabilitiesBuilder.sauceAccessKey + "@ondemand.saucelabs.com:80/wd/hub");
        } else {
            serverUrl = new URL(capabilitiesBuilder.appiumServerURL);
        }

        if (capabilitiesBuilder.platform.equalsIgnoreCase(Platform.ANDROID)) {
            UiAutomator2Options options = new UiAutomator2Options();

            // Core capabilities
            options.setDeviceName(capabilitiesBuilder.deviceName);
            options.setPlatformVersion(capabilitiesBuilder.platformVersion);
            options.setApp(capabilitiesBuilder.app);

            // Android-specific capabilities
            if (CommonUtils.isNotEmpty(capabilitiesBuilder.deviceName)) {
                options.setAvd(capabilitiesBuilder.deviceName);
            }

            if (CommonUtils.isNotEmpty(capabilitiesBuilder.appPackage)) {
                options.setAppPackage(capabilitiesBuilder.appPackage);
            }

            // Keyboard settings
            options.setUnicodeKeyboard(capabilitiesBuilder.unicodeKeyboardStatus);
            options.setResetKeyboard(capabilitiesBuilder.resetKeyboardStatus);

            // Browser name if specified
            if (CommonUtils.isNotEmpty(capabilitiesBuilder.browserName)) {
                options.setBrowserName(capabilitiesBuilder.browserName);
            }

            // Orientation
            if (CommonUtils.isNotEmpty(capabilitiesBuilder.orientation)) {
                options.setOrientation(capabilitiesBuilder.orientation);
            }

            // Extra capabilities
            if (!capabilitiesBuilder.extraCapabilitiesMap.isEmpty()) {
                for (Map.Entry<String, String> entry : capabilitiesBuilder.extraCapabilitiesMap.entrySet()) {
                    options.setCapability(entry.getKey(), entry.getValue());
                }
            }

            // Implicit wait
            options.setNewCommandTimeout(Duration.ofSeconds(capabilitiesBuilder.waitTimeInSeconds));

            driver.set(new AndroidDriver(serverUrl, options));
        } else {
            XCUITestOptions options = new XCUITestOptions();

            // Core capabilities
            options.setDeviceName(capabilitiesBuilder.deviceName);
            options.setPlatformVersion(capabilitiesBuilder.platformVersion);
            options.setApp(capabilitiesBuilder.app);

            // iOS-specific capabilities
            options.setShowIOSLog(true);

            // Keyboard settings
            options.setUnicodeKeyboard(capabilitiesBuilder.unicodeKeyboardStatus);
            options.setResetKeyboard(capabilitiesBuilder.resetKeyboardStatus);

            // Browser name if specified
            if (CommonUtils.isNotEmpty(capabilitiesBuilder.browserName)) {
                options.setBrowserName(capabilitiesBuilder.browserName);
            }

            // Orientation
            if (CommonUtils.isNotEmpty(capabilitiesBuilder.orientation)) {
                options.setOrientation(capabilitiesBuilder.orientation);
            }

            // Extra capabilities
            if (!capabilitiesBuilder.extraCapabilitiesMap.isEmpty()) {
                for (Map.Entry<String, String> entry : capabilitiesBuilder.extraCapabilitiesMap.entrySet()) {
                    options.setCapability(entry.getKey(), entry.getValue());
                }
            }

            // Implicit wait
            options.setNewCommandTimeout(Duration.ofSeconds(capabilitiesBuilder.waitTimeInSeconds));

            driver.set(new IOSDriver(serverUrl, options));
        }

        return driver.get();
    }

    /**
     * This class is used to define WebDriver Capabilities which will be used in DriverProvider
     */
    public static class CapabilitiesBuilder {
        public final String app;
        public final boolean isSauceLab = false;
        public final HashMap<String, String> extraCapabilitiesMap = new HashMap<>();
        public String platformVersion;
        public String deviceName;
        public String platform;
        public String browserName;
        public String sauceUsername;
        public String sauceAccessKey;
        public String appPackage;
        public String appiumVersion;
        public boolean resetKeyboardStatus = true;
        public boolean unicodeKeyboardStatus = true;
        public int waitTimeInSeconds = 5;
        public String orientation = "portrait";
        public String appiumServerURL = "http://0.0.0.0:4723/wd/hub";

        /**
         * Constructor with default (mandatory) parameters. Throws NullPointerException if any of these are null
         *
         * @param app             Requires description
         * @param platform        Requires description
         * @param platformVersion Requires description
         * @param deviceName      Requires description
         */
        public CapabilitiesBuilder(String app, String platform, String platformVersion, String deviceName, int waitTimeInSeconds) {
            if (CommonUtils.isEmpty(app) || CommonUtils.isEmpty(platform) || CommonUtils.isEmpty(platformVersion) || CommonUtils.isEmpty(deviceName)) {
                throw new NullPointerException("Required properties are null or empty in testNG config file");
            }
            this.deviceName = deviceName;
            this.platformVersion = platformVersion;
            this.platform = platform;
            this.app = app;
            this.waitTimeInSeconds = waitTimeInSeconds;
        }


        /**
         * Constructor with default (mandatory) parameters. Throws NullPointerException if any of these are null
         *
         * @param app             Requires description
         * @param platform        Requires description
         * @param platformVersion Requires description
         * @param deviceName      Requires description
         */
        public CapabilitiesBuilder(String app, String platform, String platformVersion, String deviceName) {
            if (CommonUtils.isEmpty(app) || CommonUtils.isEmpty(platform) || CommonUtils.isEmpty(platformVersion) || CommonUtils.isEmpty(deviceName)) {
                throw new NullPointerException("Required properties are null or empty in testNG config file");
            }
            this.deviceName = deviceName;
            this.platformVersion = platformVersion;
            this.platform = platform;
            this.app = app;
        }

        public CapabilitiesBuilder setWaitTimeInSeconds(int waitTimeInSeconds) {
            this.waitTimeInSeconds = waitTimeInSeconds;
            return this;
        }

        public CapabilitiesBuilder setBrowserName(String browserName) {
            this.browserName = browserName;
            return this;
        }

        public CapabilitiesBuilder setUnicodeKeyboardStatus(boolean unicodeKeyboardStatus) {
            this.unicodeKeyboardStatus = unicodeKeyboardStatus;
            return this;
        }

        public CapabilitiesBuilder setResetKeyboardStatus(boolean resetKeyboardStatus) {
            this.resetKeyboardStatus = resetKeyboardStatus;
            return this;
        }

        public CapabilitiesBuilder setSauceUsername(String sauceUsername) {
            this.sauceUsername = sauceUsername;
            return this;
        }

        public CapabilitiesBuilder setSauceAccessKey(String sauceAccessKey) {
            this.sauceAccessKey = sauceAccessKey;
            return this;
        }

        public DriverProvider build() {
            return new DriverProvider(this);
        }

        public CapabilitiesBuilder setOrientation(String orientation) {
            this.orientation = orientation;
            return this;
        }

        public CapabilitiesBuilder setAppiumVersion(String appiumVersion) {
            this.appiumVersion = appiumVersion;
            return this;
        }

        public CapabilitiesBuilder setAppiumServerURL(String appiumServerURL) {
            this.appiumServerURL = appiumServerURL;
            return this;
        }

        public CapabilitiesBuilder setAppPackage(String appPackage) {
            this.appPackage = appPackage;
            return this;
        }

        public CapabilitiesBuilder setPlatformVersion(String platformVersion) {
            this.platformVersion = platformVersion;
            return this;
        }

        public CapabilitiesBuilder setExtraCapability(String key, String value) {
            if (CommonUtils.isEmpty(key) || CommonUtils.isEmpty(value))
                return this;
            this.extraCapabilitiesMap.put(key, value);
            return this;
        }

        public CapabilitiesBuilder setExtraCapabilities(HashMap<String, String> extraCapabilitiesMap) {
            if (extraCapabilitiesMap == null || extraCapabilitiesMap.isEmpty())
                return this;
            this.extraCapabilitiesMap.putAll(extraCapabilitiesMap);
            return this;
        }
    }
}