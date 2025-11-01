package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import pages.CustomersPage;

@Epic("Автоматизация страницы XYZ Bank")
@Feature("Удаление клиент")
public class DeleteCustomerTest extends BaseTest{
    @Test
    @Description("Тест удаления клиента через среднее арифметическое")
    public void testDeleteCustomer(){
        CustomersPage page = new CustomersPage(getDriver(), getWaiter());
        page.openPage().openCustomersTab().deleteCustomerWithNameCloseToAverageLength();
    }
}
