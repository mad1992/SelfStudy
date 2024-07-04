package com.euka.pages;

import com.microsoft.playwright.Locator;

public class TermSelectionPage extends BasePage {
    private final String ddTerm = "//div[contains(@data-tracking-id,'Term')]//input";
    private final String btnNext = "//button[text()='Next']";

    public void selectTerm(String term) {
        page.locator(ddTerm).type(term);
        page.keyboard().press("Enter");
    }
    public void clickNext() {
        page.locator(btnNext).click();
    }
}
