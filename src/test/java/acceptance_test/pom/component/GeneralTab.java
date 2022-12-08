package acceptance_test.pom.component;

import acceptance_test.pom.StatisticPage;
import common.utils.SeleniumActionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GeneralTab {

    public enum TOGGLE {
        TOTAL_APPROVED,
        AMOUNT_DISBURSED,
        DEFAULT_RATE
    }

    private static final By TOGGLES = By.cssSelector("div[class*='col-xs-12 col-md-7'] label");
    private static final By CHARTS = By.cssSelector("g.highcharts-series.highcharts-series-0");
    private static final By TOTAL_AS_LATEST_QUARTER_POINT = By.cssSelector("g.highcharts-markers.highcharts-tracker path:last-child");
    private static final By TOTAL_NUMBER = By.cssSelector("g tspan[style='font-weight:bold;']");

    WebDriver driver;

    public GeneralTab(WebDriver driver) {
        this.driver = driver;
    }

    public void clickToggle(TOGGLE toggleName) {
        int index = 0;
        switch (toggleName) {
            case TOTAL_APPROVED: break;
            case AMOUNT_DISBURSED: index = 1; break;
            case DEFAULT_RATE: index = 2; break;
        }
        SeleniumActionUtils.waitUntilElementsPresent(driver, TOGGLES).get(index).click();
        SeleniumActionUtils.waitUntilThePageIsFullyLoaded(driver, 60);
    }

    public String getTotalAmountLatestResult() {
        SeleniumActionUtils.moveToChartPoint(driver,
                SeleniumActionUtils.waitUntilElementsPresent(driver, CHARTS).get(0),
                SeleniumActionUtils.waitUntilElementPresent(driver, TOTAL_AS_LATEST_QUARTER_POINT));
        return SeleniumActionUtils.waitUntilElementPresent(driver, TOTAL_NUMBER).getText();
    }
}
