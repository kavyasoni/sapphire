# Multi-stage Dockerfile for Sapphire Framework Testing
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy pom.xml and download dependencies (cached layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests -B

# Runtime stage
FROM eclipse-temurin:17-jre

# Install Appium prerequisites
RUN apt-get update && apt-get install -y \
    curl \
    wget \
    gnupg \
    udev \
    && rm -rf /var/lib/apt/lists/*

# Install Node.js
RUN curl -fsSL https://deb.nodesource.com/setup_20.x | bash - \
    && apt-get install -y nodejs \
    && rm -rf /var/lib/apt/lists/*

# Install Appium
RUN npm install -g appium@next \
    && appium driver install uiautomator2 \
    && appium driver install xcuitest

# Create app directory
WORKDIR /sapphire

# Copy built artifact and test resources
COPY --from=builder /app/target/sapphire-*.jar ./sapphire.jar
COPY --from=builder /app/target/test-classes ./test-classes
COPY testng.xml .
COPY android_testng.xml .
COPY ios_testng.xml .

# Create directories for reports and logs
RUN mkdir -p /sapphire/report /sapphire/logs /sapphire/screenshots

# Expose Appium port
EXPOSE 4723

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=5s --retries=3 \
    CMD curl -f http://localhost:4723/status || exit 1

# Default command
CMD ["java", "-jar", "sapphire.jar"]
