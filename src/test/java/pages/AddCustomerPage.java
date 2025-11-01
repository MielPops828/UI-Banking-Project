package pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
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

    @Step("Открыть страницу XYZ Bank")
    public AddCustomerPage openPage(){
        driver.get(ParameterProvider.get("base.url"));
        return this;
    }

    @Step("Открыть вкладку Add Customer")
    public AddCustomerPage openAddCustomer(){
        waiter.waitForElementToBeClickable(addCustomerMenuButton);
        addCustomerMenuButton.click();
        waiter.waitForElementVisible(firstNameField);
        return this;
    }

    @Step("Сгенерировать номер и записать его в поле Post Code")
    public AddCustomerPage setPostCode(){
        pc = ValueGenerator.generatePostCode();
        postCodeField.sendKeys(pc);
        return this;
    }

    @Step("Заполнить поле First Name, преобразовав введенный ранее номер в Post Code")
    public AddCustomerPage setFirstName(){
        firstNameField.sendKeys(ValueGenerator.postCodeToName(pc));
        return this;
    }

    @Step("Заполнить поле Last Name: {lastName}")
    public AddCustomerPage setLastName(String lastName){
        lastNameField.sendKeys(lastName);
        return this;
    }

    @Step("Нажать на кнопку Add Customer")
    public AddCustomerPage clickAddCustomer(){
        addCustomerButton.click();
        return this;
    }

    @Step("Проверить текст алерта")
    public String verifyAlertText() {
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
        return actualText;
    }

    @Step("Принять алерт")
    public AddCustomerPage acceptAlert(){
        driver.switchTo().alert().accept();
        return this;
    }
}