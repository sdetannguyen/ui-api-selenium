package acceptance_test.pom;

import acceptance_test.pom.component.DisbursementTab;
import acceptance_test.pom.component.GeneralTab;
import acceptance_test.pom.component.RepaymentTab;
import common.utils.SeleniumActionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticPage extends BasePage {

    public enum StatisticTab {
        GENERAL("General"),
        REPAYMENT("Repayment"),
        DISBURSEMENT("Disbursement");
        private final String val;
        private StatisticTab(String val) {
            this.val = val;
        }

        public String getValue() {
            return this.val;
        }
    }

    private static final By STATISTIC_DETAILS = By.cssSelector("div[class='statisticDetailRow']");
    private static final By STATISTIC_DETAILS_VALUE = By.cssSelector("div[class='statisticDetailRow'] font");
    private static final By STATISTIC_DETAILS_KEY = By.cssSelector("div[class='statisticDetailRow'] div div");

    public GeneralTab generalTab;
    public RepaymentTab repaymentTab;
    public DisbursementTab disbursementTab;

    public StatisticPage(WebDriver driver) {
        super(driver);
        generalTab = new GeneralTab(driver);
        repaymentTab = new RepaymentTab(driver);
        disbursementTab = new DisbursementTab(driver);
    }

    public void clickTab(StatisticTab statisticTab) {
        if(isButtonDisplayedAndClickable(statisticTab.getValue())) {
            SeleniumActionUtils.waitAndClick(driver, By.xpath(String.format(BUTTON, statisticTab.getValue())));
        }
    }

    public Map<String, String> getStatisticDetails() {
        WebElement statisticElement = SeleniumActionUtils.waitUntilElementPresent(driver, STATISTIC_DETAILS);
        List<WebElement> statisticValues = statisticElement.findElements(STATISTIC_DETAILS_VALUE);
        List<WebElement> statisticKeys = statisticElement.findElements(STATISTIC_DETAILS_KEY);
        Map<String, String> statisticDetails = new HashMap<>();
        for (int i = 0; i < statisticKeys.size(); i++) {
            statisticDetails.put(statisticKeys.get(i).getText(), statisticValues.get(i).getText());
        }
        return statisticDetails;
    }

}
