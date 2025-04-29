package ge.tbc.testautomation.steps;

import com.codeborne.selenide.SelenideElement;
import ge.tbc.testautomation.data.models.OfferModel;
import ge.tbc.testautomation.pages.PaginationPage;
import ge.tbc.testautomation.pages.SearchPage;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class OfferDetailsConsistencyStep {
     PaginationPage paginationPage = new PaginationPage();
    SearchPage searchPage = new SearchPage();
    private List<OfferModel> offers = new ArrayList<>();

    public OfferDetailsConsistencyStep captureOffersTitle() {
        ensurePartialListInitialized();
        for (int i = 0; i < searchPage.searchResults.size(); i++) {
            String title = searchPage.searchResults.get(i)
                    .$(".text-primary_black-100-value.text-md.leading-5").text();
            offers.get(i).setTitle(title);
            System.out.println(title);
        }

        return this;
    }

    public OfferDetailsConsistencyStep captureOffersPrice() {
        ensurePartialListInitialized();
        for (int i = 0; i < searchPage.searchResults.size(); i++) {
            String price = searchPage.searchResults.get(i)
                    .$x(".//h4[contains(@class, 'font-tbcx-bold')]").text();
            offers.get(i).setPrice(price);
            System.out.println(price);
        }
        return this;
    }

    public OfferDetailsConsistencyStep captureOffersDescription() {
        ensurePartialListInitialized();
        for (int i = 0; i < searchPage.searchResults.size(); i++) {
            String description = searchPage.searchResults.get(i)
                    .$x(".//div[contains(@class, 'font-tbcx-regular') and contains(@class, 'line-clamp-1')]").text();
            offers.get(i).setDescription(description);
            System.out.println(description);
        }
        return this;
    }

    private void ensurePartialListInitialized() {
        if (offers.isEmpty()) {
            for (int i = 0; i < searchPage.searchResults.size(); i++) {
                offers.add(new OfferModel());
            }
        }


    }

    public OfferDetailsConsistencyStep selectLocationByText(String locationText) {
        // Make sure the panel is visible first (e.g. click to open it if needed)
        $x("//input[@class='cursor-pointer rounded text-primary_green-100-value focus:ring-transparent border-secondary_gray-40-value w-5 h-5']/following-sibling::span[normalize-space(text())='" + locationText + "']").click();
        return this;
    }

    public List<OfferModel> buildOffers() {
        return offers.stream()
                .map(p -> new OfferModel(p.getTitle(), p.getPrice(), p.getDescription()))
                .toList();
    }

   public OfferDetailsConsistencyStep clickOnOffer()
   {
       SelenideElement offerCard = $(".relative.bg-secondary_gray-20-value");
       offerCard.click();
       return this;
   }
    public OfferDetailsConsistencyStep captureOffersTitleOnDetailsPage()
    {
        SelenideElement title = $x("//h2[@class='text-primary_black-100-value font-tbcx-medium text-2md laptop:text-2lg']");
        System.out.println(title.getText());
        return this;
    }

    public OfferDetailsConsistencyStep captureOffersDescriptionOnDetailsPage()
    {
        SelenideElement description = $(".text-md.leading-5.font-tbcx-medium.text-secondary_purple-100-value ");
        System.out.println(description.getText());
        return this;
    }

    public OfferDetailsConsistencyStep captureOffersPriceOnDetailsPage()
    {
        SelenideElement price = $(".text-primary_black-100-value.text-md.leading-5.font-tbcx-bold");
        System.out.println(price.getText());
        return this;
    }

    public OfferDetailsConsistencyStep captureOffersLocationOnDetailsPage()
    {
        SelenideElement location = $x("//p[@class='text-md leading-5 font-tbcx-regular text-[#1C1C22]']");
        System.out.println(location.getText());
        return this;
    }
}
