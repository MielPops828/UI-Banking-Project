package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AddCustomerPage;

@Epic("Автоматизация страницы XYZ Bank")
@Feature("Добавление клиента")
public class CreateCustomerTest extends BaseTest{
    @Test
    @Description("Тест добавления нового клиента")
    public void testAddCustomer(){
        AddCustomerPage page = new AddCustomerPage(getDriver(), getWaiter());
        page.openPage()
                .openAddCustomer()
                .setPostCode()
                .setFirstName()
                .setLastName("Abcdef")
                .clickAddCustomer();
        String actualText = page.verifyAlertText();
        Assert.assertTrue(actualText.contains("Customer added successfully with customer id"),
                "Текст алерта не соответствует ожидаемому. Получено: " + actualText);
        page.acceptAlert();
    }
}
