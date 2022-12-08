package acceptance_test.pom.component;

import acceptance_test.pom.BasePage;
import org.openqa.selenium.By;

public class GeneralTab extends BasePage {

    public enum TOGGLE {
        TOTAL_APPROVED,
        AMOUNT_DISBURSED,
        DEFAULT_RATE
    }

    private static final By TOGGLES = By.cssSelector("div[class*='col-xs-12 col-md-7'] label");
    private static final By CHARTS = By.cssSelector("g.highcharts-series.highcharts-series-0");
    private static final By TOTAL_AS_LATEST_QUARTER_POINT = By.cssSelector("g.highcharts-markers.highcharts-tracker path:last-child");
    private static final By TOTAL_NUMBER = By.cssSelector("g tspan[style='font-weight:bold;']");

    public void clickToggle(TOGGLE toggleName) {
        int index = 0;
        switch (toggleName) {
            case TOTAL_APPROVED: break;
            case AMOUNT_DISBURSED: index = 1; break;
            case DEFAULT_RATE: index = 2; break;
        }
        seleniumActionUtils.waitUntilElementsPresent(TOGGLES).get(index).click();
        seleniumActionUtils.waitUntilThePageIsFullyLoaded(60);
    }

    public String getTotalAmountLatestResult() {
        seleniumActionUtils.moveToChartPoint(seleniumActionUtils.waitUntilElementsPresent(CHARTS).get(0),
                seleniumActionUtils.waitUntilElementPresent(TOTAL_AS_LATEST_QUARTER_POINT));
        return seleniumActionUtils.waitUntilElementPresent(TOTAL_NUMBER).getText();
    }
}
