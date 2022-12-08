package common.driver;

import org.openqa.selenium.WebDriver;

public abstract class DriverManager {

    protected WebDriver driver;

    protected abstract void startService();

    protected abstract void stopService();

    protected abstract void createDriver();

    public WebDriver getDriver() {
        if (null == driver) {
            startService();
            createDriver();
        }
        return driver;
    }
}