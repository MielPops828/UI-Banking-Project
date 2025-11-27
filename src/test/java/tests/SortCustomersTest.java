package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CustomersPage;

import java.util.List;

@Epic("Автоматизация страницы XYZ Bank")
@Feature("Сортировка таблицы клиентов")
public class SortCustomersTest extends BaseTest{
    @Test
    @Description("Тест сортировки таблицы клиентов по имени First Name")
    public void testSortCustomers(){
        CustomersPage page = new CustomersPage(getDriver(), getWaiter());
        List<String> names = page
                .openPage()
                .openCustomersTab()
                .sortByFirstName()
                .getFirstNames();
        Assert.assertTrue(isSortedAscending(names), "Список не отсортирован");
    }
    private boolean isSortedAscending(List<String> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).compareTo(list.get(i + 1)) > 0){
                return false;
            };
        }
        return true;
    }
}
