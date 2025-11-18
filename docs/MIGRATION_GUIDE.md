# Migration Guide: Sapphire 1.x → 2.0

This guide helps you migrate from Sapphire 1.x to 2.0.

## Overview of Changes

Sapphire 2.0 is a major update that modernizes the framework:

- ✅ Java 17 (from Java 8)
- ✅ Appium 9.3.0 / Appium 2.x (from Appium 7.3.0 / Appium 1.x)
- ✅ Selenium 4.27.0 (from 4.0.0-alpha-7)
- ✅ Modern logging with SLF4J + Logback
- ✅ Removed Log4j (security vulnerability)
- ✅ Modern reporting with ExtentReports 5.x
- ✅ Enhanced configuration management
- ✅ Headless execution support
- ✅ CI/CD ready

## Breaking Changes

### 1. Java Version

**Before (1.x):**
- Java 8+

**After (2.0):**
- Java 17+ required

**Migration:**
```bash
# Update JAVA_HOME
export JAVA_HOME=/path/to/jdk-17

# Update pom.xml
<maven.compiler.source>17</maven.compiler.source>
<maven.compiler.target>17</maven.compiler.target>
```

### 2. Dependency Updates

**Before (1.x):**
```xml
<dependency>
    <groupId>io.appium</groupId>
    <artifactId>java-client</artifactId>
    <version>7.3.0</version>
</dependency>
```

**After (2.0):**
```xml
<dependency>
    <groupId>io.appium</groupId>
    <artifactId>java-client</artifactId>
    <version>9.3.0</version>
</dependency>
```

### 3. Appium Server

**Before (1.x):**
```bash
npm install -g appium@1.22.3
appium
```

**After (2.0):**
```bash
npm install -g appium@next
appium driver install uiautomator2
appium driver install xcuitest
appium
```

### 4. Logging

**Before (1.x):**
- Used System.out.println
- Log4j 1.2.17 (vulnerable)

**After (2.0):**
- SLF4J + Logback
- Structured logging

**Migration:**

Replace:
```java
System.out.println("Message");
```

With:
```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

private static final Logger logger = LoggerFactory.getLogger(YourClass.class);
logger.info("Message");
```

### 5. ExtentReports

**Before (1.x):**
```xml
<dependency>
    <groupId>com.relevantcodes</groupId>
    <artifactId>extentreports</artifactId>
    <version>2.41.2</version>
</dependency>
```

**After (2.0):**
```xml
<dependency>
    <groupId>com.aventstack</groupId>
    <artifactId>extentreports</artifactId>
    <version>5.1.2</version>
</dependency>
```

API changes are minimal; ExtentReporter wrapper maintains compatibility.

## Non-Breaking Changes (Backward Compatible)

### 1. Configuration

**New feature** - Type-safe configuration:

```java
SapphireConfig config = ConfigFactory.create(SapphireConfig.class);
String appiumUrl = config.appiumServerUrl();
int waitTime = config.implicitWait();
```

config.properties:
```properties
appium.server.url=http://localhost:4723
implicit.wait=10
screenshot.on.failure=true
```

**Old method still works:**
```java
String url = System.getProperty("appium.server.url", "http://localhost:4723");
```

### 2. Headless Execution

**New feature:**

```bash
mvn test -Dheadless=true
```

Or in config.properties:
```properties
headless=true
```

### 3. Enhanced Reporting

**New feature** - Allure support:

```bash
mvn test
allure serve target/allure-results
```

## Step-by-Step Migration

### Step 1: Update Java

```bash
# Install Java 17
# Update JAVA_HOME
java -version  # Verify Java 17+
```

### Step 2: Update pom.xml

Replace your pom.xml with the new version from Sapphire 2.0, or update manually:

```xml
<properties>
    <java.version>17</java.version>
    <appium.version>9.3.0</appium.version>
    <selenium.version>4.27.0</selenium.version>
    <testng.version>7.10.1</testng.version>
</properties>
```

Remove Log4j:
```xml
<!-- REMOVE THIS -->
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
```

Add SLF4J + Logback:
```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.12</version>
</dependency>
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.5.3</version>
</dependency>
```

### Step 3: Update Appium Server

```bash
# Uninstall old Appium
npm uninstall -g appium

# Install Appium 2.x
npm install -g appium@next

# Install drivers
appium driver install uiautomator2  # Android
appium driver install xcuitest      # iOS

# Verify
appium --version  # Should show 2.x
```

### Step 4: Add Logging Configuration

