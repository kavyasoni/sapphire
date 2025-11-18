# Sapphire Architecture

Deep dive into the Sapphire framework architecture, design decisions, and extension points.

## Overview

Sapphire is built on a layered architecture that separates concerns and provides clear extension points.

```
┌─────────────────────────────────────────────────────────────┐
│                    Test Layer                               │
│  (User Tests, Page Objects, Test Data)                     │
└─────────────────────┬───────────────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────────────┐
│                Execution Layer                              │
│  (GenericExecutor, TestDataProvider, ExtentReporter)       │
└─────────────────────┬───────────────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────────────┐
│              Inspection Layer                               │
│  (ElementInspector, Locator Strategies, Wait Mechanisms)   │
└─────────────────────┬───────────────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────────────┐
│                Driver Layer                                 │
│  (DriverProvider, Capabilities Builder, Session Manager)   │
└─────────────────────┬───────────────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────────────┐
│              Platform Layer                                 │
│         (Appium, Selenium WebDriver)                        │
└─────────────────────────────────────────────────────────────┘
```

## Core Components

### 1. Driver Layer

#### DriverProvider

Responsible for creating and managing WebDriver sessions.

**Key Features:**
- Builder pattern for capabilities
- Thread-local driver management
- Support for local and cloud providers
- Platform-specific configuration

```java
public class DriverProvider {
    private final ThreadLocal<AppiumDriver<WebElement>> driver = new ThreadLocal<>();

    public WebDriver getDriver() throws MalformedURLException {
        // Create driver based on capabilities
    }

    public static class CapabilitiesBuilder {
        // Fluent API for building capabilities
    }
}
```

**Extension Point:** Implement custom `DriverProvider` for additional platforms.

### 2. Inspection Layer

#### ElementInspector

Core element location and inspection functionality.

**Key Features:**
- Multiple locator strategies (XPath, label, accessibility ID)
- Smart waiting mechanisms
- Cross-platform element location
- Parent-child element traversal

```java
public class ElementInspector {
    // Find by label (cross-platform)
    public WebElement findElementWithLabel(String label)

    // Find by XPath
    public WebElement findElementByXpath(By xpath)

    // Find with custom wait
    public WebElement findElementByXpath(int waitTimeInSeconds, By xpath)

    // Find all elements
    public List<WebElement> findAllElementsWithLabel(String label)
}
```

**Design Decisions:**
- Overloaded methods for flexibility
- Default wait times for convenience
- Null-safe returns
- Platform-agnostic API

### 3. Execution Layer

#### GenericExecutor

High-level test execution and interaction methods.

**Key Features:**
- Element interactions (click, setText, etc.)
- Validation methods
- Scrolling and gestures
- Navigation utilities
- Integrated reporting

```java
public class GenericExecutor extends ElementInspector {
    public ExtentReporter extentReporter;

    // Click actions
    public boolean clickOnElementWithLabel(String... elementsLabel)

    // Text input
    public void setTextInElementsWithLabel(String inputText, String... elementsLabel)

    // Validation
    public boolean isElementsDisplayedWithLabel(String... elementsLabel)

    // Scrolling
    public void performScrollToBottom(int pages)

    // Navigation
    public void goBack()
}
```

**Design Decisions:**
- Extends ElementInspector for composition
- Varargs for bulk operations
- Boolean returns for assertions
- Integrated logging/reporting

### 4. Reporting Layer

#### ExtentReporter

Test execution reporting and logging.

**Key Features:**
- HTML report generation
- Screenshot attachment
- Pass/Fail tracking
- Test step logging

**Integration:**
- Embedded in GenericExecutor
- Automatic screenshot on failure (when enabled)
- Per-test and per-suite reporting

### 5. Data Layer

#### TestDataProvider

Data-driven testing support.

**Supported Formats:**
- Excel (.xlsx, .xls)
- JSON (.json)
- Custom data structures

```java
public class TestDataProvider {
    // From Excel
    public static Object[][] provideDataFromExcelFile(String fileName)

    // From JSON
    public static Object[][] provideDataFromJSONFile(String fileName)
}
```

## Design Patterns

### 1. Builder Pattern

Used in `CapabilitiesBuilder` for fluent API:

```java
DriverProvider.CapabilitiesBuilder builder =
    new DriverProvider.CapabilitiesBuilder(app, platform, version, device)
        .setWaitTimeInSeconds(10)
        .setOrientation("landscape")
        .setAppiumServerURL("http://localhost:4723")
        .setExtraCapability("key", "value")
        .build();
```

### 2. Page Object Model

Recommended pattern for test organization:

```java
public class LoginPage {
    private final GenericExecutor executor;

    public LoginPage(GenericExecutor executor) {
        this.executor = executor;
    }

    public void login(String email, String password) {
        executor.setTextInElementsWithLabel(email, "Email");
        executor.setTextInElementsWithLabel(password, "Password");
        executor.clickOnElementWithLabel("Sign In");
    }
}
```

