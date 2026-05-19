package acceptance_test;

import acceptance_test.pom.PracticeAutomationLoginPage;
import acceptance_test.pom.ReqresHomePage;
import common.AutomationConfigs;
import common.driver.DriverFactory;
import common.driver.DriverStorage;
import common.driver.DriverType;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;
    protected ReqresHomePage reqresHomePage;
    protected PracticeAutomationLoginPage loginPage;

    AutomationConfigs automationConfigs = AutomationConfigs.getInstance();

    @BeforeMethod
    public void beforeMethod() {
        DriverStorage.setDriver(DriverFactory.getManager(getBrowserConfig()).getDriver());
        driver = DriverStorage.getDriver();
        reqresHomePage = new ReqresHomePage();
        loginPage = new PracticeAutomationLoginPage();
    }

    @AfterMethod
    public void afterMethod() {
        DriverStorage.getDriver().quit();
    }

    private DriverType getBrowserConfig() {
        switch (automationConfigs.getConfigs().getProperty("automation.browser")) {
            case "chrome": return DriverType.CHROME;
        }
        return DriverType.CHROME;
    }
}
