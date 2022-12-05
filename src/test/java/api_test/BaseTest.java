package api_test;

import common.AutomationConfigs;
import common.apis.LoginAPIs;
import common.apis.UserAPIs;

public class BaseTest {

    AutomationConfigs automationConfigs = AutomationConfigs.getInstance();
    String env = automationConfigs.getConfigs().getProperty("application.env");

    LoginAPIs loginAPIs = new LoginAPIs();
    UserAPIs userAPIs = new UserAPIs();

}
