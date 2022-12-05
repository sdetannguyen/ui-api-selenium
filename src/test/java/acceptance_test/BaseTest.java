package acceptance_test;

import common.AutomationConfigs;
import common.driver.DriverFactory;
import common.driver.DriverManager;
import common.driver.DriverType;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

public class BaseTest {
    DriverManager driverManager;
    WebDriver driver;

    @BeforeTest
    public void beforeTest() {
        driverManager = DriverFactory.getManager(DriverType.CHROME);
    }

    @BeforeMethod
    public void beforeMethod() {
        driver = driverManager.getDriver();
        driver.get((String) AutomationConfigs.getInstance().getConfigs().get("application.ui.baseUrl"));
    }

    @AfterMethod
    public void afterMethod() {
        driverManager.quitDriver();
    }
}
