# Security Policy

## Supported Versions

We release patches for security vulnerabilities in the following versions:

| Version | Supported          |
| ------- | ------------------ |
| 2.x.x   | :white_check_mark: |
| 1.x.x   | :x:                |

## Reporting a Vulnerability

The Sapphire team takes security bugs seriously. We appreciate your efforts to responsibly disclose your findings.

### How to Report

**Please do not report security vulnerabilities through public GitHub issues.**

Instead, please report them via:

1. **GitHub Security Advisories** (Preferred)
   - Navigate to the repository
   - Click on "Security" tab
   - Click "Report a vulnerability"

2. **Email** (Alternative)
   - Send details to the maintainers through GitHub profile
   - Include "SECURITY" in the subject line

### What to Include

When reporting a vulnerability, please include:

- Type of vulnerability
- Full paths of source file(s) related to the vulnerability
- The location of the affected source code (tag/branch/commit or direct URL)
- Step-by-step instructions to reproduce the issue
- Proof-of-concept or exploit code (if possible)
- Impact of the issue, including how an attacker might exploit it

### Response Timeline

- **Initial Response**: Within 48 hours
- **Status Update**: Within 7 days
- **Fix Timeline**: Varies by severity
  - Critical: 7-14 days
  - High: 14-30 days
  - Medium: 30-60 days
  - Low: 60-90 days

### Disclosure Policy

- Security issues will be disclosed after a fix is released
- We follow coordinated disclosure principles
- We will credit researchers unless they prefer to remain anonymous

## Security Best Practices

When using Sapphire:

1. **Keep Dependencies Updated**: Regularly update to the latest version
2. **Secure Credentials**: Never commit credentials to version control
3. **Environment Variables**: Use environment variables for sensitive data
4. **Code Review**: Review test code for security issues
5. **Appium Server**: Secure your Appium server endpoints
6. **Cloud Providers**: Use secure authentication for cloud device farms

## Known Security Considerations

- Appium servers should not be exposed to the public internet
- Test credentials should be stored securely (vault, secrets manager)
- Screenshots/videos may contain sensitive information
- Test data should be sanitized before committing

Thank you for helping keep Sapphire and its users safe!
