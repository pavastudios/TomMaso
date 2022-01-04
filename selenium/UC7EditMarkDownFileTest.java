package com.pavastudios.TomMaso.selenium;

// Generated by Selenium IDE
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
public class UC7EditMarkDownFileTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    @Before
    public void setUp() {
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }
    @After
    public void tearDown() {
        driver.quit();
    }
    @Test
    public void uC7EditMarkDownFileTC1() {
        driver.get("http://localhost:8080/TomMaso_war_exploded/blog-manage/blogTest");
        driver.manage().window().setSize(new Dimension(1536, 816));
        driver.findElement(By.cssSelector(".fa-plus > path")).click();
        driver.findElement(By.id("titleMD")).click();
        driver.findElement(By.id("titleMD")).sendKeys("blogtest1");
        driver.findElement(By.cssSelector("#createMD .btn")).click();
        driver.findElement(By.cssSelector(".CodeMirror-scroll")).click();
        js.executeScript("window.scrollTo(0,0)");
        js.executeScript("window.scrollTo(0,0)");
        js.executeScript("window.scrollTo(0,0)");
        js.executeScript("window.scrollTo(0,0)");
        js.executeScript("window.scrollTo(0,0)");
        driver.findElement(By.cssSelector("div > textarea")).sendKeys("asdasdasdasdasdasdasdasdasdasdasdasd");
        driver.findElement(By.cssSelector(".btn-primary:nth-child(1)")).click();
        driver.findElement(By.cssSelector("path:nth-child(3)")).click();
        driver.findElement(By.linkText("Profilo")).click();
        driver.findElement(By.id("propic-svg-bblogTest")).click();
        {
            List<WebElement> elements = driver.findElements(By.cssSelector(".card-header > span:nth-child(1)"));
            assert(elements.size() > 0);
        }
        driver.findElement(By.cssSelector("path:nth-child(3)")).click();
        driver.findElement(By.linkText("Logout")).click();
    }
}
