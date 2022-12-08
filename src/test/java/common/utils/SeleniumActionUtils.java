package common.utils;

import common.AutomationConfigs;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
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
    private SeleniumActionUtils() {
    }

    public static void waitAndClick(WebDriver driver, By locator) {
        waitUntilElementPresent(driver, locator, FIXED_WAIT_TIME_OUT).click();
    }

    public static Boolean isElementClickable(WebDriver driver, By locator) {
        WebElement element = waitUntilElementClickable(driver, locator, FIXED_WAIT_TIME_OUT);
        return element != null;
    }

    public static WebElement waitUntilElementPresent(WebDriver driver, By locator, int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds)).until(
                ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static WebElement waitUntilElementPresent(WebDriver driver, By locator) {
        return waitUntilElementPresent(driver, locator, FIXED_WAIT_TIME_OUT);
    }

    public static List<WebElement> waitUntilElementsPresent(WebDriver driver, By locator, int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds)).until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public static List<WebElement> waitUntilElementsPresent(WebDriver driver, By locator) {
        return waitUntilElementsPresent(driver, locator, FIXED_WAIT_TIME_OUT);
    }


    public static WebElement waitUntilElementClickable(WebDriver driver, By locator, int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds)).until(
                ExpectedConditions.elementToBeClickable(locator));
    }

    public static void waitUntilThePageIsFullyLoaded(WebDriver driver, int seconds) {
        new WebDriverWait(driver, Duration.ofSeconds(seconds)).until(
                webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete"));
    }
    public static void moveToChartPoint(WebDriver driver, WebElement chart, WebElement point) {
        Actions actions = new Actions(driver);
        Action builder = actions.moveToElement(chart).click(point).build();
        builder.perform();
    }

}
