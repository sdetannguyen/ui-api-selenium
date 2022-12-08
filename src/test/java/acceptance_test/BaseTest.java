package acceptance_test;

import common.driver.DriverFactory;
import common.driver.DriverManager;
import common.driver.DriverType;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;

public class BaseTest {
    DriverManager driverManager;
    WebDriver driver;

    @BeforeTest
    public void beforeTest() {
        driverManager = DriverFactory.getManager(DriverType.CHROME);
    }

}
