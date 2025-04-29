import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import ge.tbc.testautomation.data.enums.Category;
import ge.tbc.testautomation.data.enums.Subcategories;
import ge.tbc.testautomation.steps.PaginationStep;
import ge.tbc.testautomation.steps.ResponsivenessStep;
import org.openqa.selenium.Dimension;
import org.testng.annotations.Test;

public class UIResponsivenessTests extends BaseTest{

    ResponsivenessStep responsivenessStep = new ResponsivenessStep();
    PaginationStep paginationStep = new PaginationStep();
    @Test
    public void mobileResponsiveTest()
    {
        responsivenessStep.validateSearchBarIsVisible().validateNavigationBarIsFullyExpanded();
        paginationStep.clickCategories().hoverOverCategory(Category.EAT_AND_DRINKS).selectSubcategory(Subcategories.RESTAURANT);
        Selenide.sleep(3000);
         responsivenessStep.offerCardsDisplayInGridLayout();

         Selenide.sleep(3000);
        WebDriverRunner.getWebDriver().manage().window().setSize(new Dimension(768, 1024));
        responsivenessStep.validateSearchBarIsNotVisible().validateNavigationBarIsFullyExpanded();

//        responsivenessStep.offerCardsDisplayInGridLayout();

        WebDriverRunner.getWebDriver().manage().window().setSize(new Dimension(375, 667));
       responsivenessStep.validateSearchIconIsVisible().clickHamburgerMenu().validateSearchBarIsEnabled();
       Selenide.sleep(3000);

    }
}
