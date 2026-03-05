package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import utilities.ParameterProvider;
import utilities.WaitHelper;

import java.net.URL;
import java.time.Duration;

public class BaseTest {

    protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected ThreadLocal<WaitHelper> waiter = new ThreadLocal<>();
    protected ThreadLocal<WebDriverWait> webDriverWait = new ThreadLocal<>();

    @BeforeClass
    public void setupClass() {
        String remoteUrl = System.getenv("SELENOID_URL");   // webhook test
        if (remoteUrl == null || remoteUrl.isEmpty()) {
            WebDriverManager.chromedriver().setup();
        }
    }

    @BeforeMethod
    public void setUp() {
        ChromeOptions opt = new ChromeOptions();
        opt.setBrowserVersion("128.0");
        String headless = System.getProperty("headless", "true");
        if (Boolean.parseBoolean(headless)) {
            opt.addArguments("--headless=new");
        }
        opt.addArguments("--no-sandbox");
        opt.addArguments("--disable-dev-shm-usage");
        try {
            String remoteUrl = System.getenv("SELENOID_URL");
            if (remoteUrl != null && !remoteUrl.isEmpty()) {
                URL url = new URL(remoteUrl);
                driver.set(new RemoteWebDriver(url, opt));
                System.out.println("Running on RemoteWebDriver: " + remoteUrl);
            } else {
                driver.set(new ChromeDriver(opt));
                System.out.println("Running locally with ChromeDriver");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize WebDriver", e);
        }
        driver.get().manage().window().maximize();

        webDriverWait.set(new WebDriverWait(driver.get(), Duration.ofSeconds(
                Long.parseLong(ParameterProvider.get("wait.time"))
        )));
        waiter.set(new WaitHelper(driver.get(), webDriverWait.get()));

        driver.get().get(ParameterProvider.get("base.url"));
    }

    @AfterMethod
    public void tearDown() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
            waiter.remove();
            webDriverWait.remove();
        }
    }

    public WebDriver getDriver() { return driver.get(); }
    public WaitHelper getWaiter() { return waiter.get(); }
}

