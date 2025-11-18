# Contributing to Sapphire

Thank you for your interest in contributing to Sapphire! This document provides guidelines and instructions for contributing.

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Development Setup](#development-setup)
- [How to Contribute](#how-to-contribute)
- [Coding Standards](#coding-standards)
- [Testing Guidelines](#testing-guidelines)
- [Pull Request Process](#pull-request-process)
- [Release Process](#release-process)

## Code of Conduct

This project adheres to the [Contributor Covenant Code of Conduct](CODE_OF_CONDUCT.md). By participating, you are expected to uphold this code.

## Getting Started

1. **Fork the repository** on GitHub
2. **Clone your fork** locally:
   ```bash
   git clone https://github.com/YOUR-USERNAME/sapphire.git
   cd sapphire
   ```
3. **Add upstream remote**:
   ```bash
   git remote add upstream https://github.com/kavyasoni/sapphire.git
   ```

## Development Setup

### Prerequisites

- **Java 17** or higher (LTS version recommended)
- **Maven 3.8+** (or use the provided Maven wrapper)
- **Git** for version control
- **Appium Server** (for integration tests)
- **Android SDK** and/or **Xcode** (for platform-specific tests)

### Building the Project

```bash
# Build the project
./mvnw clean install

# Build without tests
./mvnw clean install -DskipTests

# Run tests only
./mvnw test

# Run specific test
./mvnw test -Dtest=YourTestClass
```

### IDE Setup

#### IntelliJ IDEA
1. Import as Maven project
2. Enable annotation processing (Settings → Build → Compiler → Annotation Processors)
3. Install Lombok plugin
4. Code style: Settings → Editor → Code Style → Import `checkstyle.xml`

#### Eclipse
1. Import as Maven project
2. Install Lombok
3. Install Checkstyle plugin

## How to Contribute

### Reporting Bugs

- Check existing issues first
- Use the bug report template
- Include:
  - Clear description
  - Steps to reproduce
  - Expected vs actual behavior
  - Environment details (OS, Java version, Appium version)
  - Logs and screenshots

### Suggesting Features

- Check existing feature requests
- Use the feature request template
- Explain the use case and benefits
- Provide examples if possible

### Code Contributions

1. **Create a feature branch**:
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. **Make your changes**:
   - Write clean, readable code
   - Follow coding standards
   - Add tests for new functionality
   - Update documentation

3. **Commit your changes**:
   ```bash
   git add .
   git commit -m "Add: descriptive commit message"
   ```

   Commit message format:
   - `Add: new feature`
   - `Fix: bug description`
   - `Update: improvement description`
   - `Refactor: code improvement`
   - `Docs: documentation update`
   - `Test: test addition/update`

4. **Keep your fork updated**:
   ```bash
   git fetch upstream
   git rebase upstream/main
   ```

5. **Push to your fork**:
   ```bash
   git push origin feature/your-feature-name
   ```

6. **Create a Pull Request** on GitHub

## Coding Standards

### Java Code Style

- **Formatting**: Follow Google Java Style Guide
- **Indentation**: 4 spaces (no tabs)
- **Line length**: 120 characters max
- **Naming conventions**:
  - Classes: `PascalCase`
  - Methods: `camelCase`
  - Constants: `UPPER_SNAKE_CASE`
  - Packages: `lowercase`

### Code Quality

- **Checkstyle**: Code must pass Checkstyle checks
- **SpotBugs**: No high-priority bugs
- **PMD**: No critical violations
- **Test Coverage**: Maintain >80% coverage for new code

### Best Practices

- Use meaningful variable and method names
- Keep methods small and focused (< 50 lines)
- Add JavaDoc for public APIs
- Use SLF4J for logging (not System.out)
- Handle exceptions appropriately
- Use try-with-resources for AutoCloseable
- Prefer composition over inheritance
- Use Optional for nullable returns
- Use Java 17 features appropriately

### Example Code

```java
/**
 * Finds an element by its label text.
 *
 * @param label the text label to search for
 * @param timeout maximum wait time in seconds
 * @return the found element
 * @throws ElementNotFoundException if element is not found
 */
public WebElement findByLabel(String label, int timeout) {
    logger.debug("Finding element with label: {}", label);

    return waitFor(timeout)
        .until(driver -> {
            By locator = buildLabelLocator(label);
            return driver.findElement(locator);
        });
}
```

## Testing Guidelines

### Test Structure

```
src/test/java
├── unit/              # Unit tests (fast, no external dependencies)
├── integration/       # Integration tests (with Appium)
└── e2e/              # End-to-end tests
```

### Writing Tests

- **Unit Tests**: Use JUnit 5 and Mockito
- **Integration Tests**: Use TestNG with Appium
- **Test Naming**: `shouldDoSomethingWhenCondition()`
- **Assertions**: Use AssertJ for fluent assertions

```java
@Test
public void shouldFindElementByLabel() {
    // Given
    String label = "Submit";

    // When
    WebElement element = inspector.findByLabel(label);

    // Then
    assertThat(element).isNotNull();
    assertThat(element.getText()).isEqualTo(label);
}
```

### Running Tests

```bash
# All tests
./mvnw test

# Unit tests only
./mvnw test -Dgroups=unit

# Integration tests
./mvnw test -Dgroups=integration

# With specific platform
./mvnw test -Dplatform=android

# With coverage
./mvnw test jacoco:report
```

## Pull Request Process

### Before Submitting

- [ ] Code builds successfully
- [ ] All tests pass
- [ ] Code follows style guidelines
- [ ] New tests added for new features
- [ ] Documentation updated
- [ ] Commit messages are clear
- [ ] No merge conflicts with main

### PR Description Template

```markdown
## Description
Brief description of changes

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Breaking change
- [ ] Documentation update

## Testing
How has this been tested?

## Checklist
- [ ] Code follows style guidelines
- [ ] Self-review completed
- [ ] Comments added for complex code
- [ ] Documentation updated
- [ ] Tests added/updated
- [ ] All tests pass
```

### Review Process

1. Automated checks must pass (CI/CD)
2. At least one maintainer approval required
3. Address review comments
4. Maintainer will merge when approved

## Release Process

Releases are managed by maintainers:

1. Version bump in `pom.xml`
2. Update `CHANGELOG.md`
3. Create release tag
4. Build and publish artifacts
5. Create GitHub release with notes

## Getting Help

- **Questions**: Open a GitHub Discussion
- **Bugs**: Create an issue with bug template
- **Features**: Create an issue with feature template
- **Chat**: Join our community (if available)

## Recognition

Contributors will be recognized in:
- Release notes
- README.md contributors section
- GitHub contributors page

Thank you for contributing to Sapphire!
