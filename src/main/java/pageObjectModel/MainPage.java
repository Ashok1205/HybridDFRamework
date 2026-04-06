package pageObjectModel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends BasePage{

    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    WebElement addToCartBag ;
@FindBy (xpath = "//span[@class='shopping_cart_badge']")
WebElement clickCart ;

    public MainPage(WebDriver driver) {
        super(driver);
    }


    public void addBag(){
        addToCartBag.click();
    }

    public  void clickAddToCart(){
        clickCart.click();
    }
}
