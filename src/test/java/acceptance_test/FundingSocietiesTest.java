package acceptance_test;

import acceptance_test.pom.BasePage;
import acceptance_test.pom.StatisticPage;
import acceptance_test.pom.component.GeneralTab;
import acceptance_test.pom.component.RepaymentTab;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class FundingSocietiesTest extends BaseTest {

    @Test
    public void verify_TotalFunded_NoOfFinancing_DefaultRate_And_FinancingFulfillmentRate() {
        homePage.clickNavigationMenu(BasePage.TabMenu.STATISTICS);

        Map<String, String> statisticDetails = statisticPage.getStatisticDetails();
        Assert.assertTrue(statisticDetails.get("Total funded").matches(".*\\d.*"));
        Assert.assertTrue(statisticDetails.get("Default\nrate").matches(".*\\d.*"));
        Assert.assertTrue(statisticDetails.get("No. of\nfinancing").matches(".*\\d.*"));
        Assert.assertTrue(statisticDetails.get("Financing\nfulfillment rate").matches(".*\\d.*"));
    }

    @Test
    public void verify_General_Repayment_Disbursement_Tabs_Displayed_And_Clickable() {
        homePage.clickNavigationMenu(BasePage.TabMenu.STATISTICS);

        Assert.assertTrue(statisticPage.isButtonDisplayedAndClickable("General"));
        Assert.assertTrue(statisticPage.isButtonDisplayedAndClickable("Repayment"));
        Assert.assertTrue(statisticPage.isButtonDisplayedAndClickable("Disbursement"));
    }

    @Test
    public void verify_General_Tab_For_Total_Approved_Loans_Total_Amount_Disbursed_And_Default_Rate() {
        homePage.clickNavigationMenu(BasePage.TabMenu.STATISTICS);
        statisticPage.clickTab(StatisticPage.StatisticTab.GENERAL);

        Assert.assertTrue(Integer.parseInt(
                statisticPage.generalTab.getTotalAmountLatestResult().replace(",", "")) >= 5129893,
        "Verify total approved loans");

        statisticPage.generalTab.clickToggle(GeneralTab.TOGGLE.AMOUNT_DISBURSED);
        Assert.assertTrue( Float.parseFloat(statisticPage.generalTab.getTotalAmountLatestResult()) >= 3.96,
                "Verify total amount disbursed");

        statisticPage.generalTab.clickToggle(GeneralTab.TOGGLE.DEFAULT_RATE);
        Assert.assertTrue(Float.parseFloat(statisticPage.generalTab.getTotalAmountLatestResult()) >= 1.3,
                "Verify total default rate");
    }

    @Test
    public void verify_Repayment_Tab_For_Total_Repayment_Amount_Principal_Amount_And_Interest_Amount() {
        homePage.clickNavigationMenu(BasePage.TabMenu.STATISTICS);
        statisticPage.clickTab(StatisticPage.StatisticTab.REPAYMENT);

        String totalRepaymentStr = statisticPage.repaymentTab.getTotalRepaymentLatestResult(
                RepaymentTab.REPAYMENT.TOTAL).replace(",", "");
        Assert.assertTrue(Float.parseFloat(totalRepaymentStr) >= 3780.8, "Verify total repayment");

        String principalAmountStr = statisticPage.repaymentTab.getTotalRepaymentLatestResult(
                RepaymentTab.REPAYMENT.PRINCIPAL).replace(",", "");
        Assert.assertTrue(Float.parseFloat(principalAmountStr) >= 3684.6, "Verify principal amount");

        String interestAmountStr = statisticPage.repaymentTab.getTotalRepaymentLatestResult(
                RepaymentTab.REPAYMENT.PRINCIPAL).replace(",", "");
        Assert.assertTrue(Float.parseFloat(interestAmountStr) >= 96.1, "Verify interest amount");

    }

    @Test
    public void verify_Disbursement_Tab_By_Store_All_Industry_Names_According_Percentage() {
        homePage.clickNavigationMenu(BasePage.TabMenu.STATISTICS);
        statisticPage.clickTab(StatisticPage.StatisticTab.DISBURSEMENT);

        Map<String, Float> data = statisticPage.disbursementTab.getAllDisbursementNameAndPercentage();
        System.out.println("Print out Disbursement industry Name and Percentage by increase order");
        data.forEach((key, value) -> {
            System.out.println("Disbursement industry Names: " + key + " ==> Total Percentage: " + value);
        });
    }
}
