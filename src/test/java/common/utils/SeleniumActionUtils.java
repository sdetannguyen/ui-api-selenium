package common.utils;

import common.AutomationConfigs;
import common.driver.DriverStorage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SeleniumActionUtils {

    private static final int FIXED_WAIT_TIME_OUT = Integer.parseInt(AutomationConfigs.getInstance()
            .getConfigs().get("application.ui.waitTimeOut").toString());

    public SeleniumActionUtils() {
    }

    public void waitAndClick(By locator) {
        waitUntilElementPresent(locator, FIXED_WAIT_TIME_OUT).click();
    }

    public Boolean isElementClickable(By locator) {
        WebElement element = waitUntilElementClickable(locator, FIXED_WAIT_TIME_OUT);
        return element != null;
    }

    public WebElement waitUntilElementPresent(By locator, int seconds) {
        WebElement element;
        try {
            element = new WebDriverWait(DriverStorage.getDriver(), Duration.ofSeconds(seconds)).until(
                    ExpectedConditions.presenceOfElementLocated(locator));
        } catch (StaleElementReferenceException e) {
            element = DriverStorage.getDriver().findElement(locator);
        }
        return element;
    }

    public WebElement waitUntilElementPresent(By locator) {
        return waitUntilElementPresent(locator, FIXED_WAIT_TIME_OUT);
    }

    public List<WebElement> waitUntilElementsPresent(By locator, int seconds) {
        return new WebDriverWait(DriverStorage.getDriver(), Duration.ofSeconds(seconds)).until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public List<WebElement> waitUntilElementsPresent(By locator) {
        return waitUntilElementsPresent(locator, FIXED_WAIT_TIME_OUT);
    }


    public WebElement waitUntilElementClickable(By locator, int seconds) {
        return new WebDriverWait(DriverStorage.getDriver(), Duration.ofSeconds(seconds)).until(
                ExpectedConditions.elementToBeClickable(locator));
    }

    public void waitUntilThePageIsFullyLoaded(int seconds) {
        new WebDriverWait(DriverStorage.getDriver(), Duration.ofSeconds(seconds)).until(
                webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete"));
    }
    public void moveToChartPoint(WebElement chart, WebElement point) {
        Actions actions = new Actions(DriverStorage.getDriver());
        Action builder = actions.moveToElement(chart).click(point).build();
        builder.perform();
    }

}
