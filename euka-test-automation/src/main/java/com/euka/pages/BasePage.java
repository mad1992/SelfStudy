package com.euka.pages;

import com.microsoft.playwright.*;

import java.awt.*;
import java.nio.file.Paths;
import java.util.Base64;

public class BasePage {
    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext context;
    protected static Page page;

    public static void initializeBrowser(String browserName, Boolean headless, Boolean isRunRemotely) {
        playwright = Playwright.create();
        switch (browserName.toLowerCase()) {
            case "chrome":
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(headless));
                break;
            case "safari":
                browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(headless));
                break;
            case "chromium":
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless));
                break;
            case "firefox":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless));
                break;
            default:
                System.out.println("<<<<<   please add a correct browser name  >>>>>>");
                break;
        }
        if(isRunRemotely){
            System.setProperty("java.awt.headless", "true");
        }
    }

    public static void createContext(Boolean isRunRemotely) {
        if(!isRunRemotely){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));
        }
        else {
            context = browser.newContext();
        }
        page = context.newPage();
    }

    public static void navigateToUrl(String url) {
        page.navigate(url);
    }

    public static String takeScreenshot(String name) {
        String path = System.getProperty("user.dir") + "/screenshot/" + name + "_" + System.currentTimeMillis() + ".png";
        byte[] buffer = page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
        String base64Path = Base64.getEncoder().encodeToString(buffer);

        return base64Path;
    }

    public static void closeBrowser() {
        page.close();
        browser.close();
        playwright.close();
    }
}
