package acceptance_test.pom;

import common.utils.SeleniumActionUtils;
import org.openqa.selenium.By;

public abstract class BasePage {

    protected static final String BUTTON_XPATH = ".//button[contains(text(),'%s')]";

    protected SeleniumActionUtils seleniumActionUtils = new SeleniumActionUtils();

    public boolean isButtonDisplayedAndClickable(String name) {
        return seleniumActionUtils.isElementClickable(By.xpath(String.format(BUTTON_XPATH, name)));
    }
}
