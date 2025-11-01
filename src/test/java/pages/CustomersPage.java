package pages;

import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utilities.ParameterProvider;
import utilities.WaitHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomersPage {
    private final WebDriver driver;
    private final WaitHelper waiter;

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

    public CustomersPage openPage(){
        Allure.step("Открыть страницу XYZ Bank", () -> driver.get(ParameterProvider.get("base.url")));
        return this;
    }

    public CustomersPage openCustomersTab(){
        Allure.step("Открыть вкладку Customers", () -> {
            waiter.waitForElementToBeClickable(customerMenuButton);
            customerMenuButton.click();
            waiter.waitForElementVisible(firstNameRow);
        });
        return this;
    }

    public CustomersPage sortByFirstName(){
        Allure.step("Сортировать таблицу по First Name", () -> {
            waiter.waitForElementVisible(customerRows.get(0));
            firstNameRow.click();
            waiter.waitForElementVisible(customerRows.get(0));
            firstNameRow.click();
            waiter.waitForElementVisible(customerRows.get(0));
        });
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

    public CustomersPage deleteCustomerWithNameCloseToAverageLength(){
        List<String> firstNames = getFirstNames();

        double avgLength = firstNames.stream()
                .mapToInt(String::length)
                .average()
                .orElse(0);

        Optional<String> toDelete = firstNames.stream()
                .min((a, b) ->
                        Double.compare(Math.abs(a.length() - avgLength), Math.abs(b.length() - avgLength))
                );

        toDelete.ifPresent(name -> {
            Allure.step("Удалить клиента с именем: " + name, () -> {
                WebElement row = customerRows.stream()
                        .filter(r -> r.findElement(By.xpath("td[1]")).getText().equals(name))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Клиент не найден"));
                WebElement deleteBtn = row.findElement(By.xpath(".//button[text()='Delete']"));
                waiter.waitForElementToBeClickable(deleteBtn);
                deleteBtn.click();
            });
        });
        return this;
    }

    public CustomersPage verifySortedAscending() {
        Allure.step("Проверить выполнение сортировки", () -> {
            waiter.waitForElementVisible(firstNameCells.get(0));

            List<String> names = new ArrayList<>();
            for (WebElement cell : firstNameCells){
                names.add(cell.getText().trim());
            }

            for (int i = 0; i < names.size() - 1; i++) {
                String current = names.get(i);
                String next = names.get(i + 1);
                Assert.assertTrue(current.compareTo(next) <= 0,
                        "Список не отсортирован: " + current + " > " + next);
            }
        });
        return this;
    }
}
