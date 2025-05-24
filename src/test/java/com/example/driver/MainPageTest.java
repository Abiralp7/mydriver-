package com.example.driver;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MainPageTest {

    private WebDriver driver1;
    private WebDriver driver2;
    private WebDriver passenger1;

    @BeforeAll
    public static void setUp() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";

        // Enable Allure logging
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(false));
    }

    @BeforeEach
    public void setupBrowsers() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        driver1 = new ChromeDriver(options);
        WebDriverRunner.setWebDriver(driver1);

        driver2 = new ChromeDriver(options);
        passenger1 = new ChromeDriver(options);
    }

    @Test
    @Order(1)
    @DisplayName("Login to MyDriver Dashboard with Different Accounts in Separate Browsers")
    public void loginToDashboardInSeparateBrowsers() {
        // ===== Driver 1 Login =====
        driver1.get("https://dev-account.mydriver.au");
        $(By.xpath("//input[@id='driver-login-email-input']")).shouldBe(visible).setValue("newdrivera@mailinator.com");
        $(By.xpath("//input[@id='driver-login-password-input']")).shouldBe(visible).setValue("Test@12345");
        $(By.xpath("//button[normalize-space()='Sign In']")).shouldBe(visible).click();

        if ($(By.xpath("//a[contains(text(),'Dashboard')]")).is(visible)) {
            System.out.println("Browser1: Welcome to MyDriver Driver account");
            String firstBrowserUrl = WebDriverRunner.url();
            assertTrue(firstBrowserUrl.contains("dashboard"), "Dashboard not loaded after login in the first browser.");
        } else {
            System.out.println("Browser1: Invalid Credentials");
        }


        // ===== Driver 2 Login =====
        WebDriverRunner.setWebDriver(driver2);
        driver2.get("https://dev-account.mydriver.au");
        $(By.xpath("//input[@id='driver-login-email-input']")).shouldBe(visible).setValue("newdriverb@mailinator.com");
        $(By.xpath("//input[@id='driver-login-password-input']")).shouldBe(visible).setValue("WrongPassword123");
        $(By.xpath("//button[normalize-space()='Sign In']")).shouldBe(visible).click();

        if ($(By.xpath("//a[contains(text(),'Dashboard')]")).is(visible)) {
            System.out.println("Browser2: Welcome to MyDriver Driver account");
            String secondBrowserUrl = WebDriverRunner.url();
            assertTrue(secondBrowserUrl.contains("dashboard"), "Dashboard not loaded after login in the second browser.");
        } else {
            System.out.println("Browser2: Invalid Credentials");
        }

        // ===== Passenger Login =====
        WebDriverRunner.setWebDriver(passenger1);
        passenger1.get("https://dev-cca.mydriver.au/customer/login");

        $(By.xpath("//input[@id='passenger-login-email-input']")).shouldBe(visible).setValue("cca1passenger@mailinator.com");
        $(By.xpath("//input[@id='passenger-login-password-input']")).shouldBe(visible).setValue("Test@12345");
        $(By.xpath("//button[contains(text(),'In')]")).shouldBe(visible).click();
        $(By.xpath("//header/div[1]/div[1]/div[1]/div[1]/div[2]/a[1]")).click();
        $(By.xpath("//input[@id='point-start']")).click();
        $(By.xpath("//input[@id='point-start']")).setValue("Sydney");
        $(By.xpath("//input[@id='point-end']")).click();
        $(By.xpath("//input[@id='point-start']")).setValue("Concord");
        $(By.xpath("//input[@id='pickup-date']")).click();
        $(By.xpath("//a[contains(text(),'25')]")).click();
        $(By.xpath("//input[@id='pickup-time']"))




        // Wait for redirection to complete
//

        String expectedUrl = "https://dev-cca.mydriver.au/customer/dashboard";
        String actualUrl = WebDriverRunner.url();

        if (actualUrl.equals(expectedUrl)) {
            System.out.println("Passenger1: Welcome to MyDriver CCA Passenger account");
            assertTrue(actualUrl.contains("dashboard"), "Dashboard not loaded after login in the passenger browser.");
        } else {
            System.out.println("Passenger1: Invalid Credentials or Unexpected URL");
            fail("Expected to land on: " + expectedUrl + ", but landed on: " + actualUrl);
        }
    }

    @AfterEach
    public void tearDownBrowsers() {
        if (driver1 != null) driver1.quit();
        if (driver2 != null) driver2.quit();
        if (passenger1 != null) passenger1.quit();
    }

    @AfterAll
    public static void tearDown() {
        closeWebDriver();
    }
}
