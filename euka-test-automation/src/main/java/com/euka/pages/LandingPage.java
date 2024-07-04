package com.euka.pages;

public class LandingPage extends BasePage {
    private final String btnManageProgramsCard = "//p[text()='Manage Programs']";
    private final String lblWelcomeText = "//h2[text()='Welcome']";


    public void clickManagePrograms() {
        page.locator(btnManageProgramsCard).click();
    }

    public boolean verifyWelcomeText(){
        page.waitForSelector(lblWelcomeText);
        return page.locator(lblWelcomeText).isVisible();
    }

}
