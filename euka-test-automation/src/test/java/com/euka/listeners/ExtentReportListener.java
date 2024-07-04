package com.euka.listeners;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import static com.euka.pages.BasePage.takeScreenshot;

public class ExtentReportListener implements ITestListener {

    private static final String OUTPUT_FOLDER = "./reports/";
    private static final String FILE_NAME = "ExtentReport.html";

    private static ExtentReports extent = init();
    public static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
    private static ExtentReports extentReports;


    private static ExtentReports init() {

        Path path = Paths.get(OUTPUT_FOLDER);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        extentReports = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter(OUTPUT_FOLDER + FILE_NAME);
        reporter.config().setReportName("EUKA Automation Test Results");
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("System", "MAC");
        extentReports.setSystemInfo("Author", "Kaveesh Gamage");


        return extentReports;
    }

    @Override
    public synchronized void onStart(ITestContext context) {
        System.out.println("<<<<<<   Test Suite started!  >>>>>>>>>>>");

    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        System.out.println(("<<<<<<   Test Suite End!  >>>>>>>>>>>"));
        System.out.println(("<<<<<<   Extent Report Location :- "+OUTPUT_FOLDER+FILE_NAME+"  >>>>>>>>>>>"));
        extent.flush();
        test.remove();
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String qualifiedName = result.getMethod().getQualifiedName();
        int last = qualifiedName.lastIndexOf(".");
        int mid = qualifiedName.substring(0, last).lastIndexOf(".");
        String className = qualifiedName.substring(mid + 1, last);

        System.out.println("<<<<<<   "+methodName+" Started!!  >>>>>>>>>>>");
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),
                result.getMethod().getDescription());

        extentTest.assignCategory(result.getTestContext().getSuite().getName());
        extentTest.assignCategory(className);
        test.set(extentTest);
        test.get().getModel().setStartTime(getTime(result.getStartMillis()));
    }
    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        System.out.println("<<<<<<   "+methodName+" Passed!!  >>>>>>>>>>>");
        test.get().pass("Test passed");
        test.get().pass(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromBase64String(takeScreenshot(result.getMethod().getMethodName()),result.getMethod().getMethodName()).build());
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }
    @Override
    public synchronized void onTestFailure(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        System.out.println("<<<<<<   "+methodName+" Failed!!  >>>>>>>>>>>");
        test.get().fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromBase64String(takeScreenshot(result.getMethod().getMethodName()),result.getMethod().getMethodName()).build());
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }
    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        System.out.println("<<<<<<   "+methodName+" Skipped!!  >>>>>>>>>>>");
        test.get().skip(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromBase64String(takeScreenshot(result.getMethod().getMethodName()), result.getMethod().getMethodName()).build());
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

}