Create `src/main/resources/logback.xml`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <logger name="com.evig.sapphire" level="DEBUG"/>
    <logger name="io.appium" level="WARN"/>

    <root level="INFO">
        <appender-ref ref="STDOUT"/>
    </root>
</configuration>
```

### Step 5: Update Logging in Tests

Find and replace:
```bash
# Find System.out usage
grep -r "System.out" src/

# Replace with logger
```

Before:
```java
System.out.println("Test started");
```

After:
```java
private static final Logger logger = LoggerFactory.getLogger(MyTest.class);
logger.info("Test started");
```

### Step 6: Test Your Migration

```bash
# Clean build
mvn clean compile

# Run tests
mvn test

# Check for deprecation warnings
mvn clean compile 2>&1 | grep -i "deprecat"
```

### Step 7: Update CI/CD

If using CI/CD, update:

1. Java version to 17
2. Appium installation to 2.x
3. Add driver installation

Example GitHub Actions:
```yaml
- name: Set up JDK 17
  uses: actions/setup-java@v4
  with:
    java-version: 17

- name: Install Appium
  run: |
    npm install -g appium@next
    appium driver install uiautomator2
```

## Compatibility Table

| Feature | 1.x | 2.0 | Compatible? |
|---------|-----|-----|-------------|
| Java 8 | ✅ | ❌ | No - Upgrade to 17+ |
| Java 17 | ❌ | ✅ | Yes |
| Appium 1.x | ✅ | ⚠️ | Partial |
| Appium 2.x | ❌ | ✅ | Yes |
| Log4j | ✅ | ❌ | No - Use SLF4J |
| System.out logging | ✅ | ⚠️ | Works but deprecated |
| ExtentReports 2.x | ✅ | ❌ | No - Use 5.x |
| TestNG | ✅ | ✅ | Yes |
| Existing test code | ✅ | ✅ | Yes - 95% compatible |

## Common Migration Issues

### Issue 1: Compilation Errors

**Error:**
```
error: invalid target release: 17
```

**Solution:**
```bash
# Verify Java version
java -version

# Update pom.xml
<maven.compiler.source>17</maven.compiler.source>
<maven.compiler.target>17</maven.compiler.target>
```

### Issue 2: Appium Connection Failed

**Error:**
```
Could not start a new session
```

**Solution:**
```bash
# Verify Appium 2.x installed
appium --version

# Verify drivers installed
appium driver list

# Restart Appium
appium
```

### Issue 3: Log4j Not Found

**Error:**
```
ClassNotFoundException: org.apache.log4j.Logger
```

**Solution:**

Remove Log4j dependency, add SLF4J + Logback (see Step 2).

### Issue 4: ExtentReports API Change

**Error:**
```
Method not found: ExtentReports.startTest()
```

**Solution:**

Use Sapphire's ExtentReporter wrapper - no code changes needed.

## Testing Your Migration

### Smoke Test Checklist

- [ ] Project compiles without errors
- [ ] Tests run successfully
- [ ] Reports are generated correctly
- [ ] Screenshots work
- [ ] Logging outputs correctly
- [ ] CI/CD pipeline passes
- [ ] Headless mode works

### Validation Test

```java
@Test
public void testSapphire2Migration() {
    logger.info("Testing Sapphire 2.0 migration");

    // Verify driver creation
    assert executor.getDriver() != null;

    // Verify element finding
    WebElement element = executor.findElementWithLabel("Submit");
    assert element != null;

    // Verify reporting
    executor.extentReporter.trackReport(LogStatus.PASS, "Migration successful");

    logger.info("Migration validated successfully");
}
```

## Rollback Plan

If migration fails:

1. **Revert pom.xml**
   ```bash
   git checkout HEAD~1 pom.xml
   ```

2. **Revert Java version**
   ```bash
   export JAVA_HOME=/path/to/jdk-8
   ```

3. **Reinstall Appium 1.x**
   ```bash
   npm uninstall -g appium
   npm install -g appium@1.22.3
   ```

## Getting Help

- **GitHub Issues**: [Report migration issues](https://github.com/kavyasoni/sapphire/issues)
- **Discussions**: [Ask questions](https://github.com/kavyasoni/sapphire/discussions)
- **Documentation**: [Full docs](https://github.com/kavyasoni/sapphire/wiki)

## Next Steps

After successful migration:

1. ✅ Review new features in [CHANGELOG.md](../CHANGELOG.md)
2. ✅ Explore headless execution
3. ✅ Set up CI/CD with new workflows
4. ✅ Try Allure reporting
5. ✅ Leverage type-safe configuration

---

**Welcome to Sapphire 2.0! 💎**
