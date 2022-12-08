package acceptance_test;

import acceptance_test.pom.HomePage;
import acceptance_test.pom.StatisticPage;
import common.AutomationConfigs;
import common.driver.DriverFactory;
import common.driver.DriverManager;
import common.driver.DriverStorage;
import common.driver.DriverType;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    DriverManager driverManager;
    WebDriver driver;
    HomePage homePage;
    StatisticPage statisticPage;

    AutomationConfigs automationConfigs = AutomationConfigs.getInstance();
    @BeforeClass
    public void initDriverHook() {
    }

    @BeforeMethod
    public void beforeMethod() {
        driverManager = DriverFactory.getManager(getBrowserConfig());
        DriverStorage.setDriver(driverManager.getDriver());
        driver = DriverStorage.getDriver();
        driver.get((String) automationConfigs.getConfigs().get("application.ui.baseUrl"));

        homePage = new HomePage();
        statisticPage = new StatisticPage();
    }

    @AfterMethod
    public void afterMethod() {
        DriverStorage.getDriver().quit();
    }

    private DriverType getBrowserConfig() {
        switch ((String) automationConfigs.getConfigs().get("automation.browser")) {
            case "chrome": return DriverType.CHROME;
        }
        return DriverType.CHROME;
    }

}
