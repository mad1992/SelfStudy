package com.euka.tests;

import com.euka.pages.*;
import com.euka.utils.PropertyFileReader;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

public class TC01_StudentEnrollmentTest {
    private LoginPage loginPage;
    private LandingPage landingPage;
    private ManageProgramsPage manageProgramsPage;
    private NameSelectionPage nameSelectionPage;
    private GradeSelectionPage gradeSelectionPage;
    private EnrolmentConfirmationPage enrolmentConfirmationPage;
    private TermSelectionPage termSelectionPage;
    private ProgressPage progressPage;
    private SoftAssert softAssert;
    private String url;
    private String browser;
    private String isHeadless;
    private String isRunRemotely;
    private String userName;
    private String password;
    private String studentFirstName;
    private String studentLastName;
    private String grade;
    private String term;


    @BeforeClass
    public void setup() {
        //Get Config file data
        PropertyFileReader propertyFileReader = new PropertyFileReader();
        url = propertyFileReader.getProperty("url");
        browser = propertyFileReader.getProperty("browser");
        isHeadless = propertyFileReader.getProperty("isHeadless");
        isRunRemotely = propertyFileReader.getProperty("isRunRemotely");
        userName = propertyFileReader.getProperty("username");
        password = propertyFileReader.getProperty("password");
        studentFirstName = propertyFileReader.getProperty("studentFirstName");
        studentLastName = propertyFileReader.getProperty("studentLastName");
        grade = propertyFileReader.getProperty("grade");
        term = propertyFileReader.getProperty("term");

        //Initialize Browser & navigate to URL
        BasePage.initializeBrowser(browser, Boolean.valueOf(System.getProperty("isHeadless", isHeadless)), Boolean.valueOf(System.getProperty("isRunRemotely", isRunRemotely)));
        BasePage.createContext(Boolean.valueOf(System.getProperty("isRunRemotely", isRunRemotely)));
        BasePage.navigateToUrl(url);

        //Initialize Pages
        loginPage = new LoginPage();
        landingPage = new LandingPage();
        manageProgramsPage = new ManageProgramsPage();
        nameSelectionPage = new NameSelectionPage();
        gradeSelectionPage = new GradeSelectionPage();
        enrolmentConfirmationPage = new EnrolmentConfirmationPage();
        termSelectionPage = new TermSelectionPage();
        progressPage = new ProgressPage();
        softAssert = new SoftAssert();
    }

    @BeforeMethod
    public void login() {
        //Log in to the Application and verify the login is success
        loginPage.login(userName, password);
        Assert.assertTrue(landingPage.verifyWelcomeText(), "<<< Log in failed >>>");
    }

    @Test(priority = 1)//xxx  sdf
    public void studentEnrollmentTest() {
        //Click Manage Programs
        landingPage.clickManagePrograms();

        //Close the Rating popup if it is visible
        manageProgramsPage.closePopup();

        //Click 1st complete enrolment button
        manageProgramsPage.clickCompleteEnrolment();

        //Enter First Name Last name and click Next
        nameSelectionPage.enterName(studentFirstName, studentLastName);
        nameSelectionPage.clickNext();

        //Select Grade & click Next
        gradeSelectionPage.selectGrade(grade);
        gradeSelectionPage.clickNext();

        //Select Term & Click Next
        termSelectionPage.selectTerm(term);
        termSelectionPage.clickNext();

        //Click Next in progress page
        progressPage.clickNext();

        //Get Student details from confirmation page
        String studentName = enrolmentConfirmationPage.getStudentName();
        String startingCourse = enrolmentConfirmationPage.getStartingCourse();

        String[] values = startingCourse.split(",\\s*");

        //Verify student details
        softAssert.assertEquals(studentName, studentFirstName + " " + studentLastName, "First Name Incorrect");
        softAssert.assertEquals(values[0], grade, "Grade Incorrect");
        softAssert.assertEquals(values[1], term, "Term Incorrect");

        softAssert.assertAll();
    }

    @AfterMethod
    public void tearDown() {
        BasePage.closeBrowser();
    }
}
