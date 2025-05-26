package BookingFlow;

import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class MydriverPassenger {

        @Test
        public void testPassengerLoginAndBooking() {
            // Setup WebDriver
            WebDriver passenger1 = new ChromeDriver();
            WebDriverRunner.setWebDriver(passenger1);

            // Navigate to login page
            open("https://dev-partner.mydriver.au/customer/login");

            // Login as passenger
            $(By.id("passenger-login-email-input")).shouldBe(visible).setValue("passengerhotel@mailinator.com");
            $(By.id("passenger-login-password-input")).shouldBe(visible).setValue("Test@12345");
            $(By.xpath("//button[contains(text(),'Sign In')]")).shouldBe(visible).click();
            System.out.println("Logged in Successfully");

            // Start a booking
            $(By.xpath("//header//a[contains(text(),'Book')]")).shouldBe(visible).click();

            // Set pickup location
            $(By.id("point-start")).shouldBe(visible).setValue("Sydney");
            sleep(100);
            $(By.xpath("//body/ul[@id='ui-id-1']/li[1]/div[1]")).shouldBe(visible).click();
            System.out.println("Added the Pickup Location");

            // Set dropoff location
            $(By.id("point-end")).shouldBe(visible).setValue("Concord");
            sleep(100);
            $(By.xpath("//body/ul[@id='ui-id-2']/li[1]/div[1]/b[1]")).shouldBe(visible).click();
            System.out.println("Added the Dropoff Location");


            // Open date picker and select May 26, 2025
            $(By.id("pickup-date")).shouldBe(visible).click();
            $(By.xpath("//a[contains(text(),'27')]")).shouldBe(visible).click();// Adjust selector if needed
            System.out.println("Added the Pickup Date");


// Open time picker and select a time (placeholder: adjust to your app)
            // Click the time dropdown to open it
            $(By.id("pickup-time")).shouldBe(visible).click();

// Select the desired time from the dropdown (e.g., 11:00pm)
            $(By.id("pickup-time")).shouldBe(visible).clear();
            $(By.id("pickup-time")).setValue("11:00pm").pressEnter();
            System.out.println("Pickup time entered manually: 11:00pm");
            sleep(10);

// Optionally click 'Book Now'
            $(By.xpath("//body/div[1]/section[1]/div[1]/div[1]/div[1]/div[1]/form[1]/div[2]/div[8]/div[1]/button[1]")).shouldBe(visible, Duration.ofSeconds(50)).click();
            System.out.println("Lets begin for next Process");
            sleep(120);

// Choose the Fleet 'Click Select'
            $(By.xpath("//button[contains(text(), 'Select')]")).shouldBe(visible, Duration.ofSeconds(20)).click();
            System.out.println("Fleet is Selected");
            sleep(30);

//Passenger Info Page
            $(By.xpath("//button[contains(text(),'Submit')]")).click();
            System.out.println("Submitted successfully");
            sleep(30);

//Extras
            // Extras - Add Infant Seat
            $(By.xpath("//body/section[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[2]/form[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/input[1]"))
                    .shouldBe(visible, Duration.ofSeconds(10))
                    .scrollIntoView(true)
                    .pressEnter();
            System.out.println("Added 1 Infant Seat");
            sleep(30);

// You can add more by repeating the `.click()` if needed
// To add 2 seats:
// .click(); .click();
            //Adding Passenger
            $(By.id("passenger_count")).selectOption("3");
            System.out.println("Added  Passengers");

            //Adding Luggage
            $(By.xpath("//body/section[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[2]/form[1]/div[1]/div[1]/div[4]/div[2]/div[1]/select[1]"))
                    .shouldBe(visible, Duration.ofSeconds(10)).selectOption("2");
            System.out.println("Luggage Added");

            //Purchase Order Reference
            $(By.xpath("//body/section[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[2]/form[1]/div[1]/div[1]/div[5]/div[1]/div[1]/input[1]"))
                    .click();
            $(By.xpath("//body/section[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[2]/form[1]/div[1]/div[1]/div[5]/div[1]/div[1]/input[1]"))
                    .shouldBe(visible, Duration.ofSeconds(10)).setValue("1234567");
            System.out.println("Purchase Order Added");

            //Additional Into to your Driver
            $(By.xpath("//body/section[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[2]/form[1]/div[1]/div[1]/div[6]/div[1]/div[1]/textarea[1]"))
                    .click();
            $(By.xpath("//body/section[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[2]/form[1]/div[1]/div[1]/div[6]/div[1]/div[1]/textarea[1]"))
                    .shouldBe(visible, Duration.ofSeconds(10)).setValue("Booking is done please do come fast and take me to my Destination");
            System.out.println("Additional into to driver is added");

            $(By.xpath("//body[1]/section[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[2]/form[1]/div[2]/button[1]/strong[1]"))
                    .shouldBe(visible, Duration.ofSeconds(10)) .click();
            $(By.xpath("//body/section[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[2]/form[1]/div[1]/div[4]/div[1]/label[1]/span[1]"))
                    .shouldBe(visible, Duration.ofSeconds(10)) .click();
            $(By.xpath("//body[1]/section[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[2]/form[1]/div[2]/button[1]/strong[1]"))
                    .shouldBe(visible, Duration.ofSeconds(10)) .click();
            sleep(30);
            System.out.println("Booking confirmed successfully!");
            // Cleanup
            passenger1.quit();
        }
}
