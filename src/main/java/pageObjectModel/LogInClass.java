package pageObjectModel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LogInClass extends BasePage{

    @FindBy(id = "user-name")
    WebElement userName ;

    @FindBy(id = "password")
    WebElement password ;

    @FindBy(id = "login-button")
    WebElement logInButton ;

    @FindBy(xpath = "//div[@class='app_logo']")
    WebElement appLogo ;


    public LogInClass(WebDriver driver) {
        super(driver);
    }

  public  void setUserName (String userDetails){
        userName.sendKeys(userDetails);
   }

   public void setPassword(String pwd){
        password.sendKeys(pwd);
   }

   public void clickLoginButton(){
        logInButton.click();
   }

   public String loginStatus(){
String appLogoDetails = appLogo.getText();
       return appLogoDetails;
   }

}
