package common.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxDriverManager extends DriverManager {
    private FirefoxDriverService fService;

    @Override
    public void startService() {
        WebDriverManager.firefoxdriver().setup();
    }

    @Override
    public void stopService() {
        if (null != fService && fService.isRunning())
            fService.stop();
    }

    @Override
    public void createDriver() {
        driver = new FirefoxDriver();
    }
}
