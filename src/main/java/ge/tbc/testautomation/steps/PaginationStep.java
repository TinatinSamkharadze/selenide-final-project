package ge.tbc.testautomation.steps;

import com.codeborne.selenide.*;
import ge.tbc.testautomation.data.enums.Category;
import ge.tbc.testautomation.data.enums.OfferCategory;
import ge.tbc.testautomation.data.enums.Subcategories;
import ge.tbc.testautomation.data.models.OfferModel;
import ge.tbc.testautomation.pages.BasePage;
import ge.tbc.testautomation.pages.PaginationPage;
import ge.tbc.testautomation.pages.SubCategoryPage;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.*;

public class PaginationStep {
    BasePage basePage = new BasePage();
    SubCategoryPage subCategoryPage = new SubCategoryPage();
    private Category currentCategory;
    PaginationPage paginationPage = new PaginationPage();


    public PaginationStep clickCategories() {
        basePage.categoriesNavLink.click();
        return this;
    }

    public PaginationStep hoverOverCategory(Category category) {
        String xpath = String.format("//h4[normalize-space(text())='%s']", category.getDisplayName());
        SelenideElement categoryElement = $x(xpath);
        categoryElement.hover();
        this.currentCategory = category;
        return this;
    }

    public PaginationStep selectSubcategory(Subcategories subcategory) {
        if (currentCategory == null) {
            throw new IllegalStateException("No category has been hovered over yet");
        }

        if (!currentCategory.containsSubcategory(subcategory)) {
            throw new IllegalArgumentException(
                    "Subcategory " + subcategory.getDisplayName() +
                            " doesn't belong to category " + currentCategory.getDisplayName()
            );
        }

        String xpath = String.format("//h4[text()='%s']", subcategory.getDisplayName());
        SelenideElement subcategoryElement = $x(xpath);
        subcategoryElement.click();
        return this;
    }

    public PaginationStep ensureOfferPageIsDisplayed(int expectedPageNumber) {
        SelenideElement activePage = $$(".flex.justify-center.items-center.w-9.h-9.rounded-lg.cursor-pointer.text-md")
                .filterBy(Condition.cssClass("bg-primary_green-110-value"))
                .findBy(Condition.text(String.valueOf(expectedPageNumber)));

        activePage.shouldHave(Condition.cssClass("text-white"));

        $$(".group.flex.flex-col.gap-3.cursor-pointer")
                .shouldBe(CollectionCondition.sizeGreaterThan(0));
        return this;
    }

    public PaginationStep goToOffersPage(int pageNumber) {
        ElementsCollection pageNumbers = $$(".flex.justify-center.items-center.w-9.h-9.rounded-lg.cursor-pointer.text-md");

        // Find the element for the given page number
        SelenideElement targetPage = pageNumbers.findBy(Condition.text(String.valueOf(pageNumber)));

        // Scroll to it (optional offset scroll, or just ensure visible)
//
        targetPage.scrollTo();

        // Click it
        targetPage.click();

        return this;
    }


    public PaginationStep ensureOffersDifferBetweenPages(int page1, int page2) {
        // Go to first page and wait for offers to load
        goToOffersPage(page1);
        Selenide.sleep(3000);

        ElementsCollection offersPage1Elements = $$(".group.flex.flex-col.gap-3.cursor-pointer")
                .shouldBe(CollectionCondition.sizeGreaterThan(0));
        List<String> offersPage1 = offersPage1Elements.stream()
                .map(SelenideElement::getText)
                .collect(Collectors.toList());


        // Go to second page and wait for offers to load
        goToOffersPage(page2);
        Selenide.sleep(3000);

        ElementsCollection offersPage2Elements = $$(".group.flex.flex-col.gap-3.cursor-pointer")
                .shouldBe(CollectionCondition.sizeGreaterThan(0));
        List<String> offersPage2 = offersPage2Elements.stream()
                .map(SelenideElement::getText)
                .collect(Collectors.toList());

        // Compare offers
        if (offersPage1.equals(offersPage2)) {
            throw new AssertionError("Offers on page " + page1 + " and page " + page2 + " are unexpectedly identical.");
        }

        return this;
    }

