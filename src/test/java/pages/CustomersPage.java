package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.ParameterProvider;
import utilities.WaitHelper;

import java.util.ArrayList;
import java.util.List;

public class CustomersPage {
    private final WebDriver driver;
    private final WaitHelper waiter;
    private String toDelete;

    @FindBy(xpath = "//button[contains(text(), 'Customers')]")
    private WebElement customerMenuButton;

    @FindBy(xpath = "//a[@ng-click=\"sortType = 'fName'; sortReverse = !sortReverse\"]")
    private WebElement firstNameRow;

    @FindBy(css = "table tbody tr")
    private List<WebElement> customerRows;

    @FindBy(xpath = "//table[@class='table table-bordered table-striped']/tbody/tr/td[1]")
    private List<WebElement> firstNameCells;

    public CustomersPage(WebDriver driver, WaitHelper waiter){
        this.driver = driver;
        this.waiter = waiter;
        PageFactory.initElements(driver, this);
    }

    @Step("Открыть страницу XYZ Bank")
    public CustomersPage openPage(){
        driver.get(ParameterProvider.get("base.url"));
        return this;
    }

    @Step("Открыть вкладку Customers")
    public CustomersPage openCustomersTab(){
        waiter.waitForElementToBeClickable(customerMenuButton);
        customerMenuButton.click();
        waiter.waitForElementVisible(firstNameRow);
        return this;
    }

    @Step("Сортировать таблицу по First Name")
    public CustomersPage sortByFirstName(){
        waiter.waitForElementVisible(customerRows.get(0));
        firstNameRow.click();
        waiter.waitForElementVisible(customerRows.get(0));
        firstNameRow.click();
        waiter.waitForElementVisible(customerRows.get(0));
        return this;
    }

    public List<String> getFirstNames(){
        List<String> names = new ArrayList<>();
        for (WebElement row : customerRows){
            String firstName = row.findElement(By.xpath("td[1]")).getText();
            names.add(firstName);
        }
        return names;
    }

    @Step("Удалить клиента с именем, длина которого близка к среднему значению суммы длин всех имен")
    public String deleteCustomerWithNameCloseToAverageLength(){
        List<String> firstNames = getFirstNames();
        double avgLength = firstNames.stream()
                .mapToInt(String::length)
                .average()
                .orElse(0);
        toDelete = firstNames.stream()
                .min((a, b) ->
                        Double.compare(Math.abs(a.length() - avgLength),
                                Math.abs(b.length() - avgLength)))
                .orElseThrow(() -> new RuntimeException("Не найден подходящий клиент"));
        WebElement row = customerRows.stream()
                .filter(r -> r.findElement(By.xpath("td[1]")).getText().equals(toDelete))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Клиент не найден"));
        WebElement deleteBtn = row.findElement(By.xpath(".//button[text()='Delete']"));
        waiter.waitForElementToBeClickable(deleteBtn);
        deleteBtn.click();
        return toDelete;
    }
}
