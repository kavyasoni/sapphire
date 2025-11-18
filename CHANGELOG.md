# Changelog

All notable changes to Sapphire will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- Comprehensive documentation (README, CONTRIBUTING, CODE_OF_CONDUCT, SECURITY)
- Modern CI/CD pipeline with GitHub Actions
- Support for Appium 2.x
- Fluent API for element interactions
- Enhanced logging with SLF4J and Logback
- Modern reporting with ExtentReports 5.x and Allure
- Configuration management with type-safe config
- Exception hierarchy for better error handling
- Page Object Model base classes and examples
- Headless execution support for CI/CD
- Screenshot on failure capability
- Video recording support
- Smart wait strategies and retry mechanisms
- Accessibility ID locator support
- iOS Class Chain and Android UiSelector support
- Gesture builder for complex interactions
- Maven wrapper for reproducible builds
- Comprehensive test suite (unit, integration, E2E)
- Code quality checks (Checkstyle, SpotBugs, PMD)
- Test coverage reporting with JaCoCo
- Example projects for iOS and Android
- Migration guide from 1.x to 2.x
- Performance metrics collection
- Parallel test execution support
- Docker support for CI/CD

### Changed
- **BREAKING**: Minimum Java version: 8 → 17
- **BREAKING**: Replaced `DesiredCapabilities` with `Options` classes
- **BREAKING**: Replaced deprecated `TouchAction` with W3C Actions
- Updated Appium Java Client: 7.3.0 → 9.3.0
- Updated Selenium: 4.0.0-alpha-7 → 4.27.0
- Updated TestNG: 7.3.0 → 7.10.1
- Updated Apache POI: 4.1.2 → 5.3.0
- Updated Guava: 30.0-jre → 33.3.1-jre
- Replaced ExtentReports 2.41.2 with 5.1.2
- Refactored `ElementInspector` - split into focused interfaces
- Refactored `GenericExecutor` - modularized into action classes
- Improved element locator strategies
- Enhanced error messages and logging
- Improved test data providers with better error handling
- Modernized code style and formatting
- Improved JavaDoc documentation

### Fixed
- **CRITICAL**: Removed Log4j 1.2.17 (CVE-2021-44228 Log4Shell vulnerability)
- Fixed security vulnerabilities in all dependencies
- Fixed deprecation warnings with Selenium 4 and Appium 2
- Fixed thread safety issues in driver management
- Fixed race conditions in wait mechanisms
- Fixed hardcoded file paths in tests
- Fixed memory leaks in reporter classes
- Fixed incorrect exception handling
- Fixed flaky tests due to timing issues

### Removed
- Deprecated `DesiredCapabilities` usage
- Deprecated `TouchAction` usage
- Log4j 1.2.17 (replaced with SLF4J + Logback)
- Outdated ExtentReports 2.x
- Unused dependencies (jacob, commons-net)
- Hardcoded System.out.println logging
- Dead code and unused methods

### Security
- Upgraded all dependencies to address known CVEs
- Removed Log4j 1.2.17 (Log4Shell vulnerability)
- Added dependency vulnerability scanning in CI/CD
- Added security policy (SECURITY.md)
- Implemented secure credential handling examples

## [1.0.0] - 2020-05-01

### Added
- Initial release of Sapphire framework
- Support for iOS and Android automation with Appium
- Element inspection with XPath and label-based finding
- Basic reporting with ExtentReports
- Excel and JSON data providers
- TestNG integration
- SauceLabs support
- Basic gesture support (scroll, swipe)
- Driver management with capabilities builder

---

## Version History

- **2.0.0** - Modern, production-ready framework (2025)
- **1.0.0** - Initial release (2020)

[Unreleased]: https://github.com/kavyasoni/sapphire/compare/v1.0.0...HEAD
[1.0.0]: https://github.com/kavyasoni/sapphire/releases/tag/v1.0.0
