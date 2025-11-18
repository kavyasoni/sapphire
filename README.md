# 💎 Sapphire - Modern Mobile Test Automation Framework

<div align="center">

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-17%2B-orange.svg)](https://www.oracle.com/java/)
[![Appium](https://img.shields.io/badge/Appium-2.x-purple.svg)](https://appium.io/)
[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)](https://github.com/kavyasoni/sapphire/actions)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](CONTRIBUTING.md)

**A production-grade, open-source automation framework that makes mobile app testing significantly easier, faster, and more reliable.**

[Features](#-features) •
[Quick Start](#-quick-start) •
[Documentation](#-documentation) •
[Examples](#-examples) •
[Contributing](#-contributing)

</div>

---

## 🎯 **What is Sapphire?**

Sapphire is a **modern, production-ready mobile test automation framework** built on top of Appium 2.x for iOS and Android testing. It transforms complex Appium commands into simple, intuitive APIs that focus on **what you want to test**, not **how to write XPath**.

### **The Sapphire Promise**

✨ **Easy** - Find elements by labels, not complex XPaths
🚀 **Fast** - Smart waits and retries eliminate flaky tests
🔍 **Reliable** - Comprehensive logging, screenshots, and detailed reports
🎯 **Production-Ready** - Battle-tested patterns and enterprise-grade quality
🌍 **Cross-Platform** - Single API for both iOS and Android
🔧 **Extensible** - Clean architecture with clear extension points

---

## 🎪 **Why Sapphire?**

### Traditional Appium is Verbose and Brittle

```java
// ❌ Traditional Appium - Too much boilerplate
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
By locator = By.xpath("//*[@label='Submit' or @text='Submit' or @name='Submit']");
WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
if (element != null && element.isDisplayed()) {
    element.click();
}
```

### Sapphire is Simple and Powerful

```java
// ✅ Sapphire - Clean and intuitive
executor.clickOnElementWithLabel("Submit");
```

### The Difference

| Traditional Appium | Sapphire |
|-------------------|----------|
| 6+ lines of code | 1 line of code |
| Platform-specific XPaths | Cross-platform labels |
| Manual wait management | Automatic smart waits |
| Complex error handling | Built-in retry logic |
| Basic logging | Rich reporting with screenshots |

---

## ✨ **Key Features**

### 🎯 **Core Capabilities**

#### **Label-Based Element Finding**
Find elements by visible text - works across iOS and Android without platform-specific code:
```java
executor.findElementWithLabel("Sign In");
executor.findElementContainsLabel("Sign");  // Partial match
```

#### **Smart Waiting & Retries**
Automatic intelligent waits with configurable timeouts:
```java
executor.findElementWithLabel(15, "Slow Element");  // Wait up to 15 seconds
executor.performScrollAndFindElementWithLabel("Hidden Element");  // Scroll until found
```

#### **Gesture Support**
Built-in support for all common mobile gestures:
```java
executor.performScrollToBottom(3);      // Scroll 3 pages down
executor.performScrollLeftToRight();    // Swipe horizontally
executor.performScroll(x1, y1, x2, y2); // Custom gestures
```

#### **Screenshot on Failure**
Automatic screenshot capture when tests fail:
```java
// Configured in properties
screenshot.on.failure=true
```

#### **Rich Reporting**
Beautiful HTML reports with screenshots, logs, and metrics:
```java
executor.extentReporter.startReportingForTest("Login Test");
executor.extentReporter.trackReport(LogStatus.PASS, "Login successful");
```

### 📱 **Platform Support**

| Platform | Support | Versions |
|----------|---------|----------|
| ✅ **Android** | Native, Hybrid, Mobile Web | 7.0+ |
| ✅ **iOS** | Native, Hybrid, Mobile Web | 12.0+ |
| ✅ **Real Devices** | Local & Remote | All |
| ✅ **Simulators/Emulators** | Full Support | All |
| ✅ **Cloud Providers** | Sauce Labs, BrowserStack, AWS Device Farm | All |

### 🔧 **Advanced Features**

- **Page Object Model Support** - Built-in base classes and patterns
- **Data-Driven Testing** - Excel and JSON data providers
- **Parallel Execution** - Run tests concurrently for faster feedback
- **Headless Execution** - CI/CD ready with headless emulator support
- **Video Recording** - Optional test execution recording
- **Configuration Management** - Type-safe configuration with external properties
- **Logging Framework** - SLF4J + Logback for structured logging
- **Allure Integration** - Beautiful test reports with Allure

---

## 🚀 **Quick Start**

### Prerequisites

```bash
# Java 17 or higher
java -version

# Maven 3.8+
mvn -version

# Appium 2.x
npm install -g appium@next
appium driver install uiautomator2  # For Android
appium driver install xcuitest      # For iOS

# Start Appium Server
appium
```

### Installation

Add Sapphire to your `pom.xml`:

```xml
<dependency>
    <groupId>com.evig.sapphire</groupId>
    <artifactId>sapphire</artifactId>
    <version>2.0.0</version>
    <scope>test</scope>
</dependency>
```

### Your First Test (5 Minutes)

```java
import com.evig.sapphire.GenericExecutor;
import com.evig.sapphire.provider.DriverProvider;
import org.testng.annotations.*;

public class MyFirstTest {
    GenericExecutor executor;

    @BeforeMethod
    public void setup() throws Exception {
        // Configure capabilities
        DriverProvider.CapabilitiesBuilder builder =
            new DriverProvider.CapabilitiesBuilder(
                "/path/to/your/app.apk",  // Your app path
                "android",                 // Platform: android or ios
                "13",                      // OS version
                "emulator-5554"            // Device name
            );

        executor = new GenericExecutor(builder.build());
    }

    @Test
    public void myFirstTest() {
        // Find and click "Sign In"
        executor.clickOnElementWithLabel("Sign In");

        // Enter credentials
        executor.setTextInElementsWithLabel("test@example.com", "Email");
        executor.setTextInElementsWithLabel("password123", "Password");

        // Submit
        executor.clickOnElementWithLabel("Sign In");

        // Verify success
        assert executor.isElementsDisplayedWithLabel("Welcome");
    }

    @AfterMethod
    public void teardown() {
        executor.quitDriver();
    }
}
```

Run your test:
```bash
mvn test -Dtest=MyFirstTest
```

**🎉 That's it! Your first test is running!**

---

## 📚 **Documentation**

### **User Guides**

| Guide | Description |
|-------|-------------|
| [Quick Start](docs/QUICKSTART.md) | Get started in 5 minutes |
| [Testing Guide](docs/TESTING_GUIDE.md) | Comprehensive testing guide with headless & CI/CD |
| [Architecture](docs/ARCHITECTURE.md) | Framework architecture and design |
| [Migration Guide](docs/MIGRATION_GUIDE.md) | Upgrade from 1.x to 2.0 |

### **Core Concepts**

#### 1. Element Finding Strategies

```java
// By label (recommended - cross-platform)
executor.findElementWithLabel("Submit");
executor.findElementContainsLabel("Sub");  // Partial match

// Case-insensitive search
executor.findElementWithLabel("submit", true);

// With custom timeout
executor.findElementWithLabel(20, "Submit");  // Wait up to 20 seconds

// By XPath (when needed)
executor.findElementByXpath("//button[@id='submit']");

// Scroll to find
WebElement el = executor.performScrollAndFindElementWithLabel("Hidden Element");
```

#### 2. Element Interactions

```java
// Click
executor.clickOnElementWithLabel("Button");
executor.clickOnElement(webElement);

// Text input
executor.setTextInElementsWithLabel("John Doe", "Name");
executor.clearElementText(webElement);

// Validation
boolean displayed = executor.isElementsDisplayedWithLabel("Success");
boolean textMatches = executor.validateElementText("Expected", element);
```

#### 3. Gestures & Scrolling

```java
// Vertical scrolling
executor.performScrollToBottom();        // Scroll to bottom
executor.performScrollToBottom(3);       // Scroll 3 pages down
executor.performScrollToTop();           // Scroll to top

// Horizontal scrolling
executor.performScrollLeftToRight();
executor.performScrollRightToLeft();

// Custom gestures
executor.performScroll(fromX, fromY, toX, toY);
```

#### 4. Configuration

```java
// Create config.properties
appium.server.url=http://localhost:4723
implicit.wait=10
screenshot.on.failure=true
headless=false

// Use type-safe configuration
SapphireConfig config = ConfigFactory.create(SapphireConfig.class);
String url = config.appiumServerUrl();
```

#### 5. Reporting

```java
// Start test reporting
executor.extentReporter.startReportingForTest("Login Test");

// Log test steps
executor.extentReporter.trackReport(LogStatus.PASS, "User logged in successfully");

// Stop reporting
executor.extentReporter.stopReportingForTest();

// Reports generated at: report/[platform]/ExtentReport.html
```

---

## 💡 **Examples**

### Complete Login Test

```java
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

        // Enter credentials
        executor.setTextInElementsWithLabel("user@example.com", "Email");
        executor.setTextInElementsWithLabel("SecurePass123", "Password");

        // Submit
        executor.clickOnElementWithLabel("Sign In");
        executor.waitForSeconds(2);

        // Verify success
        assert executor.isElementsDisplayedWithLabel("Welcome", "Dashboard");

        executor.extentReporter.trackReport(LogStatus.PASS, "Login successful");
    }

    @AfterMethod
    public void teardown() {
        executor.extentReporter.stopReportingForTest();
        executor.quitDriver();
    }
}
```

### Page Object Model

```java
// LoginPage.java
public class LoginPage {
    private final GenericExecutor executor;

    public LoginPage(GenericExecutor executor) {
        this.executor = executor;
    }

    public void enterEmail(String email) {
        executor.setTextInElementsWithLabel(email, "Email");
    }

    public void enterPassword(String password) {
        executor.setTextInElementsWithLabel(password, "Password");
    }

    public void clickSignIn() {
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
    loginPage.enterEmail("user@example.com");
    loginPage.enterPassword("password");
    loginPage.clickSignIn();
    assert loginPage.isWelcomeDisplayed();
}
```

### Data-Driven Testing

```java
@DataProvider
public Object[][] loginData() {
    return TestDataProvider.provideDataFromExcelFile("login_data.xlsx");
}

@Test(dataProvider = "loginData")
public void testMultipleLogins(HashMap<String, String> data) {
    String email = data.get("email");
    String password = data.get("password");

    executor.clickOnElementWithLabel("Sign In");
    executor.setTextInElementsWithLabel(email, "Email");
    executor.setTextInElementsWithLabel(password, "Password");
    executor.clickOnElementWithLabel("Sign In");

    assert executor.isElementsDisplayedWithLabel("Welcome");
}
```

### Headless Execution (CI/CD)

```java
// HeadlessTest.java
SapphireConfig config = ConfigFactory.create(SapphireConfig.class);

DriverProvider.CapabilitiesBuilder builder =
    new DriverProvider.CapabilitiesBuilder(app, platform, version, device)
        .setExtraCapability("isHeadless", String.valueOf(config.headless()))
        .setExtraCapability("avdArgs", "-no-window -no-audio");

// Run headless
mvn test -Dheadless=true
```

See [examples/](examples/) for more complete examples.

---

## 🏗️ **Architecture**

Sapphire follows a clean, layered architecture:

```
┌─────────────────────────────────────────────────────────┐
│              Test Layer (Your Tests)                    │
└──────────────────────┬──────────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────────┐
│         Execution Layer (GenericExecutor)               │
│  • Element interactions  • Validation  • Gestures       │
└──────────────────────┬──────────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────────┐
│       Inspection Layer (ElementInspector)               │
│  • Element finding  • Waits  • Locator strategies       │
└──────────────────────┬──────────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────────┐
│          Driver Layer (DriverProvider)                  │
│  • Session management  • Capabilities  • Configuration  │
└──────────────────────┬──────────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────────┐
│              Appium / Selenium                          │
└─────────────────────────────────────────────────────────┘
```

**Key Design Principles:**
- **Separation of Concerns** - Each layer has a clear responsibility
- **Extensibility** - Easy to extend with custom functionality
- **Cross-Platform** - Single API for iOS and Android
- **Testability** - Framework code is testable with unit tests

Read more in [Architecture Documentation](docs/ARCHITECTURE.md).

---

## 🧪 **Testing**

### Run Tests

```bash
# All tests
mvn test

# Specific platform
mvn test -Pandroid
mvn test -Pios

# Specific test
mvn test -Dtest=LoginTest

# Headless mode
mvn test -Dheadless=true

# With coverage
mvn test jacoco:report
```

### Docker-Based Testing

```bash
# Start complete test environment (Appium + Emulator + Tests)
docker-compose up

# Run tests in container
docker-compose up sapphire-tests

# View logs
docker-compose logs -f

# Clean up
docker-compose down -v
```

### CI/CD Integration

Sapphire includes pre-configured GitHub Actions:

```yaml
# .github/workflows/ci.yml
- Builds on every push
- Runs tests on PR
- Multi-OS (Ubuntu, macOS)
- Multi-Java (17, 21)
- Security scanning
- Code quality checks
```

See [Testing Guide](docs/TESTING_GUIDE.md) for comprehensive testing documentation.

---

## 🛠️ **Configuration**

### Supported Platforms & Versions

| Platform | Min Version | Tested Versions |
|----------|-------------|-----------------|
| Android | 7.0 (API 24) | 7.0, 9.0, 11, 12, 13, 14 |
| iOS | 12.0 | 12.0, 13.0, 14.0, 15.0, 16.0, 17.0 |
| Appium | 2.0+ | 2.0, 2.5 |
| Java | 17+ | 17, 21 |

### Configuration Options

Create `config.properties`:

```properties
# Appium Server
appium.server.url=http://localhost:4723
appium.command.timeout=60

# Waits (seconds)
implicit.wait=10
explicit.wait=30
page.load.timeout=30

# Retry
retry.count=3
retry.delay=1000

# Screenshots & Recording
screenshot.on.failure=true
screenshot.directory=./screenshots
video.recording=false
video.directory=./videos

# Reporting
report.directory=./report
log.level=INFO

# Headless
headless=false
```

Override with system properties:
```bash
mvn test -Dappium.server.url=http://remote:4723 -Dheadless=true
```

---

## 📊 **Reporting**

Sapphire generates comprehensive test reports:

### ExtentReports
Beautiful HTML reports with screenshots, logs, and metrics.

**Location:** `report/[platform]/ExtentReport.html`

Features:
- Test execution timeline
- Pass/Fail statistics
- Screenshots on failure
- Detailed logs
- Environment info

### Allure Reports

```bash
# Generate Allure report
mvn test
allure serve target/allure-results
```

### TestNG Reports

Default TestNG HTML reports.

**Location:** `target/surefire-reports/index.html`

---

## 🤝 **Contributing**

We welcome contributions! Sapphire is open-source and community-driven.

### How to Contribute

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/amazing-feature`)
3. **Commit** your changes (`git commit -m 'Add amazing feature'`)
4. **Push** to the branch (`git push origin feature/amazing-feature`)
5. **Open** a Pull Request

### Contribution Guidelines

- Follow [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- Write tests for new features
- Update documentation
- Ensure CI passes

Read our [Contributing Guide](CONTRIBUTING.md) for detailed guidelines.

### Code of Conduct

We follow the [Contributor Covenant Code of Conduct](CODE_OF_CONDUCT.md).

### Recognition

Contributors are recognized in:
- README.md (Contributors section)
- Release notes
- GitHub contributors page

---

## 🛡️ **Security**

Security is a top priority. We:

✅ Maintain zero known vulnerabilities
✅ Regularly update dependencies
✅ Scan for security issues in CI/CD
✅ Follow secure coding practices

**Report Security Issues:** See [SECURITY.md](SECURITY.md)

---

## 📄 **License**

Sapphire is licensed under the [MIT License](LICENSE).

```
MIT License

Copyright (c) 2025 Sapphire Contributors

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction...
```

**You are free to:**
- ✅ Use commercially
- ✅ Modify
- ✅ Distribute
- ✅ Use privately

---

## 🙏 **Acknowledgments**

Sapphire is built on top of excellent open-source projects:

- [Appium](https://appium.io/) - Mobile automation framework
- [Selenium](https://www.selenium.dev/) - Web automation standard
- [TestNG](https://testng.org/) - Testing framework
- [ExtentReports](https://www.extentreports.com/) - Test reporting
- [Allure](https://docs.qameta.io/allure/) - Test reporting
- [Logback](https://logback.qos.ch/) - Logging framework

Special thanks to all [contributors](https://github.com/kavyasoni/sapphire/graphs/contributors)!

---

## 📞 **Support & Community**

### Get Help

- 📖 **Documentation**: [Wiki](https://github.com/kavyasoni/sapphire/wiki)
- 💬 **Discussions**: [GitHub Discussions](https://github.com/kavyasoni/sapphire/discussions)
- 🐛 **Issues**: [Bug Reports](https://github.com/kavyasoni/sapphire/issues)
- 📧 **Contact**: Create an issue or discussion

### Stay Updated

- ⭐ **Star** this repository
- 👀 **Watch** for releases
- 🔔 **Subscribe** to discussions

---

## 🗺️ **Roadmap**

### Current (v2.0.0)
- ✅ Appium 2.x support
- ✅ Java 17 support
- ✅ Modern logging (SLF4J + Logback)
- ✅ ExtentReports 5.x
- ✅ Allure integration
- ✅ Headless execution
- ✅ Docker support
- ✅ CI/CD ready

### Upcoming (v2.1.0)
- [ ] Enhanced fluent API
- [ ] Improved error messages
- [ ] More gesture patterns
- [ ] Performance metrics

### Future (v3.0.0)
- [ ] Playwright integration (for web testing)
- [ ] Visual regression testing
- [ ] AI-powered element detection
- [ ] Performance testing capabilities
- [ ] iOS/Android inspector tool

See [CHANGELOG.md](CHANGELOG.md) for version history.

---

## 📈 **Project Stats**

![GitHub stars](https://img.shields.io/github/stars/kavyasoni/sapphire?style=social)
![GitHub forks](https://img.shields.io/github/forks/kavyasoni/sapphire?style=social)
![GitHub watchers](https://img.shields.io/github/watchers/kavyasoni/sapphire?style=social)
![GitHub contributors](https://img.shields.io/github/contributors/kavyasoni/sapphire)
![GitHub issues](https://img.shields.io/github/issues/kavyasoni/sapphire)
![GitHub pull requests](https://img.shields.io/github/issues-pr/kavyasoni/sapphire)

---

## 🎯 **Why Choose Sapphire?**

### For Developers
- 🚀 **Faster Development** - Write tests 5x faster with intuitive APIs
- 🎯 **Better Reliability** - Smart waits eliminate flaky tests
- 🔍 **Easier Debugging** - Rich logging and screenshots
- 📚 **Great Documentation** - Comprehensive guides and examples

### For Teams
- 💰 **Zero Cost** - Completely free and open-source
- 🛡️ **Enterprise Quality** - Production-grade code and architecture
- 🤝 **Community Support** - Active community and regular updates
- 🔧 **Customizable** - Extend and adapt to your needs

### For Organizations
- ✅ **Proven** - Used in production by multiple teams
- 🔒 **Secure** - Zero known vulnerabilities
- 📊 **Maintainable** - Clean code, well-documented
- 🌍 **Cross-Platform** - Single framework for iOS and Android

---

<div align="center">

## ⭐ **Star Us on GitHub!**

If Sapphire helps you, please star the repository!

**Made with ❤️ by the Sapphire Community**

[⬆ Back to Top](#-sapphire---modern-mobile-test-automation-framework)

</div>
