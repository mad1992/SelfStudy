package com.euka.pages;

public class EnrolmentConfirmationPage extends BasePage {
    private final String txtStudentName = "//p[text()='Enrolled to']/following-sibling::p";
    private final String txtStartingCourse = "//p[text()='Starting course']/following-sibling::p";

    public String getStudentName() {
        return page.locator(txtStudentName).innerText();
    }

    public String getStartingCourse() {
        return page.locator(txtStartingCourse).innerText();
    }
}
