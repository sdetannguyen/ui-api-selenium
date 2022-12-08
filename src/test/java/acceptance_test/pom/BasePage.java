package acceptance_test.pom;

import common.utils.SeleniumActionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class BasePage {

    public enum TabMenu {
        INVESTMENT("Investment"),
        STATISTICS("Statistics"),
        HELP("Help");
        private final String val;
        private TabMenu(String val) {
            this.val = val;
        }

        public String getValue() {
            return this.val;
        }
    }

    public static final String NAV_BAR = ".//div[@class='navbar']//a[text()='%s']";
    public static final String BUTTON = ".//button[contains(text(),'%s')]";

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickNavigationMenu(TabMenu tabName) {
        SeleniumActionUtils.waitAndClick(driver, By.xpath(String.format(NAV_BAR, tabName.getValue())));
        SeleniumActionUtils.waitUntilThePageIsFullyLoaded(driver, 60);
    }

    public Boolean isButtonDisplayedAndClickable(String name) {
        return SeleniumActionUtils.isElementClickable(driver, By.xpath(String.format(BUTTON, name)));
    }

}
