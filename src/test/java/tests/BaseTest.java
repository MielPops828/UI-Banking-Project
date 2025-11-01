package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import utilities.ParameterProvider;
import utilities.WaitHelper;

import java.time.Duration;

public class BaseTest {

    protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected ThreadLocal<WaitHelper> waiter = new ThreadLocal<>();
    protected ThreadLocal<WebDriverWait> webDriverWait = new ThreadLocal<>();

    @BeforeClass
    public void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    public void setUp() {
        ChromeOptions opt = new ChromeOptions();
        // opt.addArguments("--headless");
        driver.set(new ChromeDriver(opt));
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

