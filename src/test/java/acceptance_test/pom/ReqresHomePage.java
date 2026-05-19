package acceptance_test.pom;

import common.AutomationConfigs;
import common.driver.DriverStorage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ReqresHomePage extends BasePage {

    private static final By HERO_HEADING = By.xpath(
            ".//h1[contains(.,'A real backend')]");

    public void goto_() {
        DriverStorage.getDriver().get(
                AutomationConfigs.getInstance().getConfigs().getProperty("application.ui.baseUrl"));
    }

    public WebElement getHeroHeading() {
        return seleniumActionUtils.waitUntilElementPresent(HERO_HEADING);
    }

    public boolean isHeroHeadingVisible() {
        return getHeroHeading().isDisplayed();
    }
}
