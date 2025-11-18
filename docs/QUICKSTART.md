# Sapphire Quick Start Guide

Get up and running with Sapphire in 5 minutes!

## Prerequisites

Install the following before starting:

1. **Java 17+**
   ```bash
   # Verify installation
   java -version
   # Should show: openjdk version "17" or higher
   ```

2. **Maven 3.8+**
   ```bash
   mvn -version
   ```

3. **Appium 2.x**
   ```bash
   npm install -g appium@next
   appium driver install uiautomator2  # For Android
   appium driver install xcuitest      # For iOS (macOS only)
   ```

4. **Android SDK** (for Android testing)
   - Install Android Studio
   - Set `ANDROID_HOME` environment variable

## 5-Minute Setup

### Step 1: Add Sapphire to Your Project

Create a new Maven project and add Sapphire dependency:

```xml
<dependency>
    <groupId>com.evig.sapphire</groupId>
    <artifactId>sapphire</artifactId>
    <version>2.0.0</version>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.testng</groupId>
    <artifactId>testng</artifactId>
    <version>7.10.1</version>
    <scope>test</scope>
</dependency>
```

### Step 2: Start Appium Server

```bash
appium
```

Keep this terminal open.

### Step 3: Write Your First Test

Create `src/test/java/MyFirstTest.java`:

```java
import com.evig.sapphire.GenericExecutor;
import com.evig.sapphire.provider.DriverProvider;
import org.testng.annotations.*;

public class MyFirstTest {
    GenericExecutor executor;

    @BeforeMethod
    public void setup() throws Exception {
        DriverProvider.CapabilitiesBuilder builder =
            new DriverProvider.CapabilitiesBuilder(
                "/path/to/your/app.apk",  // Your app path
                "android",                 // Platform: android or ios
                "13",                      // Android version
                "emulator-5554"            // Device name
            );

        executor = new GenericExecutor(builder.build());
    }

    @Test
    public void myFirstTest() {
        // Wait for app to load
        executor.waitForSeconds(2);

        // Find and click on "Sign In" button
        executor.clickOnElementWithLabel("Sign In");

        // Enter email
        executor.setTextInElementsWithLabel("test@example.com", "Email");

        // Enter password
        executor.setTextInElementsWithLabel("password123", "Password");

        // Click Sign In
        executor.clickOnElementWithLabel("Sign In");

        // Verify logged in
        assert executor.isElementsDisplayedWithLabel("Welcome");
    }

    @AfterMethod
    public void teardown() {
        executor.quitDriver();
    }
}
```

### Step 4: Run Your Test

```bash
mvn test -Dtest=MyFirstTest
```

### Step 5: View Results

Check the reports in:
- Console output
- `report/` directory - HTML reports with screenshots
- `target/surefire-reports/` - TestNG reports

## Common Patterns

### Finding Elements

```java
// By label (recommended - works cross-platform)
executor.findElementWithLabel("Submit");
executor.findElementContainsLabel("Sub");  // Partial match

// By XPath (when needed)
executor.findElementByXpath("//button[@id='submit']");

// Case-insensitive
executor.findElementWithLabel("submit", true);

// With custom wait
executor.findElementWithLabel(15, "Submit");  // Wait up to 15 seconds

// Find and scroll
executor.performScrollAndFindElementWithLabel("Hidden Element");
```

### Interacting with Elements

```java
// Click
executor.clickOnElementWithLabel("Button Text");

// Enter text
executor.setTextInElementsWithLabel("John Doe", "Name Field");

// Clear text
executor.clearTextOfInputFieldElements("//input[@id='email']");

// Verify element displayed
boolean isDisplayed = executor.isElementsDisplayedWithLabel("Welcome");
assert isDisplayed;
```

### Scrolling

```java
// Scroll down
executor.performScrollToBottom();
executor.performScrollToBottom(3);  // Scroll 3 pages

// Scroll up
executor.performScrollToTop();

// Horizontal scroll
executor.performScrollLeftToRight();
executor.performScrollRightToLeft();

// Custom scroll
executor.performScroll(fromX, fromY, toX, toY);
```

### Validation

```java
// Check if element is displayed
assert executor.isElementsDisplayedWithLabel("Success Message");

// Check element text
assert executor.validateElementText("Expected Text", element);

// Multiple elements
assert executor.isElementsDisplayedWithLabel("Button1", "Button2", "Button3");
```

## Real-World Example: Login Test

