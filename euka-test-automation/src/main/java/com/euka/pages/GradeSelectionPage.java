package com.euka.pages;

public class GradeSelectionPage extends BasePage {
    private final String ddlGradeDropdown = "//p[contains(text(),'Euka Primary')]/following-sibling::div/select";
    private final String btnNext = "//button[text()='Next']";

    public void selectGrade(String grade) {
        page.locator(ddlGradeDropdown).selectOption(grade);
    }
    public void clickNext() {
        page.locator(btnNext).click();
    }
}
