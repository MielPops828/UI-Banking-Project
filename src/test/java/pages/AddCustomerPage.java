package pages;

import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.ParameterProvider;
import utilities.ValueGenerator;
import utilities.WaitHelper;

import java.time.Duration;

public class AddCustomerPage {
    private final WebDriver driver;
    private final WaitHelper waiter;
    private String pc;
    private String actualText;

    @FindBy(xpath = "//button[contains(text(), 'Add Customer')]")
    private WebElement addCustomerMenuButton;

    @FindBy(css = "input[ng-model='fName']")
    private WebElement firstNameField;

    @FindBy(css = "input[ng-model='lName']")
    private WebElement lastNameField;

    @FindBy(css = "input[ng-model='postCd']")
    private WebElement postCodeField;

    @FindBy(css = "button[type='submit']")
    private WebElement addCustomerButton;

    public AddCustomerPage(WebDriver driver, WaitHelper waiter){
        this.driver = driver;
        this.waiter = waiter;
        PageFactory.initElements(driver, this);
    }

    public AddCustomerPage openPage(){
        Allure.step("Открыть страницу XYZ Bank", () -> driver.get(ParameterProvider.get("base.url")));
        return this;
    }

    public AddCustomerPage openAddCustomer(){
        Allure.step("Открыть вкладку Add Customer", () -> {
            waiter.waitForElementToBeClickable(addCustomerMenuButton);
            addCustomerMenuButton.click();
            waiter.waitForElementVisible(firstNameField);
        });
        return this;
    }

    public AddCustomerPage setPostCode(){
        Allure.step("Сгенерировать номер и записать его в поле Post Code", () -> {
            pc = ValueGenerator.generatePostCode();
            postCodeField.sendKeys(pc);
        });
        return this;
    }

    public AddCustomerPage setFirstName(){
        Allure.step("Заполнить поле First Name, преобразовав введенный ранее номер в Post Code", () -> firstNameField.sendKeys(ValueGenerator.postCodeToName(pc)));
        return this;
    }

    public AddCustomerPage setLastName(String lastName){
        Allure.step("Заполнить поле Last Name: " + lastName, () -> lastNameField.sendKeys(lastName));
        return this;
    }

    public AddCustomerPage clickAddCustomer(){
        Allure.step("Нажать на кнопку Add Customer", () -> addCustomerButton.click());
        return this;
    }

    public String verifyAlertText() {
        Allure.step("Проверить текст алерта", () -> {
            new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(driver1 -> {
                        try {
                            driver1.switchTo().alert();
                            return true;
                        } catch (Exception e) {
                            return false;
                        }
                    });
            actualText = driver.switchTo().alert().getText();
        });
        return actualText;
    }

    public AddCustomerPage acceptAlert(){
        Allure.step("Принять алерт", () -> driver.switchTo().alert().accept());
        return this;
    }
}