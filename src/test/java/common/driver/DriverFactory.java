package common.driver;

public class DriverFactory {

    public static DriverManager getManager(DriverType type) {
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        DriverManager driverManager;
        switch (type) {
            //Init more driver types (Eg: Firefox, Safari...) here
            default:
                driverManager = new ChromeDriverManager();
                break;
        }
        return driverManager;
    }
}