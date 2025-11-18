package com.evig.sapphire.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.DefaultValue;
import org.aeonbits.owner.Config.Key;

/**
 * Type-safe configuration interface for Sapphire framework.
 * Uses Owner library for property management.
 *
 * @author Sapphire Team
 * @since 2.0.0
 */
@Config.Sources({
    "system:properties",
    "system:env",
    "classpath:sapphire.properties",
    "classpath:config.properties"
})
public interface SapphireConfig extends Config {

    @Key("appium.server.url")
    @DefaultValue("http://0.0.0.0:4723")
    String appiumServerUrl();

    @Key("implicit.wait")
    @DefaultValue("10")
    int implicitWait();

    @Key("explicit.wait")
    @DefaultValue("30")
    int explicitWait();

    @Key("screenshot.on.failure")
    @DefaultValue("true")
    boolean screenshotOnFailure();

    @Key("video.recording")
    @DefaultValue("false")
    boolean videoRecording();

    @Key("headless")
    @DefaultValue("false")
    boolean headless();

    @Key("report.directory")
    @DefaultValue("./report")
    String reportDirectory();

    @Key("log.level")
    @DefaultValue("INFO")
    String logLevel();

    @Key("retry.count")
    @DefaultValue("3")
    int retryCount();

    @Key("page.load.timeout")
    @DefaultValue("30")
    int pageLoadTimeout();
}
