import org.openqa.selenium.WebElement;

public class Login {
    WebElement emailInput;
    WebElement passwordInput;
    WebElement loginButton;

    public void login(String email, String password) {
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        loginButton.click();
    }

}
