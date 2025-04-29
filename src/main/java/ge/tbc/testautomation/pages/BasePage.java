package ge.tbc.testautomation.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BasePage {
    public SelenideElement searchInputField = $(".font-tbcx-regular.outline-none.border-black"),
            categoriesNavLink = $(".items-center.w-max");
    public ElementsCollection allCategories
            = $$(".group.transition-all.flex.flex-col.items-center");
    public ElementsCollection offerCards
            = $$(".group.flex.flex-col.gap-3.cursor-pointer");


}
