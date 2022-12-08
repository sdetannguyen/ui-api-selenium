package acceptance_test.pom.component;

import acceptance_test.pom.BasePage;
import common.utils.SortingUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisbursementTab extends BasePage {

    private static final By CHARTS = By.cssSelector("g.highcharts-series.highcharts-series-0");
    private static final By CHILD = By.cssSelector("g.highcharts-series.highcharts-series-0 path");

    private static final By TOTAL_NAME = By.cssSelector("g tspan[style='font-size: 10px']");
    private static final By TOTAL_VALUE = By.cssSelector("g tspan[style='font-weight:bold;']");

    public Map<String, Float> getAllDisbursementNameAndPercentage() {
        seleniumActionUtils.waitUntilThePageIsFullyLoaded(10);
        Map<String, Float> data = new HashMap<>();
        List<WebElement> charts = seleniumActionUtils.waitUntilElementsPresent(CHARTS);
        List<WebElement> all = seleniumActionUtils.waitUntilElementsPresent(CHILD);
        for (WebElement element : all) {
            seleniumActionUtils.moveToChartPoint(charts.get(0), element);
            String disbursementName = seleniumActionUtils.waitUntilElementPresent(TOTAL_NAME).getText();
            String disbursementPercentage = seleniumActionUtils.waitUntilElementPresent(TOTAL_VALUE).getText();
            data.put(disbursementName, Float.parseFloat(disbursementPercentage));
        }
        return SortingUtils.sortByValue(data);
    }
}
