# Sapphire Testing Guide

Complete guide for running tests with the Sapphire framework.

## Table of Contents

- [Quick Start](#quick-start)
- [Running Tests Locally](#running-tests-locally)
- [Headless Execution](#headless-execution)
- [Docker-Based Testing](#docker-based-testing)
- [CI/CD Integration](#cicd-integration)
- [Best Practices](#best-practices)
- [Troubleshooting](#troubleshooting)

## Quick Start

### Prerequisites

1. **Java 17+**
   ```bash
   java -version
   ```

2. **Appium 2.x**
   ```bash
   npm install -g appium@next
   appium driver install uiautomator2  # For Android
   appium driver install xcuitest      # For iOS
   ```

3. **Android SDK** (for Android tests)
   - Install Android Studio
   - Set `ANDROID_HOME` environment variable

4. **Xcode** (for iOS tests - macOS only)

### Start Appium Server

```bash
appium
```

### Run Tests

```bash
# All tests
mvn test

# Android tests only
mvn test -Pandroid

# iOS tests only
mvn test -Pios

# Specific test
mvn test -Dtest=HeadlessAndroidTest
```

## Running Tests Locally

### 1. With Physical Device

#### Android

```bash
# Check connected devices
adb devices

# Run tests
mvn test -Pandroid \
  -Ddevice.name=<device-id> \
  -Dandroid.version=<os-version> \
  -Dapp.path=/path/to/your/app.apk
```

#### iOS

```bash
# Check connected devices
xcrun instruments -s devices

# Run tests
mvn test -Pios \
  -Ddevice.name=<device-name> \
  -Dios.version=<os-version> \
  -Dapp.path=/path/to/your/app.app
```

### 2. With Emulator/Simulator

#### Android Emulator

```bash
# List available AVDs
emulator -list-avds

# Start emulator
emulator -avd <avd-name> &

# Run tests
mvn test -Pandroid -Ddevice.name=emulator-5554
```

#### iOS Simulator

```bash
# List simulators
xcrun simctl list devices

# Boot simulator
xcrun simctl boot <device-id>

# Run tests
mvn test -Pios -Ddevice.name="iPhone 14"
```

## Headless Execution

Headless mode runs tests without displaying the UI, ideal for CI/CD environments.

### Enable Headless Mode

```bash
# Via system property
mvn test -Dheadless=true

# Via config file (config.properties)
headless=true
```

### Headless Android

```bash
# Create headless AVD
echo "no" | avdmanager create avd \
  -n test_emulator \
  -k "system-images;android-33;google_apis;x86_64" \
  --force

# Start emulator in headless mode
emulator -avd test_emulator -no-window -no-audio -no-boot-anim &

# Wait for boot
adb wait-for-device

# Run tests
mvn test -Pandroid -Dheadless=true -Ddevice.name=emulator-5554
```

### Headless iOS

iOS Simulator cannot run truly headless, but you can minimize UI:

```bash
# Boot simulator without opening Simulator app
xcrun simctl boot <device-id>

# Run tests
mvn test -Pios -Dheadless=true
```

## Docker-Based Testing

Docker provides isolated, reproducible test environments.

### Using Docker Compose

```bash
# Start all services (Appium + Emulator + Tests)
docker-compose up

# Run specific service
docker-compose up sapphire-tests

# Run and remove containers
docker-compose up --abort-on-container-exit

# View logs
docker-compose logs -f sapphire-tests

# Clean up
docker-compose down -v
```

### Building Docker Image

```bash
# Build image
docker build -t sapphire:latest .

# Run container
docker run --rm \
  -e APPIUM_SERVER_URL=http://appium:4723 \
  -e HEADLESS=true \
  -v $(pwd)/report:/sapphire/report \
  sapphire:latest
```

### Docker with Android Emulator

```bash
# Using budtmo/docker-android
docker run -d -p 4723:4723 -p 6080:6080 \
  --privileged \
  -e EMULATOR_DEVICE="Samsung Galaxy S10" \
  -e WEB_VNC=true \
  budtmo/docker-android:emulator_13.0

# Access emulator via VNC
# Open browser: http://localhost:6080
```

## CI/CD Integration

### GitHub Actions

Sapphire includes pre-configured GitHub Actions workflows:

- **`.github/workflows/ci.yml`** - Continuous Integration
- **`.github/workflows/release.yml`** - Release automation

#### Triggering Workflows

```bash
# Push to main/develop
git push origin main

# Create PR
# Workflow runs automatically

# Manual trigger
# Go to Actions tab → Select workflow → Run workflow
```

#### Viewing Results

1. Go to **Actions** tab in GitHub
2. Select workflow run
3. View logs and artifacts
4. Download test reports

### Jenkins

```groovy
pipeline {
    agent any

    tools {
        maven 'Maven 3.9'
        jdk 'JDK 17'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test -Dheadless=true'
            }
        }

        stage('Report') {
            steps {
                publishHTML([
                    reportDir: 'target/surefire-reports',
                    reportFiles: 'index.html',
                    reportName: 'Test Report'
                ])
                junit 'target/surefire-reports/*.xml'
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'report/**/*', allowEmptyArchive: true
            cleanWs()
        }
    }
}
```

### GitLab CI

```yaml
image: maven:3.9-eclipse-temurin-17

variables:
  MAVEN_OPTS: "-Dmaven.repo.local=.m2/repository"

cache:
  paths:
    - .m2/repository/
    - target/

stages:
  - build
  - test
  - report

build:
  stage: build
  script:
    - mvn clean compile

test:
  stage: test
  services:
    - name: appium/appium:v2.0.0
      alias: appium
  variables:
    APPIUM_SERVER_URL: "http://appium:4723"
    HEADLESS: "true"
  script:
    - mvn test
  artifacts:
    reports:
      junit: target/surefire-reports/*.xml
    paths:
      - target/surefire-reports/
      - report/
    expire_in: 1 week

pages:
  stage: report
  dependencies:
    - test
  script:
    - mkdir public
    - cp -r target/surefire-reports/* public/
  artifacts:
    paths:
      - public
  only:
    - main
```

## Best Practices

### 1. Test Organization

```
src/test/java/
├── unit/              # Fast unit tests
│   └── UtilsTest.java
├── integration/       # Integration tests with Appium
│   ├── AndroidLoginTest.java
│   └── IOSLoginTest.java
└── e2e/              # End-to-end test scenarios
    └── UserJourneyTest.java
```

### 2. Use Test Groups

```java
@Test(groups = {"unit"})
public void testUtilMethod() { }

@Test(groups = {"integration", "android"})
public void testAndroidLogin() { }

@Test(groups = {"e2e", "critical"})
public void testCompleteUserJourney() { }
```

Run specific groups:
```bash
mvn test -Dgroups=unit
mvn test -Dgroups=integration
mvn test -Dgroups="integration,android"
```

### 3. Configuration Management

Use separate config files for different environments:

```
src/test/resources/
├── config.properties          # Default
├── config-dev.properties      # Development
├── config-staging.properties  # Staging
├── config-prod.properties     # Production
└── config-ci.properties       # CI/CD
```

Load specific config:
```bash
mvn test -Dconfig.file=config-ci.properties
```

### 4. Parallel Execution

```xml
<!-- testng.xml -->
<suite name="Parallel Suite" parallel="tests" thread-count="4">
    <test name="Android Test 1">...</test>
    <test name="Android Test 2">...</test>
    <test name="iOS Test 1">...</test>
    <test name="iOS Test 2">...</test>
</suite>
```

### 5. Test Data Management

```java
@DataProvider
public Object[][] loginData() {
    return TestDataProvider.provideDataFromExcelFile("login_data.xlsx");
}

@Test(dataProvider = "loginData")
public void testLogin(HashMap<String, String> data) {
    // Test logic
}
```

## Troubleshooting

### Common Issues

#### 1. Appium Server Not Running

**Error**: `Connection refused`

**Solution**:
```bash
# Start Appium
appium

# Verify status
curl http://localhost:4723/status
```

#### 2. Device Not Found

**Error**: `No devices detected`

**Solution**:
```bash
# Android
adb devices
adb kill-server && adb start-server

# iOS
xcrun instruments -s devices
```

#### 3. App Not Installing

**Error**: `Failed to install app`

**Solution**:
```bash
# Android - Check app signature
adb install -r app.apk

# iOS - Check provisioning profile
xcrun simctl install booted app.app
```

#### 4. Element Not Found

**Error**: `NoSuchElementException`

**Solution**:
```java
# Increase wait time
executor.setDefaultWaitTimeInSeconds(15);

# Use scroll to find
WebElement el = executor.performScrollAndFindElementWithLabel("Text");

# Check locator
executor.findElementByXpath("//correct/xpath");
```

#### 5. Headless Mode Not Working

**Error**: Tests fail in headless but pass with UI

**Solution**:
```bash
# Android - Ensure emulator supports headless
emulator -avd test_emulator -no-window -gpu swiftshader_indirect

# Increase timeouts for slower headless execution
-Dimplicit.wait=20
```

#### 6. Screenshot Capture Fails

**Error**: `Failed to capture screenshot`

**Solution**:
```bash
# Ensure directory exists
mkdir -p screenshots

# Check permissions
chmod 755 screenshots

# Enable in config
screenshot.on.failure=true
```

### Debug Mode

Enable debug logging:

```bash
# Via Maven
mvn test -X

# Via logback
log.level=DEBUG

# Via system property
-Dlog.level=DEBUG
```

### Getting Help

1. **Check Logs**: `logs/sapphire.log`
2. **GitHub Issues**: [Report bugs](https://github.com/kavyasoni/sapphire/issues)
3. **Documentation**: [Wiki](https://github.com/kavyasoni/sapphire/wiki)
4. **Discussions**: [Ask questions](https://github.com/kavyasoni/sapphire/discussions)

---

**Happy Testing with Sapphire! 💎**
