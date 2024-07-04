package com.euka.pages;

import com.microsoft.playwright.ElementHandle;

public class ManageProgramsPage extends BasePage {
    private final String btnCompleteEnrolment = "(//p[text()='Complete enrolment'])[1]";
    private final String btnClose = "button[aria-label='Close']";
    private final String wdgtHost = "us-widget";


    public void clickCompleteEnrolment() {
        page.locator(btnCompleteEnrolment).click();
    }

    public void closePopup() {
        ElementHandle shadowHost = page.querySelector(wdgtHost);
        if(shadowHost!=null){
            ElementHandle shadowRoot = shadowHost.evaluateHandle("element => element.shadowRoot").asElement();

            ElementHandle buttonHandle = shadowRoot.evaluateHandle("element => element.querySelector(\""+btnClose+"\")").asElement();

            if (buttonHandle != null) {
                buttonHandle.click();
            }
            System.out.println("<<< Rating popup Closed >>>");
        }else{
            System.out.println("<<< Rating popup is not visible >>>");
        }
        }

}
