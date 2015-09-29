package com.BS;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

@RunWith(Parallelized.class)
public class AppTest {
  private String platform;
  private String browserName;
  private String browserVersion;

  @Parameterized.Parameters
  public static LinkedList getEnvironments() throws Exception {
    LinkedList<String[]> env = new LinkedList();

    env.add(new String[]{Platform.WIN8.toString(), "chrome", "36"});
    env.add(new String[]{Platform.WIN8.toString(),"firefox","31"});    	
    
    //add more browsers here

    return env;
  }

  public AppTest(String platform, String browserName, String browserVersion) {
    this.platform = platform;
    this.browserName = browserName;
    this.browserVersion = browserVersion;
  }

  private WebDriver driver;

  @Before
  public void setUp() throws Exception {
    DesiredCapabilities capability = new DesiredCapabilities();
    capability.setCapability("platform", platform);
    capability.setCapability("browser", browserName);
    capability.setCapability("device", browserVersion);
    capability.setCapability("build", "Sample JUnit");
    capability.setCapability("name", "Simple_Parallel_Tests");
    capability.setCapability("browserstack.debug", "true");
    driver = new RemoteWebDriver(new URL("http://<username>:<automate-key>@hub.browserstack.com/wd/hub"), capability);
  }

  @Test
  public void testSimple() throws Exception {
	  
    driver.get("http://www.google.com");
    System.out.println("Page title is: " + driver.getTitle());
    WebElement element = driver.findElement(By.name("q"));
    element.sendKeys("BrowserStack");
    element.submit();
    System.out.println("Parallel running....");
    WebDriver augmentedDriver = new Augmenter().augment(driver);
    ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);

  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
  }
}