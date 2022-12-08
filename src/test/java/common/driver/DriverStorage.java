package common.driver;

import org.openqa.selenium.WebDriver;

import java.util.concurrent.ConcurrentHashMap;

/*
 To store driver objects in case the framework running with multiple driver instances
 */
public class DriverStorage {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverStorage(){
    }

    public static void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

}
