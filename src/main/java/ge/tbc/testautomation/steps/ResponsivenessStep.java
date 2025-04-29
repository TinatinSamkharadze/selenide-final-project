package ge.tbc.testautomation.steps;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ge.tbc.testautomation.pages.BasePage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class ResponsivenessStep {
    BasePage basePage = new BasePage();
    public ResponsivenessStep validateSearchBarIsVisible()
    {
        basePage.searchInputField.shouldBe(visible);
        return this;
    }

    public ResponsivenessStep validateNavigationBarIsFullyExpanded() {
        ElementsCollection allCategories = basePage.allCategories;
        allCategories.shouldHave(CollectionCondition.size(10));
        return this;
    }

    public ResponsivenessStep offerCardsDisplayInGridLayout() {
        ElementsCollection offerCards = basePage.offerCards;

        // Ensure all offer cards are visible
        for (SelenideElement offerCard : offerCards) {
            offerCard.scrollIntoView(true).shouldBe(Condition.visible);
        }

        // Group cards by Y-coordinate (approximated row)
        Map<Integer, List<SelenideElement>> rows = new TreeMap<>();
        for (SelenideElement card : offerCards) {
            int y = card.getLocation().getY();
            int key = y / 10; // rounding to group approximately same rows
            rows.computeIfAbsent(key, k -> new ArrayList<>()).add(card);
        }

        // Assert each row has exactly 3 cards
        for (Map.Entry<Integer, List<SelenideElement>> entry : rows.entrySet()) {
            int rowNumber = entry.getKey();
            int cardCount = entry.getValue().size();
            if (cardCount != 3) {
                throw new AssertionError("Row " + rowNumber + " has " + cardCount + " cards (expected: 3)");
            }
        }

        return this;
    }


    public ResponsivenessStep validateSearchBarIsNotVisible()
    {
        basePage.searchInputField.shouldNotBe(visible);
        return this;
    }

    public ResponsivenessStep validateNavigationBarIsNotFullyExpanded() {
        ElementsCollection allCategories = basePage.allCategories;
        int actualSize = allCategories.size(); // triggers lazy evaluation
        if (actualSize >= 10) {
            throw new AssertionError("Expected fewer than 10 categories, but found: " + actualSize);
        }
        return this;
    }

    public ResponsivenessStep validateSearchIconIsVisible()
    {
        SelenideElement searchIcon = $x("//div[@class='flex  justify-start items-start p-2.5']");
        searchIcon.shouldBe(visible);
        return this;
    }

    public ResponsivenessStep clickHamburgerMenu()
    {
        SelenideElement hamburgerMenu = $x("//img[@src='/icons/search-light.svg']");
        hamburgerMenu.click();
        return this;
    }

    public ResponsivenessStep validateSearchBarIsEnabled()
    {
        basePage.searchInputField.shouldBe(enabled);
        return this;
    }
}
