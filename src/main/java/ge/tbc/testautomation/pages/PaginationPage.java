package ge.tbc.testautomation.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class PaginationPage {
    public SelenideElement dropDownToggle = $x("//span[text()='2-5 სტუმარი']/preceding::input[@id='radio-სტუმრების რაოდენობა-0']");
    String title = ".text-primary_black-100-value.text-md.leading-5";
    String price = ".//h4[contains(@class, 'text-primary_black-100-value text-2md leading-5 font-tbcx-bold')]";
    String description = ".//div[contains(@class, 'font-tbcx-regular') and contains(@class, 'line-clamp-1')]";
}
