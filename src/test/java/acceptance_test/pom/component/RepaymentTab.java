package acceptance_test.pom.component;

import common.utils.SeleniumActionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RepaymentTab {

    public enum REPAYMENT {
        TOTAL,
        PRINCIPAL,
        INTEREST
    }

    private static final By CHARTS = By.cssSelector("g.highcharts-series.highcharts-column-series");
    private static final By CHILD = By.cssSelector("g.highcharts-series.highcharts-column-series rect");
    private static final By TOTAL_REPAYMENT = By.cssSelector("g tspan[style='font-weight:bold;']");
    WebDriver driver;

    public RepaymentTab(WebDriver driver) {
        this.driver = driver;
    }

    public String getTotalRepaymentLatestResult(REPAYMENT repaymentType) {
        int index = 0;
        switch (repaymentType) {
            case TOTAL: break;
            case PRINCIPAL: index = 1; break;
            case INTEREST: index = 2; break;
        }
        SeleniumActionUtils.moveToChartPoint(driver,
                SeleniumActionUtils.waitUntilElementsPresent(driver, CHARTS).get(index),
                SeleniumActionUtils.waitUntilElementsPresent(driver, CHILD).get(index));
        return SeleniumActionUtils.waitUntilElementPresent(driver, TOTAL_REPAYMENT).getText();
    }
}
