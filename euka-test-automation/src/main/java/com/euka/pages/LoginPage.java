package com.euka.pages;

public class LoginPage extends BasePage {
    private final String txtUserName = "//input[@type='username']";
    private final String txtPassword = "//input[@type='password']";
    private final String btnLogin = "//button[text()='Log in']";

    public void login(String username, String password) {
        page.locator(txtUserName).type(username);
        page.locator(txtPassword).type(password);
        page.locator(btnLogin).click();
    }
}