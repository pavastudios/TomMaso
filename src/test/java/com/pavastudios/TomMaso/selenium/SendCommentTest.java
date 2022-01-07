package com.pavastudios.TomMaso.selenium;// Generated by Selenium IDE

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SendCommentTest extends SeleniumTest{

    @BeforeAll
    public static void setUp() {
        Utility.login(driver, "admin", "admin");
    }

    @Test
    public void uC12TC1() {

        driver.get("http://localhost:8080/TomMaso_war_exploded/blogs/BBBBBBBB/Prima.md");
        driver.manage().window().setSize(new Dimension(1382, 754));
        driver.findElement(By.id("comment")).click();
        driver.findElement(By.id("comment")).sendKeys("testingiswonderful");
        driver.findElement(By.id("sendComment")).click();
    }

    @Test
    public void uC12TC2() {
        driver.get("http://localhost:8080/TomMaso_war_exploded/blogs/BBBBBBBB/Prima.md");
        driver.manage().window().setSize(new Dimension(1382, 754));
        driver.findElement(By.id("comment")).click();
        driver.findElement(By.id("comment")).sendKeys("ab");
        driver.findElement(By.id("sendComment")).click();
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.textToBe(By.id("error"), "Commento invalido"));
    }
}