```java
import com.evig.sapphire.GenericExecutor;
import com.evig.sapphire.provider.DriverProvider;
import org.testng.annotations.*;

public class LoginTest {
    GenericExecutor executor;

    @BeforeMethod
    public void setup() throws Exception {
        DriverProvider.CapabilitiesBuilder builder =
            new DriverProvider.CapabilitiesBuilder(
                System.getenv("APP_PATH"),
                "android",
                "13",
                "emulator-5554"
            )
            .setWaitTimeInSeconds(10)
            .setAppiumServerURL("http://localhost:4723")
            .setExtraCapability("noReset", "true")
            .setExtraCapability("autoGrantPermissions", "true");

        executor = new GenericExecutor(builder.build());
        executor.extentReporter.startReportingForTest("Login Test");
    }

    @Test
    public void testSuccessfulLogin() {
        // Verify app launched
        assert executor.validateAppLaunchWithElements("Sign In", "Join");

        // Navigate to login
        executor.clickOnElementWithLabel("Sign In");
        executor.waitForSeconds(1);

        // Enter credentials
        executor.setTextInElementsWithLabel("user@example.com", "Email");
        executor.setTextInElementsWithLabel("SecurePass123", "Password");

        // Submit
        executor.clickOnElementWithLabel("Sign In");

        // Verify success
        executor.waitForSeconds(2);
        assert executor.isElementsDisplayedWithLabel("Welcome", "Dashboard");

        executor.extentReporter.trackReport(
            com.relevantcodes.extentreports.LogStatus.PASS,
            "Login successful"
        );
    }

    @AfterMethod
    public void teardown() {
        executor.extentReporter.stopReportingForTest();
        executor.quitDriver();
    }
}
```

## Page Object Model Example

```java
// LoginPage.java
public class LoginPage {
    private final GenericExecutor executor;

    public LoginPage(GenericExecutor executor) {
        this.executor = executor;
    }

    public void login(String email, String password) {
        executor.clickOnElementWithLabel("Sign In");
        executor.setTextInElementsWithLabel(email, "Email");
        executor.setTextInElementsWithLabel(password, "Password");
        executor.clickOnElementWithLabel("Sign In");
    }

    public boolean isWelcomeDisplayed() {
        return executor.isElementsDisplayedWithLabel("Welcome");
    }
}

// Test using Page Object
@Test
public void testLoginWithPageObject() {
    LoginPage loginPage = new LoginPage(executor);
    loginPage.login("user@example.com", "password");
    assert loginPage.isWelcomeDisplayed();
}
```

## Data-Driven Testing

```java
@DataProvider
public Object[][] loginData() {
    return new Object[][] {
        {"user1@example.com", "pass1"},
        {"user2@example.com", "pass2"},
        {"user3@example.com", "pass3"}
    };
}

@Test(dataProvider = "loginData")
public void testMultipleLogins(String email, String password) {
    executor.clickOnElementWithLabel("Sign In");
    executor.setTextInElementsWithLabel(email, "Email");
    executor.setTextInElementsWithLabel(password, "Password");
    executor.clickOnElementWithLabel("Sign In");
    assert executor.isElementsDisplayedWithLabel("Welcome");
}
```

## Configuration

Create `config.properties`:

```properties
appium.server.url=http://localhost:4723
implicit.wait=10
screenshot.on.failure=true
headless=false
```

Load configuration:

```java
import com.evig.sapphire.config.SapphireConfig;
import org.aeonbits.owner.ConfigFactory;

SapphireConfig config = ConfigFactory.create(SapphireConfig.class);
String appiumUrl = config.appiumServerUrl();
```

## Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=LoginTest

# Run specific test method
mvn test -Dtest=LoginTest#testSuccessfulLogin

# Run with system properties
mvn test -Dappium.server.url=http://localhost:4723 -Dheadless=true

# Generate reports
mvn test
# Reports in: report/ and target/surefire-reports/
```

## Troubleshooting

### Tests fail with "Connection refused"

```bash
# Make sure Appium is running
appium

# Verify it's accessible
curl http://localhost:4723/status
```

### "No such element" errors

```java
// Increase wait time
executor.setDefaultWaitTimeInSeconds(15);

// Or use explicit wait
executor.findElementWithLabel(20, "Element Text");

// Or scroll to find
executor.performScrollAndFindElementWithLabel("Element Text");
```

### App not installing

```bash
# For Android - check if app is valid
adb install your-app.apk

# For iOS - check if app is valid
xcrun simctl install booted your-app.app
```

## Next Steps

1. ✅ Read the [Full Documentation](../README.md)
2. ✅ Explore [Testing Guide](TESTING_GUIDE.md)
3. ✅ Learn about [Architecture](ARCHITECTURE.md)
4. ✅ Set up [CI/CD](.github/workflows/)
5. ✅ Try [Headless Execution](examples/HeadlessAndroidTest.java)

## Getting Help

- **GitHub Issues**: [Report bugs](https://github.com/kavyasoni/sapphire/issues)
- **Discussions**: [Ask questions](https://github.com/kavyasoni/sapphire/discussions)
- **Examples**: Check `examples/` directory

---

**Happy Testing! 💎**
