package utilities.test_base;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utilities.cli.CLI;
import utilities.exception_handling.ExceptionHandling;

import java.io.IOException;

public class TestBase extends AbstractTestNGCucumberTests {
    private static WebDriver webDriver;

    @BeforeMethod
    public static void setUpDriver() {
        webDriver = new ChromeDriver();
    }

    public static WebDriver getDriver() {
        try {
            assert webDriver != null;
            return webDriver;
        } catch (Exception exception) {
            ExceptionHandling.handleException(exception);
            return null;
        }
    }

    @AfterMethod
    public void tearDownDriver() {
        try {
            webDriver.quit();
        } catch (Exception exception) {
            ExceptionHandling.handleException(exception);
        }
    }

    @BeforeSuite
    public void deleteOutDatedAllureReport() throws IOException {
        CLI.executeCommandLine("cd allure-results");
        CLI.executeCommandLine("DEL /S /Q *.json");
    }

    @AfterSuite
    public void generateAllureReport() throws IOException {
        CLI.executeCommandLine("allure serve allure-results");
    }
}