### 3. Data Provider Pattern

TestNG integration for data-driven tests:

```java
@DataProvider
public Object[][] loginData() {
    return TestDataProvider.provideDataFromExcelFile("logins.xlsx");
}

@Test(dataProvider = "loginData")
public void testLogin(HashMap<String, String> data) {
    // Test with data
}
```

## Configuration Architecture

### Type-Safe Configuration

Uses Owner library for configuration management:

```java
@Config.Sources({
    "system:properties",
    "system:env",
    "classpath:config.properties"
})
public interface SapphireConfig extends Config {
    @Key("appium.server.url")
    @DefaultValue("http://0.0.0.0:4723")
    String appiumServerUrl();
}
```

### Configuration Priority

1. System properties (`-Dkey=value`)
2. Environment variables
3. config.properties file
4. Default values in interface

## Logging Architecture

### SLF4J + Logback

```
Application Code
       ↓
    SLF4J API (facade)
       ↓
  Logback (implementation)
       ↓
  ┌──────┴──────┐
  ↓             ↓
Console     File Appender
Appender    (with rolling)
```

### Log Levels

- **TRACE**: Very detailed (disabled by default)
- **DEBUG**: Debugging information
- **INFO**: General information
- **WARN**: Warning messages
- **ERROR**: Error messages

### Configuring Logging

Edit `src/main/resources/logback.xml`:

```xml
<!-- Set framework log level -->
<logger name="com.evig.sapphire" level="DEBUG"/>

<!-- Set Appium log level -->
<logger name="io.appium" level="WARN"/>
```

## Extension Points

### 1. Custom Locator Strategies

Extend ElementInspector:

```java
public class CustomElementInspector extends ElementInspector {
    public WebElement findByAccessibilityId(String id) {
        return findElementByXpath(By.accessibilityId(id));
    }
}
```

### 2. Custom Executors

Extend GenericExecutor:

```java
public class CustomExecutor extends GenericExecutor {
    public void performCustomAction() {
        // Custom logic
    }
}
```

### 3. Custom Reporters

Implement custom reporter:

```java
public class CustomReporter {
    public void logStep(String message) {
        // Custom reporting logic
    }
}
```

### 4. Platform-Specific Implementations

```java
public class AndroidExecutor extends GenericExecutor {
    public void openNotifications() {
        getAndroidDriver().openNotifications();
    }
}

public class IOSExecutor extends GenericExecutor {
    public void pressHomeButton() {
        // iOS-specific implementation
    }
}
```

## Thread Safety

### Driver Management

- Uses `ThreadLocal<AppiumDriver>` for thread-safe parallel execution
- Each thread gets its own driver instance
- Automatic cleanup on thread termination

### Parallel Test Execution

```xml
<suite name="Parallel Suite" parallel="tests" thread-count="4">
    <!-- Tests run in parallel -->
</suite>
```

## Error Handling

### Exception Hierarchy

```
RuntimeException
    └── SapphireException (planned)
            ├── DriverException
            ├── ElementNotFoundException
            ├── TimeoutException
            └── ConfigurationException
```

### Current Error Handling

- Null-safe returns from element finders
- Try-catch in critical paths
- Logging of errors
- Screenshot on failure (optional)

## Performance Considerations

### 1. Wait Strategies

- Implicit waits (default)
- Explicit waits (recommended for stability)
- Fluent waits (planned)

### 2. Locator Optimization

- Prefer ID and accessibility ID over XPath
- Use relative XPath, not absolute
- Cache frequently used elements

### 3. Session Management

- Reuse driver sessions when possible
- Use `noReset` capability for faster tests
- Proper cleanup in `@AfterMethod`

## Future Enhancements

### Planned Features

1. **Fluent API Redesign**
   ```java
   executor.find("Submit")
          .waitUntil(visible())
          .click();
   ```

2. **Plugin Architecture**
   ```java
   Sapphire.register(new CustomPlugin());
   ```

3. **Visual Testing**
   ```java
   executor.assertVisualMatch("expected-screenshot.png");
   ```

4. **AI-Powered Element Detection**
   ```java
   executor.findByAI("login button");
   ```

5. **Performance Metrics**
   ```java
   PerformanceReport report = executor.getPerformanceMetrics();
   ```

## Best Practices

1. **Separation of Concerns**: Keep tests, page objects, and framework separate
2. **DRY Principle**: Reuse common methods
3. **Explicit Waits**: Prefer explicit waits over Thread.sleep()
4. **Logging**: Use SLF4J, not System.out
5. **Configuration**: Externalize all configuration
6. **Clean Up**: Always quit driver in @AfterMethod
7. **Assertions**: Use AssertJ for fluent assertions

## Contributing to Architecture

When proposing architectural changes:

1. Open an issue describing the problem
2. Discuss the design approach
3. Submit a PR with:
   - Implementation
   - Tests
   - Documentation updates
   - Migration guide (if breaking)

---

For implementation details, see the source code in `src/main/java/com/evig/sapphire/`.
