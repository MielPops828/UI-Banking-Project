package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import pages.CustomersPage;

@Epic("Автоматизация страницы XYZ Bank")
@Feature("Сортировка таблицы клиентов")
public class SortCustomersTest extends BaseTest{
    @Test
    @Description("Тест сортировки таблицы клиентов по имени First Name")
    public void testSortCustomers(){
        CustomersPage page = new CustomersPage(getDriver(), getWaiter());
        page.openPage()
                .openCustomersTab()
                .sortByFirstName()
                .verifySortedAscending();
    }
}
