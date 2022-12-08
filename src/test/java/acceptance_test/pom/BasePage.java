package acceptance_test.pom;

import common.utils.SeleniumActionUtils;
import org.openqa.selenium.By;

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

    protected SeleniumActionUtils seleniumActionUtils = new SeleniumActionUtils();

    public void clickNavigationMenu(TabMenu tabName) {
        seleniumActionUtils.waitAndClick(By.xpath(String.format(NAV_BAR, tabName.getValue())));
        seleniumActionUtils.waitUntilThePageIsFullyLoaded(60);
    }

    public Boolean isButtonDisplayedAndClickable(String name) {
        return seleniumActionUtils.isElementClickable(By.xpath(String.format(BUTTON, name)));
    }

}
