package com.pavastudios.TomMaso.selenium;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ChatTestingTest {
    static JavascriptExecutor js;
    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver");
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
        Utility.login(driver, "provaaaa", "prova123");
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    @Test
    public void chatTest() throws InterruptedException {
        driver.get("http://localhost:8080/TomMaso_war_exploded/chat/admin");
        driver.manage().window().setSize(new Dimension(1550, 830));
        Thread.sleep(500);
        int oldLength = driver.findElements(By.cssSelector(".msg-card")).size();
        driver.findElement(By.id("messaggio")).click();
        driver.findElement(By.id("messaggio")).sendKeys("ciao");
        driver.findElement(By.id("invia")).click();
        Assertions.assertEquals(oldLength + 1, driver.findElements(By.cssSelector(".msg-card")).size());
        driver.findElement(By.id("messaggio")).click();
        driver.findElement(By.id("messaggio")).clear();
        driver.findElement(By.id("invia")).click();
        List<WebElement> elements = driver.findElements(By.cssSelector(".modal-error"));
        new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOf(elements.get(elements.size() - 2)));
    }
}
