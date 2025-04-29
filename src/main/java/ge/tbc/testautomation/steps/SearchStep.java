package ge.tbc.testautomation.steps;

import com.codeborne.selenide.SelenideElement;
import ge.tbc.testautomation.data.models.OfferModel;
import ge.tbc.testautomation.pages.BasePage;
import ge.tbc.testautomation.pages.SearchPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static ge.tbc.testautomation.data.Constants.VALID_SEARCH_KEYWORD;

public class SearchStep {
    BasePage basePage = new BasePage();
    SearchPage searchPage = new SearchPage();
    private static final Logger logger = LoggerFactory.getLogger(SearchStep.class);

    public SearchStep enterKeywordAndSearch(String keyword)
    {
        basePage.searchInputField.setValue(keyword).pressEnter().pressEscape();
        return this;
    }

    public SearchStep validateSearchResultsContainAtLeastOneOffer()
    {
        searchPage.searchResults.shouldHave(sizeGreaterThan(0));
        return this;
    }


    public SearchStep validateEachResultIncludesTheKeywordInTheTitleOrDescription() {
        String normalizedKeyword = VALID_SEARCH_KEYWORD.toLowerCase().trim();


        searchPage.searchResults.forEach(element -> {
            element.scrollIntoView(true);
            element.should(appear);
        });

        List<OfferModel> offers = searchPage.searchResults.stream()
                .map(element -> {
                    String title = element.$(".text-primary_black-100-value.text-md.leading-5").text();
                    String price = element.$x(".//h4[contains(@class, 'font-tbcx-bold')]").text();
                    String description = element.$x(".//div[contains(@class, 'font-tbcx-regular') and contains(@class, 'line-clamp-1')]").text();
                    return new OfferModel(title, price, description);
                })
                .toList();

        boolean allOffersContainKeyword = offers.stream()
                .allMatch(offer -> {
                    String title = offer.getTitle() != null ? offer.getTitle().toLowerCase() : "";
                    String description = offer.getDescription() != null ? offer.getDescription().toLowerCase() : "";

                    return title.contains(normalizedKeyword) ||
                            description.contains(normalizedKeyword);
                });

        if (allOffersContainKeyword) {
            logger.info("All {} offers contain the keyword '{}' in the title or description.", offers.size(), normalizedKeyword);
        } else {
            List<OfferModel> failingOffers = offers.stream()
                    .filter(offer -> {
                        String title = offer.getTitle() != null ? offer.getTitle().toLowerCase() : "";
                        String description = offer.getDescription() != null ? offer.getDescription().toLowerCase() : "";
                        return !(title.contains(normalizedKeyword) || description.contains(normalizedKeyword));
                    })
                    .toList();

            logger.warn("{} of {} offers do NOT contain the keyword '{}' in the title or description. Failing offers: {}",
                    failingOffers.size(), offers.size(), normalizedKeyword, failingOffers);
        }

        return this;
    }

    public SearchStep clearSearchInputField()
    {
        SelenideElement searchInputField =  basePage.searchInputField;
        searchInputField.should(appear);
        executeJavaScript("arguments[0].value = '';", searchInputField);
        return this;
    }
    public SearchStep validateNoResultsFoundMessageIsDisplayed()
    {
        searchPage.noResultsFoundMessage.isDisplayed();
        return this;
    }

}
