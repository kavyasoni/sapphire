package com.evig.sapphire.examples;

import com.evig.sapphire.GenericExecutor;
import com.evig.sapphire.config.SapphireConfig;
import com.evig.sapphire.provider.DriverProvider;
import org.aeonbits.owner.ConfigFactory;
import org.testng.annotations.*;

import java.net.MalformedURLException;

/**
 * Example headless Android test for CI/CD environments.
 * This test demonstrates how to run Sapphire tests in headless mode
 * without a physical device or visible emulator.
 *
 * @author Sapphire Team
 * @since 2.0.0
 */
public class HeadlessAndroidTest {

    private GenericExecutor executor;
    private final SapphireConfig config = ConfigFactory.create(SapphireConfig.class);

    @BeforeClass
    public void setupClass() {
        System.out.println("=".repeat(80));
        System.out.println("Sapphire Headless Test Suite - Android");
        System.out.println("=".repeat(80));
        System.out.println("Configuration:");
        System.out.println("  Appium Server: " + config.appiumServerUrl());
        System.out.println("  Headless Mode: " + config.headless());
        System.out.println("  Screenshot on Failure: " + config.screenshotOnFailure());
        System.out.println("=".repeat(80));
    }

    @BeforeMethod
    public void setup() throws MalformedURLException, InterruptedException {
        // Wait for emulator to be ready (in CI/CD)
        Thread.sleep(5000);

        // Configure capabilities for headless execution
        DriverProvider.CapabilitiesBuilder builder =
            new DriverProvider.CapabilitiesBuilder(
                System.getProperty("app.path", "/path/to/app.apk"),
                "android",
                System.getProperty("android.version", "13"),
                System.getProperty("device.name", "emulator-5554")
            )
            .setWaitTimeInSeconds(config.implicitWait())
            .setAppiumServerURL(config.appiumServerUrl())
            .setExtraCapability("noReset", "true")
            .setExtraCapability("autoGrantPermissions", "true")
            .setExtraCapability("isHeadless", String.valueOf(config.headless()));

        // For CI/CD, add additional capabilities
        if (config.headless()) {
            builder.setExtraCapability("avd", "test_emulator")
                   .setExtraCapability("avdArgs", "-no-window -no-audio -no-boot-anim");
        }

        executor = new GenericExecutor(builder.build());
    }

    @Test(groups = {"integration", "headless", "android"})
    public void testAppLaunchInHeadlessMode() {
        System.out.println("\n[TEST] Verifying app launch in headless mode");

        // Wait for app to load
        executor.waitForSeconds(3);

        // Verify app launched successfully
        boolean isLaunched = executor.isElementsDisplayedWithLabel("Sign In", "Join");

        if (isLaunched) {
            System.out.println("✓ App launched successfully in headless mode");
        } else {
            System.out.println("✗ App failed to launch in headless mode");
        }

        assert isLaunched : "App should launch successfully in headless mode";
    }

    @Test(groups = {"integration", "headless", "android"}, dependsOnMethods = "testAppLaunchInHeadlessMode")
    public void testBasicNavigationInHeadlessMode() {
        System.out.println("\n[TEST] Testing basic navigation in headless mode");

        // Click on an element
        boolean clicked = executor.clickOnElementWithLabel("Sign In");

        if (clicked) {
            System.out.println("✓ Navigation successful in headless mode");
        } else {
            System.out.println("✗ Navigation failed in headless mode");
        }

        assert clicked : "Should be able to navigate in headless mode";
    }

    @Test(groups = {"integration", "headless", "android"})
    public void testElementInteractionInHeadlessMode() {
        System.out.println("\n[TEST] Testing element interaction in headless mode");

        // Test finding elements
        boolean elementsFound = executor.isElementsDisplayedWithLabel("Email", "Password");

        if (elementsFound) {
            System.out.println("✓ Elements found successfully in headless mode");

            // Test text input
            executor.setTextInElementsWithLabel("test@example.com", "Email");
            executor.setTextInElementsWithLabel("password123", "Password");

            System.out.println("✓ Text input successful in headless mode");
        } else {
            System.out.println("✗ Elements not found in headless mode");
        }

        assert elementsFound : "Should be able to find and interact with elements in headless mode";
    }

    @Test(groups = {"integration", "headless", "android"})
    public void testScrollingInHeadlessMode() {
        System.out.println("\n[TEST] Testing scrolling in headless mode");

        try {
            // Perform scroll
            executor.performScrollToBottom(2);
            System.out.println("✓ Scrolling works in headless mode");

            // Scroll back up
            executor.performScrollToTop(2);
            System.out.println("✓ Scroll-to-top works in headless mode");

            assert true;
        } catch (Exception e) {
            System.out.println("✗ Scrolling failed in headless mode: " + e.getMessage());
            assert false : "Scrolling should work in headless mode";
        }
    }

    @Test(groups = {"integration", "headless", "android"})
    public void testScreenshotCaptureInHeadlessMode() {
        System.out.println("\n[TEST] Testing screenshot capture in headless mode");

        if (config.screenshotOnFailure()) {
            try {
                // Capture screenshot
                String timestamp = String.valueOf(System.currentTimeMillis());
                String screenshotPath = config.reportDirectory() + "/screenshot_" + timestamp + ".png";

                com.evig.sapphire.utils.CommonUtils.captureScreen(
                    executor.getDriver(),
                    screenshotPath
                );

                System.out.println("✓ Screenshot captured in headless mode: " + screenshotPath);
                assert true;
            } catch (Exception e) {
                System.out.println("✗ Screenshot capture failed: " + e.getMessage());
                assert false : "Should be able to capture screenshots in headless mode";
            }
        } else {
            System.out.println("⚠ Screenshot capture disabled in configuration");
            assert true;
        }
    }

    @AfterMethod
    public void teardown() {
        if (executor != null) {
            try {
                executor.quitDriver();
                System.out.println("✓ Driver quit successfully");
            } catch (Exception e) {
                System.out.println("⚠ Error quitting driver: " + e.getMessage());
            }
        }
    }

    @AfterClass
    public void teardownClass() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("Headless Test Suite Completed");
        System.out.println("=".repeat(80));
    }
}
