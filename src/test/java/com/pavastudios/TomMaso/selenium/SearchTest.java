package com.pavastudios.TomMaso.selenium;// Generated by Selenium IDE

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

public class SearchTest {
    static JavascriptExecutor js;
    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver");
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    @Test
    public void uC21TC1() {
        driver.get("http://localhost:8080/TomMaso_war_exploded/");
        driver.manage().window().setSize(new Dimension(1287, 738));
        driver.findElement(By.id("navbarSearchText")).click();
        driver.findElement(By.id("navbarSearchText")).click();
        driver.findElement(By.id("navbarSearchText")).click();
        {
            WebElement element = driver.findElement(By.id("navbarSearchText"));
            Actions builder = new Actions(driver);
            builder.doubleClick(element).perform();
        }
        driver.findElement(By.id("navbarSearchText")).click();
        {
            WebElement element = driver.findElement(By.cssSelector(".col-10"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).clickAndHold().perform();
        }
        {
            WebElement element = driver.findElement(By.cssSelector(".flex-lg-row-reverse"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).release().perform();
        }
        driver.findElement(By.id("navbarSearchText")).click();
        driver.findElement(By.id("navbarSearchText")).sendKeys("CCCCCCCC");
        driver.findElement(By.cssSelector(".btn:nth-child(4)")).click();
        Assertions.assertEquals(driver.getTitle(), "Errore - TomMASO");
    }

    @Test
    public void uC21TC2() {
        driver.get("http://localhost:8080/TomMaso_war_exploded/");
        driver.manage().window().setSize(new Dimension(1382, 754));
        driver.findElement(By.id("navbarSearchText")).click();
        driver.findElement(By.id("navbarSearchText")).sendKeys("DDDDDDDD");
        driver.findElement(By.cssSelector(".btn:nth-child(4)")).click();
        Assertions.assertEquals(driver.getTitle(), ("TomMASO - Profilo di DDDDDDDD"));
    }

    @Test
    public void uC21TC3() {
        driver.get("http://localhost:8080/TomMaso_war_exploded/");
        driver.manage().window().setSize(new Dimension(1287, 738));
        driver.findElement(By.id("navbarSearchType")).click();
        driver.findElement(By.id("navbarSearchBlog")).click();
        driver.findElement(By.id("navbarSearchText")).click();
        driver.findElement(By.id("navbarSearchText")).sendKeys("AAAAAAAA");
        driver.findElement(By.cssSelector(".btn:nth-child(4)")).click();
        Assertions.assertEquals(driver.getTitle(), ("Errore - TomMASO"));
    }

    @Test
    public void uC21TC4() {
        driver.get("http://localhost:8080/TomMaso_war_exploded/");
        driver.manage().window().setSize(new Dimension(1287, 738));
        driver.findElement(By.id("navbarSearchType")).click();
        driver.findElement(By.id("navbarSearchBlog")).click();
        driver.findElement(By.id("navbarSearchText")).click();
        driver.findElement(By.id("navbarSearchText")).sendKeys("BBBBBBBB");
        driver.findElement(By.cssSelector(".btn:nth-child(4)")).click();
        Assertions.assertEquals(driver.getTitle(), ("Ultimi post - BBBBBBBB"));
    }
}
