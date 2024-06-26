package com.pavastudios.TomMaso.selenium;

// Generated by Selenium IDE

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class UploadFileTest extends SeleniumTest{

    @BeforeAll
    public static void setUp() {
        Utility.login(driver, "admin", "admin");
    }


    private static void wait(By selector) {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(driver.findElement(selector)));
    }


    @Test
    public void uC6UploadFileFilePresente() {
        driver.get("http://localhost:8080/TomMaso_war_exploded/blog-manage/llllllllllllll");
        driver.findElement(By.cssSelector(".fa-plus")).click();
        wait(By.id("file"));
        driver.findElement(By.id("file")).sendKeys(System.getenv("uploadedFile"));
        driver.findElement(By.cssSelector("fieldset:nth-child(1) .btn")).click();

        driver.findElement(By.cssSelector(".fa-plus")).click();
        wait(By.id("file"));
        driver.findElement(By.id("file")).sendKeys(System.getenv("uploadedFile"));
        driver.findElement(By.cssSelector("fieldset:nth-child(1) .btn")).click();

        driver.findElement(By.cssSelector(".col-6:nth-child(1) > .delete-anim")).click();
        driver.findElement(By.id("deleteBlog")).click();
    }


    @Test
    public void uC6UploadFileFileNonPresente() {
        driver.get("http://localhost:8080/TomMaso_war_exploded/blog-manage/llllllllllllll");
        driver.findElement(By.cssSelector(".fa-plus")).click();
        wait(By.id("file"));
        driver.findElement(By.id("file")).sendKeys(System.getenv("uploadedFile"));
        driver.findElement(By.cssSelector("fieldset:nth-child(1) .btn")).click();

        driver.findElement(By.cssSelector(".col-6:nth-child(1) > .delete-anim")).click();
        driver.findElement(By.id("deleteBlog")).click();
    }
}