    public PaginationStep validateOffersMatchCategory(OfferCategory category) {
        List<String> keywords = category.getKeywords();

        // Convert keywords to lowercase once
        List<String> lowercaseKeywords = keywords.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        // Collect offers from the page
        ElementsCollection cards = $$(".group.flex.flex-col.gap-3.cursor-pointer");
        List<OfferModel> offers = new ArrayList<>();

        for (SelenideElement card : cards) {
            String title = card.$(".text-primary_black-100-value.text-md.leading-5").text();
            String price = card.$x(".//h4[contains(@class, 'text-primary_black-100-value text-2md leading-5 font-tbcx-bold')]").text();
            String description = card.$x(".//div[contains(@class, 'font-tbcx-regular') and contains(@class, 'line-clamp-1')]").text();

            offers.add(new OfferModel(title, price, description));
        }

        // Validate each offer
        for (OfferModel offer : offers) {
            String combinedText = (offer.getTitle() + " " + offer.getDescription()).toLowerCase();
            boolean matches = lowercaseKeywords.stream()
                    .anyMatch(keyword -> combinedText.contains(keyword.toLowerCase()));

            if (!matches) {
                throw new AssertionError("Offer does not match category: " + offer +
                        "\nExpected keywords: " + keywords +
                        "\nLowercase keywords: " + lowercaseKeywords);
            }
        }

        System.out.println("✔ All offers match the selected category: " + category.name());
        return this;
    }

    public void scrollToElementWithOffset(SelenideElement element, int offset) {
        Selenide.executeJavaScript(
                "window.scrollTo({ top: arguments[0].getBoundingClientRect().top + window.pageYOffset - arguments[1], behavior: 'smooth' });",
                element,
                offset
        );
    }

    private static final Logger logger = LoggerFactory.getLogger(PaginationStep.class);

    public PaginationStep testPaginationForwardAndBack() {
        // Start from first page
        int currentPage = 1;
        validateActivePageHighlight(currentPage);

        // First, go forward through all pages
        while (canGoToNextPage()) {
            logger.info("➡ Moving forward to page: " + currentPage);
            validatePageContent();
            goToNextPage();
            currentPage++;
            validateActivePageHighlight(currentPage);
        }

        logger.info("✅ Reached last page: " + currentPage);

        // Then go back to first page
        while (canGoToPreviousPage()) {
            logger.info("⬅ Moving back to page: " + (currentPage - 1));
            goToPreviousPage();
            currentPage--;
            validatePageContent();
            validateActivePageHighlight(currentPage);
        }

        logger.info("✔ Successfully returned to first page");
        return this;
    }

    private void validatePageContent() {
        validateOffersLoaded();
        // Add any additional page validation here
    }

