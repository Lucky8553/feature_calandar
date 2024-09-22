package feature_calander;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class DatePickerTest1 {
    WebDriver driver;
    String selectedDate;

  
	@BeforeClass
    public void setup() {
        // To Set the path for the ChromeDriver
        System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");

        // Initializing the ChromeDriver
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // Open the jQuery UI Datepicker page
        driver.get("https://jqueryui.com/datepicker/");
        
        // Switch to iframe since the date picker is inside an iframe
        driver.switchTo().frame(driver.findElement(By.className("demo-frame")));
    }

    @Test
    public void selectDateOfBirth() {
       
        WebElement dateInput = driver.findElement(By.id("datepicker"));
        dateInput.click();

        // Select the year 1998
        while (true) {
            String currentYear = driver.findElement(By.className("ui-datepicker-year")).getText();
            if (currentYear.equals("1998")) {
                break;
            } else {
                driver.findElement(By.className("ui-datepicker-prev")).click();
            }
        }

        // Select the month August
        while (true) {
            String currentMonth = driver.findElement(By.className("ui-datepicker-month")).getText();
            if (currentMonth.equals("August")) {
                break;
            } else {
                driver.findElement(By.className("ui-datepicker-prev")).click();
            }
        }

        // Select the day 14
        driver.findElement(By.xpath("//a[text()='14']")).click();

        // Get the selected date
        selectedDate = dateInput.getAttribute("value");

        // Calculate previous and next dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate selectedLocalDate = LocalDate.parse(selectedDate, formatter);

        LocalDate previousDate = selectedLocalDate.minusDays(1);
        LocalDate nextDate = selectedLocalDate.plusDays(1);

        // for Printing the previous, selected, and next dates
        System.out.println("Previous Date: " + previousDate.format(formatter));
        System.out.println("Date of Birth: " + selectedDate);
        System.out.println("Future Date: " + nextDate.format(formatter));

    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}

