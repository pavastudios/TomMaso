package com.pavastudios.TomMaso.selenium;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public abstract class SeleniumTest {
    static JavascriptExecutor js;
    static WebDriver driver;
    @BeforeAll
    public static void setupDriver(){
        System.setProperty("webdriver.gecko.driver", System.getenv("webDriverDir"));
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;

    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }
}
