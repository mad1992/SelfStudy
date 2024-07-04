package com.euka;

import com.microsoft.playwright.*;

import java.awt.*;

public class Login {
    public static void main(String[] args) {
        String studentFirstName = "Kaveesh";
        String studentLastName = "Gamage";

        String grade = "Grade 2";
        String term = "Term 1";

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        BrowserContext context = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));
        Page page = browser.newPage();
        page.navigate("https://portal.euka.edu.au/login");
        context.clearCookies();
        Locator txtEmail = page.locator("//input[@type='username']");
        txtEmail.type("anjula.w+eukatest_candidate3@euka.edu.au");
        Locator txtPassword = page.locator("//input[@type='password']");
        txtPassword.type("EukaAuto@123");
        Locator lblLogin = page.locator("//button[text()='Log in']");
        lblLogin.click();
        Locator lblManagePrograms = page.locator("//p[text()='Manage Programs']");
        lblManagePrograms.click();
        Locator lblCompleteEnrolment = page.locator("(//p[text()='Complete enrolment'])[1]");
        lblCompleteEnrolment.click();
        Locator txtFirstName = page.locator("//input[@name='firstName']");
        txtFirstName.type(studentFirstName);
        Locator txtLastName = page.locator("//input[@name='lastName']");
        txtLastName.type(studentLastName);
        Locator btnNext = page.locator("//button[text()='Next']");
        btnNext.click();

        Locator ddGrade = page.locator("//p[contains(text(),'Euka Primary')]/following-sibling::div/select");
        ddGrade.selectOption(grade);
        btnNext.click();


        Locator ddTerm = page.locator("//div[contains(@data-tracking-id,'Term')]//input");
        ddTerm.type(term);
        page.keyboard().press("Enter");
        btnNext.click();

        btnNext.click();

        Locator lblName = page.locator("//p[text()='Enrolled to']/following-sibling::p");
        Locator lblStartingCourse = page.locator("//p[text()='Starting course']/following-sibling::p");

        String text = lblStartingCourse.innerText();

        String[] values = text.split(",\\s*");

        assert lblName.equals(txtFirstName+" "+txtLastName) : "First Name Incorrect";
        assert values[0].equals(grade) : "Grade Incorrect";
        assert values[1].equals(term) : "Term Incorrect";


        page.close();
        browser.close();
        playwright.close();

    }
}
