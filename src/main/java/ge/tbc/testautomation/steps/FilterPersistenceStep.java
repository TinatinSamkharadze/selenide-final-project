package ge.tbc.testautomation.steps;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import ge.tbc.testautomation.pages.SubCategoryPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class FilterPersistenceStep {
    SubCategoryPage subCategoryPage = new SubCategoryPage();
    private static final Logger logger = LoggerFactory.getLogger(FilterPersistenceStep.class);


    public FilterPersistenceStep applyLocationFilter()
    {
        SelenideElement location =  $("#checkbox-მდებარეობა-0");
        scrollToElementWithOffset(location, 50);
        location.click();
        return this;
    }
    public FilterPersistenceStep applyPriceRange()
    {
        SelenideElement price = $x("//span[text()='ფასი']");
//       scrollToElementWithOffset(price, 400);
        SelenideElement priceRange = $(".flex.flex-col.gap-4.items-start.mb-5.w-full");
        priceRange.$(byText("200₾ - 300₾")).click();
        return this;
    }
    public void scrollToElementWithOffset(SelenideElement element, int offset) {
        Selenide.executeJavaScript(
                "window.scrollTo({ top: arguments[0].getBoundingClientRect().top + window.pageYOffset - arguments[1], behavior: 'smooth' });",
                element,
                offset
        );
    }

    public FilterPersistenceStep validateOffersPriceIsInRange() {
        ElementsCollection offerCards = subCategoryPage.offerCards;
        List<String> invalidPrices = offerCards.stream()
                .map(offer -> offer.$x(".//h4[contains(@class, 'text-primary_black-100-value text-2md leading-5 font-tbcx-bold')]").getText().trim())
                .filter(priceText -> !isPriceInRange(priceText, 200, 300))
                .collect(Collectors.toList());

        if (!invalidPrices.isEmpty()) {
            logger.error("Offers with prices outside the expected range (200–300 GEL): {}", invalidPrices);
            throw new AssertionError("Some offers have prices outside the 200–300 GEL range.");
        } else {
            logger.info("All offers have prices within the correct range (200–300 GEL).");
        }

        return this;
    }

    private boolean isPriceInRange(String text, int minPrice, int maxPrice) {
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            int price = Integer.parseInt(matcher.group(1));
            return price >= minPrice && price <= maxPrice;
        }
        return false; // No number found
    }


}
