package com.example.excitingform;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

// page_url = http://localhost:8000/signup
public class ExcitingForm {
    @FindBy(css = "[data-test = 'name']")
    public WebElement nameInput;

    @FindBy(css = "#name-error")
    public WebElement nameError;

    @FindBy(css = "[data-test = 'email']")
    public WebElement emailInput;

    @FindBy(css = "#email-error")
    public WebElement emailError;

    @FindBy(css = "[data-test = 'pwd']")
    public WebElement pwdInput;

    @FindBy(css = "[data-test = 'confirm-pwd']")
    public WebElement confirmPwdInput;

    @FindBy(css = "#password-error")
    public WebElement passwordError;

    @FindBy(css = "[data-test = 'signup-btn']")
    public WebElement signupBtnButton;

    // No page elements added

    public ExcitingForm(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}