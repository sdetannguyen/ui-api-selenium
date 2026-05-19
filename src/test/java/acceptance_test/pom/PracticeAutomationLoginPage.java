package acceptance_test.pom;

import common.AutomationConfigs;
import common.driver.DriverStorage;
import org.openqa.selenium.By;

public class PracticeAutomationLoginPage extends BasePage {

    public void navigateTo() {
        DriverStorage.getDriver().get(
                AutomationConfigs.getInstance().getConfigs().getProperty("application.auth.baseUrl")
                        + "/practice-test-login/");
    }

    private static final By USERNAME_FIELD = By.id("username");
    private static final By PASSWORD_FIELD = By.id("password");
    private static final By SUBMIT_BTN = By.id("submit");
    private static final By SUCCESS_MSG = By.xpath(".//h1[contains(text(),'Logged In Successfully')]");

    public void login(String username, String password) {
        seleniumActionUtils.waitUntilElementPresent(USERNAME_FIELD).sendKeys(username);
        seleniumActionUtils.waitUntilElementPresent(PASSWORD_FIELD).sendKeys(password);
        seleniumActionUtils.waitAndClick(SUBMIT_BTN);
    }

    public boolean isLoginSuccessful() {
        return seleniumActionUtils.waitUntilElementPresent(SUCCESS_MSG).isDisplayed();
    }
}
