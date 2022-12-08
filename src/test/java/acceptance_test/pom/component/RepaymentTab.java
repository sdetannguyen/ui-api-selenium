package acceptance_test.pom.component;

import acceptance_test.pom.BasePage;
import org.openqa.selenium.By;

public class RepaymentTab extends BasePage {

    public enum REPAYMENT {
        TOTAL,
        PRINCIPAL,
        INTEREST
    }

    private static final By CHARTS = By.cssSelector("g.highcharts-series.highcharts-column-series");
    private static final By CHILD = By.cssSelector("g.highcharts-series.highcharts-column-series rect");
    private static final By TOTAL_REPAYMENT = By.cssSelector("g tspan[style='font-weight:bold;']");

    public String getTotalRepaymentLatestResult(REPAYMENT repaymentType) {
        int index = 0;
        switch (repaymentType) {
            case TOTAL: break;
            case PRINCIPAL: index = 1; break;
            case INTEREST: index = 2; break;
        }
        seleniumActionUtils.moveToChartPoint(seleniumActionUtils.waitUntilElementsPresent(CHARTS).get(index),
                seleniumActionUtils.waitUntilElementsPresent(CHILD).get(index));
        return seleniumActionUtils.waitUntilElementPresent(TOTAL_REPAYMENT).getText();
    }
}
