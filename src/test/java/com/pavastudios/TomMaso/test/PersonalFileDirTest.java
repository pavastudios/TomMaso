package com.pavastudios.TomMaso.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.HashMap;
import java.util.Map;
public class PersonalFileDirTest {
    private static WebDriver driver;
    private static Map<String, Object> vars;
    static JavascriptExecutor js;
    @BeforeAll
    static void setUp() {
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<>();
    }
    @AfterAll
    static void tearDown() {
        driver.quit();
    }
    @Test
    public void prova() {
        driver.get("https://duckduckgo.com/");
        driver.manage().window().setSize(new Dimension(1000, 916));
        driver.findElement(By.id("search_form_input_homepage")).sendKeys("ciaone");
        driver.findElement(By.id("search_form_input_homepage")).sendKeys(Keys.ENTER);
        Assertions.assertEquals("ciaone at DuckDuckGo",driver.getTitle());
    }
}
