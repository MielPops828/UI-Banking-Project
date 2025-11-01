package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
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
                .clickAddCustomer()
                .verifyAlertText("Customer added successfully with customer id")
                .acceptAlert();
    }
}
