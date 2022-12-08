package acceptance_test.pom.component;

import common.utils.SeleniumActionUtils;
import common.utils.SortingUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisbursementTab {

    private static final By CHARTS = By.cssSelector("g.highcharts-series.highcharts-series-0");
    private static final By CHILD = By.cssSelector("g.highcharts-series.highcharts-series-0 path");

    private static final By TOTAL_NAME = By.cssSelector("g tspan[style='font-size: 10px']");
    private static final By TOTAL_VALUE = By.cssSelector("g tspan[style='font-weight:bold;']");

    WebDriver driver;

    public DisbursementTab(WebDriver driver) {
        this.driver = driver;
    }

    public Map<String, Float> getAllDisbursementNameAndPercentage() {
        Map<String, Float> data = new HashMap<>();
        List<WebElement> charts = SeleniumActionUtils.waitUntilElementsPresent(driver, CHARTS);
        List<WebElement> all = SeleniumActionUtils.waitUntilElementsPresent(driver, CHILD);
        for (WebElement element : all) {
            SeleniumActionUtils.moveToChartPoint(driver, charts.get(0), element);
            String disbursementName = SeleniumActionUtils.waitUntilElementPresent(driver, TOTAL_NAME).getText();
            String disbursementPercentage = SeleniumActionUtils.waitUntilElementPresent(driver, TOTAL_VALUE).getText();
            data.put(disbursementName, Float.parseFloat(disbursementPercentage));
        }
        return SortingUtils.sortByValue(data);
    }
}
