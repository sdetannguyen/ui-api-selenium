package api_test;

import common.AutomationConfigs;
import common.apis.UsersApiClient;

public class BaseTest {

    AutomationConfigs automationConfigs = AutomationConfigs.getInstance();
    String env = automationConfigs.getConfigs().getProperty("application.env");
    UsersApiClient usersApiClient = new UsersApiClient();
}
