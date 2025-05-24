package iteration;

import org.junit.jupiter.api.RepeatedTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class IterationTest {

    @RepeatedTest(100)
    public void loginToDashboardInSeparateBrowsers() {
        System.out.println("======= Test Iteration Start =======");

        WebDriver browser1 = null;
        WebDriver browser2 = null;

        try {
            // Browser 1: Successful login
            browser1 = new ChromeDriver();
            browser1.get("https://dev-cca.mydriver.au/customer/login");

            browser2.findElement(By.xpath("//input[@id='passenger-login-email-input']")).sendKeys("cca1passenger@mailinator.com");
            browser2.findElement(By.xpath("//input[@id='passenger-login-password-input']")).sendKeys("Test@12345");
            browser2.findElement((By.xpath("//button[contains(text(),'In')]"))).click();

            WebDriverWait wait1 = new WebDriverWait(browser1, Duration.ofSeconds(10));
            wait1.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

            String page1 = browser1.getPageSource();
            assertTrue(page1.contains("Welcome to MyDriver Driver account"), "Browser 1: Login failed or message not found");
            System.out.println("Browser1: Login successful");

            // Browser 2: Failed login
            browser2 = new ChromeDriver();
            browser2.get("https://dev-cca.mydriver.au/customer/login");

            browser2.findElement(By.xpath("//input[@id='passenger-login-email-input']")).sendKeys("cca1passenger@mailinator.com");
            browser2.findElement(By.xpath("//input[@id='passenger-login-password-input']")).sendKeys("Test@12345");
            browser2.findElement((By.xpath("//button[contains(text(),'In')]"))).click();

            WebDriverWait wait2 = new WebDriverWait(browser2, Duration.ofSeconds(10));
            wait2.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

            String page2 = browser2.getPageSource();
            assertTrue(page2.contains("Invalid Credentials"), "Browser 2: Invalid login message not shown");
            System.out.println("Browser2: Invalid login verified");

        } finally {
            if (browser1 != null) browser1.quit();
            if (browser2 != null) browser2.quit();
        }

        System.out.println("======= Test Iteration End =======\n");
    }
}
