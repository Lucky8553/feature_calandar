package feature_calander;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class DatePickerTest1 {
    WebDriver driver;
    String selectedDate;

    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setup() {
        // Initialize ExtentReports and set the path for the report
        ExtentSparkReporter reporter = new ExtentSparkReporter("reports/DatePickerTestReport.html");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
        
        // Create test instance
        test = extent.createTest("Date Picker Test", "Select Date of Birth and validate previous and next dates");

        // To Set the path for the ChromeDriver
        System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");

        // Initializing the ChromeDriver
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        test.log(Status.INFO, "Launching the Chrome Browser");

        // Open the jQuery UI Datepicker page
        driver.get("https://jqueryui.com/datepicker/");
        test.log(Status.INFO, "Navigated to jQuery UI Datepicker page");
        
        // Switch to iframe since the date picker is inside an iframe
        driver.switchTo().frame(driver.findElement(By.className("demo-frame")));
        test.log(Status.INFO, "Switched to iframe");
    }

    @Test
    public void selectDateOfBirth() {
        test.log(Status.INFO, "Starting the test to select date of birth");

        WebElement dateInput = driver.findElement(By.id("datepicker"));
        dateInput.click();
        test.log(Status.INFO, "Clicked on the date input field");

        // Select the year 1998
        while (true) {
            String currentYear = driver.findElement(By.className("ui-datepicker-year")).getText();
            if (currentYear.equals("1998")) {
                test.log(Status.INFO, "Year 1998 selected");
                break;
            } else {
                driver.findElement(By.className("ui-datepicker-prev")).click();
                test.log(Status.INFO, "Clicking previous year arrow");
            }
        }

        // Select the month August
        while (true) {
            String currentMonth = driver.findElement(By.className("ui-datepicker-month")).getText();
            if (currentMonth.equals("August")) {
                test.log(Status.INFO, "Month August selected");
                break;
            } else {
                driver.findElement(By.className("ui-datepicker-prev")).click();
                test.log(Status.INFO, "Clicking previous month arrow");
            }
        }

        // Select the day 14
        driver.findElement(By.xpath("//a[text()='14']")).click();
        test.log(Status.INFO, "Day 14 selected");

        // Get the selected date
        selectedDate = dateInput.getAttribute("value");

        // Calculate previous and next dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate selectedLocalDate = LocalDate.parse(selectedDate, formatter);

        LocalDate previousDate = selectedLocalDate.minusDays(1);
        LocalDate nextDate = selectedLocalDate.plusDays(1);

        // Print the previous, selected, and next dates
        test.log(Status.INFO, "Previous Date: " + previousDate.format(formatter));
        test.log(Status.PASS, "Date of Birth: " + selectedDate);
        test.log(Status.INFO, "Future Date: " + nextDate.format(formatter));
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
        test.log(Status.INFO, "Closed the browser");

        extent.flush();
    }
}
