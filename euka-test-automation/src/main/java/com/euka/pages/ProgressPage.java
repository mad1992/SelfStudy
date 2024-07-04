package com.euka.pages;

public class ProgressPage extends BasePage{
    private final String btnNext = "//button[text()='Next']";

    public void clickNext() {
        page.locator(btnNext).click();
    }
}
