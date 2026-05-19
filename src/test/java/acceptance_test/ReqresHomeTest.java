package acceptance_test;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ReqresHomeTest extends BaseTest {

    @Test
    public void verifyHeroHeadingIsDisplayed() {
        reqresHomePage.navigateTo();

        Assert.assertTrue(reqresHomePage.isHeroHeadingVisible(),
                "Hero heading should be visible on reqres.in homepage");
    }

    @Test
    public void verifyLoginSuccessfulWithValidCredentials() {
        loginPage.navigateTo();
        loginPage.login(
                automationConfigs.getConfigs().getProperty("application.auth.username"),
                automationConfigs.getConfigs().getProperty("application.auth.password")
        );

        Assert.assertTrue(loginPage.isLoginSuccessful(),
                "Login should succeed with valid credentials");
    }
}
