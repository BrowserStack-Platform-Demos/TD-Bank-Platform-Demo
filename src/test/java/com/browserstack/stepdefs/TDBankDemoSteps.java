package com.browserstack.stepdefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;

public class TDBankDemoSteps {
    private WebDriver driver;
    boolean isMobile = false;

    @Before
    public void setUp() throws MalformedURLException {
        MutableCapabilities capabilities = new MutableCapabilities();
        HashMap<String, String> bstackOptions = new HashMap<>();
        bstackOptions.putIfAbsent("source", "cucumber-java:sample-master:v1.2");
        capabilities.setCapability("bstack:options", bstackOptions);
        driver = new RemoteWebDriver(
                new URL("https://hub.browserstack.com/wd/hub"), capabilities);
    }

    @Given("I am on the {string} website")
    public void iAmOnTheTDBankWebsite(String url) {
        driver.get(url);
    }

    @When("I select a the About Us option in the bottom nav")
    public void iSelectATheAboutUsOptionInTheBottomNav() {
        try {
           List<WebElement> burger = driver.findElements(By.xpath("//button[contains(@class, 'openMenubar')]"));
           List<WebElement> accordion = driver.findElements(By.xpath("//a[contains(@class, 'accordion-expand')]"));
           WebElement aboutUs = driver.findElement(By.xpath("//*[@id=\"cmp_mobile_primary_navigation\"]/nav/ul/li[1]/ul/li[5]/a"));

           if (burger.get(0).isDisplayed()) { burger.get(0).click(); }
           if (accordion.get(0).isDisplayed()) { accordion.get(0).click(); }
           if (aboutUs.isDisplayed()) { aboutUs.click(); }
           isMobile = true;

        } catch (Exception e) {
            System.out.println("Not on Mobile, no burger menu");
        }
        if (!isMobile) {
            driver.findElement(By.xpath("//*[@id=\"container-12ee37eb01\"]/div[1]/div/div/div[1]/ul/li[5]/a")).click();
        }
    }

    @Then("the About Us page is displayed")
    public void theAboutUsPageIsDisplayed() {
        Assert.assertTrue(driver.getCurrentUrl().contains("about-us"));
    }

    @After
    public void teardown(Scenario scenario) throws Exception {
        Thread.sleep(2000);
        driver.quit();
    }
}