    private boolean canGoToNextPage() {
        try {
            SelenideElement nextButton = $x("//img[@alt='right arrow']")
                    .shouldBe(Condition.visible, Duration.ofSeconds(2));
            return nextButton.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    private boolean canGoToPreviousPage() {
        try {
            SelenideElement prevButton = $x("//img[@alt='left arrow']")
                    .shouldBe(Condition.visible, Duration.ofSeconds(2));
            return prevButton.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    private void goToNextPage() {
        $x("//img[@alt='right arrow']")
                .shouldBe(Condition.interactable, Duration.ofSeconds(5))
                .click();
        waitForPageLoad();
    }

    private void goToPreviousPage() {
        $x("//img[@alt='left arrow']")
                .shouldBe(Condition.interactable, Duration.ofSeconds(5))
                .click();
        waitForPageLoad();
    }

    private void waitForPageLoad() {
        // Wait for either loading spinner to disappear or content to appear
        try {
            $(".loading-spinner").should(Condition.disappear, Duration.ofSeconds(5));
        } catch (Exception e) {
            // If no spinner exists, wait for offers to load
            validateOffersLoaded();
        }
    }

    private void validateActivePageHighlight(int expectedPageNumber) {
        // More robust active page validation
        SelenideElement activePage = $x("//div[contains(@class, 'bg-primary_green-110-value') and contains(@class, 'text-white')]")
                .shouldBe(Condition.visible, Duration.ofSeconds(5));

        activePage.shouldHave(Condition.cssClass("text-white"));
        activePage.shouldHave(Condition.cssClass("bg-primary_green-110-value"));

        // Optional: Verify page number if displayed
        try {
            String pageText = activePage.getText();
            if (pageText.matches("\\d+")) {
                int actualPageNumber = Integer.parseInt(pageText);
                if (actualPageNumber != expectedPageNumber) {
                    throw new AssertionError("Expected active page to be " + expectedPageNumber + " but found " + actualPageNumber);
                }
            }
        } catch (Exception e) {
            // Skip if page number isn't visible or parseable
        }
    }

    private void validateOffersLoaded() {
        $$(".group.flex.flex-col.gap-3.cursor-pointer")
                .shouldBe(CollectionCondition.sizeGreaterThan(0), Duration.ofSeconds(10));
    }

    public PaginationStep clickCategoryByText(Category category) {
        String xpath = String.format("//p[text()='%s']", category.getDisplayName());
        WebElement categoryElement = $x(xpath);
        categoryElement.click();
        return this;
    }

    public PaginationStep selectFourGuestAsTheFilterOption()
    {
        SelenideElement dropDownToggle = paginationPage.dropDownToggle;
        scrollToElementWithOffset(dropDownToggle, 100);
        dropDownToggle.click();
        Selenide.sleep(4000);
        return this;
    }

    public PaginationStep validateOffersHaveGuestCapacity() {
        ElementsCollection offerCards = subCategoryPage.offerCards;
        List<String> offersMissingCapacity = offerCards.stream()
                .map(offer -> offer.$("h4").getText().trim())
                .filter(title -> !hasGuestCapacityInfo(title))
                .collect(Collectors.toList());

        if (!offersMissingCapacity.isEmpty()) {
            logger.error("Offers missing guest capacity info: {}", offersMissingCapacity);
            throw new AssertionError("Some offers are missing guest capacity information.");
        } else {
            logger.info("All offers correctly display guest capacity information.");
        }

        return this;
    }

    private boolean hasGuestCapacityInfo(String text) {
        String regex = "\\d+(-\\d+)?\\s*(ადამიან(ზე|ისთვის)?|სტუმარ(ზე|ისთვის)?|კაც(ზე|ისთვის)?)";
        return Pattern.compile(regex).matcher(text).find();
    }

    public PaginationStep validateOffersAreFilteredCorrectly()
    {
        ElementsCollection offerCards = subCategoryPage.offerCards;
        List<String> invalidOffers = offerCards.stream()
                .map(offer -> offer.$("h4").getText().trim())
                .filter(title -> !isGuestCapacityInRange(title, 2, 6))
                .collect(Collectors.toList());

        if (!invalidOffers.isEmpty()) {
            logger.error("Offers with invalid guest capacity (expected 2-5): {}", invalidOffers);
            throw new AssertionError("Some offers have guest capacity outside the 2-5 range.");
        } else {
            logger.info("All offers have correct guest capacity between 2-5 guests.");
        }
        return this;
    }
    private boolean isGuestCapacityInRange(String text, int minGuests, int maxGuests) {
        Pattern pattern = Pattern.compile("(\\d+)(?:-(\\d+))?\\s*(ადამიან|სტუმარ|კაც)");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            int firstNumber = Integer.parseInt(matcher.group(1));
            int secondNumber = matcher.group(2) != null ? Integer.parseInt(matcher.group(2)) : firstNumber;

            return firstNumber >= minGuests && secondNumber <= maxGuests;
        }
        return false; // ვერ მოიძებნა რიცხვი საერთოდ
    }
}
