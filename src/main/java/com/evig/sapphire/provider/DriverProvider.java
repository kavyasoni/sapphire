package com.evig.sapphire.provider;

import com.evig.sapphire.constants.Platform;
import com.evig.sapphire.utils.CommonUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for creating WebDriver using CapabilitiesBuilder
 * <p>
 * Created by ksoni on 01/05/18.
 */
public class DriverProvider {
    public final CapabilitiesBuilder capabilitiesBuilder;
    private final ThreadLocal<AppiumDriver<WebElement>> driver = new ThreadLocal<>();

    public DriverProvider(CapabilitiesBuilder capabilitiesBuilder) {
        this.capabilitiesBuilder = capabilitiesBuilder;
    }

    /**
     * Method to provide Driver based on DesiredCapabilities
     *
     * @return WebDriver Requires description
     * @throws MalformedURLException Requires description
     */
    public WebDriver getDriver() throws MalformedURLException {
        DesiredCapabilities capabilities;
        if (capabilitiesBuilder.platform.equalsIgnoreCase(Platform.ANDROID)) {
            capabilities = DesiredCapabilities.android();
            capabilities.setCapability(AndroidMobileCapabilityType.AVD, capabilitiesBuilder.deviceName);

            // Set app package if not null or empty
            if (CommonUtils.isNotEmpty(capabilitiesBuilder.appPackage))
                capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, capabilitiesBuilder.appPackage);

        } else {
            if (capabilitiesBuilder.deviceName.contains(Platform.iPAD)) {
                capabilities = DesiredCapabilities.ipad();
            } else {
                capabilities = DesiredCapabilities.iphone();
            }
            capabilities.setCapability("automationName", "XCUITest");
            capabilities.setCapability(IOSMobileCapabilityType.SHOW_IOS_LOG, true);

        }

        if (!capabilitiesBuilder.extraCapabilitiesMap.isEmpty()) {
            for (Map.Entry<String, String> entry : capabilitiesBuilder.extraCapabilitiesMap.entrySet()) {
                capabilities.setCapability(entry.getKey(), entry.getValue());
            }
        }

        capabilities.setCapability(MobileCapabilityType.APP, capabilitiesBuilder.app);
        capabilities.setCapability(MobileCapabilityType.PLATFORM, capabilitiesBuilder.platform);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, capabilitiesBuilder.deviceName);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, capabilitiesBuilder.deviceName);

        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, capabilitiesBuilder.platformVersion);

        if (CommonUtils.isNotEmpty(capabilitiesBuilder.appiumVersion))
            capabilities.setCapability(MobileCapabilityType.APPIUM_VERSION, capabilitiesBuilder.appiumVersion);

        // Set device orientation if not null or empty
        if (CommonUtils.isNotEmpty(capabilitiesBuilder.orientation))
            capabilities.setCapability("deviceOrientation", capabilitiesBuilder.orientation);

        // Set browser name if not null or empty else set keyboard
        if (CommonUtils.isNotEmpty(capabilitiesBuilder.browserName))
            capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, capabilitiesBuilder.browserName);

        capabilities.setCapability("unicodeKeyboardStatus", capabilitiesBuilder.unicodeKeyboardStatus);
        capabilities.setCapability("resetKeyboardStatus", capabilitiesBuilder.resetKeyboardStatus);

        if (capabilitiesBuilder.isSauceLab && CommonUtils.isNotEmpty(capabilitiesBuilder.sauceUsername) && CommonUtils.isNotEmpty(capabilitiesBuilder.sauceAccessKey)) {
            if (capabilitiesBuilder.platform.equalsIgnoreCase(Platform.ANDROID)) {
                driver.set(new AndroidDriver<>(new URL("http://" + capabilitiesBuilder.sauceUsername + ":" + capabilitiesBuilder.sauceAccessKey + "@ondemand.saucelabs.com:80/wd/hub"), capabilities));
            } else {
                driver.set(new IOSDriver<>(new URL("http://" + capabilitiesBuilder.sauceUsername + ":" + capabilitiesBuilder.sauceAccessKey + "@ondemand.saucelabs.com:80/wd/hub"), capabilities));
            }
        } else {
            if (capabilitiesBuilder.platform.equalsIgnoreCase(Platform.ANDROID)) {
                driver.set(new AndroidDriver<>(new URL(capabilitiesBuilder.appiumServerURL), capabilities));
            } else {
                driver.set(new IOSDriver<>(new URL(capabilitiesBuilder.appiumServerURL), capabilities));
            }
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