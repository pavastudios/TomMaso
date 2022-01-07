package com.pavastudios.TomMaso.selenium;

import com.pavastudios.TomMaso.utility.Tuple2;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Utility {
    public static void login(WebDriver driver, Tuple2<String,String>data) {
        login(driver,data.get1(),data.get2());
    }

    public static void login(WebDriver driver, String username, String password) {
        driver.get("http://localhost:8080/TomMaso_war_exploded/");
        driver.manage().window().setSize(new Dimension(1536, 816));
        driver.findElement(By.linkText("Login")).click();
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(driver.findElement(By.id("username-login"))));
        driver.findElement(By.id("username-login")).click();
        driver.findElement(By.id("username-login")).sendKeys(username);
        driver.findElement(By.id("password-login")).click();
        driver.findElement(By.id("password-login")).sendKeys(password);
        driver.findElement(By.id("navbarLoginSubmit")).click();
    }


}
