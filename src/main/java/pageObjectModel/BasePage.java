package pageObjectModel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
    WebDriver driver ;
     BasePage(WebDriver driver){
         PageFactory.initElements(driver,this);
this.driver = driver ;
    }
}
