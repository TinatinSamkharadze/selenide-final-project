import com.codeborne.selenide.Selenide;
import ge.tbc.testautomation.data.enums.Category;
import ge.tbc.testautomation.data.enums.OfferCategory;
import ge.tbc.testautomation.data.enums.Subcategories;
import ge.tbc.testautomation.steps.FilterPersistenceStep;
import ge.tbc.testautomation.steps.OfferDetailsConsistencyStep;
import ge.tbc.testautomation.steps.PaginationStep;
import ge.tbc.testautomation.steps.SearchStep;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.data.Constants.INVALID_SEARCH_KEYWORD;
import static ge.tbc.testautomation.data.Constants.VALID_SEARCH_KEYWORD;

public class CoreFunctionalityTests extends BaseTest{
    SearchStep searchStep = new SearchStep();
    PaginationStep paginationStep = new PaginationStep();
    OfferDetailsConsistencyStep offerDetailsConsistencyStep = new OfferDetailsConsistencyStep();
    FilterPersistenceStep filterPersistenceStep = new FilterPersistenceStep();
    @Test
    public void searchTest()
    {
        searchStep.enterKeywordAndSearch(VALID_SEARCH_KEYWORD).validateSearchResultsContainAtLeastOneOffer().validateEachResultIncludesTheKeywordInTheTitleOrDescription().clearSearchInputField().enterKeywordAndSearch(INVALID_SEARCH_KEYWORD).validateNoResultsFoundMessageIsDisplayed();
        Selenide.sleep(4000);
    }

    @Test
    public void  paginationTest()
    {
        paginationStep
                .clickCategories()
                .hoverOverCategory(Category.HOLIDAY).selectSubcategory(Subcategories.MOUNTAIN_RESORTS).ensureOfferPageIsDisplayed(1).goToOffersPage(2).ensureOffersDifferBetweenPages(1, 2).validateOffersMatchCategory(OfferCategory.MOUNTAIN_RESORTS).goToOffersPage(3).ensureOffersDifferBetweenPages(2, 3).validateOffersMatchCategory(OfferCategory.MOUNTAIN_RESORTS).testPaginationForwardAndBack();

    }

    @Test
    public void numberOfGuestsTest()
    {
        paginationStep.clickCategoryByText(Category.EAT_AND_DRINKS).selectFourGuestAsTheFilterOption().validateOffersHaveGuestCapacity().validateOffersAreFilteredCorrectly();
    }

    @Test
    public void  offerDetailsConsistencyTest()
    {
         paginationStep.clickCategories().hoverOverCategory(Category.HOLIDAY).selectSubcategory(Subcategories.SEASIDE_HOTELS);
         offerDetailsConsistencyStep.selectLocationByText("ციხისძირი").captureOffersTitle()
                 .captureOffersPrice()
                 .captureOffersDescription().clickOnOffer().captureOffersTitleOnDetailsPage().captureOffersDescriptionOnDetailsPage().captureOffersPriceOnDetailsPage().captureOffersLocationOnDetailsPage();
        Selenide.sleep(3000);
    }

    @Test
    public void  filterPersistenceTest()
    {
        paginationStep.clickCategories().hoverOverCategory(Category.HOLIDAY).selectSubcategory(Subcategories.KAKHETI);
        filterPersistenceStep.applyLocationFilter().applyPriceRange().validateOffersPriceIsInRange();
        Selenide.sleep(3000);
    }
}
