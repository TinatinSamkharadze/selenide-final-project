package ge.tbc.testautomation.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class SearchPage {
    public ElementsCollection searchResults = $$x("//div[@class='grid laptop:grid-cols-3 grid-flow-row gap-x-4 gap-y-8 grid-cols-2']//div[@class='relative']");
    public SelenideElement noResultsFoundMessage = $("h2"),
    searchOffers = $(".gap-x-4.gap-y-8.grid-cols-2");


}
