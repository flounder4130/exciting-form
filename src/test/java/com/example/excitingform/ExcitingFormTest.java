package com.example.excitingform;

import com.codeborne.selenide.Selenide;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.net.BindException;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

public class ExcitingFormTest {

    private static final int PORT = 8000;
    private static final String DEFAULT_NAME = "Full Name";
    private static final String DEFAULT_EMAIL = "zenia_ellender7@served.dz";
    private static final String DEFAULT_PWD = "U15QqlAz3mENUrZ6QY";
    static HttpServer server;
    static ExcitingForm excitingForm;

    @BeforeAll
    static void setup() throws Exception {
        try {
            server = Server.create(PORT);
            server.start();
        } catch (BindException e) {
            // server already running?
        }
        open(String.format("http://localhost:%d/signup", PORT));
        excitingForm = new ExcitingForm(Selenide.webdriver().object());
    }

    @BeforeEach
    void reset() {
        excitingForm.nameInput.clear();
        excitingForm.emailInput.clear();
        excitingForm.pwdInput.clear();
        excitingForm.confirmPwdInput.clear();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/names-emails.csv")
    void dictionaryNames(String fullName, String _email) {
        excitingForm.nameInput.sendKeys(fullName);
        excitingForm.emailInput.sendKeys(DEFAULT_EMAIL);
        excitingForm.pwdInput.sendKeys(DEFAULT_PWD);
        excitingForm.confirmPwdInput.sendKeys(DEFAULT_PWD);
        excitingForm.signupBtnButton.click();
        assertFalse(excitingForm.nameError.isDisplayed());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/names-with-digits.csv")
    void namesWithDigits(String fullName, String _email) {
        excitingForm.nameInput.sendKeys(fullName);
        excitingForm.emailInput.sendKeys(DEFAULT_EMAIL);
        excitingForm.pwdInput.sendKeys(DEFAULT_PWD);
        excitingForm.confirmPwdInput.sendKeys(DEFAULT_PWD);
        excitingForm.signupBtnButton.click();
        assertTrue(excitingForm.nameError.isDisplayed());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/names-emails.csv")
    void validEmails(String _fullName, String email) {
        excitingForm.nameInput.sendKeys(DEFAULT_NAME);
        excitingForm.emailInput.sendKeys(email);
        excitingForm.pwdInput.sendKeys(DEFAULT_PWD);
        excitingForm.confirmPwdInput.sendKeys(DEFAULT_PWD);
        excitingForm.signupBtnButton.click();
        assertFalse(excitingForm.emailError.isDisplayed());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/passwords.csv")
    void matchingPasswords(String pwd) {
        excitingForm.nameInput.sendKeys(DEFAULT_NAME);
        excitingForm.emailInput.sendKeys(DEFAULT_EMAIL);
        excitingForm.pwdInput.sendKeys(pwd);
        excitingForm.confirmPwdInput.sendKeys(pwd);
        excitingForm.signupBtnButton.click();
        assertFalse(excitingForm.passwordError.isDisplayed());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/passwords.csv")
    void differentPasswords(String pwd) {
        excitingForm.nameInput.sendKeys(DEFAULT_NAME);
        excitingForm.emailInput.sendKeys(DEFAULT_EMAIL);
        excitingForm.pwdInput.sendKeys(pwd);
        excitingForm.confirmPwdInput.sendKeys(pwd + "1");
        excitingForm.signupBtnButton.click();
        assertTrue(excitingForm.passwordError.isDisplayed());
    }

    @AfterAll
    static void tearDown() {
        if (server != null) {
            server.stop(0);
        }
    }
}
