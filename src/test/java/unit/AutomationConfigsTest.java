package unit;

import common.AutomationConfigs;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Properties;

public class AutomationConfigsTest {

    @Test
    public void getInstance_returnsSameInstance() {
        AutomationConfigs first = AutomationConfigs.getInstance();
        AutomationConfigs second = AutomationConfigs.getInstance();

        Assert.assertSame(first, second, "getInstance should return the same singleton");
    }

    @Test
    public void getConfigs_loadsExpectedProperties() {
        Properties configs = AutomationConfigs.getInstance().getConfigs();

        Assert.assertNotNull(configs, "Configs should not be null");
        Assert.assertNotNull(configs.getProperty("application.api.baseUrl"), "API base URL should be set");
        Assert.assertNotNull(configs.getProperty("application.ui.baseUrl"), "UI base URL should be set");
        Assert.assertNotNull(configs.getProperty("automation.browser"), "Browser should be set");
    }

    @Test
    public void getConfigs_returnsCorrectApiBaseUrl() {
        String apiBaseUrl = AutomationConfigs.getInstance().getConfigs().getProperty("application.api.baseUrl");

        Assert.assertEquals(apiBaseUrl, "https://jsonplaceholder.typicode.com");
    }
}
