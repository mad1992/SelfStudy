package com.euka.pages;

public class NameSelectionPage extends BasePage {
    private final String txtFirstName = "//input[@name='firstName']";
    private final String txtLastName = "//input[@name='lastName']";
    private final String btnNext = "//button[text()='Next']";

    public void enterName(String firstName, String lastName) {
        page.locator(txtFirstName).type(firstName);
        page.locator(txtLastName).type(lastName);
    }

    public void clickNext() {
        page.locator(btnNext).click();
    }
}
